package com.thoughtworks.cashier.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.thoughtworks.cashier.common.db.exception.DataSourceNotFoundException;
import com.thoughtworks.cashier.common.util.JSONUtil;
import com.thoughtworks.cashier.vo.Good;
import com.thoughtworks.cashier.vo.PrinterItem;
import com.thoughtworks.cashier.vo.PrinterPromotion;
import com.thoughtworks.cashier.vo.PrinterPromotionItem;
import com.thoughtworks.cashier.vo.Promotion;

/**
 * 收银小票打印机
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class CashierTicketPrinter {
	private static String SHOP_PRINT_NAME;

	private GoodService goodService;
	private PromotionService promotionService;

	private static CashierTicketPrinter instance = new CashierTicketPrinter();

	private CashierTicketPrinter() {
		this.goodService = GoodService.getInstance();
		this.promotionService = PromotionService.getInstance();
	}

	public static CashierTicketPrinter getInstance() {
		return instance;
	}

	public static String getShopPrintName() {
		return SHOP_PRINT_NAME;
	}

	public static void setShopPrintName(String shopName) {
		SHOP_PRINT_NAME = shopName;
	}

	//@formatter:off
	/**
	 * 根据扫描机扫到的条码数据, 进行结算<br>
	 * <pre>
	 * 处理逻辑:
	 * 条码List去重并迭加总数转换为PrinterItem
	 * 循环所有的PrinterItem, 生成关联的所有营销活动信息
	 * 循环所有的营销活动数据, 逐个从营销活动处理优惠 
	 * 	将结算明细(PrinterItem)传入到营销活动的Factory. 
	 * 	Factory根据营销类型返回营销活动处理器, 处理器处理结算项目, 并记录优惠数据. 
	 * 
	 * 打印小票头部 
	 * 循环PrinterItem, 打印单个商品信息 -->并叠加计算结账总价 
	 * 循环PrinterPromotion, 打印营销活动信息 -->并叠加计算优惠总数 
	 * 打印小票结算区.
	 * </pre>
	 * @param settlement 结算的JSON格式条码数据
	 * @return
	 * @throws Exception
	 */
	 //@formatter:on
	public String getPrintContent(String settlement) throws Exception {
		if (settlement == null || settlement.trim().length() == 0)
			throw new IllegalArgumentException("结算JSON数据不能为空");

		ArrayList<String> codes = JSONUtil.unmarshal(settlement, ArrayList.class);
		if (codes == null || codes.size() == 0)
			throw new IllegalArgumentException("结算JSON数据不包含有效信息");

		// 转换成结算商品数据
		Map<String, PrinterItem> items = parsePrinterItem(codes);

		// 将结算商品数据, 转换成营销活动的优惠数据
		Map<Integer, PrinterPromotion> promotions = parsePromotionData(items);

		// 将上面两步生成的数据, 交给优惠处理器进行处理
		handlePromotion(items, promotions);

		// TODO 对优惠处理器处理过的最终数据进行格式化输出
		StringBuilder sb = new StringBuilder();
		sb.append("***<").append(SHOP_PRINT_NAME).append(">购物清单***\r\n");

		// 处理结算明细区的数据, 并接收结算总金额
		BigDecimal totalMoney = handlerPrinterItem(items, sb);

		// 处理优惠明细区的文本数据, 并接收优惠总金额
		BigDecimal totalSaveMoney = handlerPrinterPromotion(promotions, sb);

		// 打印底部结算区
		sb.append("----------------------\r\n");
		sb.append("总计：").append(totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()).append("(元)\r\n");
		sb.append("节省：").append(totalSaveMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()).append("(元)\r\n");
		sb.append("**********************\r\n");

		return sb.toString();
	}

	private BigDecimal handlerPrinterPromotion(Map<Integer, PrinterPromotion> promotions, StringBuilder sb) {
		BigDecimal totalSaveMoney = BigDecimal.ZERO;

		StringBuilder resultText = new StringBuilder();
		if (promotions.size() > 0) {
			Iterator<Integer> itor = promotions.keySet().iterator();
			while (itor.hasNext()) {
				StringBuilder sbPromotionItem = new StringBuilder();
				
				PrinterPromotion ppromotion = promotions.get(itor.next());
				List<PrinterPromotionItem> proItems = ppromotion.getItems();
				int proItemsSize = proItems.size();
				for (int i = 0; i < proItemsSize; i++) {
					PrinterPromotionItem item = proItems.get(i);
					
					//如果当前商品没有优惠数量, 也没有折扣金额, 则直接跳过
					if(item.getFreeAmount().compareTo(BigDecimal.ZERO)==0 && item.getDiscountMoney().compareTo(BigDecimal.ZERO)==0)
						continue;
					
					//叠加计算总优惠金额
					totalSaveMoney = totalSaveMoney.add(item.getDiscountMoney());
					totalSaveMoney = totalSaveMoney.add(item.getGood().getPrice().multiply(item.getFreeAmount()));
					
					// 如果不独立打印在优惠区域, 则不继续组装输出部分
					if (ppromotion.isIndependentPrint() == false)
						continue;

					// 格式化输出优惠活动明细
					sbPromotionItem.append("名称：").append(item.getGood().getName());
					// 如果有优惠数量, 则输出优惠数量部分
					if (item.getFreeAmount().compareTo(BigDecimal.ZERO) == 1) {
						sbPromotionItem.append("，数量：").append(item.getFreeAmount().setScale(1, BigDecimal.ROUND_HALF_UP).toPlainString()).append(item.getGood().getCountUnit());
					}
					// 如果有折扣金额, 则输出折扣部分
					if (item.getDiscountMoney().compareTo(BigDecimal.ZERO) == 1) {
						sbPromotionItem.append("，折扣优惠：").append(item.getDiscountMoney().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()).append("元");
					}
					sbPromotionItem.append("\r\n");
				}
				
				//组装本次要打印的营销活动所有明细
				if (ppromotion.isIndependentPrint() && sbPromotionItem.length()>0){
					resultText.append(ppromotion.getPromotion().getName()).append("商品:\r\n").append(sbPromotionItem.toString());
				}
			}
		}
		if (resultText.length() > 0) {
			sb.append("----------------------\r\n").append(resultText.toString());
		}
		return totalSaveMoney;
	}

	private BigDecimal handlerPrinterItem(Map<String, PrinterItem> items, StringBuilder sb) {
		BigDecimal totalMoney = BigDecimal.ZERO;
		Iterator<String> itor = items.keySet().iterator();
		while (itor.hasNext()) {
			PrinterItem item = items.get(itor.next());
			Good good = item.getGood();
			sb.append("名称：").append(good.getName()).append("，数量：").append(item.getAmount()).append(item.getGood().getCountUnit()).append("，单价：").append(good.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()).append("(元)，小计：").append(item.getSubtotal().setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()).append("(元)").append(item.getExtraMessage() == null ? "" : "," + item.getExtraMessage()).append("\r\n");

			totalMoney = totalMoney.add(item.getSubtotal());
		}
		return totalMoney;
	}

	/**
	 * 处理待结算商品的优惠信息
	 * 
	 * @param items
	 * @param promotions
	 * @throws Exception
	 */
	private void handlePromotion(Map<String, PrinterItem> items, Map<Integer, PrinterPromotion> promotions) throws Exception {
		Iterator<Integer> itor = promotions.keySet().iterator();
		while (itor.hasNext()) {
			Integer promotionId = itor.next();
			PrinterPromotion ppromotion = promotions.get(promotionId);

			// 获取当前营销活动的类型识别码
			String proTypeCode = promotionService.getPromotionTypeCode(ppromotion.getPromotion());

			// 将数据交给优惠处理器进行处理
			PromotionFactory.getProcesser(proTypeCode).process(ppromotion, items);
		}
	}

	
	/**
	 * 将结算商品数据, 转换成营销活动的优惠数据
	 * 
	 * @param items
	 * @return
	 * @throws SQLException
	 * @throws DataSourceNotFoundException
	 */
	private Map<Integer, PrinterPromotion> parsePromotionData(Map<String, PrinterItem> items) throws SQLException, DataSourceNotFoundException {
		Map<Integer, PrinterPromotion> promotions = new HashMap<Integer, PrinterPromotion>();
		Iterator<String> itor = items.keySet().iterator();
		while (itor.hasNext()) {
			String code = itor.next();
			PrinterItem item = items.get(code);

			Promotion suitPromotion = promotionService.getGoodsPromotion(item.getGood());
			if (suitPromotion == null)
				continue;

			// 本次新增的优惠项目
			PrinterPromotionItem promotionItem = new PrinterPromotionItem();
			promotionItem.setGood(item.getGood());

			PrinterPromotion printerPromotion = promotions.get(suitPromotion.getId());
			if (printerPromotion == null) {
				// 初始化PrinterPromotion
				printerPromotion = new PrinterPromotion();
				printerPromotion.setPromotion(suitPromotion);

				// 将当前优惠项目添加到PrinterPromotion Map中
				promotions.put(suitPromotion.getId(), printerPromotion);
			}
			// 添加优惠商品项目
			printerPromotion.addItems(promotionItem);
		}
		return promotions;
	}

	
	/**
	 * 将条码数据转换为PrinterItem, 去重并迭加总数.
	 * 
	 * @param codes
	 * @return
	 * @throws Exception
	 */
	private Map<String, PrinterItem> parsePrinterItem(ArrayList<String> codes) throws Exception {
		Map<String, PrinterItem> items = new HashMap<String, PrinterItem>();

		int codesSize = codes.size();
		for (int i = 0; i < codesSize; i++) {
			// 原始条码, 可能是伪码
			String srcCode = codes.get(i);

			// 真实商品条码
			String realCode = goodService.getRealCode(codes.get(i));

			// 处理商品数量
			BigDecimal amount = null;
			if (realCode.equalsIgnoreCase(srcCode)) {
				amount = BigDecimal.ONE;
			} else {
				// 伪码的数量处理
				amount = new BigDecimal(srcCode.substring(srcCode.lastIndexOf('-') + 1));
			}

			PrinterItem item = items.get(realCode);
			if (item == null) {
				item = new PrinterItem();
				item.setGood(goodService.getByCode(realCode));
				item.setAmount(amount);
				items.put(realCode, item);
				continue;
			}

			// 对商品数量进行累加处理
			item.setAmount(item.getAmount().add(amount));
		}

		return items;
	}

}
