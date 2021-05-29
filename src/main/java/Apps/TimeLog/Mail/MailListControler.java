package Apps.TimeLog.Mail;

import java.util.List;

import Apps.TimeLog.Mail.Mail;
import Apps.TimeLog.Mail.MailEntryControler;
import Apps.TimeLog.Mail.MailList;
import Apps.TimeLog.Models.Model;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public class MailListControler implements EventHandler {
	private final MailList mailList;
	private @Setter List<Mail> list;
	private Model model = Model.getModel();
	private Stage stage;

	public MailListControler() {
		stage = new Stage();
		this.mailList = new MailList(this);
		this.loadData();
		this.fillTable();
		this.stage.setTitle("Mail list");
		this.stage.setScene(new Scene(mailList, 500, 550));
		this.stage.show();
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.mailList.getNewBtn())) {
			newEntry();
		}
		if (source.equals(this.mailList.getEditBtn())) {
			editEntry();
		}
	}

	void newEntry() {
		new MailEntryControler(new Mail());
	}

	void editEntry() {
		Mail mail = (Mail) this.mailList.getTableView().getSelectionModel().getSelectedItem();
		if (mail != null) {
			new MailEntryControler(mail);
		} else {
			model.msgW("No Mail selected!");
		}
	}
	
	public void loadData() {
		this.list = this.model.loadMailList();
	}
	
	public void fillTable() {
		this.mailList.filltableView(list);
	}
}
