package Apps.TimeLog.Windows.List;

import Apps.TimeLog.Models.Model;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class WindowList {
	Stage stage = new Stage();
	Button newBtn = new Button ("New");
	Button editBtn = new Button ("Edit");
	Model model = Model.getModel();
    GridPane grid = new GridPane();
    Scene scene = new Scene(new VBox(), 500, 550);
    VBox root = (VBox)scene.getRoot();
	Runnable callback;
	WindowList() {
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(15, 10, 10, 10));
        grid.add(newBtn, 0, 0);
        grid.add(editBtn, 1, 0);
        newBtn.setOnAction(value ->  {newEntry();});
        editBtn.setOnAction(value ->  {editEntry();});
		scene.getStylesheets().add("windowList.css");
        root.getChildren().add(grid);
	    stage.setScene(scene);
	    stage.show();
	    callback = model.addRefreshCallback(() -> LoadData());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                model.removeRefreshCallback(callback);
            }
        }); 
	}
	abstract void newEntry();
	abstract void editEntry();
	abstract void AddColumns();
	abstract void LoadData();
}
