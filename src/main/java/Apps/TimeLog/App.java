package Apps.TimeLog;

import Apps.TimeLog.TimeLog.LogEntryController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		new LogEntryController(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
