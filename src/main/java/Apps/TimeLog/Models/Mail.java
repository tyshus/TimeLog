package Apps.TimeLog.Models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "mails")
@Table(name = "mails")
public class Mail {
	@Id
	@SequenceGenerator(
			name = "mails_sequence",
			sequenceName = "mails_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "mails_sequence"
	)
	private long id;
	private LocalDate date;
	private String too;
	private String cc;
	private String bcc;
	private String subject;
	@Column(length=10485760)
	private String body;
	private String attachment;
	private String sourcetype;
	private String sourceid;
	private boolean sent;
	
	public Mail() {
		// TODO Auto-generated constructor stub
	}

	public Mail(long id, LocalDate date, String too, String cc, String bcc, String subject, String body,
			String attachment, String sourcetype, String sourceid, boolean sent) {
		super();
		this.id = id;
		this.date = date;
		this.too = too;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.body = body;
		this.attachment = attachment;
		this.sourcetype = sourcetype;
		this.sourceid = sourceid;
		this.sent = sent;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getTo() {
		return too;
	}
	public void setTo(String too) {
		this.too = too;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getSourcetype() {
		return sourcetype;
	}

	public void setSourcetype(String sourcetype) {
		this.sourcetype = sourcetype;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	@Override
	public String toString() {
		return "Mail [id=" + id + ", date=" + date + ", too=" + too + ", cc=" + cc + ", bcc=" + bcc + ", subject="
				+ subject + ", body=" + body + ", attachment=" + attachment + ", sourcetype=" + sourcetype
				+ ", sourceid=" + sourceid + ", sent=" + sent + "]";
	}



}
