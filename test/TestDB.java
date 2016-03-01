import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.thoughtworks.cashier.common.db.DBHelper;
import com.thoughtworks.cashier.common.db.exception.DataSourceNotFoundException;
import com.thoughtworks.cashier.common.util.JSONUtil;


public class TestDB extends TestBase{

	@Test
	public void testDBConnection() throws SQLException, DataSourceNotFoundException{
		List<HashMap<String, Object>> list = DBHelper.executeFreeQuery("SELECT * FROM good");
		System.out.println(JSONUtil.marshal(list));
		
		Assert.assertNotNull("数据库链接失败", list);
		Assert.assertEquals("数据初始化不正确", 3, list.size());
	}
}
