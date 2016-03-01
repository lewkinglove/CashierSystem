package com.thoughtworks.cashier.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 小票打印机结算优惠对象<br>
 * 每个实例代表本次结算的所有同类优惠活动明细
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PrinterPromotion {
	
	/**
	 * 活动对象
	 */
	private Promotion promotion;

	/**
	 * 是否独立打印<br>
	 * 控制是否在商品结算区下面独立打印
	 */
	private boolean independentPrint;

	/**
	 * 所有的优惠商品明细对象
	 */
	private List<PrinterPromotionItem> items = new ArrayList<PrinterPromotionItem>();
	
	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public boolean isIndependentPrint() {
		return independentPrint;
	}

	public void setIndependentPrint(boolean independentPrint) {
		this.independentPrint = independentPrint;
	}

	public List<PrinterPromotionItem> getItems() {
		return items;
	}

	public void addItems(PrinterPromotionItem item) {
		this.items.add(item);
	}

}
