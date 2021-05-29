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
		this.setContact(contact);
		final Scene scene = new Scene(contactEntry, 450, 250);
		this.stage.setTitle("Contact");
		this.stage.setScene(scene);
		this.stage.show();
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.contactEntry.getSaveBtn())) {
			save();
		}
	}

	public void setContact(Contact contact) {
		this.contactEntry.setCompanies(this.model.loadCompanies());
		if (contact == null) {
			this.contactEntry.setDefaults();

		} else {
			this.contactEntry.setId(contact.getId());
			this.contactEntry.setCompany(contact.getCompany());
			this.contactEntry.setName(contact.getName());
			this.contactEntry.setEmail(contact.getEmail());
			this.contactEntry.setManager(contact.isManager());
			this.contactEntry.setActive(contact.isActive());
			this.contactEntry.setEmailtype(contact.getEmailtype());
		}
	}

	private void save() {
		this.contact.setName(contactEntry.getName().getText());
		this.contact.setEmail(contactEntry.getEmail().getText());
		this.contact.setActive(contactEntry.getActive().isSelected());
		this.contact.setManager(contactEntry.getManager().isSelected());
		this.contact.setCompany(contactEntry.getCompany().getValue());
		this.contact.setEmailtype(contactEntry.getEmailtype().getValue());
		if (contact.getId() <= 0) {
			this.model.persist(this.contact);
		} else {
			this.model.merge(this.contact);
		}
		this.model.refresh();
		this.stage.close();
	}
}
