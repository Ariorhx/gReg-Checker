package Test.GregFastCheck.Interfaces;

import java.util.ArrayList;

public interface IDataBase {
	
	public void connnect_to_database();
	
	public ArrayList<String> request(String table_name, String ...colomns);
	
	public void update(String update);
	
	public void close_connection();
}
