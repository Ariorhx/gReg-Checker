package Test.GregFastCheck;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application{
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = loader.load();
		
        Scene scene = new Scene(root);
		stage.setTitle("gReg filament");
		stage.setScene(scene);	
		stage.show();
		
		GUIController controller = (GUIController)loader.getController();
		stage.setOnCloseRequest(e -> controller.gdb.close_connection());
	}
	public static void main(String[] args) {
		launch(args);
	}

}
