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
	private static final String CONN_STRING = "jdbc:mysql://localhost/myMemories";
	public String currentUser = "";

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

			String sql = "INSERT INTO Users(USERNAME,PASSWORD,RESOURCE_POINTS)"
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

	public String getUser() {
		return currentUser;
	}

	public int getUserID() {
		Connection conn = null;
		Statement stmt = null;
		int result = 0;

		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String sql = "select U_ID from USERS " + "where username =  '"
					+ getUser() + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				result = rs.getInt(1);
			}
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
		return result;
	}

	public boolean login(String username, String password) {
		Connection conn = null;
		Statement stmt = null;
		currentUser = username;
		System.out.println(currentUser);
		boolean log = false;
		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("connected Tble");
			// STEP 4: Execute a query
			System.out.println("Login...");
			stmt = conn.createStatement();

			String sql = "SELECT * FROM Users WHERE USERNAME = '" + username
					+ "'" + "and password = '" + password + "'";

			ResultSet rs = stmt.executeQuery(sql);
			// System.out.println(rs);
			if (rs.next()) {
				log = true;
			} else {
				log = false;

			}
			rs.close();

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
		return log;
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

	public void deleteMemory(String memory) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			stmt = conn.createStatement();

			String sql = "DELETE FROM Memories " + "where memory = '" + memory
					+ "'"
					+ "and U_ID = (select U_ID from users where username ='"
					+ getUser() + "')";
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public void addResource(String resource, String memory) {
		Connection conn = null;
		Statement stmt = null;
		int rp;
		boolean flag = false;

		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			stmt = conn.createStatement();
			String sql = "SELECT RESOURCE_POINTS FROM USERS WHERE U_ID = (SELECT U_ID FROM Memories where Memory = '"
					+ memory + "') ";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				rp = rs.getInt("Resource_Points");
				System.out.println(rp);
				if (rp > 2) {
					flag = true;
				} else {
					System.out.println("Sorry you do not have enough points");
				}
			}
rs.close();
			do {
				sql = "INSERT INTO Resources(Resource,M_ID) " + "VALUES ('"
						+ resource
						+ "',(Select M_ID from Memories where Memory = '"
						+ memory + "'))";
				stmt.executeUpdate(sql);

				sql = "SELECT RESOURCE_POINTS FROM USERS WHERE U_ID ="
						+ " (SELECT U_ID FROM Memories where Memory = '"
						+ memory + "') ";
				rs = stmt.executeQuery(sql);
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					// Retrieve by column name
					int id = rs.getInt("Resource_Points");
					id = id - 2;
					System.out.println(id + "mm");

					sql = "UPDATE Users " + "SET Resource_Points = " + id + " "
							+ "where U_ID = (SELECT U_ID FROM Memories where'"
							+ memory + "' = '" + memory + "')";
				}
				rs.close();
				stmt.executeUpdate(sql);
				flag = false;

				System.out.println("Congrats you have added a resource");
				// while goes here
			} while (flag);

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

	public String viewMemories() {
		Connection conn = null;
		Statement stmt = null;

		String result = "";

		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			stmt = conn.createStatement();

			String sql = "Select memory FROM Memories "
					+ "where U_ID = (SELECT U_ID from Users where username ='"
					+ getUser() + "')";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {

				result = rs.getString(1) + " " + result;
			}

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return "Your current stored Memories are" + "\n" + result;
	}

	public void sendInvite(String friendUsername) {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			stmt = conn.createStatement();

			stmt = conn.createStatement();
			String sql = "INSERT INTO Friends(Sender_ID,Friend_ID,Request,status)"
					+ "VALUES ('"
					+ getUserID()
					+ "',(SELECT U_ID from Users where username = '"
					+ friendUsername + "'),1,0)";

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}
	}

	public boolean viewInvites() {
		Connection conn = null;
		Statement stmt = null;
		boolean flag = false;

		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			stmt = conn.createStatement();

			String sql = "Select request from friends " + "where FRIEND_ID = '"
					+ getUserID() + "'" + "and status = 0";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("Request");
				if (id != 0) {
					flag = true;
				} else {
					flag = false;
				}
			}

		} catch (SQLException e) {

			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return flag;
	}

	public String viewInviteSender() {
		Connection conn = null;
		Statement stmt = null;
		String sender = "";

		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			stmt = conn.createStatement();

			String sql = "Select username from users "
					+ "where U_ID = (Select sender_ID from friends)";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				sender = rs.getString(1);
				;

			}

		} catch (SQLException e) {

			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return sender;
	}

	public void acceptInvite() {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			stmt = conn.createStatement();

			String sql = " Update Friends "
			+ "set status = 1";
			//+ "where F_ID=1";
			
			stmt.executeUpdate(sql);
			
			stmt = conn.createStatement();
			sql = "SELECT RESOURCE_POINTS FROM USERS WHERE U_ID ="
					+ " (SELECT Sender_Id FROM friends) ";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				int points = rs.getInt("Resource_Points");
				points = points + 10;
				System.out.println(points + "mm");

				sql = "UPDATE Users " + "SET Resource_Points = " + points + " "
						+ "where U_ID = (SELECT Sender_ID FROM Friends )";
			}
			rs.close();
			stmt.executeUpdate(sql);
			

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
	public int viewPoints() {
		Connection conn = null;
		Statement stmt = null;
		int points = 0;

		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			stmt = conn.createStatement();

			String sql = "Select resource_points from users "
					+ "where U_ID = '"+getUserID()+"'";

			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				points = rs.getInt("resource_points");
			}

		} catch (SQLException e) {
			
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}return points;
	}
	
	public void deleteResource(String resource){
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

			stmt = conn.createStatement();

			String sql = "DELETE FROM Resources " + "where resource like '" + resource
					+ "'"
					+ "and U_ID = (select U_ID from users where username ='"
					+ getUser() + "')";
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
	}

	

	public void shareMemory(String memory, String username){
		Connection conn = null;
		Statement stmt = null;
		int points = 0;
		try{
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		System.out.println("connected Tble");
		//STEP 4: Execute a query
	      System.out.println("Inserting records into the Memory table...");
	      stmt = conn.createStatement();
	      
	      String sql = "INSERT INTO Memories(Memory,U_ID) " +
                   "VALUES ('"+memory+"',(Select U_ID from users where USERNAME = '"+username+"'))";
	      stmt.executeUpdate(sql);
	      
	      stmt = conn.createStatement();
	       sql = "Select resource_points from users "
					+ "where USERNAME = '"+username+"'";
	      	
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
			points = rs.getInt("resource_points");
				points = points+5;
			}
		
			stmt.executeQuery(sql);
			rs.close();
			
			stmt = conn.createStatement();
			 				
			 sql = "UPDATE Users " +
	                   "SET Resource_Points = "+points+" "
	                   		+ "where USERNAME = '"+username+"'";
	      stmt.executeUpdate(sql);
	     
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
				
			
	public void transferPoints(int mypoints, String username){
		int points = 0;
		int userpoints = 0;
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		
//		  get User points and deduct by user input
	      stmt = conn.createStatement();
	      String sql = "Select resource_points from users "
					+ "where U_ID = "+getUserID()+"";
	      	
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				points = rs.getInt("resource_points");
				points = points-mypoints;
			}
		
			stmt.executeQuery(sql);
			rs.close();
			
			stmt = conn.createStatement();
			 				
			String sqli = "UPDATE Users " +
	                   "SET Resource_Points = "+points+" "
	                   		+ "where U_ID= '"+getUserID()+"'";
	      stmt.executeUpdate(sqli);
//	      -------------------------------------------
	      
//	      ------------Update Chosen User points-------
	      stmt = conn.createStatement();
	    String   sqll = "Select resource_points from users "
					+ "where username = '"+username+"'";
	      	
			 rs=stmt.executeQuery(sqll);
			while(rs.next()){
				userpoints = rs.getInt("resource_points");
				userpoints = userpoints+mypoints;
			}
		
			stmt.executeQuery(sqll);
			rs.close();
			
			stmt = conn.createStatement();
//			 				
		String sqlit = "UPDATE Users " +
				 "SET Resource_Points = "+userpoints+" "
            		+ "where USERNAME= '"+username+"'";
		stmt.executeUpdate(sqlit);
	      
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
	
}
