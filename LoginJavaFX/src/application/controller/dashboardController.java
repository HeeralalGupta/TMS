package application.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import application.Main;
import application.persistance.DatabaseConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;

public class dashboardController implements Initializable {

	@FXML
	private Label btnAllTasks;

	@FXML
	private Label btnAssignedTasks;

	@FXML
	private Label btnDashboard;

	@FXML
	private Label btnEmployeeLIst;

	@FXML
	private Label btnMyTasks;

	@FXML
	private Label btnProfile;

	@FXML
	private Label dirStatus;

	@FXML
	private HBox headerStatus1;

	@FXML
	private Label headingStatus;

	@FXML
	private ImageView icon;

	@FXML
	private Button btnLogout;

	@FXML
	private AnchorPane ap;

	@FXML
	private AnchorPane ap1;

	@FXML
	private BorderPane bp;

	public String eml;
	
	 @FXML
	 private PieChart pieChart;
	 
	 @FXML
		private Label allTask;
	 @FXML
		private Label pending;
	 @FXML
		private Label progress;
	 @FXML
		private Label completed;
	 
	 @FXML
	    private BarChart<String, Number> barChart;

	    @FXML
	    private CategoryAxis xAxis;

	    @FXML
	    private NumberAxis yAxis;
	    
	    public XYChart.Series<String, Number> series;
	// This is for body

	/*
	 * @FXML void handleClick(MouseDragEvent event) { if(event.getSource() ==
	 * btnDashboard) { dirStatus.setText("/home/dashboard");
	 * headingStatus.setText("Dashboard"); headerStatus1.setBackground(new
	 * Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY,
	 * Insets.EMPTY))); bp.setCenter(ap); } else if(event.getSource() ==
	 * btnAllTasks) { dirStatus.setText("/home/all tasks");
	 * headingStatus.setText("All Tasks"); headerStatus1.setBackground(new
	 * Background(new BackgroundFill(Color.rgb(153, 58, 194), CornerRadii.EMPTY,
	 * Insets.EMPTY))); bp.getChildren().removeAll(ap); loadPage("allTasks"); } else
	 * if(event.getSource() == btnEmployeeLIst) {
	 * dirStatus.setText("/home/employee list");
	 * headingStatus.setText("Employee List"); headerStatus1.setBackground(new
	 * Background(new BackgroundFill(Color.rgb(194, 58, 171), CornerRadii.EMPTY,
	 * Insets.EMPTY))); bp.getChildren().removeAll(ap); loadPage("empList"); } else
	 * if(event.getSource() == btnMyTasks) { dirStatus.setText("/home/my tasks");
	 * headingStatus.setText("My Tasks"); headerStatus1.setBackground(new
	 * Background(new BackgroundFill(Color.rgb(194, 58, 58), CornerRadii.EMPTY,
	 * Insets.EMPTY))); bp.getChildren().removeAll(ap); loadPage("myTasks"); } else
	 * if(event.getSource() == btnAssignedTasks) {
	 * dirStatus.setText("/home/assigned tasks");
	 * headingStatus.setText("Assigned Tasks"); headerStatus1.setBackground(new
	 * Background(new BackgroundFill(Color.rgb(183, 194, 58), CornerRadii.EMPTY,
	 * Insets.EMPTY))); bp.getChildren().removeAll(ap); loadPage("assignTasks"); }
	 * 
	 * 
	 * }
	 */

	public void getId(String email) {
		System.out.print(email + "dash");
		this.eml = email;
	}

	
	@FXML
	void btnDashboard(MouseEvent event) {
		dirStatus.setText("/home/dashboard");
		headingStatus.setText("Dashboard");
		headerStatus1.setBackground(
				new Background(new BackgroundFill(Color.valueOf("#47bc99"), CornerRadii.EMPTY, Insets.EMPTY)));
		bp.setCenter(ap);
	    barChart.getData().remove(series);
	    ObservableList<PieChart.Data> data = pieChart.getData();
        data.clear(); 
		chart();
	}

	@FXML
	void btnAllTasks(MouseEvent event) {
		dirStatus.setText("/home/all tasks");
		headingStatus.setText("All Tasks");
		headerStatus1.setBackground(
				new Background(new BackgroundFill(Color.rgb(153, 58, 194), CornerRadii.EMPTY, Insets.EMPTY)));
		bp.getChildren().removeAll(ap);
		loadPage("allTasks");

	}

	@FXML
	void btnEmployeeLIst(MouseEvent event) {
		dirStatus.setText("/home/employee list");
		headingStatus.setText("Employee List");
		headerStatus1.setBackground(
				new Background(new BackgroundFill(Color.rgb(194, 58, 171), CornerRadii.EMPTY, Insets.EMPTY)));
		bp.getChildren().removeAll(ap);
		loadPage("empList");
	}

	@FXML
	void btnMyTasks(MouseEvent event) {
		dirStatus.setText("/home/my tasks");
		headingStatus.setText("My Tasks");
		headerStatus1.setBackground(
				new Background(new BackgroundFill(Color.rgb(194, 58, 58), CornerRadii.EMPTY, Insets.EMPTY)));
		bp.getChildren().removeAll(ap);

		List<String> list = new ArrayList<>();
		Parent root = null;
		String name = null;
		try {

			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select name from employee");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				name = rs.getString("name");
				list.add(name);
			}

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/myTasks.fxml"));
			root = loader.load();
			AddTaskController addTask = loader.getController();
			addTask.getId(eml, list);
			System.out.print(eml + "dashController");
		} catch (Exception e) {
			e.printStackTrace();
		}
		bp.setCenter(root);
	}

	@FXML
	void btnAssignedTasks(MouseEvent event) {
		dirStatus.setText("/home/assigned tasks");
		headingStatus.setText("Assigned Tasks");
		headerStatus1.setBackground(
				new Background(new BackgroundFill(Color.rgb(183, 194, 58), CornerRadii.EMPTY, Insets.EMPTY)));
		bp.getChildren().removeAll(ap);
		loadPage("assignTasks");
	}

	public void loadPage(String page) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/application/fxml/" + page + ".fxml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		bp.setCenter(root);
	}

	private void profile(ActionEvent event) {
		dirStatus.setText("/home/profile");
		headingStatus.setText("Profile");
		headerStatus1.setBackground(
				new Background(new BackgroundFill(Color.rgb(194, 0, 58), CornerRadii.EMPTY, Insets.EMPTY)));
		bp.getChildren().removeAll(ap);
		Parent root=null;
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/profile.fxml"));
			root = loader.load();
			ProfileController addTask = loader.getController();
			addTask.getId(eml);		
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		bp.setCenter(root);
	}

	private void logOut(ActionEvent event) {
		try {
			Main main = new Main();
//	   	    	main.dashboardController("Sample.fxml");
			main.logoutAction("/application/fxml/Sample.fxml");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Image iconImage = new Image(getClass().getResourceAsStream("/image/logout.png"));
		ImageView iconImageView = new ImageView(iconImage);
		iconImageView.setFitWidth(20); // Set the desired width of the icon
		iconImageView.setFitHeight(20);

		Image iconImage1 = new Image(getClass().getResourceAsStream("/image/user.png"));
		ImageView iconImageView1 = new ImageView(iconImage1);
		iconImageView1.setFitWidth(20); // Set the desired width of the icon
		iconImageView1.setFitHeight(20);

		MenuItem item1 = new MenuItem(" " + "My Profile", iconImageView1);
		MenuItem item2 = new MenuItem(" " + "Log out", iconImageView);

		item1.setOnAction(this::profile);
		item2.setOnAction(this::logOut);

		ContextMenu contextMenu = new ContextMenu(item1, item2);

		// Attach the context menu to the icon
		icon.setOnMouseClicked(e -> contextMenu.show(icon, e.getScreenX(), e.getScreenY()));
       chart();
	}
	
	public void chart()
	{
		int pendingCount = 0;
		int progressCount = 0;
		int resolveCount = 0;
		int newCount = 0;
		int closedCount = 0;
		int record=0;
	    PreparedStatement ps,ps1;
	    ResultSet resultSet,rs;
		try
		{
		Connection conn=DatabaseConnection.getConnection();
		 ps=conn.prepareStatement("SELECT status, COUNT(*) FROM tasks GROUP BY status");
		 ps1=conn.prepareStatement("SELECT COUNT(*) FROM tasks");
		 resultSet = ps.executeQuery();
		 rs = ps1.executeQuery();
		
		while (resultSet.next()) {
			String status = resultSet.getString("status");
		    int count = resultSet.getInt("COUNT(*)");
		    
		    if (status.equals("Pending")) {
		        pendingCount = count;
		    } else if (status.equals("In Progress")) {
		        progressCount = count;
		    } else if (status.equals("Resolved")) {
		        resolveCount = count;
		    }
		    else if (status.equals("New")) {
		        	newCount = count;
		    }
		    else if (status.equals("Closed")) {
		        closedCount = count;
		    }
		   }
		  while(rs.next())
			{
			  record=rs.getInt(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// pie chart code--------------------------------------------
		  allTask.setText(String.valueOf(record));
		  pending.setText(String.valueOf(pendingCount));
		  progress.setText(String.valueOf(progressCount));
		  completed.setText(String.valueOf(resolveCount));
		  pieChart.getData().addAll(new PieChart.Data("Pending",pendingCount),
		    		new PieChart.Data("Progress",progressCount),new PieChart.Data("Completed",resolveCount),
		    		new PieChart.Data("New",newCount),new PieChart.Data("Closed",closedCount));
		// bar chart code--------------------------------------------
		    series = new XYChart.Series<>();
	        series.getData().add(new XYChart.Data<>("Pending",pendingCount));
	        series.getData().add(new XYChart.Data<>("Progress",progressCount));
	        series.getData().add(new XYChart.Data<>("Completed",resolveCount));
	        series.getData().add(new XYChart.Data<>("New",newCount));
	        series.getData().add(new XYChart.Data<>("Closed",closedCount));

	        // Add series to the chart
	        barChart.getData().add(series);
	 }

}
