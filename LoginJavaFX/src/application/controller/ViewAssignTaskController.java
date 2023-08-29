package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class ViewAssignTaskController implements Initializable {
	
	@FXML
	private Label idLabel;
	@FXML
	private Label taskNameLabel;
	@FXML
	private Label estimateDateLabel;
	@FXML
	private Label assignDateLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private Label assignToLabel;
	@FXML
	private Label priorityLabel;
	@FXML
	private Label ownerLabel;
	@FXML
	private Label currentOwnerLabel;
	@FXML
	private Label assignIdLabel;
	@FXML
	private TextArea descriptionLabel;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void data(String id, String taskName, String estimateDate, String status, String assignTo,
			String currentOwner, String owner, String assignDate, String priority, String description,
			String assignId)
	{
		idLabel.setText(id);
	    taskNameLabel.setText(taskName);
	    estimateDateLabel.setText(estimateDate);
		assignDateLabel.setText(assignDate);
		statusLabel.setText(status);
		assignToLabel.setText(assignTo);
		priorityLabel.setText(priority);
		ownerLabel.setText(owner);
		currentOwnerLabel.setText(currentOwner);
		assignIdLabel.setText(assignId);
		descriptionLabel.setText(description);
		
	}

}
