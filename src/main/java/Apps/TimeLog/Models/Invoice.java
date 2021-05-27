package Apps.TimeLog.Models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;



@Entity(name = "invoices")
@Table(name = "invoices")
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoices_sequence")
	@GenericGenerator(name = "invoices_sequence", strategy = "Apps.TimeLog.Models.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "PST_"),
			@Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String serno;
	private LocalDate date;
	private String company;
	private String operation;
	private String period;
	private Double amount;
	private boolean printed;
	private String printout;
	private boolean sent;

	public Invoice() {
	}

	public Invoice(String serno, LocalDate date, String company, String operation, String period, Double amount,
			boolean printed, String printout, boolean sent) {
		super();
		this.serno = serno;
		this.date = date;
		this.company = company;
		this.operation = operation;
		this.period = period;
		this.amount = amount;
		this.printed = printed;
		this.printout = printout;
		this.sent = sent;
	}

	public String getSerno() {
		return serno;
	}

	public void setSerno(String serno) {
		this.serno = serno;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public boolean isPrinted() {
		return printed;
	}

	public void setPrinted(boolean printed) {
		this.printed = printed;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public String getPrintout() {
		return printout;
	}

	public void setPrintout(String printout) {
		this.printout = printout;
	}

	@Override
	public String toString() {
		return "Invoice [serno=" + serno + ", date=" + date + ", company=" + company + ", operation=" + operation
				+ ", period=" + period + ", amount=" + amount + ", printed=" + printed + ", printout=" + printout
				+ ", sent=" + sent + "]";
	}

}
