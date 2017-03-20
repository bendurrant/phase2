package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

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
					User user = loginUser(con.stmt);
					if(user != null){
						enterApplication(con, user);
						break;
					}
					
					else
						System.out.println("Error! Enter valid username/password");
				}
				if (c == 2)
				{
					User user = registerUser(con.stmt);
					if(user != null){
						enterApplication(con, user);
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
	
	public static User registerUser(Statement stmt) throws IOException
	{
		User user = null;
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
			user = new User(login,password, false);
			
			
		}
		catch(SQLIntegrityConstraintViolationException e)
		{
			System.out.println("Username already exists. Please choose a different username.");
			
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query");
			System.out.println(e.getMessage());
		}
		
		return user;
	}

	public static User loginUser(Statement stmt) throws IOException
	{
		User user = null;
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
			if(rs.first())
			{
				user = new User(login, password, rs.getInt("userType") == 1);
			}
			
			
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
		return user;
	}
	
	public static void enterApplication(Connector con, User user) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//FIXME Make this.
		while (true) 
		{
			displayOptions();
			String choice = null;
			while ((choice = in.readLine()) == null || choice.length() == 0)
				;
			int c = 100000;
			try {
				c = Integer.parseInt(choice);
			} catch (Exception e) {
				c = 100000;
			}
			
			switch(c)
			{
			case 0:
				try {
					con.stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				break;
			case 1:
				createListing(con.stmt, user);
				break;
			case 2:
				//do this
				break;
			case 3:
				browseTH(con.stmt, user);
			}
			
		}
	}
	
	public static void browseTH(Statement stmt, User user) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Integer> searchPArray = new ArrayList<Integer>();
		//keywords is probably an arraylist
		String city,keyWords,category;
		String searchParams;
		int priceLow,priceHigh;
		System.out.println("      Browsing Menu       /n");
		System.out.println("Please select the parameters of your search");
		System.out.println("If you wish to select a combination of the following /n then enter all the numbers that correspond to parameters you want to search..");
		System.out.println("Example (If you wish to search by price and by address simply enter 12");
		System.out.println("However if you wish to search multiple things but not necessarily in combination.");
		System.out.println("Simply type a space between the numbers you wish to search. Example 1 2");
		System.out.println("");
		System.out.println("1. Price Range");
		System.out.println("2. Address(city)");
		System.out.println("3. Name by Keywords");
		System.out.println("4. Category");
		while ((searchParams = in.readLine()) == null || searchParams.length() == 0) {
			try
			{
				searchPArray.add(Integer.parseInt(searchParams));
			}
			catch(Exception e)
			{
				
				for(String x: searchParams.split(" "))
				{
					searchPArray.add(Integer.parseInt(x));
				}
			}
		}
		for(Integer param : searchPArray)
		{
			if(param.toString().contains("1"))
			{
				//get price from user
			}
			else if(param.toString().contains("2"))
			{
				//get city from user
			}
			else if(param.toString().contains("3"))
			{
				//get keyWords from user
			}
			else if(param.toString().contains("4"))
			{
				//get category from user
			}
		}
		
		String filter;
		System.out.println("Sort by ");
		System.out.println("1. Price");
		System.out.println("2. Average feedback score");
		System.out.println("3. Averge trusted user feedback score");
		while ((filter = in.readLine()) == null || filter.length() == 0)
			;
		try 
		{
			int c = Integer.parseInt(filter);
		} 
		catch (Exception e)
		{
			System.out.println("Please enter valid number");
		}
		
		//do the query
	}
	
	public static void createListing(Statement stmt,User user) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String category, url, name, address, phone, yearBuilt, priceString;
		int price = 0;
		System.out.println("Please Enter TH Category");
		while ((category = in.readLine()) == null || category.length() == 0 || category.length() > 40) {
			System.out.println("Invalid response please try again.");
		}
		
		System.out.println("Please enter year th was built:");
		while ((yearBuilt = in.readLine()) == null || yearBuilt.length() == 0 
				|| yearBuilt.length() > 4 || !yearBuilt.matches("[0-9]+")) {
			System.out.println("Invalid response please try again.");
		}

		System.out.println("Please enter the name for your th:");
		while ((name = in.readLine()) == null || name.length() == 0 || name.length() > 45) {
			System.out.println("Invalid response please try again.");
		}

		System.out.println("Please enter th phone number:");
		while ((phone = in.readLine()) == null || phone.length() == 0 || phone.length() > 10) {
			System.out.println("Invalid response please try again.");
		}

		System.out.println("Please enter the address of the th:");
		while ((address = in.readLine()) == null || address.length() == 0 || address.length() > 75) {
			System.out.println("Invalid response please try again.");
		}

		System.out.println("Please enter th url:");
		while ((url = in.readLine()) == null || url.length() == 0 || url.length() > 45) {
			System.out.println("Invalid response please try again.");
		}

		System.out.println("Please enter price of th per night:");
		while ((priceString = in.readLine()) == null || priceString.length() == 0 || price == 0) {
			try {
				price = Integer.parseInt(priceString);
				break;
			} catch (Exception e) {
				System.out.println("Please provide a number.");
				continue;
			}
		}
		
		String sql = "INSERT INTO TH (category, url, name, address, phone, yearBuilt, price, login) VALUES ('"+ category + "', '"+ url + "', '" + name + "', '"+ address + "', '" + phone + "', '" + yearBuilt + "', '" + price + "' ,'" + user.login + "');";
		System.out.println("executing "+sql);
		try{
			
			stmt.executeUpdate(sql);
			
		}
		catch(SQLIntegrityConstraintViolationException e)
		{
			System.out.println("Error please try again");
		}
		catch(Exception e)
		{
			System.out.println("cannot execute the query");
			System.out.println(e.getMessage());
		}
		
	}
		
	public static void displayOptions()
	{
		System.out.println("     Options     ");
		System.out.println("0. logout");
		System.out.println("1. Create a new listing");
		System.out.println("2. Alter existing TH");
		System.out.println("3. Browse TH");
	}


}
