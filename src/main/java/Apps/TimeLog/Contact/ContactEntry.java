package Apps.TimeLog.Contact;

import Apps.TimeLog.Windows.Fields.NumberTextField;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class ContactEntry extends VBox {
	private NumberTextField id = new NumberTextField();
	private ComboBox<String> company = new ComboBox<String>();
	private ComboBox<String> emailtype = new ComboBox<String>();
	private TextField name = new TextField("");
	private TextField email = new TextField("");
	private CheckBox active = new CheckBox("Active");
	private CheckBox manager = new CheckBox("Manager");
	private @Getter Button saveBtn = new Button("Save");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public ContactEntry(ContactEntryController contactEntryController) {
		saveBtn.setOnAction(contactEntryController);
		this.setScene();
	}

	public void setScene() {
		emailtype.getItems().addAll("not sent", "To", "Cc", "Bcc");
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
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().add(grid);
	}

	public void setDefaults() {
		emailtype.getSelectionModel().selectFirst();
		company.getSelectionModel().selectFirst();
	}

	public void setContact(Contact contact) {
		if (contact.getId() <=0 ) {
			this.setDefaults();
		} else {
			id.SetLong(contact.getId());
			company.getSelectionModel().select(contact.getCompany());
			name.setText(contact.getName());
			email.setText(contact.getEmail());
			manager.setSelected(contact.isManager());
			active.setSelected(contact.isActive());
			emailtype.getSelectionModel().select(contact.getEmailtype());
		}
	}

	public void getContact(Contact contact) {
		contact.setName(name.getText());
		contact.setEmail(email.getText());
		contact.setActive(active.isSelected());
		contact.setManager(manager.isSelected());
		contact.setCompany(company.getValue());
		contact.setEmailtype(emailtype.getValue());
	}

	public void fillCompanies(ObservableList<String> list) {
		company.getItems().addAll(list);
	}
}
