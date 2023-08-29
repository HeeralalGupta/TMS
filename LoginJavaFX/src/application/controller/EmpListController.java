package application.controller;

import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import application.controller.entity.Employees;
import application.persistance.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;

import javafx.stage.Stage;
import javafx.util.Callback;

public class EmpListController implements Initializable {
	@FXML
	private TableView<Employees> tableView;
	@FXML
	private TableColumn<Employees, String> idColumn;
	@FXML
	private TableColumn<Employees, String> nameColumn;
	@FXML
	private TableColumn<Employees, String> designationColumn;
	@FXML
	private TableColumn<Employees, String> emailColumn;
	@FXML
	private TableColumn<Employees, String> mobileColumn;
	
	@FXML
	private TableColumn<Employees, Void> actionColumn;

	@FXML
	private TableRow<Employees> row;

	@FXML
	private TextField searchBar;

	private int currentPage = 0;
	private final int rowsPerPage = 10;
	private ObservableList<Employees> data;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initialezeTable();
	}

	public void initialezeTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		// actionColumn.setCellValueFactory(new PropertyValueFactory<>("edit"));
		// actionColumn.setCellValueFactory(new PropertyValueFactory<>("delete"));
		data = FXCollections.observableArrayList();
		System.out.println(data);
		fetchDataFromDatabase(currentPage);
		tableView.setItems(data);
		addButtonToTable();
	}

	private void addButtonToTable() {
		Callback<TableColumn<Employees, Void>, TableCell<Employees, Void>> cellFactory = new Callback<TableColumn<Employees, Void>, TableCell<Employees, Void>>() {
			@Override
			public TableCell<Employees, Void> call(final TableColumn<Employees, Void> param) {
				final TableCell<Employees, Void> cell = new TableCell<Employees, Void>() {

					Image edit = new Image("image/edit.png");
					Image delete = new Image("image/delete.png");

					ImageView editView = new ImageView(edit);
					ImageView deleteView = new ImageView(delete);

					Button editBtn = new Button("");
					Button deleteBtn = new Button("");
					HBox box = new HBox();
					{
						HBox.setMargin(editBtn, new Insets(0, 10, 0, 0));
						HBox.setMargin(deleteBtn, new Insets(0, 10, 0, 0));
						box.getChildren().addAll(editBtn, deleteBtn);
						editView.setFitWidth(20); // Set your preferred width
						editView.setFitHeight(20);
						editBtn.setGraphic(editView);
						editBtn.setOnAction((ActionEvent event) -> {
							Employees data = getTableView().getItems().get(getIndex());
                                  
							try {
								Stage primaryStage = new Stage();
								FXMLLoader loader = new FXMLLoader(
								           getClass().getResource("/application/fxml/updateEmp.fxml"));
								Parent root = loader.load();
								UpdateEmpController addTask = loader.getController();
								
								addTask.getDetail(data.getId(),data.getName(),data.getDesignation(),data.getEmail(),data.getMobile());
								System.out.print("dashController");
								Scene scene = new Scene(root);
								//scene.getStylesheets().add(getClass().getResource("/application/fxml/css/style.css").toExternalForm());
								primaryStage.getIcons().add(new Image("image/xyz.jfif"));
								primaryStage.setResizable(false);
								primaryStage.setScene(scene);
								primaryStage.show();
								
							} catch (Exception e) {
								e.printStackTrace();
							}		

						});

						deleteView.setFitWidth(20); // Set your preferred width
						deleteView.setFitHeight(20);
						deleteBtn.setGraphic(deleteView);
						deleteBtn.setOnAction((ActionEvent event) -> {
							Employees data = getTableView().getItems().get(getIndex());
							System.out.println("selectedData: " + data.getId());
							try {
								Connection conn = DatabaseConnection.getConnection();
								PreparedStatement ps = conn.prepareStatement("Delete from employee WHERE id=?");
								ps.setInt(1, data.getId());
								int i = ps.executeUpdate();
								if (i > 0) {

									JOptionPane.showMessageDialog(null, "Data Delete Successfully");
									initialezeTable();

								} else {
									JOptionPane.showMessageDialog(null, "Data could not delete");
								}

							} catch (Exception e) {
								e.printStackTrace();
							}

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(box);
						}
					}
				};
				return cell;
			}
		};

		actionColumn.setCellFactory(cellFactory);

	}

	private void fetchDataFromDatabase(int page) {
		try {
			Connection connection = DatabaseConnection.getConnection();
			int offset = page * rowsPerPage;
			String query = "SELECT * FROM employee LIMIT " + offset + ", " + rowsPerPage;
			PreparedStatement statement = connection.prepareStatement(query);

			ResultSet resultSet = statement.executeQuery();
			data.clear();
			while (resultSet.next()) {

				data.add(new Employees(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getString("designation"), resultSet.getString("email"),
						resultSet.getString("mobile")));
				// data=FXCollections.observableArrayList(new
				// DataObject(resultSet.getString("name"),resultSet.getInt("age")));

				// Create a DataObject from the result set and add it to the data list
				// data.add(new DataObject(resultSet.getString("column1"),
				// resultSet.getString("column2"), ...));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@FXML
	private void preBtn(MouseEvent event) {
		if (currentPage > 0) {
			currentPage--;
			fetchDataFromDatabase(currentPage);
		}
	}

	@FXML
	private void nextBtn(MouseEvent event) {
		// Assuming you have some way of knowing the total number of rows in the table
		int totalRows = getTotalRowsFromDatabase();
		if ((currentPage + 1) * rowsPerPage < totalRows) {
			currentPage++;
			fetchDataFromDatabase(currentPage);
		}
	}

	// Implement the method to get the total number of rows in your table from the
	// database
	private int getTotalRowsFromDatabase() {
		// Query your database to get the total number of rows in the table
		int row = 0;
		try {
			Connection connection = DatabaseConnection.getConnection();
			String query = "SELECT COUNT(*) FROM employee";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				row = resultSet.getInt(1);
				System.out.println(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return row; // Replace with the actual count
	}

	@FXML
	private void reload() {
		searchBar.setText("");
		System.out.println("reload");
		initialezeTable();
	}

	@FXML
	private void search(MouseEvent event) {
		// row.setStyle("-fx-background-color: #05d1c4;");
		// data.clear();
		nameColumn.setCellFactory(column -> {
			return new TableCell<Employees, String>() {
				@Override
				protected void updateItem(String data, boolean empty) {
					super.updateItem(data, empty);
					if (data == null || empty) {
						setText(null);
						setStyle(""); // Reset cell style
					} else {
						setText(data);
						if (searchBar.getText().isEmpty()) {
							setStyle(""); // Reset cell style
						} else if (data.contains(searchBar.getText())) {
							setStyle("-fx-background-color: darkgreen;");
						} else {
							setStyle(""); // Reset cell style
						}
					}
				}
			};
		});
		tableView.setRowFactory(tv -> {
			TableRow<Employees> row = new TableRow<>();
			row.itemProperty().addListener((obs, oldItem, newItem) -> {
				if (newItem != null) {

					// while(newItem.getTaskName().contains(searchBar.getText())){}
					if (searchBar.getText().equals("")) {
						row.setStyle("");
						System.out.println(newItem.getName());

					} else if (newItem.getDesignation().contains(searchBar.getText())
							|| newItem.getEmail().contains(searchBar.getText())
							|| newItem.getMobile().contains(searchBar.getText())) {
						row.setStyle("-fx-background-color: lightgreen;");
						System.out.println(newItem.getName());

					} else {
						row.setStyle("");
					}
				}
			});
			return row;
		});

	}
	
	@FXML
	private void addEmp(ActionEvent event)
	{
		try {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(
			           getClass().getResource("/application/fxml/addEmp.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("/application/fxml/css/style.css").toExternalForm());
			primaryStage.getIcons().add(new Image("image/xyz.jfif"));
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
