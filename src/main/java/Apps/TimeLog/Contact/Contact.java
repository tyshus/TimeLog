package Apps.TimeLog.Contact;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@Entity(name = "contacts")
@Table(name = "contacts")
public class Contact {
	@Id
	@SequenceGenerator(name = "contacts_sequence", sequenceName = "contacts_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacts_sequence")
	private long id;
	private String company;
	private String name;
	private String email;
	private String emailtype;
	private boolean active;
	private boolean manager;

	public Contact() {
	}

	public Contact(long id, String company, String name, String email, String emailtype, boolean active,
			boolean manager) {
		super();
		this.id = id;
		this.company = company;
		this.name = name;
		this.email = email;
		this.emailtype = emailtype;
		this.active = active;
		this.manager = manager;
	}
}
