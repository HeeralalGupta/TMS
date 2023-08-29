package application.controller;

import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.swing.JOptionPane;

import application.persistance.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateEmpController implements Initializable {
	@FXML
	private TextField nameField;
	@FXML
	private TextField designationField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField mobileField;
	
	public PreparedStatement ps;
	
	public Connection conn=DatabaseConnection.getConnection();
	
	public int id;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
    public void getDetail(int id,String name,String designation,String email,String mobile)
    {
    	this.id=id;
    	nameField.setText(name);
    	designationField.setText(designation);
    	emailField.setText(email);
    	mobileField.setText(mobile);
    }
    @FXML
    private void update(ActionEvent event)
    {

		if (nameField.getText().equals("") || designationField.getText().equals("") 
				|| emailField.getText().equals("") ||mobileField.getText().equals("")) 
		{
			JOptionPane.showMessageDialog(null, "All fields are required");
		}
		else {
			try {
				ps = conn.prepareStatement("update employee set name=?, designation=?, email=?, mobile=? WHERE id=?");
				
				
				ps.setString(1, nameField.getText());
				ps.setString(2, designationField.getText());
				ps.setString(3, emailField.getText());
				ps.setString(4, mobileField.getText());
				ps.setInt(5, id);
				
				int i = ps.executeUpdate();
				if (i > 0) {
					JOptionPane.showMessageDialog(null, "Data Update successfully");
					Stage stage=(Stage) nameField.getScene().getWindow();
					stage.close();
					
				} else {
					JOptionPane.showMessageDialog(null, "Technical error data could not Update");
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

    }
    
    
    @FXML
	private void saveEmp() {
    	if (nameField.getText().equals("") || designationField.getText().equals("") 
				|| emailField.getText().equals("") ||mobileField.getText().equals("")) 
		{
			JOptionPane.showMessageDialog(null, "All fields are required");
		}
		else {
			try {
				ps = conn.prepareStatement("insert into employee(id,name,designation,email,mobile) values(?,?,?,?,?)");
				
				UUID uuid = UUID.randomUUID();
				int id=uuid.hashCode();
				System.out.println(id);
				ps.setInt(1, id);
				ps.setString(2, nameField.getText());
				ps.setString(3, designationField.getText());
				ps.setString(4, emailField.getText());
				ps.setString(5, mobileField.getText());
				
				
				int i = ps.executeUpdate();
				if (i > 0) {
					JOptionPane.showMessageDialog(null, "Data save successfully");
					nameField.setText(""); 
					designationField.setText("");
					emailField.setText("");
					mobileField.setText("");
					
				} else {
					JOptionPane.showMessageDialog(null, "Technical error data could not Update");
					nameField.setText("");
					designationField.setText("");
					emailField.setText("");
					mobileField.setText("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
 }
}
