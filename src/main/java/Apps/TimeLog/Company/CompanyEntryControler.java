package Apps.TimeLog.Company;

import Apps.TimeLog.Models.Model;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("rawtypes")
public class CompanyEntryControler implements EventHandler {
	private final CompanyEntry companyEntry;
	private Model model = Model.getModel();
	private Company company;
	private Stage stage;

	public CompanyEntryControler(Company company) {
		this.stage = new Stage();
		this.company = company;
		this.companyEntry = new CompanyEntry(this);
		this.companyEntry.setCompany(company);
		this.stage.setTitle("Company");
		this.stage.setScene(new Scene(companyEntry, 450, 250));
		this.stage.show();
	}

	@Override
	public void handle(Event event) {
		final Object source = event.getSource();
		if (source.equals(this.companyEntry.getSaveBtn())) {
			save();
		}
	}

	private void save() {
		companyEntry.getCompany(company);
		if (model.getCompany(company.getId()) == null) {
			this.model.persist(company);
		} else {
			this.model.merge(company);
		}
		this.stage.close();
	}
}
