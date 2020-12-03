package Test.GregFastCheck;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Test.GregFastCheck.Arrangement.AppLogic;
import Test.GregFastCheck.Arrangement.Greg_DataBase;
import Test.GregFastCheck.Arrangement.Greg_Internet;
import Test.GregFastCheck.Arrangement.TabularData;
import Test.GregFastCheck.Interfaces.Filament;
import Test.GregFastCheck.Interfaces.IInteractionWtihDataBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

public class GUIController implements Initializable{
	
	@FXML private Label update_time_lable;
	@FXML private TableView<TabularData> table;
	@FXML private Button update_information;
	@FXML private Button update_database;
	
	@FXML private TableColumn<TabularData, String> material_column;
	@FXML private TableColumn<TabularData, String> color_column;
	@FXML private TableColumn<TabularData, Integer> length_column;
	@FXML private TableColumn<TabularData, String> available_in_store_column;
	
	AppLogic al = new AppLogic();
	Greg_DataBase gdb = new Greg_DataBase();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gdb.connnect_to_database();
		
		update_information.setOnMouseClicked(e -> update_information(""));
		update_database.setOnMouseClicked(e -> update_database());
		
		update_information("");
	}
	
	private void update_information(String test) {
		ArrayList<String> intenet_results;
		if(test.equals("test"))
			intenet_results = al.get_data_from_internet(new Greg_Internet("test"));
		else 
			intenet_results = al.get_data_from_internet(new Greg_Internet());
		
		ArrayList<String> database_results = al.get_data_from_database(gdb);
		
		ObservableList<TabularData> show_data = al.make_show_data(intenet_results, database_results);
		
		initialized_colomns(al.getDatabase_filament(), al.getInternet_filament(), al.getUnchanged_filament());
		
		table.setItems(show_data);
		
		update_time_lable.setText(update_time_label_get_text());
	}
	
	private void update_database() {
		ArrayList<IInteractionWtihDataBase> unchanged_filament = 
				(ArrayList<IInteractionWtihDataBase>)((ArrayList<?>)al.getUnchanged_filament());
		ArrayList<IInteractionWtihDataBase> old_filament = 
				(ArrayList<IInteractionWtihDataBase>)((ArrayList<?>)al.getDatabase_filament());
		ArrayList<IInteractionWtihDataBase> new_filament = 
				(ArrayList<IInteractionWtihDataBase>)((ArrayList<?>)al.getInternet_filament());
		
		al.update_data_in_database(gdb, unchanged_filament, old_filament, new_filament)	;
		
		gdb.update("UPDATE updatetime "
				+ "SET update_time = NOW() "
				+ "WHERE id = 1;");
		
		update_information("");
	}
	
	public String update_time_label_get_text() {
		Date date = new Date();
		SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
		String time_now= DateFor.format(date);
		
		String database_time = gdb.request("updatetime", "update_time").get(0);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Last information update time: ").append(time_now);
		sb.append("\tLast database update time: ").append(database_time);
		return sb.toString();
	}
	
	private void initialized_colomns(
			ArrayList<Filament> database_filament,
			ArrayList<Filament> internet_filament,
			ArrayList<Filament> unchanged_filament
			) {
		material_column.setCellValueFactory(cellData -> cellData.getValue().getMaterial());
		color_column.setCellValueFactory(cellData -> 	cellData.getValue().getColor());
		length_column.setCellValueFactory(cellData -> 	cellData.getValue().getLength().asObject());
		available_in_store_column.setCellValueFactory(cellData -> 	cellData.getValue().getAvailiable_in_store());
		
		available_in_store_column.setCellFactory(colomn -> {
	        return new TableCell<TabularData, String>() {
	        	@Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty ? "" : item);
	                setGraphic(null);

	                TableRow<TabularData> current_row = getTableRow();

	                if (isEmpty() | current_row.getItem() == null) return;
	                
	                char color_char = 'a';
	                
	                // these overrun for loops instead of coloring by indexes
	                // are needed in case of sort by column
	                for(Filament f : internet_filament)
	                	if(current_row.getItem().getFilament().equals(f))
	                		color_char = 'g';
	                for(Filament f : database_filament)
		                if(current_row.getItem().getFilament().equals(f))
	                		color_char = 'r';
	                		
	                if(item.contains("/ -"))
	                	color_char = 'c';
	                else if(item.contains("/ +"))
	                	color_char = 'y';
	                
	                set_style(this, current_row, color_char);
	            }
	        };
	    });
	}
	
	private void set_style(TableCell<TabularData, String> table_cell, TableRow<TabularData> current_row, char color_char) {
		// currentRow.setStyle() and setStyle() methods both are needed 
        // because using only one of them cause unpredictable aftermath
		 if( color_char == 'r' ) {
     		current_row.setStyle("-fx-background-color:IndianRed");
     		table_cell.setStyle("-fx-background-color:IndianRed");
         }
         else if( color_char == 'g' ) {
         	current_row.setStyle("-fx-background-color:lightgreen");
         	table_cell.setStyle("-fx-background-color:lightgreen");
         }
         else if( color_char == 'c' ) {
         	current_row.setStyle("");
         	table_cell.setStyle("-fx-background-color:lightcoral");
         }
         else if( color_char == 'y' ) {
         	current_row.setStyle("");
         	table_cell.setStyle("-fx-background-color:GreenYellow");
         }
         else {
         	current_row.setStyle("");
         	table_cell.setStyle("");
         }
	}
}
