package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
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

public class AddTaskController implements Initializable {

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
		} else if (priority.getValue().equals("") || status.getValue().equals("") || currentOwner.getValue()==null || estimatDate.getValue() == null) {
			JOptionPane.showMessageDialog(null, "All fields are required");
		} else {
			String uniqueId = "T-" + String.valueOf(Instant.now().toEpochMilli());
			try {
				ps = conn.prepareStatement("insert into tasks values(?,?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, uniqueId);
				ps.setString(2, taskName.getText());
				if (assignDate.getValue() == null) {
					ps.setNull(3, java.sql.Types.DATE);
				} else {
					ps.setDate(3, Date.valueOf(assignDate.getValue()));
				}
				ps.setDate(4, Date.valueOf(estimatDate.getValue()));
				ps.setString(5, priority.getValue());
				ps.setString(6, status.getValue());
				ps.setString(7, assignTo.getValue());
				if (assignId.getText().equals("Click here fetch employee id")) {
					ps.setInt(8, 0);
				} else {
					ps.setInt(8, Integer.parseInt(assignId.getText()));
				}
				ps.setString(9, owner.getText());
				ps.setString(10, currentOwner.getValue());
				ps.setString(11, description.getText());
				int i = ps.executeUpdate();
				if (i > 0) {
					JOptionPane.showMessageDialog(null, "Data save successfully");
					taskName.setText("");
					assignDate.setValue(null);
					estimatDate.setValue(null);
					priority.setValue("");
					status.setValue("");
					assignTo.setValue("");
					currentOwner.setValue("");
					description.setText("");
					if (assignId.getText().equals("Click here fetch employee id")) {
						System.out.println("Data save successfully");
					} else {
						assignId.setText("Click here fetch employee id");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Technical error data could not save");
					taskName.setText("");
					assignDate.setValue(null);
					estimatDate.setValue(null);
					priority.setValue("");
					status.setValue("");
					assignTo.setValue("");
					currentOwner.setValue("");
					description.setText("");
					if (assignId.getText().equals("Click here fetch employee id")) {
						System.out.println("Data save successfully");
					} else {
						assignId.setText("Click here fetch employee id");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void getId(String email, List<String> list) {
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
