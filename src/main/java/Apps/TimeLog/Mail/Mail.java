package Apps.TimeLog.Mail;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
@Entity(name = "mails")
@Table(name = "mails")
public class Mail {
	@Id
	@SequenceGenerator(name = "mails_sequence", sequenceName = "mails_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mails_sequence")
	private long id;
	private LocalDate date;
	private String too;
	private String cc;
	private String bcc;
	private String subject;
	@Column(length = 10485760)
	private String body;
	private String attachment;
	private String sourcetype;
	private String sourceid;
	private boolean sent;
}
