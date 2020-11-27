package Test.GregFastCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    
	public static void main(String[] args) throws IOException {
		new App();
	}
	
	App() throws IOException{
		Internet internet = new Internet();
		ArrayList<String> intenet_results = internet.getResults();
		
		DataBase db = new DataBase();
		db.connnect_to_database();
		ArrayList<String> database_results = db.request("gregfilament", "color", "material", "length", "number", "id");
		db.close_connection();
		
		ArrayList<GregFilament> database_filament = make_filament_array_from_database_results(database_results);
		ArrayList<GregFilament> internet_filament = make_filament_array_from_internet_results(intenet_results);
		
//		database_filament.forEach((f) -> System.out.println(f.toString("short")));
//		divide();
//		internet_filament.forEach((f) -> { if(f.getMaterial().equals("PET-G")) System.out.println(f.toString("short"));});
//		divide();
//		divide();
//		divide();
		ArrayList<GregFilament> equal_filament = compare_filament_arrays(database_filament, internet_filament);
		
//		update_data_in_database(equal_filament, database_filament, internet_filament);
	}
	
	
	public void update_data_in_database(
			ArrayList<GregFilament> equal_filament,
			ArrayList<GregFilament> old_filament, 
			ArrayList<GregFilament> new_filament) {
		
		DataBase db = new DataBase();
		db.connnect_to_database();
		
		for(int i=0; i<equal_filament.size(); i+=2) {
			equal_filament.get(i+1).setId( equal_filament.get(i).getId() );
			db.update(equal_filament.get(i+1).update_in_gregfilament_table_string());
		}
		
		old_filament.forEach((f) -> db.update(f.delete_from_gregfilament_table_string()));
		new_filament.forEach((f) -> db.update(f.set_to_gregfilament_table_string()));
		
		db.close_connection();
	}
	
	public ArrayList<GregFilament> compare_filament_arrays(
			ArrayList<GregFilament> database_filament, 
			ArrayList<GregFilament> internet_filament) {

		ArrayList<GregFilament> equal_filament = new ArrayList<GregFilament>();
		
		boolean decrement_i = false;
		for(int i=0; i<database_filament.size(); i++) {
			for(int j=0; j<internet_filament.size(); j++) 
				if(database_filament.get(i).equals(internet_filament.get(j))) {
					equal_filament.add(database_filament.get(i));
					equal_filament.add(internet_filament.get(j));
					database_filament.remove(i);
					internet_filament.remove(j);
//					System.out.printf("i=%d, j=%d", i, j);
					if(internet_filament.size() != 1) {decrement_i = true; j--;}
				}
			if(decrement_i) { i--; decrement_i = false;}
		}
		
		// after this operation we have in internet_ifilament only new data, 
		// that is needed to be set in database, and
		// in database_filament old data, that is needed to be deleted
		
		compare_filament_number(equal_filament);
		divide();
		System.out.println("Новые:");
		internet_filament.forEach((f) -> {if(f.getMaterial().equals("PET-G")) System.out.println(f.toString("short"));});
		divide();
		System.out.println("Удалённые:");
		database_filament.forEach((f)-> System.out.println(f.toString("short")));
		
		return equal_filament;
	}
	
	public void compare_filament_number(ArrayList<GregFilament> equal_filament) {
		for(int i=0; i<equal_filament.size(); i+=2) {
			
			System.out.print(equal_filament.get(i+1).toString("short"));
			
			int comp = equal_filament.get(i+1).getNumber()-equal_filament.get(i).getNumber();
			
			if(comp > 0) System.out.print(" увеличилось на" + comp);
			else if(comp < 0) System.out.print(" уменьшилось на " + Math.abs(comp));
			
			System.out.println();
		}
	}
	
	public ArrayList<GregFilament> make_filament_array_from_internet_results(ArrayList<String> results) {
		ArrayList<GregFilament> array = new ArrayList<GregFilament>();
		for(int i=0; i<results.size(); i+=4) {
			array.add( new GregFilament(
					results.get(i), results.get(i+1), 
					parse_int(results.get(i+2)), parse_int(results.get(i+3)))
					);
		}
		return array;
	}
	
	public ArrayList<GregFilament> make_filament_array_from_database_results(ArrayList<String> results) {
		ArrayList<GregFilament> array = new ArrayList<GregFilament>();
		for(int i=0; i<results.size(); i+=5) {
			array.add( new GregFilament(
					results.get(i), results.get(i+1), 
					parse_int(results.get(i+2)), parse_int(results.get(i+3)),
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
	
	private void divide() {
		System.out.println();
		System.out.println("-----------------------------------------");
		System.out.println();
	}
}
