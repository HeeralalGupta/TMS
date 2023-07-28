package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import in.pandit.persistance.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SampleController implements Initializable {
	
    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;
    
    @FXML
    private Label wrongMsg;
    
    @FXML
    private TextField email;
    
    @FXML
    private Button regBtn;
  

    @FXML
    void registerPage(ActionEvent event) {
    	Main main = new Main();
    	try {
    		main.registerPageController("signUp.fxml");
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    }
   
    
	    @FXML
	    void login(ActionEvent event) {
	    	Main m = new Main();
//	    	JOptionPane.showMessageDialog(null, "Hii");
	    	String emailId = email.getText();
	    	String pass = password.getText();
	    	

	    	
	    	String dbEmail = null;
	    	String dbPassword = null;
	    	
	    	
	    	if(emailId.equals("") || pass.equals("")) {
	    		JOptionPane.showMessageDialog(null, "Username or password cannot blank");
	    	}
	    	else {
	    		
	    		try {
	    			
					Connection conn = DatabaseConnection.getConnection();
					
	    			PreparedStatement ps = conn.prepareStatement("select email, password from users where email = ?");
	    			ps.setString(1, emailId);
	    			ResultSet rs = ps.executeQuery();
					
					while(rs.next()) {
						dbEmail = rs.getString(1);
						dbPassword = rs.getString(2);
					}
					
						if(emailId.equals(dbEmail)  && pass.equals(dbPassword)) {
							m.dashboardController("dashboard.fxml");
							JOptionPane.showMessageDialog(null, "Logged in successfully");
							
						}
						else {
							wrongMsg.setText("Wrong username or password");
//							JOptionPane.showMessageDialog(null, "Username or password is incorrect !");
						}	
					
	    		}catch(Exception e) {
	    			System.out.println(e);
	    		}
	    		
	    	}
	    	
	    }
	    
	  
	  

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
  
}
