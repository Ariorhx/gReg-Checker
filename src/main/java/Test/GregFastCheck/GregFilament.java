package Test.GregFastCheck;

public class GregFilament extends Filament{

	public GregFilament(String color, String material, int length, int number) {
		super(color, material, length, number);
	}
	
	public GregFilament(String color, String material, int length, int number, int id) {
		super(color, material, length, number, id);
	}
	
	public String set_to_gregfilament_table_string() {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO gregfilament( color, material, length, number)");
		sb.append( "VALUES( ").append(this.toString("short")).append(")");
		
		return sb.toString();
	}
	
	public String update_in_gregfilament_table_string() {
		
		check_id_not_null();
		
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE gregfilament SET ");
		sb.append(this.toString());
		sb.append(" WHERE id = ").append(this.getId()).append(";");
		
		return sb.toString();
	}
	
	public String delete_from_gregfilament_table_string() {
		
		check_id_not_null();
		
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM gregfilament ");
		sb.append(" WHERE id = ").append(this.getId()).append(";");
		
		return sb.toString();
	}
	
	private void check_id_not_null() {
		if(this.getId() == -1) throw new NullPointerException();
	}
}
