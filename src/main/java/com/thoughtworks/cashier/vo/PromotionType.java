package com.thoughtworks.cashier.vo;


/**
 * 营销活动类型
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PromotionType {
	/**
	 * 主键;自增ID;
	 */
	private int id;
	
	/**
	 * 营销活动类型编码;<br>
	 * 每一类营销活动和系统中的活动处理逻辑块儿对应;
	 */
	private String code;
	
	/**
	 * 营销活动类型名称
	 */
	private String name;

	/**
	 * 营销活动说明; 必填; 主要包含技术类说明数据, 如活动类型参数数据结构等;
	 */
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
