package application;
	
import application.controller.dashboardController;
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
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/fxml/Sample.fxml"));
			Scene scene = new Scene(root,500,400);
			stg.getIcons().add(new Image("image/xyz.jfif"));
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
		stg.getIcons().add(new Image("image/xyz.jfif"));
		stg.setTitle("Register");
		stg.setScene(scene);
		stg.setResizable(false);
		stg.show();
	
	}
	public void dashboardHandle(String email) throws Exception{
		
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/fxml/dashboard.fxml"));
		Parent  pane = loader.load();
		dashboardController dashController=loader.getController();
		dashController.getId(email);
		System.out.print(email+"main");
		Scene scene = new Scene(pane);
		stg.getIcons().add(new Image("image/xyz.jfif"));
		stg.setTitle("Dashboard");
		stg.setScene(scene);
		stg.setResizable(true);
		stg.show();
		
	}
	
	
	public void logoutAction(String fxml) throws Exception{
		Parent  pane = FXMLLoader.load(getClass().getResource(fxml));
		Scene scene = new Scene(pane,500,400);
		stg.getIcons().add(new Image("image/xyz.jfif"));
		stg.setTitle("Login");
		stg.setScene(scene);
		stg.setResizable(false);
		stg.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
