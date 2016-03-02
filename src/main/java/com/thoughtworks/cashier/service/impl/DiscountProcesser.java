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

public class DiscountProcesser implements IPromotionProcesser {

	public final String TYPE_CODE = "DISCOUNT";

	@Override
	public String getPromotionTypeCode() {
		return TYPE_CODE;
	}

	@Override
	public void process(PrinterPromotion ppromotion, Map<String, PrinterItem> items) throws Exception {
		// 设置不独立打印在活动区
		ppromotion.setIndependentPrint(false);

		Promotion curPromotion = ppromotion.getPromotion();

		// 解析优惠类型参数, 获得折扣率
		Map<String, Object> promotionArgs = (Map<String, Object>) JSONUtil.unmarshal(curPromotion.getPromotionTypeArgs(), Map.class);
		Object discountValueObj = promotionArgs.get("discount_value");
		if (discountValueObj == null)
			throw new Error("DISCOUNT营销活动处理器发现营销活动[" + curPromotion.getId() + "]的优惠类型参数设置不正确[" + curPromotion.getPromotionTypeArgs() + "]");
		BigDecimal discountValue = new BigDecimal(discountValueObj.toString());
		// 如果discountValue不是介于0和一之间, 则报错
		if (false == (discountValue.compareTo(BigDecimal.ZERO) == 1 && discountValue.compareTo(BigDecimal.ONE) == -1))
			throw new Error("DISCOUNT营销活动处理器发现营销活动[" + curPromotion.getId() + "]的优惠类型参数设置有误, 折扣率必须介于0和1之间. [" + discountValue + "]");

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

			// 获取折扣前金额
			BigDecimal totalMoney = printerItem.getTotalMoney();
			// 计算折扣金额
			BigDecimal discountMoney = totalMoney.subtract(totalMoney.multiply(discountValue));

			// 将小计金额和折扣信息设置到PrinterItem上
			printerItem.setSubtotal(totalMoney.subtract(discountMoney));
			printerItem.setExtraMessage("节省" + discountMoney.setScale(2).toPlainString() + "(元)");

			// 记录优惠金额到PrinterPromotionItem上
			pproItem.setDiscountMoney(discountMoney);
		}
	}

}
