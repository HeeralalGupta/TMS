package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import application.persistance.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class registerController {

    @FXML
    private Button backLogin;

    @FXML
    private TextField email;

    @FXML
    private TextField mobile;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Button registerBtn;

    @FXML
    void backToLogin(ActionEvent event) {
    	try {
    	Main main = new Main();
//    	main.dashboardController("Sample.fxml");
    	main.logoutAction("Sample.fxml");
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    }

    @FXML
    void register(ActionEvent event) {
    	String username = name.getText();
    	String emailId = email.getText();
    	String mobileNo = mobile.getText();
    	String pass = password.getText();
    	if(username.equals("") || emailId.equals("") || mobileNo.equals("") || pass.equals("")) {
    		JOptionPane.showInternalMessageDialog(null, "Some fields are blank");
    	}else {
    	try {
    		Connection conn = DatabaseConnection.getConnection();
//    		 Fetching max id from sign up table for auto increment id
 			int count = 0;
 			PreparedStatement st = conn.prepareStatement("select max(id) as id from users");

 			ResultSet rs = st.executeQuery();
 			while (rs.next()) {
 				count = rs.getInt("id");
 			}
 				
 			// Inserting data into data base
 			PreparedStatement stmt = conn.prepareStatement("insert into users values(?, ?, ?, ?, ?)");
 			stmt.setInt(1, ++count);
 			stmt.setString(2, username);
 			stmt.setString(3, emailId);
 			stmt.setString(4, mobileNo);
 			stmt.setString(5, pass);
 			int status = stmt.executeUpdate();
 		if(status > 0) {
 			Main main = new Main();
	    	main.logoutAction("Sample.fxml");
	    	JOptionPane.showMessageDialog(null, "Registerd successfully");
 		}
 		else {
 			JOptionPane.showMessageDialog(null, "Something went wrong!");
 		}
    		
    	}catch(Exception e) {
    		System.out.println(e);
    	}
    }
   }

}
