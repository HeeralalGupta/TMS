package application.controller;

import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


import javax.swing.JOptionPane;

import application.persistance.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class UpdateProfileController implements Initializable {

	@FXML
	private TextField nameField;
	@FXML
	private TextField emailField;

	@FXML
	private TextField mobileField;
	@FXML
	private Label idField;

	public Connection conn = DatabaseConnection.getConnection();

	public PreparedStatement ps;

	public ResultSet rs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void getId(String id,String name,String email,String mobile)
	{
		nameField.setText(name);
		emailField.setText(email);
		mobileField.setText(mobile);
		idField.setText(id);
	}
	
	@FXML
    private void saveBtn(ActionEvent event)
    {
		try {
			ps=conn.prepareStatement("update users set name=?, email=?, mobile=? WHERE id=?");
			ps.setString(1, nameField.getText());
			ps.setString(2, emailField.getText());
			ps.setString(3, mobileField.getText());
			ps.setInt(4,Integer.parseInt(idField.getText()));
			int i=ps.executeUpdate();
			if(i>0)
			{
				JOptionPane.showMessageDialog(null, "Data Update successfully");
				Stage stage=(Stage)nameField.getScene().getWindow();
				stage.close();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Technical error data could not Update");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
}
