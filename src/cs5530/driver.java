package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class driver {

	public driver() {}
	/**
	 * @param args
	 */
	public static void displayMenu()
	{
		System.out.println("		Welcome to the UTrack System     ");
		System.out.println("1. Sign in with existing account");
		System.out.println("2. Register a new Account");
		System.out.println("3. exit:");
		System.out.println("Please enter your choice:");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Example for cs5530");
		Connector con=null;
		String choice;
		String cname;
		String dname;
		String sql=null;
		//comment
		int c=0;
		try
		{
			//remember to replace the password
			con= new Connector();
			System.out.println ("Database connection established");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while(true){
				displayMenu();
				while ((choice = in.readLine()) == null || choice.length() == 0)
					;
				try {
					c = Integer.parseInt(choice);
				} catch (Exception e) {
					continue;
				}
				if (c < 1 | c > 3)
					continue;
				// Case for logging in
				if (c == 1) {
					if(loginUser(con.stmt)){
						enterApplication(con);
						break;
					}
					
					else
						System.out.println("Error! Enter valid username/password");
				}
				if (c == 2)
				{
					if(registerUser(con.stmt)){
						enterApplication(con);
					}
					break;
				}
				else{
					 System.out.println("EoM");
            		 con.stmt.close();        
            		 break;
				}
			}
			
			

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println ("Either connection error or query execution error!");
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.closeConnection();
					System.out.println ("Database connection terminated");
				}

				catch (Exception e) { /* ignore close errors */ }
			}	 
		}
		

	}
	
	public static boolean registerUser(Statement stmt) throws IOException
	{
		boolean result = false;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String login, password, name, address, phoneNumber;
		
		System.out.println("Enter Login Name: ");
		while((login = in.readLine()) == null || login.length() == 0);

		System.out.println("Enter Password: ");
		while((password = in.readLine()) == null || password.length() == 0);
		
		System.out.println("Enter name:");
		while((name = in.readLine()) == null || login.length() == 0);

		System.out.println("Enter address: ");
		while((address = in.readLine()) == null || password.length() == 0);
		
		System.out.println("Enter phone number: ");
		while((phoneNumber = in.readLine()) == null || password.length() == 0);
		
		String sql = "INSERT INTO Users (login, name, userType, address, phoneNumber, password) VALUES ('"+ login + "', '"+ name + "', '" + "0" + "', '"+ address + "', '" + phoneNumber + "', '" + password +"');";
		
		System.out.println("executing "+sql);
		try{
			
			stmt.executeUpdate(sql);
			result = true;
			
			
		}
		catch(SQLIntegrityConstraintViolationException e)
		{
			System.out.println("Username already exists. Please choose a different username.");
			return false;
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query");
			System.out.println(e.getMessage());
			result = false;
		}
		
		return result;
	}

	public static boolean loginUser(Statement stmt) throws IOException
	{
		boolean result = false;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String login, password;
		System.out.println("Enter Login Name: ");
		while((login = in.readLine()) == null || login.length() == 0);

		System.out.println("Enter Password: ");
		while((password = in.readLine()) == null || password.length() == 0);

		String sql = "select * from Users where login = '" + login + "' and password = '" + password + "';";
		ResultSet rs = null; 
		System.out.println("executing "+sql);
		try{
			
			rs=stmt.executeQuery(sql);
			if(rs.first()) result = true;
			
			
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query");
			System.out.println(e.getMessage());
		}
		finally
		{
			try{
				if (rs!=null && !rs.isClosed())
					rs.close();
			}
			catch(Exception e)
			{
				System.out.println("cannot close resultset");
			}
		}
		return result;
	}
	
	public static void enterApplication(Connector con)
	{
		//FIXME Make this.
		System.out.println("Application entered. Pretend there is a menu.");
	}
}
