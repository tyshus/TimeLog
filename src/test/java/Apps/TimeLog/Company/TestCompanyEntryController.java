package Apps.TimeLog.Company;

import org.junit.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class TestCompanyEntryController {

	@Test
	public void testCompanyEntryController() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Company company = new Company("TEST", "test company", "address", "122344", "AA1233", 10.0);
						new CompanyEntryController(company);
					}
				});
			}
		});
		thread.start();
		Thread.sleep(10000);
	}

}
