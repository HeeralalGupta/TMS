package application.controller;

import java.net.URL;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.controller.entity.DataObject;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AllTaskController implements Initializable {
	@FXML
	private TableView<DataObject> tableView;
	@FXML
	private TableColumn<DataObject, String> idColumn;
	@FXML
	private TableColumn<DataObject, String> taskNameColumn;
	@FXML
	private TableColumn<DataObject, String> estimateColumn;
	@FXML
	private TableColumn<DataObject, String> statusColumn;
	@FXML
	private TableColumn<DataObject, String> assignToColumn;
	@FXML
	private TableColumn<DataObject, String> currentOwnerColumn;
	@FXML
	private TableColumn<DataObject, Void> actionColumn;

	@FXML
	private TableRow<DataObject> row;
	
	@FXML
	private HBox hbox;
	@FXML
	private Pane pane;
    @FXML
	private TextField searchBar;

	private int currentPage = 0;
	private final int rowsPerPage = 10;
	private ObservableList<DataObject> data;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initialezeTable();
	}

	public void initialezeTable() {
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
		estimateColumn.setCellValueFactory(new PropertyValueFactory<>("estimateDate"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		assignToColumn.setCellValueFactory(new PropertyValueFactory<>("assignTo"));
		currentOwnerColumn.setCellValueFactory(new PropertyValueFactory<>("currentOwner"));
		// actionColumn.setCellValueFactory(new PropertyValueFactory<>("edit"));
		// actionColumn.setCellValueFactory(new PropertyValueFactory<>("delete"));
		data = FXCollections.observableArrayList();
		System.out.println(data);
		fetchDataFromDatabase(currentPage);
		tableView.setItems(data);
		addButtonToTable();
	}

	private void addButtonToTable() {
		Callback<TableColumn<DataObject, Void>, TableCell<DataObject, Void>> cellFactory = new Callback<TableColumn<DataObject, Void>, TableCell<DataObject, Void>>() {
			@Override
			public TableCell<DataObject, Void> call(final TableColumn<DataObject, Void> param) {
				final TableCell<DataObject, Void> cell = new TableCell<DataObject, Void>() {

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
							DataObject data = getTableView().getItems().get(getIndex());

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
								Stage primaryStage = new Stage();
								FXMLLoader loader = new FXMLLoader(
										getClass().getResource("/application/fxml/updateTasks.fxml"));
								root = loader.load();
								updateTaskController addTask = loader.getController();
								addTask.getId(data.getOwner(), list, data.getId());
								System.out.print("dashController");
								Scene scene = new Scene(root);
								primaryStage.getIcons().add(new Image("image/xyz.jfif"));
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
							DataObject data = getTableView().getItems().get(getIndex());
							System.out.println("selectedData: " + data.getId());
							try {
								Connection conn = DatabaseConnection.getConnection();
								PreparedStatement ps = conn.prepareStatement("Delete from tasks WHERE id=?");
								ps.setString(1, data.getId());
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
			String query = "SELECT * FROM tasks LIMIT " + offset + ", " + rowsPerPage;
			PreparedStatement statement = connection.prepareStatement(query);

			ResultSet resultSet = statement.executeQuery();
			data.clear();
			while (resultSet.next()) {

				java.sql.Date date = resultSet.getDate("estimatedDate");
				String dateString = formatDateToString(date);
				
				java.sql.Date date1 = resultSet.getDate("assignDate");
				String dateString1 = formatDateToString(date1);

				data.add(new DataObject(resultSet.getString("id"), resultSet.getString("taskName"), dateString,
						resultSet.getString("status"), resultSet.getString("assignTo"),
						resultSet.getString("currentOwner"), resultSet.getString("owner"),dateString1,
						resultSet.getString("priority"), resultSet.getString("description"), String.valueOf(resultSet.getInt("assignId"))));
				
				
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

	private static String formatDateToString(java.sql.Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
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
			String query = "SELECT COUNT(*) FROM tasks";
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
		taskNameColumn.setCellFactory(column -> {
			return new TableCell<DataObject, String>() {
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
			TableRow<DataObject> row = new TableRow<>();
			row.itemProperty().addListener((obs, oldItem, newItem) -> {
				if (newItem != null) {

					// while(newItem.getTaskName().contains(searchBar.getText())){}
					if (searchBar.getText().equals("")) {
						row.setStyle("");
						System.out.println(newItem.getTaskName());

					} else if (newItem.getTaskName().contains(searchBar.getText())
							|| newItem.getStatus().contains(searchBar.getText())
							|| newItem.getCurrentOwner().contains(searchBar.getText())
							|| newItem.getAssignTo().contains(searchBar.getText())) {
						row.setStyle("-fx-background-color: lightgreen;");
						System.out.println(newItem.getTaskName());

					}
					 else {
						row.setStyle("");
					}
				}
			});
			return row;
		});

	}

}
