import java.math.BigDecimal;
import java.util.Scanner;

import org.junit.Test;

import com.thoughtworks.cashier.service.CashierTicketPrinter;

public class TestPrinter extends TestBase{
	static String appleCode = "ITEM000003";
	static String cokecoleCode = "ITEM000005";
	static String yumaoqiuCode = "ITEM000001";

	public static String createSettlementJSON(int yumaoqiuCount, int cokecoleCount, BigDecimal appleCount) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < yumaoqiuCount; i++)
			sb.append("\"").append(yumaoqiuCode).append("\",");

		for (int i = 0; i < cokecoleCount; i++)
			sb.append("\"").append(cokecoleCode).append("\",");

		if (appleCount.compareTo(BigDecimal.ZERO) == 1)
			sb.append("\"").append(appleCode).append("-").append(appleCount.toPlainString()).append("\",");

		sb.deleteCharAt(sb.length() - 1); // 删除末尾逗号

		return sb.length() > 0 ? "[" + sb.toString() + "]" : null;
	}

	@Test
	public void main() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.print("收银系统\n输入羽毛球数量:\t");
		int yumaoqiuCount = sc.nextInt();

		System.out.print("输入可口可乐数量:\t");
		int cokecoleCount = sc.nextInt();

		System.out.print("输入苹果数量:\t");
		Double appleCount = sc.nextDouble();

		String settlement = createSettlementJSON(yumaoqiuCount, cokecoleCount, new BigDecimal(appleCount.toString()));
		String content = CashierTicketPrinter.getInstance().getPrintContent(settlement);
		System.out.println("\r\n\r\n" + content + "\r\n\r\n");
	}
}
