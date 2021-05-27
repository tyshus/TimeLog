package Apps.TimeLog.Models;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import Apps.TimeLog.Tools.PropertyLoader;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Model {

    private Model()
    {}
    
    private static Model model = null;
    public static Model getModel()
    {
        if (model == null)
        {
            model = new Model();
            createEntityManager();
        }
        return model;
    }
    
    public PropertyLoader prop = PropertyLoader.getInstance();
    
    private static EntityManager em;
    
    private static void createEntityManager() {
        try
        {
        	em = Persistence.createEntityManagerFactory("TIMELOG").createEntityManager();
        }catch(Exception e)
        {
    		displayError(e);
        }
    }
    
    public EntityManager getEntityManager()
    {
        return em;
    }
    
    public void persist(Object entity)
    {
        try
        {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }
        catch(Exception e)
        {
            displayError(e);
        }
    }
    
    public void merge(Object entity)
    {
        try
        {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        }
        catch(Exception e)
        {
            displayError(e);
        }
    }
    
    public Company GetCompany(String id) {
    	return em.find(Company.class, id);
    }
    
    public Invoice GetInvoice(String serno) {
    	return  em.find(Invoice.class, serno);
    }
    
    @SuppressWarnings("unchecked")
	public List<Contact> ContactList(){
    	return (List<Contact>) em.createQuery("FROM contacts").getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<Company> CompanyObjectList(){
    	return (List<Company>) em.createQuery("FROM companies").getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<Invoice> InvoiceList(){
    	return (List<Invoice>) em.createQuery("FROM invoices").getResultList();
    }
    
    @SuppressWarnings("unchecked")
	public List<Mail> MailList(){
    	return (List<Mail>) em.createQuery("FROM mails").getResultList();
    }

	public ObservableList<String> ContacNametList(String company) {
		ObservableList<String> result = FXCollections.observableArrayList();
		if (em != null) {
			@SuppressWarnings("unchecked")
			List<Contact> list = (List<Contact>) em.createQuery("FROM contacts WHERE company = :cpy").setParameter("cpy", company).getResultList();
			for (Contact temp : list) {
				result.add(temp.getName());
			}
        }
	    return result;
    }
	
	public String getEmails(String company, String type) {
		String emails = "";
		@SuppressWarnings("unchecked")
		List<Contact> list = (List<Contact>) em.createQuery("from contacts WHERE company = :cpy and emailtype = :type")
			.setParameter("cpy", company)
			.setParameter("type", type)
			.getResultList();
	    for (Contact temp : list) {
	    	if (!emails.isEmpty())
	    		emails += ";";
	    	emails += temp.getEmail();
        }
		return emails;
	}
	
	@SuppressWarnings("unchecked")
	public List<TimeLog> ReportData(String company,LocalDate sdat,LocalDate edat) {
		return (List<TimeLog>)  em.createQuery("FROM timelogs WHERE company = :cpy and logdate >= :sdat and logdate <= :edat")
		.setParameter("cpy", company)
		.setParameter("sdat", sdat)
		.setParameter("edat", edat)
		.getResultList();

    }

	public ObservableList<String>  CompanyList() {
		ObservableList<String> result = FXCollections.observableArrayList();
		if (em != null) {
			@SuppressWarnings("unchecked")
			List<Company> list = (List<Company>) em.createQuery("from companies").getResultList();
		    for (Company temp : list) {
	            result.add(temp.getId());
	        }
        }
	    return result;
	}
	
    private List<Runnable> listRefreshCallbacks = new ArrayList<>();
    public Runnable addRefreshCallback(Runnable callback)
    {
        listRefreshCallbacks.add(callback);
    	return listRefreshCallbacks.get(listRefreshCallbacks.size()-1);
    }
    public void removeRefreshCallback(Runnable callback)
    {
        listRefreshCallbacks.remove(callback);
    }
    public void refresh()
    {
        listRefreshCallbacks.forEach(c -> c.run());
    }
	
	public void CreateDirectory(String directoryName) {
		File directory = new File(directoryName);
		if (! directory.exists()){
			directory.mkdirs();
		}
	}
    
    public static void displayError(Exception e)
    {
        String msg = e.getMessage() + "\n\nStack trace:\n";
        for (StackTraceElement s : e.getStackTrace())
        {
            msg += s.toString() + "\n";
        }
        System.err.print(msg);
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).show();
    }
    
    public void msg(String msg)
    {
        new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK).show();
    }

    public void msgW(String msg)
    {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).show();
    }

}
