package Apps.TimeLog.Windows.Entry;

import java.time.LocalDate;

import Apps.TimeLog.Models.Invoice;
import Apps.TimeLog.Models.Mail;
import Apps.TimeLog.Tools.SendMail;
import Apps.TimeLog.Windows.NumberTextField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class MailEntry extends WindowEntry{
	private NumberTextField id = new NumberTextField();
	private DatePicker date = new DatePicker();
	private TextField to = new TextField("");
	private TextField cc = new TextField("");
	private TextField bcc = new TextField("");
	private TextField subject = new TextField("");
	private TextField attachment = new TextField("");
	private TextArea body = new TextArea ("");
	private CheckBox sent = new CheckBox("Sent"); 
	private Button sendBtn = new Button ("Send");
	private Mail mail = new Mail();
	
	public MailEntry() {
		stage.setTitle("Mail"); 
		
		sendBtn.setOnAction(value ->  {SendeMail();});
		
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
		hline.getChildren().addAll(new Label("ID: "), id, new Label("Date: "),date,sent,new Label("Att.: "),attachment);

		grid.add(hline,1,0,1,1);
        grid.add(new Label("To: "), 0, 1);
        grid.add(to, 1,1); 
        grid.add(new Label("CC: "), 0, 2);
        grid.add(cc, 1,2); 
        grid.add(new Label("Bcc: "), 0, 3);
        grid.add(bcc, 1,3); 
        grid.add(new Label("Subject: "), 0, 4);
        grid.add(subject, 1,4); 
        grid.add(body, 1,5); 
        grid.add(saveBtn, 1,6); 
        grid.add(sendBtn, 2,6); 
        
        id.setEditable(false); 
        sent.setSelected(false);
        sent.setDisable(true);
		stage.setWidth(800);
		stage.setHeight(450);
	}
	
	private void SendeMail() {
		SendMail sendMail = new SendMail();
		if (sendMail.send(mail)) {
			mail.setSent(true);
			model.merge(mail);
			model.refresh();
			sendBtn.setDisable(true);
			if (mail.getSourcetype() == "inv") {
				Invoice invoice = model.GetInvoice(mail.getSourceid());
				invoice.setSent(true);
				model.merge(invoice);
			}
			setMail(mail);
			model.msg("eMail sent!");
		}
		else
			model.msgW("Failed to send email!");
			
	}

	@Override
	void Save() {
		mail.setDate(date.getValue());
		mail.setTo(to.getText());
		mail.setCc(cc.getText());
		mail.setBcc(bcc.getText());
		mail.setSubject(subject.getText());
		mail.setBody(body.getText());
		mail.setSent(sent.isSelected());
		mail.setAttachment(attachment.getText());
		if (id.getText() == null)
			model.persist(mail);
		else
			model.merge(mail);
		model.refresh();
		setMail(mail);
		//stage.close();
		
	}
    public void setMail(Mail mail)
    {
        this.mail = mail;
    	id.SetLong(mail.getId());
    	date.setValue(mail.getDate());
    	to.setText(mail.getTo());
    	cc.setText(mail.getCc());
    	bcc.setText(mail.getBcc());
    	subject.setText(mail.getSubject());
    	body.setText(mail.getBody());
    	sent.setSelected(mail.isSent());
    	attachment.setText(mail.getAttachment());
    	//if (mail.isSent())
    	//	sendBtn.setDisable(true);
    		
    }
}
