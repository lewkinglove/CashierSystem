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

	public String getPrintContent(String settlement) throws Exception {
		if (settlement == null || settlement.trim().length() == 0)
			throw new IllegalArgumentException("结算JSON数据不能为空");

		ArrayList<String> codes = JSONUtil.unmarshal(settlement, ArrayList.class);
		if (codes == null || codes.size() == 0)
			throw new IllegalArgumentException("结算JSON数据不包含有效信息");
		//@formatter:off
		/**
		 * 转换条码List为PrinterItem, 去重并迭加总数. 
		 * 循环所有的PrinterItem 
		 * 	获取对应的营销活动, 如果有的话. 
		 * 	传入当前PrinterItem到营销活动的Dispatcher. 
		 * 	Dispatcher转发至指定营销活动处理函数, 并返回PrinterPromotion优惠项目. 
		 * 	PrinterPromotion包含不计费数量, 折扣扣减金额.等信息
		 * 开始打印小票头部 
		 * 循环PrinterItem, 打印单个商品信息 -->并叠加计算结账总价 
		 * 循环PrinterPromotion, 打印营销活动信息 -->并叠加计算优惠总数 
		 * 打印小票结算区.
		 */
		//@formatter:on
		
		//转换成结算商品数据
		Map<String, PrinterItem> items = parsePrinterItem(codes);
		System.out.println("-----------------------------");
		System.out.println(JSONUtil.marshal(items));

		//将结算商品数据, 转换成营销活动的优惠数据
		Map<Integer, PrinterPromotion> promotions = parsePromotionData(items);
		System.out.println("-----------------------------");
		System.out.println(JSONUtil.marshal(promotions));
		
		//TODO 将上面两步生成的数据, 交给优惠处理器进行处理
		
		
		//TODO 对最终数据进行格式化输出
		

		return "";
	}

	/**
	 * 将结算商品数据, 转换成营销活动的优惠数据
	 * @param items
	 * @return
	 * @throws SQLException
	 * @throws DataSourceNotFoundException
	 */
	private Map<Integer, PrinterPromotion> parsePromotionData(Map<String, PrinterItem> items) throws SQLException, DataSourceNotFoundException {
	    Map<Integer, PrinterPromotion> promotions = new HashMap<Integer, PrinterPromotion>();
		Iterator<String> itor = items.keySet().iterator();
		while(itor.hasNext()){
			String code = itor.next();
			PrinterItem item = items.get(code);
			
			Promotion suitPromotion = promotionService.getGoodsPromotion(item.getGood());
			if(suitPromotion==null)
				continue;
			
			//本次新增的优惠项目
			PrinterPromotionItem promotionItem = new PrinterPromotionItem();
			promotionItem.setGood(item.getGood());
			
			PrinterPromotion printerPromotion = promotions.get(suitPromotion.getId());
			if(printerPromotion==null){
				//初始化PrinterPromotion
				printerPromotion = new PrinterPromotion();
				printerPromotion.setPromotion(suitPromotion);
				
				//将当前优惠项目添加到PrinterPromotion Map中
				promotions.put(suitPromotion.getId(), printerPromotion);
			}
			//添加优惠商品项目
			printerPromotion.addItems(promotionItem);
			
			System.out.println(JSONUtil.marshal(item.getGood()));
			System.out.println(JSONUtil.marshal(suitPromotion));
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
