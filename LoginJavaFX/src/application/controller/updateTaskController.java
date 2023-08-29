package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.persistance.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class updateTaskController implements Initializable {

	@FXML
	private TextField taskName, owner;

	@FXML
	private TextArea description;

	@FXML
	private Label assignId;

	@FXML
	private ComboBox<String> priority, status, assignTo, currentOwner;

	@FXML
	private DatePicker assignDate, estimatDate;

	public String email;
	public String id;

	public Connection conn = DatabaseConnection.getConnection();

	public PreparedStatement ps;

	public ResultSet rs;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		String dateFormatPattern = "dd/MM/yyyy"; // Customize the pattern as needed
		assignDate.setConverter(new CustomDateConverter(dateFormatPattern));

		estimatDate.setConverter(new CustomDateConverter(dateFormatPattern));

		priority.getItems().addAll("Low", "Medium", "High");
		status.getItems().addAll("New", "In Progress", "Pending", "Resolved", "Closed");
		System.out.println(email + "1234");
		try {
			ps = conn.prepareStatement("Select email from users");
			rs = ps.executeQuery();
			while (rs.next()) {
				email = rs.getString("email");
				currentOwner.getItems().add(email);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// (id,taskName,assignDate,estimatedDate,priority,status,assignTo,assignId,)
	public void save(ActionEvent event) {

		if (taskName.getText().equals("") || owner.getText().equals("") || description.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "All fields are required");
		} else if (priority.getValue().equals("") || status.getValue().equals("") || currentOwner.getValue()==null  || estimatDate.getValue() == null) {
			JOptionPane.showMessageDialog(null, "All fields are required");
		} else {
			try {
				ps = conn.prepareStatement("update tasks set taskName=?, assignDate=?, estimatedDate=?, priority=?,status=?, "
						+ "assignTo=?, assignId=?, owner=?,currentOwner=?,description=? WHERE id=?");
				
				ps.setString(1, taskName.getText());
				if (assignDate.getValue() == null) {
					ps.setNull(2, java.sql.Types.DATE);
				} else {
					ps.setDate(2, Date.valueOf(assignDate.getValue()));
				}
				ps.setDate(3, Date.valueOf(estimatDate.getValue()));
				ps.setString(4, priority.getValue());
				ps.setString(5, status.getValue());
				if (assignTo.getValue() == null) {
					ps.setString(6,"Not Assigned");
				} else {
					ps.setString(6, assignTo.getValue());
				}
				if (assignId.getText().equals("Click here fetch employee id")) {
					ps.setInt(7, 0);
				} else {
					ps.setInt(7, Integer.parseInt(assignId.getText()));
				}
				ps.setString(8, owner.getText());
				ps.setString(9, currentOwner.getValue());
				ps.setString(10, description.getText());
				ps.setString(11, id);
				int i = ps.executeUpdate();
				if (i > 0) {
					JOptionPane.showMessageDialog(null, "Data Update successfully");
					Stage stage=(Stage)owner.getScene().getWindow();
					stage.close();
					
				} else {
					JOptionPane.showMessageDialog(null, "Technical error data could not Update");
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void getId(String email, List<String> list,String id)
	{
		
		try {
			System.out.println("update 3"+id);
			ps = conn.prepareStatement("Select * from tasks where id=?");
			ps.setString(1,id);
			rs = ps.executeQuery();
			while (rs.next()) {
				taskName.setText(rs.getString("taskName"));
				description.setText(rs.getString("description"));
				priority.setValue(rs.getString("priority"));
				status.setValue(rs.getString("status"));
				Date assigDate=rs.getDate("assignDate");
				Date estimateDate=rs.getDate("estimatedDate");
				if(estimateDate==null)
				{
					assignDate.setValue(null);
				}
				else
				{
				assignDate.setValue(assigDate.toLocalDate());
				}
				estimatDate.setValue(estimateDate.toLocalDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.id=id;
		assignTo.getItems().addAll(list);
		this.email = email;
		owner.setText(email);
	}

	public void empId(MouseEvent event) {
		String name = assignTo.getValue();
		int id = 0;
		try {
			ps = conn.prepareStatement("Select id from employee where name=?");
			ps.setString(1, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
			}
			assignId.setText(String.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
