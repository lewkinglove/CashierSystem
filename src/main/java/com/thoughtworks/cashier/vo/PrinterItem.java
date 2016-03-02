package com.thoughtworks.cashier.vo;

import java.math.BigDecimal;

/**
 * 小票打印机结算项目<br>
 * 每个结算项目代表一件结算商品.
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PrinterItem {

	/**
	 * 结算商品
	 */
	private Good good;

	/**
	 * 总数量<br>
	 * 鉴于生鲜果蔬类可能非整, 所以使用Decimal
	 */
	private BigDecimal amount = BigDecimal.ZERO;

	/**
	 * 小计金额<br>
	 * 此金额为优惠处理器处理过的金额.
	 */
	private BigDecimal subtotal;
	
	/**
	 * 额外信息<br>
	 * 用于优惠处理器添加额外打印信息使用
	 */
	private String extraMessage;

	public Good getGood() {
		return good;
	}

	public void setGood(Good good) {
		this.good = good;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSubtotal() {
		if(subtotal==null)
			subtotal = getGood().getPrice().multiply(getAmount());
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getExtraMessage() {
		return extraMessage;
	}

	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}

	/**
	 * 获取总计金额, 不包含优惠
	 * @return
	 */
	public BigDecimal getTotalMoney() {
		if(this.getGood()==null)
			return BigDecimal.ZERO;
		return this.getGood().getPrice().multiply(this.getAmount());
	}

}
