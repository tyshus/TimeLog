package Apps.TimeLog.Mail;

import java.time.LocalDate;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class MailList extends VBox {
	private TableColumn<Mail, Long> id = new TableColumn<>("ID");
	private TableColumn<Mail, LocalDate> date = new TableColumn<>("Date");
	private TableColumn<Mail, String> to = new TableColumn<>("To");
	private TableColumn<Mail, String> subject = new TableColumn<>("Subject");
	private TableColumn<Mail, Boolean> sent = new TableColumn<>("Sent");
	private @Getter TableView<Mail> tableView = new TableView<Mail>();
	private @Getter Button newBtn = new Button("New");
	private @Getter Button editBtn = new Button("Edit");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public MailList(MailListControler mailListControler) {
		newBtn.setOnAction(mailListControler);
		editBtn.setOnAction(mailListControler);
		setScene();
	}

	@SuppressWarnings("unchecked")
	private void setScene() {
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		to.setCellValueFactory(new PropertyValueFactory<>("too"));
		subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
		sent.setCellValueFactory(new PropertyValueFactory<>("sent"));
		this.sent.setCellFactory(tc -> new TableCell<Mail, Boolean>() {
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null
						: item.booleanValue() ? Character.toString((char) 0x2714) : Character.toString((char) 0x2717));
			}
		});
		tableView.getColumns().addAll(id, date, to, subject, sent);
		this.tableView.setPadding(new Insets(5, 5, 5, 5));
		this.grid.setVgap(10);
		this.grid.setHgap(10);
		this.grid.setPadding(new Insets(15, 10, 10, 10));
		this.grid.add(newBtn, 0, 0);
		this.grid.add(editBtn, 1, 0);
		this.getChildren().add(grid);
		this.getChildren().add(tableView);
		this.getStylesheets().add("windowList.css");
		this.tableView.setId("table-view");
	}

	public void filltableView(List<Mail> list) {
		this.tableView.getItems().clear();
		this.tableView.getItems().addAll(list);
	}
}
