package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class dashboardController implements Initializable{
	
	  @FXML
	    private Button btnLogout;

	    @FXML
	    void logout(ActionEvent event) throws Exception {
	    	Main main = new Main();
//	    	main.dashboardController("Sample.fxml");
	    	main.logoutAction("Sample.fxml");
	    }
	    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
