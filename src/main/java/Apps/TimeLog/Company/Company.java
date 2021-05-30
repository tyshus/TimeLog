package Apps.TimeLog.Company;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "companies")
@Table(name = "companies")
public class Company {
	@Id
	private String id;
	private String name;
	private String address;
	private String code;
	private String vatRegNo;
	private Double rate;

}
