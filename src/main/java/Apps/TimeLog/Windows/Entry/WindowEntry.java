package Apps.TimeLog.Windows.Entry;

import Apps.TimeLog.Models.Model;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class WindowEntry {
	Stage stage = new Stage();
	GridPane grid = new GridPane();
	Scene scene = new Scene(new VBox(), 450, 250);
	Button saveBtn = new Button("Save");
	Model model = Model.getModel();
	VBox root = (VBox) scene.getRoot();

	public WindowEntry() {
		saveBtn.setOnAction(value -> {
			save();
		});
		grid.setVgap(4);
		grid.setHgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		root.getChildren().add(grid);
		stage.setScene(scene);
		stage.show();
	}

	abstract void save();
}
