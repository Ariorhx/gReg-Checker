package Test.GregFastCheck.Interfaces;

public interface IInteractionWtihDataBase {
	public String set_to_database(String table);
	
	public String update_in_databse(String table);
	
	public String delete_from_database(String table);
}
