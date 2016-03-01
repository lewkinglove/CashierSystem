package com.thoughtworks.cashier.service;

import java.sql.SQLException;
import java.util.List;

import com.thoughtworks.cashier.common.db.exception.DataSourceNotFoundException;
import com.thoughtworks.cashier.dao.GoodDAO;
import com.thoughtworks.cashier.dao.PromotionDAO;
import com.thoughtworks.cashier.dao.PromotionTargetDAO;
import com.thoughtworks.cashier.dao.PromotionTypeDAO;
import com.thoughtworks.cashier.vo.Good;
import com.thoughtworks.cashier.vo.Promotion;

/**
 * 营销活动服务对象
 * 
 * @author liujing(lewkinglove@gmail.com)
 */
public class PromotionService {

	private GoodDAO goodDao;
	private PromotionDAO proDao;
	private PromotionTypeDAO proTypeDao;
	private PromotionTargetDAO proTargetDao;
	

	private static PromotionService instance = new PromotionService();

	private PromotionService() {
		this.goodDao = new GoodDAO();
		this.proDao = new PromotionDAO();
		this.proTypeDao = new PromotionTypeDAO();
		this.proTargetDao = new PromotionTargetDAO();
	}

	public static PromotionService getInstance() {
		return instance;
	}
	
	
	/**
	 * 获取商品当前参与的营销活动
	 * 
	 * @param good
	 * @return
	 * @throws SQLException
	 * @throws DataSourceNotFoundException
	 */
	public Promotion getGoodsPromotion(Good good) throws SQLException, DataSourceNotFoundException{
		/**
		 * 搜索条件:
		 * 	活动状态为正常, 且当前时间是活动时间内.
		 * 	适用于所有商品的活动, 或者适用于当前商品或所属商品类型的活动
		 *  如果有多条符合的活动, 则只取优先级最高的活动.
		 * -------------------------
		 * SELECT m.* FROM promotion m
		 * INNER JOIN promotion_target ptr ON m.id=ptr.pro_id
		 * WHERE m.status=1
		 * AND m.start_time<CURRENT_TIMESTAMP AND m.end_time>CURRENT_TIMESTAMP
		 * AND (m.suit_all=1 OR
		 * 	     ( (ptr.target_type='SPEC_GOOD' AND ptr.pro_target=1 ) OR (ptr.target_type='GOOD_TYPE' AND ptr.pro_target=2) )
		 *     )
		 * ORDER BY m.priority DESC LIMIT 1
		 */
		String sql = "SELECT m.* FROM promotion m " + 
				"INNER JOIN promotion_target ptr ON m.id=ptr.pro_id " + 
				"WHERE m.status=1 " + 
				"AND m.start_time<CURRENT_TIMESTAMP AND m.end_time>CURRENT_TIMESTAMP " + 
				"AND (m.suit_all=1 OR " + 
				"	( (ptr.target_type='SPEC_GOOD' AND ptr.pro_target=? ) OR (ptr.target_type='GOOD_TYPE' AND ptr.pro_target=?) ) " + 
				") ORDER BY m.priority DESC LIMIT 1";
		
		List<Promotion> promotions = proDao.executeListQuery(sql, proDao, good.getId(), good.getType());
		if(promotions!=null && promotions.size()>0)
			return promotions.get(0);
		return null;
	}
	
	
}
