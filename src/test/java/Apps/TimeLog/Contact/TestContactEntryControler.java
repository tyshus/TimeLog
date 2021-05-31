package Apps.TimeLog.Contact;

import org.junit.Test;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

public class TestContactEntryControler {

	@Test
	public void testContactEntryControler() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Contact contact = new Contact(1, "test company", "test name", "test@mail.com", "cc", true,
								true);
						new ContactEntryControler(contact);
					}
				});
			}
		});
		thread.start();
		Thread.sleep(10000);
	}
}
