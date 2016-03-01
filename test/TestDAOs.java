import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.thoughtworks.cashier.common.db.exception.DataSourceNotFoundException;
import com.thoughtworks.cashier.common.util.DateUtil;
import com.thoughtworks.cashier.common.util.JSONUtil;
import com.thoughtworks.cashier.dao.GoodDAO;
import com.thoughtworks.cashier.dao.GoodTypeDAO;
import com.thoughtworks.cashier.dao.PromotionDAO;
import com.thoughtworks.cashier.dao.PromotionTargetDAO;
import com.thoughtworks.cashier.dao.PromotionTypeDAO;
import com.thoughtworks.cashier.vo.Good;
import com.thoughtworks.cashier.vo.GoodType;
import com.thoughtworks.cashier.vo.Promotion;
import com.thoughtworks.cashier.vo.PromotionTarget;
import com.thoughtworks.cashier.vo.PromotionType;


public class TestDAOs extends TestBase{

	@Test
	public void testGoodDAO() throws SQLException, DataSourceNotFoundException{
		GoodDAO dao = new GoodDAO();
		
		List<Good> goods = dao.executeListQuery("SELECT * FROM good", dao);
		System.out.println(JSONUtil.marshal(goods));
		System.out.println(DateUtil.formatDate(goods.get(0).getLastUpdate()));
		
		Assert.assertNotNull("数据库链接失败", goods);
		Assert.assertEquals("数据初始化不正确", 3, goods.size());
	}
	
	@Test
	public void testGoodTypeDAO() throws SQLException, DataSourceNotFoundException{
		GoodTypeDAO dao = new GoodTypeDAO();
		List<GoodType> goodTypes = dao.executeListQuery("SELECT * FROM good_type", dao);
		System.out.println(JSONUtil.marshal(goodTypes));
		System.out.println(DateUtil.formatDate(goodTypes.get(0).getLastUpdate()));
		
		Assert.assertNotNull("数据库链接失败", goodTypes);
		Assert.assertEquals("数据初始化不正确", 3, goodTypes.size());
	}
	
	@Test
	public void testPromotionDAO() throws SQLException, DataSourceNotFoundException{
		PromotionDAO dao = new PromotionDAO();
		List<Promotion> promotiones = dao.executeListQuery("SELECT * FROM promotion", dao);
		System.out.println(JSONUtil.marshal(promotiones));
		System.out.println(DateUtil.formatDate(promotiones.get(0).getStartTime()));
		
		Assert.assertNotNull("数据库链接失败", promotiones);
		Assert.assertEquals("数据初始化不正确", 2, promotiones.size());
	}
	
	@Test
	public void testPromotionTypeDAO() throws SQLException, DataSourceNotFoundException{
		PromotionTypeDAO dao = new PromotionTypeDAO();
		List<PromotionType> proTypes = dao.executeListQuery("SELECT * FROM promotion_type", dao);
		System.out.println(JSONUtil.marshal(proTypes));
		
		Assert.assertNotNull("数据库链接失败", proTypes);
		Assert.assertEquals("数据初始化不正确", 2, proTypes.size());
	}
	
	@Test
	public void testPromotionTargetDAO() throws SQLException, DataSourceNotFoundException{
		PromotionTargetDAO dao = new PromotionTargetDAO();
		List<PromotionTarget> proTargetes = dao.executeListQuery("SELECT * FROM promotion_target", dao);
		System.out.println(JSONUtil.marshal(proTargetes));
		
		Assert.assertNotNull("数据库链接失败", proTargetes);
		Assert.assertEquals("数据初始化不正确", 2, proTargetes.size());
	}
}
