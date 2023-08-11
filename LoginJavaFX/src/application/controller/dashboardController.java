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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;

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


public class dashboardController implements Initializable{
	
	
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
	    private Label icon;
	    
	    @FXML
	    private Button btnLogout;
	    
	    @FXML	  
	    private AnchorPane ap;
	    
	    @FXML	  
	    private AnchorPane ap1;
     
        @FXML
        private BorderPane bp;
	    
	     public String eml;
	    // This is for body
	     

	    

	   /* @FXML
	    void handleClick(MouseDragEvent event) {
	    	if(event.getSource() == btnDashboard) {
	    		dirStatus.setText("/home/dashboard");
	    		headingStatus.setText("Dashboard");
	    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(63, 43, 99), CornerRadii.EMPTY, Insets.EMPTY)));
	    		bp.setCenter(ap);
	    	}
	    	else if(event.getSource() == btnAllTasks) {
	    		dirStatus.setText("/home/all tasks");
	    		headingStatus.setText("All Tasks");
	    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(153, 58, 194), CornerRadii.EMPTY, Insets.EMPTY)));
	    		bp.getChildren().removeAll(ap);
	    	    loadPage("allTasks");
	    	}
	    	else if(event.getSource() == btnEmployeeLIst) {
	    		dirStatus.setText("/home/employee list");
	    		headingStatus.setText("Employee List");
	    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(194, 58, 171), CornerRadii.EMPTY, Insets.EMPTY)));
	    		bp.getChildren().removeAll(ap);
	    	    loadPage("empList");
	    	}
	    	else if(event.getSource() == btnMyTasks) {
	    		dirStatus.setText("/home/my tasks");
	    		headingStatus.setText("My Tasks");
	    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(194, 58, 58), CornerRadii.EMPTY, Insets.EMPTY)));
	    		bp.getChildren().removeAll(ap);
	    	    loadPage("myTasks");
	    	}
	    	else if(event.getSource() == btnAssignedTasks) {
	    		dirStatus.setText("/home/assigned tasks");
	    		headingStatus.setText("Assigned Tasks");
	    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(183, 194, 58), CornerRadii.EMPTY, Insets.EMPTY)));
	    		bp.getChildren().removeAll(ap);
	    	    loadPage("assignTasks");
	    	}
	    	
	    	
	    }*/
	   
        public void getId(String email)
        {
         System.out.print(email+"dash");
         this.eml=email;
        }
        
        
        @FXML
	    void btnDashboard(MouseEvent event){
        	dirStatus.setText("/home/dashboard");
    		headingStatus.setText("Dashboard");
    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.valueOf("#47bc99"), CornerRadii.EMPTY, Insets.EMPTY)));
    		bp.setCenter(ap);
	    }
        
        @FXML
	    void btnAllTasks(MouseEvent event){
        	dirStatus.setText("/home/all tasks");
    		headingStatus.setText("All Tasks");
    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(153, 58, 194), CornerRadii.EMPTY, Insets.EMPTY)));
    		bp.getChildren().removeAll(ap);
    	    loadPage("allTasks");
    	   
	    }
        
        @FXML
	    void btnEmployeeLIst(MouseEvent event){
        	dirStatus.setText("/home/employee list");
    		headingStatus.setText("Employee List");
    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(194, 58, 171), CornerRadii.EMPTY, Insets.EMPTY)));
    		bp.getChildren().removeAll(ap);
    	    loadPage("empList");
	    }
        
        @FXML
	    void btnMyTasks(MouseEvent event){
        	dirStatus.setText("/home/my tasks");
    		headingStatus.setText("My Tasks");
    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(194, 58, 58), CornerRadii.EMPTY, Insets.EMPTY)));
    		bp.getChildren().removeAll(ap);
    		 
    		List<String> list=new ArrayList<>();
    	    Parent root=null;
    	    String name=null;
			try {
			
				Connection conn=DatabaseConnection.getConnection();
				PreparedStatement ps=conn.prepareStatement("Select name from employee");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					name=rs.getString("name");
					list.add(name);
				}
				
				FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/fxml/myTasks.fxml"));
				root=loader.load();
				AddTaskController addTask=loader.getController();
				addTask.getId(eml,list);
				System.out.print(eml+"dashController");
			} catch (Exception e) {
				e.printStackTrace();
			}
			bp.setCenter(root);
	    }
        
        @FXML
	    void btnAssignedTasks(MouseEvent event){
        	dirStatus.setText("/home/assigned tasks");
    		headingStatus.setText("Assigned Tasks");
    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(183, 194, 58), CornerRadii.EMPTY, Insets.EMPTY)));
    		bp.getChildren().removeAll(ap);
    	    loadPage("assignTasks");
	    }
	  
	    
	    public void loadPage(String page)
		{
			Parent root=null;
			try {
				root=FXMLLoader.load(getClass().getResource("/application/fxml/"+page+".fxml"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			bp.setCenter(root);
		}
	    
	    
	  
	    private void profile(ActionEvent event) {
	        dirStatus.setText("/home/profile");
    		headingStatus.setText("Profile");
    		headerStatus1.setBackground(new Background(new BackgroundFill(Color.rgb(194, 0, 58), CornerRadii.EMPTY, Insets.EMPTY)));
    		bp.getChildren().removeAll(ap);
    	    loadPage("profile");
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
		
	        MenuItem item1 = new MenuItem(" "+"My Profile",iconImageView1);
	        MenuItem item2 = new MenuItem(" "+"Log out",iconImageView);

	        
	        item1.setOnAction(this::profile);
	        item2.setOnAction(this::logOut);

	        ContextMenu contextMenu = new ContextMenu(item1, item2);

	        // Attach the context menu to the icon
	        icon.setOnMouseClicked(e -> contextMenu.show(icon, e.getScreenX(), e.getScreenY()));
		
	}

}
