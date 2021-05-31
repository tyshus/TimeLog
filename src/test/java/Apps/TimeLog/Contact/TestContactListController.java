package Apps.TimeLog.Contact;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import javafx.application.Platform;
//import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.*;

public class TestContactListController {

	@Test
	public void testContactListController() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ContactListController contactListController = new ContactListController();
						List<Contact> list = new ArrayList<Contact>();
						list.add(new Contact(1, "company", "name", "test@mail.com", "cc", true, true));
						list.add(new Contact(2, "company2", "name2", "test2@mail.com", "to", true, true));
						contactListController.setList(list);
						contactListController.fillTable();
					}
				});
			}
		});
		thread.start();
		Thread.sleep(10000);
	}

}
