package Apps.TimeLog.Company;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Apps.TimeLog.Contact.Contact;
import Apps.TimeLog.Contact.ContactEntryControler;
import Apps.TimeLog.Contact.ContactListControler;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

@SuppressWarnings("unused")
public class TestCompanyListControler {

	@Test
	public void testCompanyListControler() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						CompanyListControler companyListControler = new CompanyListControler();
						List<Company> list = new ArrayList<Company>();
						list.add(new Company("TEST","test company","address","122344","AA1233",10.1));
						list.add(new Company("TEST2","test2 company","address","22222","AA555",20.2));
						companyListControler.setList(list);
						companyListControler.fillTable();
					}
				});
			}
		});
		thread.start();
		Thread.sleep(10000);
	}
}
