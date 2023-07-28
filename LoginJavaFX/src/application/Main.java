package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	private static Stage stg;
	@Override
	public void start(Stage primaryStage) {
		try {
			stg = primaryStage;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,500,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Login Page");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void registerPageController(String fxml) throws Exception{
		Parent  pane = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(pane,512,510);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stg.getIcons().add(new Image("C:\\Users\\Heera\\eclipse-workspace\\LoginJavaFX\\src\\image\\xyz.jfif"));
		stg.setTitle("Register");
		stg.setScene(scene);
		stg.setResizable(false);
		stg.show();
		
	}
	public void dashboardController(String fxml) throws Exception{
		Parent  pane = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(pane,942,648);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stg.setTitle("Dashboard");
		stg.setScene(scene);
		stg.setResizable(false);
		stg.show();
		
	}
	
	public void logoutAction(String fxml) throws Exception{
		Parent  pane = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(pane,500,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stg.setTitle("Dashboard");
		stg.setScene(scene);
		stg.setResizable(false);
		stg.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
