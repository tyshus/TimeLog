package Apps.TimeLog.Models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import Apps.TimeLog.Contact.Contact;
import Apps.TimeLog.Tools.PropertyLoader;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Model {
	private static Model model = null;
	public PropertyLoader prop = PropertyLoader.getInstance();
	private static EntityManager em;
	private List<Runnable> listRefreshCallbacks = new ArrayList<>();

	private Model() {
	}

	public static Model getModel() {
		if (model == null) {
			model = new Model();
			createEntityManager();
		}
		return model;
	}

	private static void createEntityManager() {
		try {
			em = Persistence.createEntityManagerFactory("TIMELOG").createEntityManager();
		} catch (Exception e) {
			displayError(e);
		}
	}

	public EntityManager getEntityManager() {
		return em;
	}

	public void persist(Object entity) {
		try {
			em.getTransaction().begin();
			em.persist(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			displayError(e);
		}
	}

	public void merge(Object entity) {
		try {
			em.getTransaction().begin();
			em.merge(entity);
			em.getTransaction().commit();
		} catch (Exception e) {
			displayError(e);
		}
	}

	public Company getCompany(String id) {
		if (em == null) {
			return null;
		}
		return em.find(Company.class, id);
	}

	public Invoice getInvoice(String serno) {
		if (em == null) {
			return null;
		}
		return em.find(Invoice.class, serno);
	}

	@SuppressWarnings("unchecked")
	public List<Contact> loadContactList() {
		if (em == null) {
			return Collections.emptyList();
		}
		return (List<Contact>) em.createQuery("FROM contacts").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Company> loadCompanyList() {
		if (em == null) {
			return Collections.emptyList();
		}
		return (List<Company>) em.createQuery("FROM companies").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Invoice> loadInvoiceList() {
		if (em == null) {
			return Collections.emptyList();
		}
		return (List<Invoice>) em.createQuery("FROM invoices").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Mail> loadMailList() {
		if (em == null) {
			return Collections.emptyList();
		}
		return (List<Mail>) em.createQuery("FROM mails").getResultList();
	}

	public String getEmails(String company, String type) {
		if (em == null) {
			return null;
		}
		String emails = "";
		@SuppressWarnings("unchecked")
		List<Contact> list = (List<Contact>) em.createQuery("from contacts WHERE company = :cpy and emailtype = :type")
				.setParameter("cpy", company).setParameter("type", type).getResultList();
		for (Contact temp : list) {
			if (!emails.isEmpty())
				emails += ";";
			emails += temp.getEmail();
		}
		return emails;
	}

	@SuppressWarnings("unchecked")
	public List<TimeLog> loadReportData(String company, LocalDate sdat, LocalDate edat) {
		if (em == null) {
			return Collections.emptyList();
		}
		return (List<TimeLog>) em
				.createQuery("FROM timelogs WHERE company = :cpy and logdate >= :sdat and logdate <= :edat")
				.setParameter("cpy", company).setParameter("sdat", sdat).setParameter("edat", edat).getResultList();
	}

	public ObservableList<String> loadContacts(String company) {
		ObservableList<String> observableList = FXCollections.observableArrayList();
		if (em == null) {
			return observableList;
		}

		@SuppressWarnings("unchecked")
		List<Contact> list = (List<Contact>) em.createQuery("FROM contacts WHERE company = :cpy")
				.setParameter("cpy", company).getResultList();
		for (Contact temp : list) {
			observableList.add(temp.getName());
		}
		return observableList;
	}

	public ObservableList<String> loadCompanies() {
		ObservableList<String> observableList = FXCollections.observableArrayList();
		if (em == null) {
			return observableList;
		}
		@SuppressWarnings("unchecked")
		List<Company> list = (List<Company>) em.createQuery("from companies").getResultList();
		for (Company temp : list) {
			observableList.add(temp.getId());
		}
		return observableList;
	}

	public Runnable addRefreshCallback(Runnable callback) {
		listRefreshCallbacks.add(callback);
		return listRefreshCallbacks.get(listRefreshCallbacks.size() - 1);
	}

	public void removeRefreshCallback(Runnable callback) {
		listRefreshCallbacks.remove(callback);
	}

	public void refresh() {
		listRefreshCallbacks.forEach(c -> c.run());
	}

	public void createDirectory(String directoryName) {
		File directory = new File(directoryName);
		if (!directory.exists()) {
			directory.mkdirs();
		}
	}

	public static void displayError(Exception e) {
		String msg = e.getMessage() + "\n\nStack trace:\n";
		for (StackTraceElement s : e.getStackTrace()) {
			msg += s.toString() + "\n";
		}
		System.err.print(msg);
		new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).show();
	}

	public void msg(String msg) {
		new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK).show();
	}

	public void msgW(String msg) {
		new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).show();
	}

}
