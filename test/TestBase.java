import org.junit.Before;

import com.thoughtworks.cashier.ConfigPool;


public class TestBase {
	
	@Before
	public void setUp() throws Exception{
		if(ConfigPool.getInstance().getCurrentEnvironment()==null)
			ConfigPool.getInstance().init();
	}

}
