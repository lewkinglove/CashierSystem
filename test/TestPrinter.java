import java.math.BigDecimal;

import org.junit.Test;

import com.thoughtworks.cashier.service.CashierTicketPrinter;


public class TestPrinter extends TestBase{
	String appleCode = "ITEM000003";
	String cokecoleCode = "ITEM000005";
	String yumaoqiuCode = "ITEM000001";
	

	public String createSettlementJSON(int yumaoqiuCount, int cokecoleCount, BigDecimal appleCount){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < yumaoqiuCount; i++)
	        sb.append("\"").append(yumaoqiuCode).append("\",");
		
		for (int i = 0; i < cokecoleCount; i++)
			sb.append("\"").append(cokecoleCode).append("\",");
		
		if(appleCount.compareTo(BigDecimal.ZERO)==1)
			sb.append("\"").append(appleCode).append("-").append(appleCount.toPlainString()).append("\",");

		sb.deleteCharAt(sb.length()-1); //删除末尾逗号
		
		return sb.length()>0 ? "["+sb.toString()+"]" : null; 
	}
	
	/**
	 * 五个羽毛球/三个可乐/两斤苹果小票测试
	 * @throws Exception
	 */
	@Test
	public void testTicket532() throws Exception{
		int yumaoqiuCount = 5;
		int cokecoleCount = 3;
		BigDecimal appleCount = new BigDecimal("2");
		String settlement = createSettlementJSON(yumaoqiuCount, cokecoleCount, appleCount);
		String content = CashierTicketPrinter.getInstance().getPrintContent(settlement);
		System.out.println(content);
	}
	
	/**
	 * 六个羽毛球/三个可乐/两斤苹果小票测试
	 * @throws Exception
	 */
	@Test
	public void testGood632() throws Exception{
		int yumaoqiuCount = 6;
		int cokecoleCount = 3;
		BigDecimal appleCount = new BigDecimal("2");
		String settlement = createSettlementJSON(yumaoqiuCount, cokecoleCount, appleCount);
		String content = CashierTicketPrinter.getInstance().getPrintContent(settlement);
		System.out.println(content);
	}
	
}
