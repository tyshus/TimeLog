package Apps.TimeLog.Invoice;

import java.time.LocalDate;

import org.junit.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class TestInvoiceEntryController {

	@Test
	public void testInvoiceEntryController() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Invoice invoice = new Invoice("test", LocalDate.now(), "Company code", "invoice operaation",
								"year 2021", 100.00, false, "", false);
						new InvoiceEntryController(invoice);
					}
				});
			}
		});
		thread.start();
		Thread.sleep(10000);
	}

}
