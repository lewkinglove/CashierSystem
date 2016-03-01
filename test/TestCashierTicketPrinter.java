import org.junit.Test;

import com.thoughtworks.cashier.service.CashierTicketPrinter;


public class TestCashierTicketPrinter extends TestBase{

	@Test
	public void testPrintContent() throws Exception{
		String settlement = "[\r\n" + 
				"    \"ITEM000001\",\r\n" + 
				"    \"ITEM000001\",\r\n" + 
				"    \"ITEM000001\",\r\n" + 
				"    \"ITEM000001\",\r\n" + 
				"    \"ITEM000001\",\r\n" + 
				"    \"ITEM000003-2\",\r\n" + 
				"    \"ITEM000005\",\r\n" + 
				"    \"ITEM000005\",\r\n" + 
				"    \"ITEM000005\"\r\n" + 
				"]";
		
		String content = CashierTicketPrinter.getInstance().getPrintContent(settlement);
		System.out.println(content);
	}
}
