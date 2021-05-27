package Apps.TimeLog.Windows.Entry;

import Apps.TimeLog.Models.Contact;
import Apps.TimeLog.Windows.Fields.NumberTextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ContactEntry extends WindowEntry {
	private ComboBox<String> company = new ComboBox<String>();
	private ComboBox<String> emailtype = new ComboBox<String>();
	private NumberTextField id = new NumberTextField();
	private TextField name = new TextField("");
	private TextField email = new TextField("");
	private CheckBox active = new CheckBox("Active");
	private CheckBox manager = new CheckBox("Manager");
	private Contact contact = new Contact();

	public ContactEntry() {
		stage.setTitle("Contact");
		emailtype.getItems().addAll("not sent", "To", "Cc", "Bcc");
		emailtype.getSelectionModel().selectFirst();
		company.getItems().addAll(model.loadCompanies());
		company.getSelectionModel().selectFirst();
		grid.add(new Label("Company: "), 0, 0);
		grid.add(company, 1, 0);
		grid.add(new Label("Contact code: "), 0, 1);
		grid.add(id, 1, 1);
		grid.add(new Label("Full name: "), 0, 2);
		grid.add(name, 1, 2);
		grid.add(new Label("Email: "), 0, 3);
		grid.add(email, 1, 3);
		grid.add(emailtype, 2, 3);
		grid.add(new Label("Active: "), 0, 4);
		grid.add(active, 1, 4);
		grid.add(new Label("Manager: "), 0, 5);
		grid.add(manager, 1, 5);
		grid.add(saveBtn, 0, 6);
		id.setEditable(false);
	}

	void save() {
		contact.setName(name.getText());
		contact.setEmail(email.getText());
		contact.setActive(active.isSelected());
		contact.setManager(manager.isSelected());
		contact.setCompany(company.getValue());
		contact.setEmailtype(emailtype.getValue());
		if (id.getText() == null) {
			model.persist(contact);
		} else {
			model.merge(contact);
		}
		model.refresh();
		stage.close();
	}

	public void setCustomer(Contact contact) {
		this.contact = contact;
		id.SetLong(contact.getId());
		company.getSelectionModel().select(contact.getCompany());
		name.setText(contact.getName());
		email.setText(contact.getEmail());
		manager.setSelected(contact.isManager());
		active.setSelected(contact.isActive());
		emailtype.getSelectionModel().select(contact.getEmailtype());
	}
}
