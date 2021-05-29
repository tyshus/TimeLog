package Apps.TimeLog.Mail;

import java.time.LocalDate;

import Apps.TimeLog.Invoice.Invoice;
import Apps.TimeLog.Tools.SendMail;
import Apps.TimeLog.Windows.Entry.WindowEntry;
import Apps.TimeLog.Windows.Fields.NumberTextField;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class MailEntry extends VBox {
	private NumberTextField id = new NumberTextField();
	private DatePicker date = new DatePicker();
	private TextField to = new TextField("");
	private TextField cc = new TextField("");
	private TextField bcc = new TextField("");
	private TextField subject = new TextField("");
	private TextField attachment = new TextField("");
	private TextArea body = new TextArea("");
	private CheckBox sent = new CheckBox("Sent");
	private @Getter Button sendBtn = new Button("Send");
	private @Getter Button saveBtn = new Button("Save");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public MailEntry(MailEntryControler mailEntryControler) {
		saveBtn.setOnAction(mailEntryControler);
		sendBtn.setOnAction(mailEntryControler);
		setScene();
	}

	private void setScene() {
		date.setValue(LocalDate.now());
		id.setPrefWidth(50);
		id.setMaxWidth(50);
		date.setPrefWidth(150);
		date.setMaxWidth(150);
		to.setMaxWidth(400);
		cc.setMaxWidth(400);
		bcc.setMaxWidth(400);
		HBox hline = new HBox();
		hline.setSpacing(15.0);
		hline.getChildren().addAll(new Label("ID: "), id, new Label("Date: "), date, sent, new Label("Att.: "),
				attachment);
		grid.add(hline, 1, 0, 1, 1);
		grid.add(new Label("To: "), 0, 1);
		grid.add(to, 1, 1);
		grid.add(new Label("CC: "), 0, 2);
		grid.add(cc, 1, 2);
		grid.add(new Label("Bcc: "), 0, 3);
		grid.add(bcc, 1, 3);
		grid.add(new Label("Subject: "), 0, 4);
		grid.add(subject, 1, 4);
		grid.add(body, 1, 5);
		grid.add(saveBtn, 1, 6);
		grid.add(sendBtn, 2, 6);

		id.setEditable(false);
		sent.setSelected(false);
		sent.setDisable(true);
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().add(grid);
	}

	public void setMail(Mail mail) {
		if (mail.getId() > 0) {
			id.SetLong(mail.getId());
			date.setValue(mail.getDate());
			to.setText(mail.getToo());
			cc.setText(mail.getCc());
			bcc.setText(mail.getBcc());
			subject.setText(mail.getSubject());
			body.setText(mail.getBody());
			sent.setSelected(mail.isSent());
			attachment.setText(mail.getAttachment());
			if (mail.isSent()) {
				sendBtn.setDisable(true);
			}
		}
	}

	public void getMail(Mail mail) {
		mail.setDate(date.getValue());
		mail.setToo(to.getText());
		mail.setCc(cc.getText());
		mail.setBcc(bcc.getText());
		mail.setSubject(subject.getText());
		mail.setBody(body.getText());
		mail.setSent(sent.isSelected());
		mail.setAttachment(attachment.getText());
	}
}
