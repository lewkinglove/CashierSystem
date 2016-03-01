package com.thoughtworks.cashier.vo;

/**
 * 营销活动目标
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PromotionTarget {
	/**
	 * 组合主键;<br>
	 * 外键: promotion.id;
	 */
	private int proId;
	
	/**
	 * 优惠目标类型;<br>
	 * 枚举: <br>
	 * GOOD_TYPE:部分类型商品参与活动; <br>
	 * SPEC_GOOD: 指定商品参与活动;<br>
	 */
	private int proTarget;

	/**
	 * 优惠目标ID; <br>
	 * 外键; 根据target_type不同, 可能为good.id或者good_type.id
	 */
	private String targetType;

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}

	public int getProTarget() {
		return proTarget;
	}

	public void setProTarget(int proTarget) {
		this.proTarget = proTarget;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

}
