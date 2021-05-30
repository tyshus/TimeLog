package Apps.TimeLog.Mail;

import java.util.List;

import Apps.TimeLog.Tools.Model;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

@SuppressWarnings("rawtypes")
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
		this.stage.setWidth(700);
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
		this.mailList.fillTableView(list);
	}
}
