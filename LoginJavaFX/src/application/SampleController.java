package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SampleController implements Initializable {
	
	
	
    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

	    @FXML
	    void login(ActionEvent event) {
//	    	JOptionPane.showMessageDialog(null, "Hii");
	    	String name = username.getText();
	    	String pass = password.getText();
	    	

	    	
	    	String dbName = "heera";
	    	String dbPassword = "123";
	    	
	    	
	    	if(name.equals("") || pass.equals("")) {
	    		JOptionPane.showMessageDialog(null, "Username or password cannot blank");
	    	}
	    	else {
	    		
	    		try {
	    			
	    			String url = "jdbc:postgresql://localhost:5432/TMS";
					String user = "postgres";
					String password = "admin";
					Class.forName("org.postgresql.Driver");
					Connection conn = DriverManager.getConnection(url, user, password);
					if (conn != null) {
						System.out.println("Database connected successfully");
					}
	    			PreparedStatement ps = conn.prepareStatement("select username, password from login");
	    			ResultSet rs = ps.executeQuery();
					
					while(rs.next()) {
						dbName = rs.getString(1);
						dbPassword = rs.getString(2);
					}
					
						if(name.equals(dbName)  && pass.equals(dbPassword)) {
							JOptionPane.showMessageDialog(null, "Logged in successfully");
							
						}
						else {
							JOptionPane.showMessageDialog(null, "Username or password is incorrect !");
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
