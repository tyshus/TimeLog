package Apps.TimeLog;

import Apps.TimeLog.Windows.Entry.LogEntry;
import javafx.application.Application;
import javafx.stage.Stage;



public class App extends Application
{
	@Override
	public void start(Stage stage) throws Exception {
		new LogEntry(stage);
	}	
	
    public static void main( String[] args ) 
    {		
    	launch(args);
    }
       
}
