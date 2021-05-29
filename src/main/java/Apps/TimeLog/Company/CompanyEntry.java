package Apps.TimeLog.Company;

import Apps.TimeLog.Windows.Fields.DoubleTextField;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class CompanyEntry extends VBox {
	private TextField idField = new TextField("");
	private TextField name = new TextField("");
	private TextField address = new TextField("");
	private TextField code = new TextField("");
	private TextField vatRegNo = new TextField("");
	private DoubleTextField rate = new DoubleTextField();
	private @Getter Button saveBtn = new Button("Save");
	private GridPane grid = new GridPane();

	@SuppressWarnings("unchecked")
	public CompanyEntry(CompanyEntryControler companyEntryControler) {
		saveBtn.setOnAction(companyEntryControler);
		this.setScene();
	}

	public void setScene() {
		grid.add(new Label("Code: "), 0, 0);
		grid.add(idField, 1, 0);
		grid.add(new Label("Name: "), 0, 1);
		grid.add(name, 1, 1);
		grid.add(new Label("Address: "), 0, 2);
		grid.add(address, 1, 2);
		grid.add(new Label("Company code: "), 0, 3);
		grid.add(code, 1, 3);
		grid.add(new Label("VAT reg. no.: "), 0, 4);
		grid.add(vatRegNo, 1, 4);
		grid.add(new Label("Hourly rate (EUR): "), 0, 5);
		grid.add(rate, 1, 5);
		grid.add(saveBtn, 0, 6);
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		this.getChildren().add(grid);
	}

	public void setCompany(Company company) {
		if (company.getId() != null) {
			idField.setText(company.getId());
			name.setText(company.getName());
			address.setText(company.getAddress());
			code.setText(company.getCode());
			vatRegNo.setText(company.getVatRegNo());
			rate.SetDouble(company.getRate());
		}
	}

	public void getCompany(Company company) {
		company.setId(idField.getText());
		company.setName(name.getText());
		company.setAddress(address.getText());
		company.setCode(code.getText());
		company.setVatRegNo(vatRegNo.getText());
		company.setRate(rate.getDouble());
	}
}
