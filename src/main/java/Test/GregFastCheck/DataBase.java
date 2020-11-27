package Test.GregFastCheck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	private static final String url = "jdbc:mysql://localhost:3306/test";
	private static final String user = "Greg";
	private static final String password = "GregPass";
	
	private static Connection con;
	private static Statement stmt;
	
//	public static void main(String[] args) {
//		DataBase db = new DataBase();
//		db.connnect_to_database();
//		db.print(db);
//		GregFilament gf = new GregFilament("Бирюзовый", "PLA", 50, 1, 2);
//		db.update( gf.update_in_gregfilament_table_string() );
//		db.print(db);
//		db.close_connection();
//	}
//	
//	public void print(DataBase db) {
//		ResultSet rs = db.request("SELECT id, material, color, length, number\r\n"
//				+ "FROM gregfilament");
//		try {
//			while(rs.next()) {
//				System.out.print(rs.getString("id")+", ");
//				System.out.print(rs.getString("material")+", ");
//				System.out.print(rs.getString("color")+", ");
//				System.out.print(rs.getInt("length")+", ");
//				System.out.println(rs.getInt("number"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try { if(rs != null) rs.close(); } catch(SQLException e) { e.printStackTrace(); }
//		}
//	}
	
	public void connnect_to_database() {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet request(String query) {
		ResultSet rs = null;
		try{ rs = stmt.executeQuery(query); } catch (SQLException e) { e.printStackTrace(); }
		return rs;
	}
	
	public void update(String update) {
		try { stmt.executeUpdate(update); } catch(SQLException e) { e.printStackTrace(); }
	}
	
	public void close_connection() {
		try { if(con!=null) con.close(); } catch(SQLException e) { e.printStackTrace(); }
		try { if(stmt!=null) stmt.close(); } catch(SQLException e) { e.printStackTrace(); }
	}
}
