package Test.GregFastCheck.Arrangement;

import Test.GregFastCheck.Interfaces.Filament;
import Test.GregFastCheck.Interfaces.IInteractionWtihDataBase;

public class GregFilament extends Filament implements IInteractionWtihDataBase{

	public GregFilament(String color, String material, int length, int available_in_store) {
		super(color, material, length, available_in_store);
	}
	
	public GregFilament(String color, String material, int length, int available_in_store, int id) {
		super(color, material, length, available_in_store, id);
	}
	
	private void check_id_not_null() {
		if(this.getId() == -1) throw new NullPointerException();
	}

	@Override
	public String set_to_database(String table) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO "+table+"( color, material, length, number)");
		sb.append( "VALUES( ").append(this.toString("short")).append(")");
		
		return sb.toString();
	}

	@Override
	public String update_in_databse(String table) {
		check_id_not_null();
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE "+table+" SET ");
		sb.append(this.toString());
		sb.append(" WHERE id = ").append(this.getId()).append(";");
		
		return sb.toString();

	}

	@Override
	public String delete_from_database(String table) {
		check_id_not_null();
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM "+table+" ");
		sb.append(" WHERE id = ").append(this.getId()).append(";");
		
		return sb.toString();
	}
}
