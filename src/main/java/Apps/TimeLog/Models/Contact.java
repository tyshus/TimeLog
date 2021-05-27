package Apps.TimeLog.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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

	public boolean isActive() {
		return active;
	}

	public Contact setActive(boolean active) {
		this.active = active;

		return this;
	}

	public boolean isManager() {
		return manager;
	}

	public Contact setManager(boolean manager) {
		this.manager = manager;

		return this;
	}

	public long getId() {
		return id;
	}

	public Contact setId(long id) {
		this.id = id;

		return this;
	}

	public String getCompany() {
		return company;
	}

	public Contact setCompany(String company) {
		this.company = company;

		return this;
	}

	public String getName() {
		return name;
	}

	public Contact setName(String name) {
		this.name = name;

		return this;
	}

	public String getEmail() {
		return email;
	}

	public Contact setEmail(String email) {
		this.email = email;

		return this;
	}

	public String getEmailtype() {
		return emailtype;
	}

	public void setEmailtype(String emailtype) {
		this.emailtype = emailtype;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", company=" + company + ", name=" + name + ", email=" + email + ", emailtype="
				+ emailtype + ", active=" + active + ", manager=" + manager + "]";
	}

}
