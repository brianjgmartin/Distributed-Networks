package com.lynda.javatraining.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String CONN_STRING =
			"jdbc:mysql://localhost/MemoryCatcher";
	
	public static void main(String[] args) throws ClassNotFoundException {
		//Class.forName("con.mysql.jdbc.driver");
	Main m = new Main();
//	m.register("ff","ME");
//	m.register("second_user", "second_Pass");
//	m.login("second_user", "second_Pass");
//	m.login("ff", "ME");
//	m.addmemory("newmemory","ff");
	m.addResource("this is a resource for a memory","newmemory");
	}
	

	private  void register(String username , String password) {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		try{
			conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
		System.out.println("connected bitch");
		//STEP 4: Execute a query
	      System.out.println("Inserting records into the table...");  
	      stmt = conn.createStatement();
	      String sql = "INSERT INTO Users(USERNAME,PASSWD,RESOURCE_POINTS)" +
                  "VALUES ('"+username+"','"+password+"',20)";
	    		  
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
		
		public boolean login(String username, String password) {
			Connection conn = null;
			Statement stmt = null;
	 boolean log= false;
			try{
				conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("connected Tble");
			//STEP 4: Execute a query
		      System.out.println("Login...");
		      stmt = conn.createStatement();

		      String sql = "SELECT * FROM Users WHERE USERNAME = '"+username+"'";
		      				
		      ResultSet rs=stmt.executeQuery(sql);
		      //System.out.println(rs);
		      while(rs.next()){
		          //Retrieve by column name
		          int id  = rs.getInt("U_ID");
		        //Display values
		          if(id > 0 ){
		          log = true;   
		          System.out.println("cool registered");
		          break;
		          }
		          else {
		        	  log = false;
		        	  System.out.println("not registered");
		        	  break;
		          }
		          } 
		        	 
		      System.out.println("fuc");
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
		public void addmemory(String memory, String username){
			Connection conn = null;
			Statement stmt = null;
			try{
				conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("connected Tble");
			//STEP 4: Execute a query
		      System.out.println("Inserting records into the Memory table...");
		      stmt = conn.createStatement();
		      
		      String sql = "INSERT INTO Memories(Memory,U_ID) " +
	                   "VALUES ('"+memory+"',(Select U_ID from users where USERNAME = '"+username+"'))";
		      stmt.executeUpdate(sql);
		      System.out.println("Congrats you have added an memory");
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
		public void deleteMemory(String memory){
			Connection conn = null;
			Statement stmt = null;
			try{
				conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
			System.out.println("connected Tble");
			//STEP 4: Execute a query
		      System.out.println("Inserting records into the table...");
		      stmt = conn.createStatement();
		      
		      String sql = "DELETE FROM Memories "+memory+" varchar(255)";
		      stmt.executeUpdate(sql);
		      System.out.println("Congrats you have deleted a memory");
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



