package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Start of the application
 * @author William Huynh Justin Alan Marcus Douglas
 * @precondition: none
 *
 */
public class Main extends Application {
	private static final String MAIN_WINDOW = "../view/Main.fxml";
	private static final String MAIN_WINDOW_TITLE = "Chatroom";
	
	/**
	 * Sets the frame for application
	 * precondition: none
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(Main.MAIN_WINDOW));
		loader.load();
		Parent parent = loader.getRoot();
		Scene scene = new Scene(parent);
		primaryStage.setTitle(Main.MAIN_WINDOW_TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Launches the application
	 * @param args
	 * precondition: none
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
