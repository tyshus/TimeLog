package Apps.TimeLog.Contact;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class ContactList extends VBox {

	private TableColumn<Contact, String> id = new TableColumn<>("ID");
	private TableColumn<Contact, String> name = new TableColumn<>("Name");
	private TableColumn<Contact, String> company = new TableColumn<>("Company");
	private @Getter TableView<Contact> tableView = new TableView<Contact>();
	private @Getter Button newBtn = new Button("New");
	private @Getter Button editBtn = new Button("Edit");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public ContactList(ContactListControler contactListControler) {
		newBtn.setOnAction(contactListControler);
		editBtn.setOnAction(contactListControler);
		this.setScene();
	}

	@SuppressWarnings("unchecked")
	void setScene() {
		this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.company.setCellValueFactory(new PropertyValueFactory<>("company"));
		this.tableView.getColumns().addAll(id, name, company);
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

	public void fillTableView(List<Contact> list) {
		this.tableView.getItems().clear();
		this.tableView.getItems().addAll(list);
	}

}
