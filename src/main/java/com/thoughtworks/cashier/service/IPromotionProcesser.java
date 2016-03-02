package com.thoughtworks.cashier.service;

import java.util.Map;

import com.thoughtworks.cashier.vo.PrinterItem;
import com.thoughtworks.cashier.vo.PrinterPromotion;

/**
 * 营销活动处理器
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public interface IPromotionProcesser {
	
	/**
	 * 获取当前处理器的类型识别码
	 * @return
	 */
	String getPromotionTypeCode();
	
	/**
	 * 处理结帐数据
	 * @param ppromotion
	 * @param items
	 * @throws Exception
	 */
	void process(PrinterPromotion ppromotion, Map<String, PrinterItem> items) throws Exception;
	
}
