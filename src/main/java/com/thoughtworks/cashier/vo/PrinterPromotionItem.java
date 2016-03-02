package com.thoughtworks.cashier.vo;

import java.math.BigDecimal;

/**
 * 小票打印机结算优惠item对象<br>
 * 每个实例代表本次结算的所有优惠活动中的明细商品个体
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PrinterPromotionItem {

	/**
	 * 优惠商品
	 */
	private Good good;
	
	/**
	 * 不计费数量
	 */
	private BigDecimal freeAmount = BigDecimal.ZERO;

	/**
	 * 折扣扣减金额
	 */
	private BigDecimal discountMoney = BigDecimal.ZERO;

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	public BigDecimal getFreeAmount() {
		return freeAmount;
	}

	public void setFreeAmount(BigDecimal freeAmount) {
		this.freeAmount = freeAmount;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}


}
