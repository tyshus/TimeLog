package Apps.TimeLog.Contact;

import Apps.TimeLog.Models.Model;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
public class ContactEntryControler implements EventHandler {
	private final ContactEntry contactEntry;
	private Model model = Model.getModel();
	private Contact contact;
	private Stage stage;

	public ContactEntryControler(Contact contact) {
		this.stage = new Stage();
		this.contact = contact;
		this.contactEntry = new ContactEntry(this);
		this.contactEntry.fillCompanies(model.loadCompanies());
		this.contactEntry.setContact(contact);
		this.stage.setTitle("Contact");
		this.stage.setScene(new Scene(contactEntry, 450, 250));
		this.stage.show();
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.contactEntry.getSaveBtn())) {
			save();
		}
	}

	private void save() {
		contactEntry.getContact(contact);
		if (contact.getId() <= 0) {
			this.model.persist(this.contact);
		} else {
			this.model.merge(this.contact);
		}
		this.stage.close();
	}
}
