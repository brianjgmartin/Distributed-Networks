// The package containing our stubs.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import MemoryApp._MemoryImplBase;

//Servant must inherit the generated code
class MemoryServant extends _MemoryImplBase {
	// Add the sayHello method here in the next step.
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String CONN_STRING = "jdbc:mysql://localhost/MemoryCatcher";
	public String currentUser="";

	public static void main(String[] args) throws ClassNotFoundException {
		// //Class.forName("con.mysql.jdbc.driver");
		// MemoryServant ms = new MemoryServant();
		// ms.register("Check","ME");
	}

	public void register(String username, String password) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		currentUser = username;
		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("connected Memories");
			// STEP 4: Execute a query
			System.out.println("Inserting records into the table...");
			stmt = conn.createStatement();

			String sql = "INSERT INTO Users(USERNAME,PASSWD,RESOURCE_POINTS)"
					+ "VALUES ('" + username + "','" + password + "',20)";
			stmt.executeUpdate(sql);
		}

		catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}
    
	public String getUser(){
		return currentUser;
	}
	
	public boolean login(String username, String password) {
		Connection conn = null;
		Statement stmt = null;
		currentUser = username;
		System.out.println(currentUser);
		boolean log = false;
		try{
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		System.out.println("connected Tble");
		//STEP 4: Execute a query
	      System.out.println("Login...");
	      stmt = conn.createStatement();

	      String sql = "SELECT * FROM Users WHERE USERNAME = '"+username+"'"
		      		+ "and passwd = '"+password+"'";
	      				
	      ResultSet rs=stmt.executeQuery(sql);
	      //System.out.println(rs);
	      if(rs.next()){
	         log = true;
	          }
	          else {
	        	  log = false;
	        	 
	     
	          }
	      rs.close();

		
		}
		catch(SQLException e){
			System.err.println(e);
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} return log;
}

	public void addMemory(String memory) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("connected Tble");
			// STEP 4: Execute a query
			System.out.println("Inserting records into the Memory table...");
			stmt = conn.createStatement();

			String sql = "INSERT INTO Memories(Memory,U_ID) " + "VALUES ('"
					+ memory + "',(Select U_ID from users where USERNAME = '"
					+ getUser() + "'))";
			stmt.executeUpdate(sql);
			System.out.println("Congrats you have added a memory");
		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
	public void deleteMemory(String memory){
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
	
	      stmt = conn.createStatement();
	      
	      String sql = "DELETE FROM Memories "
	      		+ "where memory = '"+memory+"'"
	      				+ "and U_ID = (select U_ID from users where username ='"+getUser()+"')";
	      stmt.executeUpdate(sql);
	      
		}
		catch(SQLException e){
			System.err.println(e);
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void addResource(String resource, String memory){
		Connection conn = null;
		Statement stmt = null;
		int rp;
		boolean flag = false;
		
		try{
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
	      stmt = conn.createStatement();
	      String sql =  "SELECT RESOURCE_POINTS FROM USERS WHERE U_ID = (SELECT U_ID FROM Memories where Memory = '"+memory+"') ";
	      ResultSet rs = stmt.executeQuery(sql);
	      while(rs.next()){
	          //Retrieve by column name
	           rp  = rs.getInt("Resource_Points");
	         System.out.println(rp);
	         if (rp > 2){
	        	 flag = true;
	         }
	         else {
	        	 System.out.println("Sorry you do not have enough points");
	         }
	      }
	         
	      
	    	 
	     do {
	       sql = "INSERT INTO Resources(Resource,M_ID) " +
                   "VALUES ('"+resource+"',(Select M_ID from Memories where Memory = '"+memory+"'))";
	      stmt.executeUpdate(sql);
	      
	      sql = "SELECT RESOURCE_POINTS FROM USERS WHERE U_ID = (SELECT U_ID FROM Memories where Memory = '"+memory+"') ";
	    rs = stmt.executeQuery(sql);
	     rs= stmt.executeQuery(sql);
	      while(rs.next()){
	         // Retrieve by column name
	           int id  = rs.getInt("Resource_Points");
	          id =id -2;
	          System.out.println(id+"mm");
	      
	      
       sql = "UPDATE Users " +
                   "SET Resource_Points = "+id+" "
                   		+ "where U_ID = (SELECT U_ID FROM Memories where'"+memory+"' = '"+memory+"')";
       }
	      rs.close();
      stmt.executeUpdate(sql);
      flag = false;
	      
	      System.out.println("Congrats you have added a resource");
	    // while goes here 
	     }while (flag);
	    
	      }
		
		catch(SQLException e){
			System.err.println(e);
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public  String viewMemories(){
		Connection conn = null;
		Statement stmt = null;
		
		String result = "";
		
		try{
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
	
	      stmt = conn.createStatement();
	      
	      String sql = "Select memory FROM Memories "
	      		+ "where U_ID = (SELECT U_ID from Users where username ='"+getUser()+"')";
	   ResultSet  rs = stmt.executeQuery(sql);
	   while(rs.next()){
		  
		   result = rs.getString(1) + " " + result;	          
	   }
	
	      
		}
		catch(SQLException e){
			System.err.println(e);
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		}return "Your current stored Memories are" + "\n"
				+result;
		}
}
