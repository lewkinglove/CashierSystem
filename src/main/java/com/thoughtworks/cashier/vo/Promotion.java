package com.thoughtworks.cashier.vo;

import java.util.Date;

/**
 * 营销活动
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class Promotion {
	/**
	 * 主键;自增ID;
	 */
	private int id;

	/**
	 * 营销活动名称
	 */
	private String name;

	/**
	 * 活动开始时间
	 */
	private Date startTime;

	/**
	 * 活动结束时间
	 */
	private Date endTime;

	/**
	 * 优惠是否适用于所有商品; 0: 否; 1: 是;
	 */
	private int suitAll;

	/**
	 * 活动优先级别, 用于对活动进行排他的优先选择; 优先级越高, 则优先被选中; 建议使用创建日期的数字格式;
	 */
	private int priority;

	/**
	 * 营销活动类型; 外键:promotion_type.id
	 */
	private int promotionType;

	/**
	 * 营销活动类型参数; JSON格式化数据;程序生成;
	 */
	private String promotionTypeArgs;

	/**
	 * 状态;1:正常;2:禁用;3已删除;
	 */
	private int status;

	/**
	 * 更新时间
	 */
	private Date lastUpdate;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getSuitAll() {
		return suitAll;
	}

	public void setSuitAll(int suitAll) {
		this.suitAll = suitAll;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(int promotionType) {
		this.promotionType = promotionType;
	}

	public String getPromotionTypeArgs() {
		return promotionTypeArgs;
	}

	public void setPromotionTypeArgs(String promotionTypeArgs) {
		this.promotionTypeArgs = promotionTypeArgs;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
