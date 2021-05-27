package Apps.TimeLog.Windows.List;

import java.time.LocalDate;

import Apps.TimeLog.Models.Mail;
import Apps.TimeLog.Windows.Entry.MailEntry;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MailList extends WindowList {
	private TableColumn<Mail, Long> id;
	private TableColumn<Mail, LocalDate> date;
	private TableColumn<Mail, String> to;
	private TableColumn<Mail, String> subject;
	private TableColumn<Mail, Boolean> sent;
	private TableView<Mail> tableView;

	public MailList() {
		stage.setTitle("Mail List");
		tableView = new TableView<Mail>();
		AddColumns();
		LoadData();
		root.getChildren().add(tableView);
		tableView.setId("table-view");
	}

	@Override
	void newEntry() {
		new MailEntry();
	}

	@Override
	void editEntry() {
		Mail mail = (Mail) tableView.getSelectionModel().getSelectedItem();
		if (mail != null) {
			MailEntry mailEntry = new MailEntry();
			mailEntry.setMail(mail);
		} else {
			model.msgW("No mail selected!");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	void AddColumns() {
		id = new TableColumn<>("ID");
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		date = new TableColumn<>("Date");
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		to = new TableColumn<>("To");
		to.setCellValueFactory(new PropertyValueFactory<>("too"));
		subject = new TableColumn<>("Subject");
		subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
		sent = new TableColumn<>("Sent");
		sent.setCellValueFactory(new PropertyValueFactory<>("sent"));
		sent.setCellFactory(tc -> new TableCell<Mail, Boolean>() {
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null
						: item.booleanValue() ? Character.toString((char) 0x2714) : Character.toString((char) 0x2717));
			}
		});
		tableView.getColumns().addAll(id, date, to, subject, sent);
		tableView.setPadding(new Insets(5, 5, 5, 5));
	}

	@Override
	void LoadData() {
		tableView.getItems().clear();
		for (Mail temp : model.MailList()) {
			tableView.getItems().add(temp);
		}

	}

}
