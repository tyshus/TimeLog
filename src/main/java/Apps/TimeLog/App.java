package Apps.TimeLog;

import Apps.TimeLog.TimeLog.LogEntryControler;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		new LogEntryControler(stage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
