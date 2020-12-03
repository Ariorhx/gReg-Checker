package Test.GregFastCheck.Arrangement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Test.GregFastCheck.Interfaces.IDataBase;

public class Greg_DataBase implements IDataBase{
	private static final String url = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false";
	private static final String user = "Greg";
	private static final String password = "GregPass";
	
	private static Connection con;
	private static Statement stmt;
	
	public void connnect_to_database() {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> request(String table_name, String ...colomns) {
		ArrayList<String> results = new ArrayList<>();
		
		try( ResultSet rs = stmt.executeQuery(makeQuery(table_name, colomns)); ){
			while(rs.next()) {
				for(int i=0; i<colomns.length; i++) {
					results.add(rs.getString(colomns[i]));
				}
			}
		} catch (SQLException e) { e.printStackTrace(); }
		
		return results;
	}
	
	private String makeQuery(String table_name, String ...colomns) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("SELECT ");
		for(int i=0; i<colomns.length-1; i++)
			sb.append(colomns[i]).append(", ");
		sb.append(colomns[colomns.length-1]).append(" ");
		sb.append("FROM ").append(table_name).append(";");
		
		return sb.toString();
	}
	
	public void update(String update) {
		try { stmt.executeUpdate(update); } catch(SQLException e) { e.printStackTrace(); }
	}
	
	public void close_connection() {
		try { if(con!=null) con.close(); } catch(SQLException e) { e.printStackTrace(); }
		try { if(stmt!=null) stmt.close(); } catch(SQLException e) { e.printStackTrace(); }
	}
}
