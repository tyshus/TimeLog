package Apps.TimeLog.Company;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class CompanyList extends VBox {
	private TableColumn<Company, String> id = new TableColumn<>("Code");
	private TableColumn<Company, String> name = new TableColumn<>("Name");
	private TableColumn<Company, String> address = new TableColumn<>("Address");
	private TableColumn<Company, String> code = new TableColumn<>("Company code");
	private TableColumn<Company, String> vatRegNo = new TableColumn<>("VAT reg. no.");
	private @Getter TableView<Company> tableView = new TableView<Company>();
	private @Getter Button newBtn = new Button("New");
	private @Getter Button editBtn = new Button("Edit");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public CompanyList(CompanyListControler companyListControler) {
		newBtn.setOnAction(companyListControler);
		editBtn.setOnAction(companyListControler);
		this.setScene();
	}

	@SuppressWarnings("unchecked")
	void setScene() {
		this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.address.setCellValueFactory(new PropertyValueFactory<>("address"));
		this.code.setCellValueFactory(new PropertyValueFactory<>("code"));
		this.vatRegNo.setCellValueFactory(new PropertyValueFactory<>("vatRegNo"));
		this.tableView.getColumns().addAll(id, name, address, code, vatRegNo);
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

	public void fillTableView(List<Company> list) {
		this.tableView.getItems().clear();
		this.tableView.getItems().addAll(list);
	}
}
