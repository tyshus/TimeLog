package Apps.TimeLog.Contact;

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

}
