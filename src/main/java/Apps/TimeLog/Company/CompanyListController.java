package Apps.TimeLog.Company;

import java.util.List;

import Apps.TimeLog.Tools.Model;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Setter;

@SuppressWarnings("rawtypes")
public class CompanyListController implements EventHandler {
	private final CompanyList companyList;
	private @Setter List<Company> list;
	private Model model = Model.getModel();
	private Stage stage;

	public CompanyListController() {
		stage = new Stage();
		this.companyList = new CompanyList(this);
		this.loadData();
		this.fillTable();
		this.stage.setTitle("Company list");
		this.stage.setScene(new Scene(companyList, 500, 550));
		this.stage.show();
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.companyList.getNewBtn())) {
			newEntry();
		}
		if (source.equals(this.companyList.getEditBtn())) {
			editEntry();
		}

	}

	void newEntry() {
		new CompanyEntryController(new Company());
	}

	void editEntry() {
		Company company = (Company) this.companyList.getTableView().getSelectionModel().getSelectedItem();
		if (company != null) {
			new CompanyEntryController(company);
		} else {
			model.msgW("No contact selected!");
		}
	}

	public void loadData() {
		this.list = this.model.loadCompanyList();
	}

	public void fillTable() {
		this.companyList.fillTableView(list);
	}

}
