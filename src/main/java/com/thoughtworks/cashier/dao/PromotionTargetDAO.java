package com.thoughtworks.cashier.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.NotImplementedException;

import com.thoughtworks.cashier.common.db.BaseDao;
import com.thoughtworks.cashier.vo.PromotionTarget;

/**
 * 营销活动目标表对应的数据访问对象
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PromotionTargetDAO extends BaseDao<PromotionTarget> {

	/**
	 * 表名
	 */
	public static final String TABLE_NAME = "promotion_target";

	/**
	 * 主键列名
	 */
	public static final String PK_COLUMN_NAME = "id";

	@Override
	public String getPkName() {
		return PK_COLUMN_NAME;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public boolean save(PromotionTarget model) throws Exception {
		throw new NotImplementedException();
	}

	@Override
	public PromotionTarget mappingRow(ResultSet rs) throws SQLException {
		PromotionTarget item = new PromotionTarget();
		item.setProId(rs.getInt("pro_id"));
		item.setTargetType(rs.getString("target_type"));
		item.setProTarget(rs.getInt("pro_target"));
		return item;
	}

}
