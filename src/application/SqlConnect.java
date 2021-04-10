package application;
import java.sql.*;

public class SqlConnect {

	static final String JDBC_driver="com.mysql.jdbc.Driver";
	static final String DB_URL="jdbc:mysql://localhost:3306/ecommerce?useUnicode=true&characterEncoding=utf8";
	static final String USER="root";
	static final String password="123456";
	public static ResultSet SqlFind(String sql) throws ClassNotFoundException {
		
		Connection con=null;
		Statement sm=null;
		ResultSet result=null;
		try {
			Class.forName(JDBC_driver);
		
			
			con=DriverManager.getConnection(DB_URL, USER, password);

			
			sm=con.createStatement();
			
			result=sm.executeQuery(sql);
			
			 
		}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			return result;
}
	
public static void SqlUpdate(String sql) throws ClassNotFoundException {
		
		Connection con=null;
		Statement sm=null;
		ResultSet result=null;
		try {
			Class.forName(JDBC_driver);
		;
			
			con=DriverManager.getConnection(DB_URL, USER, password);

			
			sm=con.createStatement();
		
			
			sm.executeUpdate(sql);
			//System.out.println(result);
			
		}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			//return result;
}
	
}

