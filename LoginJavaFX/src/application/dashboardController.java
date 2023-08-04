package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class dashboardController implements Initializable{
	
	@FXML
    private MenuItem logoutMenue;
	  
	  @FXML
	    private Button btnAllTasks;

	    @FXML
	    private Button btnAssignedTasks;

	    @FXML
	    private Button btnDashboard;

	    @FXML
	    private Button btnEmployeeLIst;

	    @FXML
	    private Button btnMyTasks;

	    @FXML
	    private Button btnProfile;

	    @FXML
	    private Label dirStatus;

	    @FXML
	    private HBox headerStatus;

	    @FXML
	    private Label headingStatus;
	    
	    @FXML
	    private Button btnLogout;
	    
	    @FXML
	    private MenuItem profileMenu;
	    // This is for body
	    
	    @FXML
	    private GridPane dashboardBody;
	    
	    @FXML
	    private GridPane empListbody;
	    
	    @FXML
	    private GridPane allTasksBody;

	    @FXML
	    private GridPane assignedTasksBody;
	    
	    @FXML
	    private GridPane myTasksBody;

	    @FXML
	    private GridPane profileBody;

	    @FXML
	    void handleClick(ActionEvent event) {
	    	if(event.getSource() == btnDashboard) {
	    		dirStatus.setText("/home/dashboard");
	    		headingStatus.setText("Dashboard");
	    		headerStatus.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
	    		dashboardBody.toFront();
	    	}
	    	else if(event.getSource() == btnAllTasks) {
	    		dirStatus.setText("/home/all tasks");
	    		headingStatus.setText("All Tasks");
	    		headerStatus.setBackground(new Background(new BackgroundFill(Color.rgb(153, 58, 194), CornerRadii.EMPTY, Insets.EMPTY)));
	    		allTasksBody.toFront();
	    	}
	    	else if(event.getSource() == btnEmployeeLIst) {
	    		dirStatus.setText("/home/employee list");
	    		headingStatus.setText("Employee List");
	    		headerStatus.setBackground(new Background(new BackgroundFill(Color.rgb(194, 58, 171), CornerRadii.EMPTY, Insets.EMPTY)));
	    		empListbody.toFront();
	    	}
	    	else if(event.getSource() == btnMyTasks) {
	    		dirStatus.setText("/home/my tasks");
	    		headingStatus.setText("My Tasks");
	    		headerStatus.setBackground(new Background(new BackgroundFill(Color.rgb(194, 58, 58), CornerRadii.EMPTY, Insets.EMPTY)));
	    		myTasksBody.toFront();
	    	}
	    	else if(event.getSource() == btnAssignedTasks) {
	    		dirStatus.setText("/home/assigned tasks");
	    		headingStatus.setText("Assigned Tasks");
	    		headerStatus.setBackground(new Background(new BackgroundFill(Color.rgb(183, 194, 58), CornerRadii.EMPTY, Insets.EMPTY)));
	    		assignedTasksBody.toFront();
	    	}
	    	else if(event.getSource() == btnProfile || event.getSource()  == profileMenu) {
	    		dirStatus.setText("/home/profile");
	    		headingStatus.setText("Profile");
	    		headerStatus.setBackground(new Background(new BackgroundFill(Color.rgb(194, 133, 58), CornerRadii.EMPTY, Insets.EMPTY)));
	    		profileBody.toFront();
	    	}
	    	else if(event.getSource() == btnLogout) {
	    		try {
	    			Main main = new Main();
	    			main.logoutAction("Sample.fxml");
	    		}catch(Exception e) {
	    			System.out.println(e);
	    		}
	    	}
	    }
	  

	    @FXML
	    void logoutAction(ActionEvent event) throws Exception {
	    	Main main = new Main();
	    	main.logoutAction("Sample.fxml");
	    }
	    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
