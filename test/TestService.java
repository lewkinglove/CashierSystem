import org.junit.Assert;
import org.junit.Test;

import com.thoughtworks.cashier.common.util.JSONUtil;
import com.thoughtworks.cashier.service.GoodService;
import com.thoughtworks.cashier.vo.Good;

public class TestService extends TestBase {
	
	@Test
	public void testGoodService() throws Exception{
		GoodService service = GoodService.getInstance();
		Good good = service.getByID(1);
		System.out.println(JSONUtil.marshal(good));
		Assert.assertNotNull("getByID查询失败", good);
		
		Good good2 = service.getByCode("ITEM000001");
		System.out.println(JSONUtil.marshal(good2));
		Assert.assertNotNull("getByCode查询失败", good2);
		
		Good good3 = service.getByCode("ITEM000003-2");
		System.out.println(JSONUtil.marshal(good3));
		Assert.assertNotNull("getByCode-伪码查询失败", good3);
	}
	

}
