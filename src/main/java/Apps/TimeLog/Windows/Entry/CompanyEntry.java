package Apps.TimeLog.Windows.Entry;

import Apps.TimeLog.Models.Company;
import Apps.TimeLog.Windows.Fields.DoubleTextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CompanyEntry extends WindowEntry {
	private TextField id = new TextField("");
	private TextField name = new TextField("");
	private TextField address = new TextField("");
	private TextField code = new TextField("");
	private TextField vatRegNo = new TextField("");
	private Company company = new Company();
	boolean EditFlg = false;
	private DoubleTextField rate = new DoubleTextField();

	public CompanyEntry() {
		stage.setTitle("Company");
		grid.add(new Label("Code: "), 0, 0);
		grid.add(id, 1, 0);
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
	}

	public void setCompany(Company company) {
		this.company = company;
		EditFlg = true;
		id.setEditable(false);
		id.setText(company.getId());
		name.setText(company.getName());
		address.setText(company.getAddress());
		code.setText(company.getCode());
		vatRegNo.setText(company.getVatRegNo());
		rate.SetDouble(company.getRate());
	}

	@Override
	void save() {
		company.setId(id.getText());
		company.setName(name.getText());
		company.setAddress(address.getText());
		company.setCode(code.getText());
		company.setVatRegNo(vatRegNo.getText());
		company.setRate(rate.getDouble());
		if (EditFlg == false) {
			model.persist(company);
		} else {
			model.merge(company);
		}
		model.refresh();
		stage.close();
	}

}
