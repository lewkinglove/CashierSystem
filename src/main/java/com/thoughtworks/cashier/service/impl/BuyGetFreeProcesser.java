package com.thoughtworks.cashier.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.thoughtworks.cashier.common.util.JSONUtil;
import com.thoughtworks.cashier.service.IPromotionProcesser;
import com.thoughtworks.cashier.vo.PrinterItem;
import com.thoughtworks.cashier.vo.PrinterPromotion;
import com.thoughtworks.cashier.vo.PrinterPromotionItem;
import com.thoughtworks.cashier.vo.Promotion;

public class BuyGetFreeProcesser implements IPromotionProcesser {

	public final String TYPE_CODE = "BUY_GET_FREE";

	@Override
	public String getPromotionTypeCode() {
		return TYPE_CODE;
	}

	@Override
	public void process(PrinterPromotion ppromotion, Map<String, PrinterItem> items) throws Exception {
		// 设置需要独立打印在活动区
		ppromotion.setIndependentPrint(true);

		Promotion curPromotion = ppromotion.getPromotion();

		// 解析优惠参数
		Map<String, Object> promotionArgs = (Map<String, Object>) JSONUtil.unmarshal(curPromotion.getPromotionTypeArgs(), Map.class);

		// 解析处理 买满数量 参数
		BigDecimal buyCountValue = parseArgBuyCount(curPromotion, promotionArgs);
		// 如果buyCountValue小于等于0, 则报错
		if (buyCountValue.compareTo(BigDecimal.ZERO) == -1 || buyCountValue.compareTo(BigDecimal.ZERO) == 0)
			throw new Error("BUY_GET_FREE营销活动处理器发现营销活动[" + curPromotion.getId() + "]的优惠类型参数设置有误, 买满数量必须大于0. [" + buyCountValue + "]");

		// 解析处理 赠送数量 参数
		BigDecimal freeCountValue = parseArgFreeCount(curPromotion, promotionArgs);
		// 如果freeCountValue小于等于0, 则报错
		if (freeCountValue.compareTo(BigDecimal.ZERO) == -1 || freeCountValue.compareTo(BigDecimal.ZERO) == 0)
			throw new Error("BUY_GET_FREE营销活动处理器发现营销活动[" + curPromotion.getId() + "]的优惠类型参数设置有误, 赠送数量必须大于0. [" + freeCountValue + "]");

		// 解析处理 最大赠送数量 参数
		BigDecimal maxFreeCountValue = parseArgMaxFreeCount(curPromotion, promotionArgs);
		// 如果maxFreeCountValue小于等于0 或者 小于赠送数量, 则报错
		if (maxFreeCountValue.compareTo(BigDecimal.ZERO) == -1 || maxFreeCountValue.compareTo(BigDecimal.ZERO) == 0 || maxFreeCountValue.compareTo(freeCountValue) == -1)
			throw new Error("BUY_GET_FREE营销活动处理器发现营销活动[" + curPromotion.getId() + "]的优惠类型参数设置有误, 最大赠送数量必须大于0 且不小于赠送数量. [" + maxFreeCountValue + "]");

		// 对优惠进行处理
		List<PrinterPromotionItem> promotionItemList = ppromotion.getItems();
		int promotionItemListSize = promotionItemList.size();
		for (int i = 0; i < promotionItemListSize; i++) {
			//@formatter:off
			/**
			 * 拿到当前的优惠项目
			 * 找到PrinterItem对应的结算项目 
			 * 进行优惠记录处理 ->PrinterItem 
			 * 进行优惠记录处理 ->PrinterPromotionItem
			 */
			//@formatter:on
			PrinterPromotionItem pproItem = promotionItemList.get(i);
			PrinterItem printerItem = items.get(pproItem.getGood().getCode());

			// 获取优惠前数量
			BigDecimal amount = printerItem.getAmount();

			// 计算免费赠送数量
			// 计算公式: 总数量 / (买满数量+赠送数量) ->取整
			// 备注: 此处暂不考虑散称类商品赠送非整数数量(如: 买慢1.5kg, 送0.25kg等 )的问题
			BigDecimal freeCount = amount.divideToIntegralValue(buyCountValue.add(freeCountValue));
			// 如果赠送数量大于最大赠送数量, 则只赠送最大赠送数量
			freeCount = freeCount.compareTo(maxFreeCountValue)==1 ? maxFreeCountValue : freeCount;

			// 获取优惠前总金额
			BigDecimal totalMoney = printerItem.getTotalMoney();
			// 计算小计金额; 公式: 原始总金额 - 免费数量*商品单价
			BigDecimal subtotalMoney = totalMoney.subtract(freeCount.multiply(printerItem.getGood().getPrice()));

			// 将小计金额设置到PrinterItem上
			printerItem.setSubtotal(subtotalMoney);

			//如果当前优惠涉及商品, 实际没有享受优惠, 则从优惠的list中移除
			if(freeCount.compareTo(BigDecimal.ZERO)!=1){
				promotionItemList.remove(pproItem);
				i--;	//处理索引前移
				promotionItemListSize = promotionItemList.size();	//重新获取list大小
				continue;
			}
			
			// 记录赠送数量到PrinterPromotionItem上
			pproItem.setFreeAmount(freeCount);
		}
	}

	/**
	 * 解析处理 买满数量 参数
	 * 
	 * @param curPromotion
	 * @param promotionArgs
	 * @return
	 * @throws Error
	 */
	private BigDecimal parseArgBuyCount(Promotion curPromotion, Map<String, Object> promotionArgs) throws Error {
		Object buyCountObj = promotionArgs.get("buy_count");
		if (buyCountObj == null)
			throw new Error("BUY_GET_FREE营销活动处理器发现营销活动[" + curPromotion.getId() + "]的优惠类型参数设置不正确, 缺少buy_count参数.[" + curPromotion.getPromotionTypeArgs() + "]");
		BigDecimal buyCountValue = new BigDecimal(buyCountObj.toString());
		return buyCountValue;
	}

	/**
	 * 解析处理 赠送数量 参数
	 * @param curPromotion
	 * @param promotionArgs
	 * @return
	 * @throws Error
	 */
	private BigDecimal parseArgFreeCount(Promotion curPromotion, Map<String, Object> promotionArgs) throws Error {
		Object freeCountObj = promotionArgs.get("free_count");
		if (freeCountObj == null)
			throw new Error("BUY_GET_FREE营销活动处理器发现营销活动[" + curPromotion.getId() + "]的优惠类型参数设置不正确, 缺少free_count参数.[" + curPromotion.getPromotionTypeArgs() + "]");
		BigDecimal freeCountValue = new BigDecimal(freeCountObj.toString());
		return freeCountValue;
	}

	/**
	 * 解析处理 最大赠送数量 参数
	 * @param curPromotion
	 * @param promotionArgs
	 * @return
	 * @throws Error
	 */
	private BigDecimal parseArgMaxFreeCount(Promotion curPromotion, Map<String, Object> promotionArgs) throws Error {
		Object maxFreeCountObj = promotionArgs.get("max_free_count");
		if (maxFreeCountObj == null)
			throw new Error("BUY_GET_FREE营销活动处理器发现营销活动[" + curPromotion.getId() + "]的优惠类型参数设置不正确, 缺少max_free_count参数.[" + curPromotion.getPromotionTypeArgs() + "]");
		BigDecimal maxFreeCountValue = new BigDecimal(maxFreeCountObj.toString());
		return maxFreeCountValue;
	}

}
