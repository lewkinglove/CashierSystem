package com.thoughtworks.cashier.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.NotImplementedException;

import com.thoughtworks.cashier.common.db.BaseDao;
import com.thoughtworks.cashier.vo.GoodType;

/**
 * 商品类型表对应的数据访问对象
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class GoodTypeDAO extends BaseDao<GoodType>{
	
	/**
	 * 表名
	 */
	public static final String TABLE_NAME = "good_type";
	
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
    public boolean save(GoodType model) throws Exception {
	    throw new NotImplementedException();
    }

	@Override
    public GoodType mappingRow(ResultSet rs) throws SQLException {
		GoodType item = new GoodType();
	    item.setId(rs.getInt("id"));
	    item.setName(rs.getString("name"));
	    item.setDescription(rs.getString("description"));
	    item.setStatus(rs.getInt("status"));
	    item.setLastUpdate(rs.getTimestamp("last_update"));
	    item.setCreateTime(rs.getTimestamp("create_time"));
	    return item;
    }

}
