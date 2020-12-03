package Test.GregFastCheck.Arrangement;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Test.GregFastCheck.Interfaces.Filament;
import Test.GregFastCheck.Interfaces.IDataBase;
import Test.GregFastCheck.Interfaces.IInteractionWtihDataBase;
import Test.GregFastCheck.Interfaces.IInternet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppLogic {
    
	private ArrayList<Filament> database_filament;
	private ArrayList<Filament> internet_filament;
	private ArrayList<Filament> unchanged_filament;
	
	public ArrayList<Filament> getDatabase_filament() {
		return database_filament;
	}
	public ArrayList<Filament> getInternet_filament() {
		return internet_filament;
	}
	public ArrayList<Filament> getUnchanged_filament() {
		return unchanged_filament;
	}
	
	public int getDatabase_filament_size() {
		return database_filament.size();
	}
	public int getInternet_filament_size() {
		return internet_filament.size();
	}
	public int getUnchanged_filament_size() {
		return unchanged_filament.size();
	}

	public ArrayList<String> get_data_from_internet(IInternet iInternet){
		return iInternet.get_data();
	}
	
	public ArrayList<String> get_data_from_database(IDataBase database){
		return database.request("gregfilament", "color", "material", "length", "number", "id");
	}
	
	public void update_data_in_database(
			IDataBase database,
			ArrayList<IInteractionWtihDataBase> unchanged_filament,
			ArrayList<IInteractionWtihDataBase> old_filament, 
			ArrayList<IInteractionWtihDataBase> new_filament) {
		
		database.connnect_to_database();
		
		unchanged_filament.forEach(f-> database.update(f.update_in_databse("gregfilament")) );
		old_filament.forEach(f -> database.update(f.delete_from_database("gregfilament")));
		new_filament.forEach(f -> database.update(f.set_to_database("gregfilament")));
	}
	
	public ArrayList<Filament> compare_filament_arrays(
			ArrayList<Filament> database_filament, 
			ArrayList<Filament> internet_filament) {

		ArrayList<Filament> unchanged_filament = new ArrayList<Filament>();
		
		//Search filament matches in database_filament and internet_filament
		for(int i=0; i<database_filament.size(); i++) {
			for(int j=0; j<internet_filament.size(); j++) {
				if(database_filament.get(i).equals(internet_filament.get(j))) {
					
					// id is needed to update database 
					internet_filament.get(j).setId(database_filament.get(i).getId());
					
					unchanged_filament.add(database_filament.get(i));
					unchanged_filament.add(internet_filament.get(j));
					database_filament.remove(i);
					internet_filament.remove(j);
					
					i--; j--; break;
				}
			}
		}
		
		// after this operation we have in internet_ifilament only new data, 
		// that is needed to be set in database, and
		// in database_filament old data, that is needed to be deleted from database
		
		return unchanged_filament;
	}
	
	public ArrayList<Filament> make_filament_array_from_internet_results(ArrayList<String> results) {
		ArrayList<Filament> array = new ArrayList<Filament>();
		
		for(int i=0; i<results.size(); i+=4) {
			array.add( new GregFilament(
										results.get(i), 
										results.get(i+1), 
										parse_int(results.get(i+2)), 
										parse_int(results.get(i+3)))
										);
		}
		return array;
	}
	
	public ArrayList<Filament> make_filament_array_from_database_results(ArrayList<String> results) {
		ArrayList<Filament> array = new ArrayList<Filament>();
		
		for(int i=0; i<results.size(); i+=5) {
			array.add( new GregFilament(
										results.get(i), 
										results.get(i+1), 
										parse_int(results.get(i+2)), 
										parse_int(results.get(i+3)),
										parse_int(results.get(i+4)))
										);
		}
		return array;
	}
	
	private Pattern p = Pattern.compile("\\d+");
	private int parse_int(String text) {
		Matcher m = p.matcher(text);
		m.find();
		return Integer.parseInt( m.group(0) );
	}
	
	public ObservableList<TabularData> make_show_data(
			ArrayList<String> intenet_results,
			ArrayList<String> database_results){
		
		ArrayList<Filament> database_filament = make_filament_array_from_database_results(database_results);
		ArrayList<Filament> internet_filament = make_filament_array_from_internet_results(intenet_results);
		ArrayList<Filament> unchanged_filament = compare_filament_arrays(database_filament, internet_filament);
		
		return make_show_data(database_filament, internet_filament, unchanged_filament);
	}
	
	public ObservableList<TabularData> make_show_data(
			ArrayList<Filament> database_filament, 
			ArrayList<Filament> internet_filament,
			ArrayList<Filament> unchanged_filament) {
		
		ObservableList<TabularData> show_data = FXCollections.observableArrayList();
		
		// change in number of filaments that available in store in unchanged_filament
		ArrayList<String> change_in_store_unchanged = compare_filament_available_in_store_number(unchanged_filament);
		delete_unnecessary_filament_from(unchanged_filament);
		
		// This order is accepted because I want to show new results, 
		// after this unchanged results, and finally deleted results
		internet_filament.forEach(f -> show_data.add( new TabularData(f, "") ));
		
		for(int i =0; i<unchanged_filament.size(); i++) {
			show_data.add( new TabularData( unchanged_filament.get(i), change_in_store_unchanged.get(i) ) );
		}
		
		database_filament.forEach(f -> show_data.add( new TabularData(f, "") ));
		
		this.database_filament = database_filament;
		this.internet_filament = internet_filament;
		this.unchanged_filament = unchanged_filament;
		
		return show_data;
	}
	
	public ArrayList<String> compare_filament_available_in_store_number(ArrayList<Filament> unchanged_filament) {
		
		 ArrayList<String> change_in_store_unchanged = new  ArrayList<String>();
		 
		for(int i=0; i<unchanged_filament.size(); i+=2) {
			
			int change = unchanged_filament.get(i+1).getAvailable_in_store()-unchanged_filament.get(i).getAvailable_in_store();
			
			if(change > 0) change_in_store_unchanged.add(" / +" + change);
			else if(change < 0) change_in_store_unchanged.add(" / -" + Math.abs(change));
			else change_in_store_unchanged.add("");
		}
		
		return change_in_store_unchanged;
	}
	
	public void delete_unnecessary_filament_from(ArrayList<Filament> unchanged_filament) {
		// keep in unchanged_filament only new data (from internet_filament) 
		for(int i=unchanged_filament.size()-1; i>=0; i-=2) {
			unchanged_filament.remove(i-1);
		}
	}
}
