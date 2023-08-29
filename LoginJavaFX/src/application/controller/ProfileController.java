package application.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import application.persistance.DatabaseConnection;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ProfileController implements Initializable {
	
	@FXML
	private ImageView imageView; 
	@FXML
	private Label icon;
	
	@FXML
	private Label icon2; 
	@FXML
	private Label id; 
	@FXML
	private Label name; 
	@FXML
	private Label email; 
	@FXML
	private Label mobile; 
	
	public Connection conn = DatabaseConnection.getConnection();

	public PreparedStatement ps;

	public ResultSet rs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		Image iconImage = new Image(getClass().getResourceAsStream("/image/delete.png"));
		ImageView iconImageView = new ImageView(iconImage);
		iconImageView.setFitWidth(20); // Set the desired width of the icon
		iconImageView.setFitHeight(20);

		Image iconImage1 = new Image(getClass().getResourceAsStream("/image/user.png"));
		ImageView iconImageView1 = new ImageView(iconImage1);
		iconImageView1.setFitWidth(20); // Set the desired width of the icon
		iconImageView1.setFitHeight(20);

		MenuItem item1 = new MenuItem(" " + "Change Photo", iconImageView1);
		MenuItem item2 = new MenuItem("    " + "Remove", iconImageView);

		item1.setOnAction(this::edit);
		item2.setOnAction(this::remove);

		ContextMenu contextMenu = new ContextMenu(item1, item2);
		
		icon.setOnMouseClicked(e -> contextMenu.show(icon, e.getScreenX(), e.getScreenY()));
		icon2.setOnMouseClicked(e -> contextMenu.show(icon, e.getScreenX(), e.getScreenY()));

	}
	
	private void edit(ActionEvent event) {
		
		Stage primaryStage=new Stage();
		 FileChooser fileChooser = new FileChooser();
	        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png"));
	        File selectedFile = fileChooser.showOpenDialog(primaryStage);

	        if (selectedFile != null) {
	            try {
	                // Convert the selected image to a byte array
	                byte[] imageData = convertImageToByteArray(selectedFile);
	                ps=conn.prepareStatement("update users set image=? WHERE id=?");
	                ps.setBytes(1, imageData);
	                ps.setInt(2,Integer.parseInt(id.getText()));
	                int i=ps.executeUpdate();
	                
	                if(i>0)
	                {
	                	JOptionPane.showMessageDialog(null, "Image Update successfully");
	                }
	                else
	                {
	                	JOptionPane.showMessageDialog(null, "Technical error Image could not Update");
	                }
	            }
	            catch(Exception e)
	            {
                   e.printStackTrace();	            	
	            }
	            }
	        getId(email.getText());
	}
	
	private void remove(ActionEvent event) {
		
	            try {
	            	
	            	byte[] defaultImageBytes = getDefaultImageBytes();
	                ps=conn.prepareStatement("update users set image=? WHERE id=?");
	                ps.setBytes(1, defaultImageBytes);
	                ps.setInt(2,Integer.parseInt(id.getText()));
	                int i=ps.executeUpdate();
	                
	                if(i>0)
	                {
	                	 Image image = new Image("/image/profile.png");
	                     imageView.setImage(image);
	                     imageView.setFitWidth(160); // Set the width of the image view
	            	     imageView.setFitHeight(160);
	            	     Circle clip = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, imageView.getFitWidth() / 2);
	            	     imageView.setClip(clip);
	                }
	                else
	                {
	                	
	                }
	             }
	            catch(Exception e)
	            {
                  e.printStackTrace();	            	
	            }
	}
	
	public void getId(String email2)
	{
	
		int id1=0;
		String name1=null;
		String email1=null;
		String mobile1=null;
		byte[] imageData=null;
		 BufferedImage bufferedImage=null;
		try {
			
            Connection conn = DatabaseConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select * from users WHERE email=?");
			ps.setString(1, email2);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				id1=rs.getInt("id");
				name1=rs.getString("name");
				email1=rs.getString("email");
				mobile1=rs.getString("mobile");
				imageData = rs.getBytes("image");
			    bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
			}
			id.setText(String.valueOf(id1));
			name.setText(name1);
			email.setText(email1);
			mobile.setText(mobile1);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		

        // Resize the image to the desired dimensions
        int targetWidth = 500; // Set the desired width
        int targetHeight = 500; // Set the desired height
        Image resizedImage = resizeImage(bufferedImage, targetWidth, targetHeight);

	    //Image image = new Image(new ByteArrayInputStream(imageData));
         imageView.setImage(resizedImage);
         imageView.setFitWidth(160); // Set the width of the image view
	     imageView.setFitHeight(160);
	     Circle clip = new Circle(imageView.getFitWidth() / 2, imageView.getFitHeight() / 2, imageView.getFitWidth() / 2);
	     imageView.setClip(clip);
    }
	
	@FXML
	private void reload(MouseEvent event)
	{
		String email1=null;
		try {

			Connection conn = DatabaseConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select * from users WHERE id=?");
			ps.setInt(1, Integer.parseInt(id.getText()));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				email1=rs.getString("email");
			}
			getId(email1);
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@FXML
	private void updateBtn(ActionEvent event) {
	
		try {
			Stage primaryStage=new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/updateProfile.fxml"));
			Parent root = loader.load();
			UpdateProfileController addTask = loader.getController();
			addTask.getId(id.getText(),name.getText(),email.getText(),mobile.getText());
			Scene scene=new Scene(root);
			primaryStage.getIcons().add(new Image("image/xyz.jfif"));
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	  private byte[] convertImageToByteArray(File imageFile) throws IOException {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(ImageIO.read(imageFile), "jpg", baos);
	        return baos.toByteArray();
	    }
	  
	  private static byte[] getDefaultImageBytes() throws IOException {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        // Replace "path_to_default_image.jpg" with your default image's path
	        ImageIO.write(ImageIO.read(ProfileController.class.getResourceAsStream("/image/profile.png")), "png", baos);
	        return baos.toByteArray();
	    }
	  
	   private Image resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
	        Image resizedImage = null;

	        BufferedImage resizedBufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D graphics = resizedBufferedImage.createGraphics();
	        graphics.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	        graphics.dispose();

	        resizedImage = SwingFXUtils.toFXImage(resizedBufferedImage, null);

	        return resizedImage;
	    }



}
