package Apps.TimeLog.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class TestInvoiceListController {

	@Test
	public void testInvoiceListController() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						InvoiceListController invoiceListController = new InvoiceListController();
						List<Invoice> list = new ArrayList<Invoice>();
						list.add(new Invoice("test1", LocalDate.now(), "Company1", "invoice operaation1", "year 2020",
								100.00, false, "", false));
						list.add(new Invoice("test2", LocalDate.now(), "Company2", "invoice operaation1", "year 2022",
								200.00, false, "", false));
						invoiceListController.setList(list);
						invoiceListController.fillTable();
					}
				});
			}
		});
		thread.start();
		Thread.sleep(10000);
	}

}
