package Apps.TimeLog.Company;

import static org.junit.Assert.*;
import javafx.embed.swing.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import Apps.TimeLog.Contact.Contact;
import Apps.TimeLog.Contact.ContactEntryController;
import Apps.TimeLog.Contact.ContactListController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

@SuppressWarnings("unused")
public class TestCompanyListController {

	@Test
	public void testCompanyListController() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						CompanyListController companyListController = new CompanyListController();
						List<Company> list = new ArrayList<Company>();
						list.add(new Company("TEST", "test company", "address", "122344", "AA1233", 10.1));
						list.add(new Company("TEST2", "test2 company", "address", "22222", "AA555", 20.2));
						companyListController.setList(list);
						companyListController.fillTable();
					}
				});
			}
		});
		thread.start();
		Thread.sleep(10000);
	}
}
