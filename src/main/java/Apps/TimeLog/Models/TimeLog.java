package Apps.TimeLog.Models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name = "timelogs")
@Table(name = "timelogs")
public class TimeLog {
	@Id
	@SequenceGenerator(name = "timeLogs_sequence", sequenceName = "timeLogs_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeLogs_sequence")
	private long id;
	private String company;
	private String contactName;
	@Column(name = "logdate")
	private LocalDate logDate;
	private LocalDate taskDate;
	private String taskType;
	private String taskName;
	@Column(length = 10485760)
	private String taskBody;
	@Column(length = 10485760)
	private String myComment;
	private double time;
	@Column(name = "hours", nullable = true)
	private int hours;
	@Column(name = "mins", nullable = true)
	private int mins;

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMins() {
		return mins;
	}

	public void setMins(int mins) {
		this.mins = mins;
	}

	public TimeLog(String company, String contactName, LocalDate logdate, LocalDate taskDate, String taskType,
			String taskName, String taskBody, String myComment, double time) {
		super();
		this.company = company;
		this.contactName = contactName;
		this.logDate = logdate;
		this.taskDate = taskDate;
		this.taskType = taskType;
		this.taskName = taskName;
		this.taskBody = taskBody;
		this.myComment = myComment;
		this.time = time;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public LocalDate getLogDate() {
		return logDate;
	}

	public void setLogDate(LocalDate logDate) {
		this.logDate = logDate;
	}

	public LocalDate getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(LocalDate taskDate) {
		this.taskDate = taskDate;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskBody() {
		return taskBody;
	}

	public void setTaskBody(String taskBody) {
		this.taskBody = taskBody;
	}

	public String getMyComment() {
		return myComment;
	}

	public void setMyComment(String myComment) {
		this.myComment = myComment;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public TimeLog() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TimeLog [id=" + id + ", company=" + company + ", contactName=" + contactName + ", logDate=" + logDate
				+ ", taskDate=" + taskDate + ", taskType=" + taskType + ", taskName=" + taskName + ", taskBody="
				+ taskBody + ", myComment=" + myComment + ", time=" + time + ", hours=" + hours + ", mins=" + mins
				+ "]";
	}

}
