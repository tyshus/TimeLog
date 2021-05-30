package Apps.TimeLog.TimeLog;

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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

}
