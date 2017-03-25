package cs5530;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.Spring;

import com.mysql.jdbc.StringUtils;

public class driver {

	public driver() {}
	/**
	 * @param args
	 */
	static Session currentSession;
	public static void displayMenu()
	{
		System.out.println("		Welcome to the Uotel System     ");
		System.out.println("1. Sign in with existing account");
		System.out.println("2. Register a new Account");
		System.out.println("3. Exit:");
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
				if(c==7)//cheat in without typing
				{
					User boss = new User("JoshNelson", "IHeartGrannies", true);
					enterApplication(con, boss);
					break;
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
		currentSession=new Session(user);
		leaveFeedback(con.stmt, user, 5, "It was alright", new TH(5, null, null, null, null, null, null, 0, null));
	
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
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
					return;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				break;
			case 1:
				createListing(con.stmt, user);
				break;
			case 2:
				editListing(con, user);
				break;
			case 3:
				browseTH(con, user);
				break;
			case 6:
				degreesOfSeperation(con);
			case 8:
				displayStats(con,user);
				break;
			}
			
		}
	}
	
	public static void degreesOfSeperation(Connector con) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String firstUser = null;
		String secondUser = null;
		while(true){
		System.out.println("Enter the first users login");
		while((firstUser = in.readLine()) == null || firstUser.length() == 0)
			System.out.println("Please Enter Valid Login");
		try{

		}
		catch(Exception e)
		{
			System.out.println("Please input a valid Login");
		}
		String sql = "Select * From user WHERE login = '" + firstUser + "';";
		ResultSet rs = null;
		boolean isValid = false;
				try {
					rs = con.stmt.executeQuery(sql);
					isValid = rs.next();
					rs.close();
				}
		catch (Exception e) {
			System.out.println("cannot execute query: " + sql);
		} finally {
			try {
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		if(isValid)
		{
			break;
		}
		else
		{
			System.out.println("Not a valid login");
		}
		}
		while(true){
			System.out.println("Enter the second users login");
			while((secondUser = in.readLine()) == null || secondUser.length() == 0)
				System.out.println("Please Enter Valid Login");
			try{

			}
			catch(Exception e)
			{
				System.out.println("Please input a valid Login");
			}
			String sql = "Select * From user WHERE login = '" + secondUser + "';";
			ResultSet rs = null;
			boolean isValid = false;
					try {
						rs = con.stmt.executeQuery(sql);
						isValid = rs.next();
						rs.close();
					}
			catch (Exception e) {
				System.out.println("cannot execute query: " + sql);
			} finally {
				try {
					if (rs != null && !rs.isClosed())
						rs.close();
				} catch (Exception e) {
					System.out.println("cannot close resultset");
				}
			}
			if(isValid)
			{
				break;
			}
			else
			{
				System.out.println("Not a valid login");
			}
			}
		
		if(twoDegreesSeparated(con,firstUser,secondUser))
		{
			System.out.println("yes");
		}
		else
		{
			System.out.println("no");
		}
	}
	public static boolean twoDegreesSeparated(Connector con, String firstUser, String secondUser)
	{
		//TODO write the query to see if they are 
		//two degrees apart
		return false;
	}
	public static void displayStats(Connector con, User user) throws IOException
	{
		System.out.println("1. View most popular TH by category");
		System.out.println("2. View most expensive TH by category");
		System.out.println("3. View highest rated TH by category");
		System.out.println("Enter a number to see corresponding statistics");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input = null;
		int inputInt = -1;
		while((input = in.readLine()) == null || input.length() == 0)
				;
			try{
				inputInt = Integer.parseInt(input);
			}
			catch(Exception e)
			{
				System.out.println("Please input a valid number");
			}
		int choice = inputInt;
		System.out.println("Enter max number of houses to be displayed per Category");
		while(true)
		{
			input = null;
			int maxEntries = -1;
			while((input = in.readLine()) == null || input.length() == 0)
				;
			try{
				maxEntries = Integer.parseInt(input);
			}
			catch(Exception e)
			{
				System.out.println("Please input a valid number");
				continue;
			}
			
			if(choice == 1)
			{
				ArrayList<TH> mostPop = findMostPopular(con);
				//mostPop = limitToMaxEntries(mostPop, maxEntries);
				viewTH(mostPop,con,user);
				
			}
			if(choice == 2)
			{
				ArrayList<TH> mostExpensive = findMostExpensive(con, maxEntries);
				//mostExpensive = limitToMaxEntries(mostExpensive, maxEntries);
				viewTH(mostExpensive,con,user);
			}
			if(choice == 3)
			{
				ArrayList<TH> bestRated = findBestRated(con);
				//bestRated = limitToMaxEntries(bestRated, maxEntries);
				viewTH(bestRated,con,user);
			}
		}
	}
	
	
	public static ArrayList<TH> findBestRated(Connector con)
	{
		String sql = "select DISTINCT category AS category FROM TH;";
		ResultSet rs = null;
		ArrayList<String> categories = new ArrayList<String>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next())
			{
				 categories.add(rs.getString("category"));
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("cannot execute query: " + sql);
			return null;
		} finally {
			try {
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		ArrayList<TH> returnThList = new ArrayList<TH>();
		for(String cat : categories)
		{
			sql = "";// TODO write correct query!
			
			
			try{
				rs = con.stmt.executeQuery(sql);
				while(rs.next())
				{
					TH temp = new TH(rs.getInt("thid"), rs.getString("category"), rs.getString("url"), rs.getString("name"), rs.getString("address"), rs.getString("phone"),rs.getString("yearBuilt") , rs.getInt("price"), rs.getString("login"));
					returnThList.add(temp);
				}
				rs.close();
			}
			catch(Exception e) {
				System.out.println("cannot execute query: " + sql);
				return null;
			} finally {
				try {
					if (rs != null && !rs.isClosed())
						rs.close();
				} catch (Exception e) {
					System.out.println("cannot close resultset");
				}
			}
		}
		
		return returnThList;
	}
	
	public static ArrayList<TH> findMostExpensive(Connector con, int rowLimit)
	{
		String sql = "select DISTINCT category AS category FROM TH;";
		ResultSet rs = null;
		ArrayList<String> categories = new ArrayList<String>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next())
			{
				 categories.add(rs.getString("category"));
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("cannot execute query: " + sql);
			return null;
		} finally {
			try {
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		ArrayList<TH> returnThList = new ArrayList<TH>();
		for(String cat : categories)
		{
			sql = "select * from TH t WHERE t.category ='" + cat + "' Order BY t.price DESC LIMIT "+ rowLimit + ";";
			
			
			try{
				rs = con.stmt.executeQuery(sql);
				while(rs.next())
				{
					TH temp = new TH(rs.getInt("thid"), rs.getString("category"), rs.getString("url"), rs.getString("name"), rs.getString("address"), rs.getString("phone"),rs.getString("yearBuilt") , rs.getInt("price"), rs.getString("login"));
					returnThList.add(temp);
				}
				rs.close();
			}
			catch(Exception e) {
				System.out.println("cannot execute query: " + sql);
				return null;
			} finally {
				try {
					if (rs != null && !rs.isClosed())
						rs.close();
				} catch (Exception e) {
					System.out.println("cannot close resultset");
				}
			}
		}
		
		return returnThList;
		
	}
	
	public static ArrayList<TH> findMostPopular(Connector con)
	{
		String sql = "select DISTINCT category AS category FROM TH;";
		ResultSet rs = null;
		ArrayList<String> categories = new ArrayList<String>();
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next())
			{
				 categories.add(rs.getString("category"));
			}
			rs.close();
		}
		catch(Exception e) {
			System.out.println("cannot execute query: " + sql);
			return null;
		} finally {
			try {
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		ArrayList<TH> returnThList = new ArrayList<TH>();
		for(String cat : categories)
		{
			sql = "";// TODO write correct query!
			
			
			try{
				rs = con.stmt.executeQuery(sql);
				while(rs.next())
				{
					TH temp = new TH(rs.getInt("thid"), rs.getString("category"), rs.getString("url"), rs.getString("name"), rs.getString("address"), rs.getString("phone"),rs.getString("yearBuilt") , rs.getInt("price"), rs.getString("login"));
					returnThList.add(temp);
				}
				rs.close();
			}
			catch(Exception e) {
				System.out.println("cannot execute query: " + sql);
				return null;
			} finally {
				try {
					if (rs != null && !rs.isClosed())
						rs.close();
				} catch (Exception e) {
					System.out.println("cannot close resultset");
				}
			}
		}
		
		return returnThList;
	}
	
	public static void editListing(Connector con,User user) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<TH> usersTH = new ArrayList<TH>();
		String sql = "Select * from TH where login = '" + user.login + "';";
		System.out.println("executing "+sql);
		ResultSet rs = null;
		
		try{
			rs = con.stmt.executeQuery(sql);
			while(rs.next())
			{
				TH th = new TH(rs.getInt("thid"), rs.getString("category"), rs.getString("url"), rs.getString("name"), rs.getString("address"), rs.getString("phone"),rs.getString("yearBuilt") , rs.getInt("price"), rs.getString("login"));
				usersTH.add(th);
			}
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("cannot execute query: " + sql);
		}finally{
			try{
				if (rs != null && !rs.isClosed())
					rs.close();
			} catch (Exception e) {
				System.out.println("cannot close resultset");
			}
		}
		
		
		if(usersTH.isEmpty())
		{
			System.out.println("You don't have any THs to edit");
			System.out.println("Returning to Main Menu");
			return;
		}
		System.out.println("     Your THs      ");
		int count = 0;
		for(TH th: usersTH)
		{
			System.out.println(count + ". " + th.toString());
			count++;
		}
		
		System.out.println("Enter number of TH you wish to update");
		String input = null;
		int inputInt = -1;
		while ((input = in.readLine()) == null || input.length() == 0)
			;
		try 
		{
			inputInt = Integer.parseInt(input);
		} 
		catch (Exception e)
		{
			System.out.println("Please enter valid number");
		}
		
		TH selectedTH = usersTH.get(inputInt);
		boolean update= true;
		try{
			while(update == true)
			{
				System.out.println("Please enter the number of the field you wish to update");
				System.out.println("1. Category");
				System.out.println("2. url");
				System.out.println("3. name");
				System.out.println("4. address");
				System.out.println("5. phone");
				System.out.println("6. yearBuilt");
				System.out.println("7. price");
				System.out.println("8. Keywords");
				System.out.println("9 Availabilities");
				System.out.println("10. Finished Updating");
				while ((input = in.readLine()) == null || input.length() == 0)
					;
				try 
				{
					inputInt = Integer.parseInt(input);
				} 
				catch (Exception e)
				{
					System.out.println("Please enter valid number");
				}
				switch(inputInt)
				{
				case 1:
					System.out.println("Enter new Category");
					while ((input = in.readLine()) == null || input.length() == 0 || input.length() > 40)
						System.out.println("enter valid input");
					selectedTH.category = input;
					break;
				case 2:
					System.out.println("Enter new URL");
					while ((input = in.readLine()) == null || input.length() == 0 || input.length() > 45)
						System.out.println("enter valid input");
					selectedTH.url = input;
					break;
				case 3:
					System.out.println("Enter new Name");
					while ((input = in.readLine()) == null || input.length() == 0 || input.length() > 45)
						System.out.println("enter valid input");
					selectedTH.name = input;
					break;	
				case 4:
					System.out.println("Enter new address");
					while ((input = in.readLine()) == null || input.length() == 0 || input.length() > 75)
						System.out.println("enter valid input");
					selectedTH.address = input;
					break;
				case 5:
					System.out.println("Enter new phone");
					while ((input = in.readLine()) == null || input.length() == 0 || input.length() > 10)
						System.out.println("enter valid input");
					selectedTH.phone = input;
					break;
				case 6:
					System.out.println("Enter new yearBuilt");
					while ((input = in.readLine()) == null || input.length() == 0|| input.length() > 4 || !input.matches("[0-9]+"))
						System.out.println("enter valid input");
					selectedTH.yearBuilt = input;
					break;
					
				case 7:
					System.out.println("Enter new price");
					while ((input = in.readLine()) == null || input.length() == 0 || input == "0")
						System.out.println("enter valid input");
					try{
					selectedTH.price = Integer.parseInt(input);
					}catch(Exception e)
					{
						System.out.println("enter valid number");
					}
					break;
				case 8:
					System.out.println("Enter new Keyword");
					//while ((input = in.readLine()) == null || input.length() == 0)
					//	;
					//figure out how to do this needs option to add and remove
					//selectedTH.name = input;
					break;
				case 9:
					System.out.println("Enter new Availability date");
					//while ((input = in.readLine()) == null || input.length() == 0)
					//	;
					//figure out how to do this
					//most likely needs a date range. From start to end
					//selectedTH.name = input;
					break;
				case 10:
					System.out.println("Updating TH");
					sql = "Update TH SET category='" + selectedTH.category + "', url='" + selectedTH.url + "', name='"
							+ selectedTH.name + "', address='" + selectedTH.address + "', phone='" + selectedTH.phone + "', yearbuilt='"
							+ selectedTH.yearBuilt + "', price ='" + selectedTH.price + "' WHERE thid='"+ selectedTH.thid +"';";
					try{
						System.out.println("executing: " + sql);
						con.stmt.executeUpdate(sql);
						
						
						
					}
					catch(Exception e)
					{
						System.out.println("cannot execute the query");
						System.out.println(e.getMessage());
					}
					update = false;
					break;
				default:
					System.out.println("Enter valid selection");
				}
				
				
				
			}
		}
		catch(Exception e)
		{
			System.out.println("error executing query.");
			return;
			
		}
	}
	
	public static void leaveFeedback(Statement stmt,User user,int score,String text,TH th)
	{
		LocalDate now= LocalDate.now();
		java.sql.Date sqlDate=java.sql.Date.valueOf(now);
		
		
		String sql = "INSERT INTO Feedback (thid, score, text, fbdate, login) VALUES ('"+ th.thid + "', '"+ score + "', '" + text + "', '"+ sqlDate + "', '" + user.login +"');";
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
	
	public static void browseTH(Connector con, User user) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Integer> searchPArray = new ArrayList<Integer>();
		//String[] keywords;	
		String city,keyWord,category,state;
		String searchParams;		
		int priceLow,priceHigh;
		int filterMode;
		int filterType;
		System.out.println("      Browsing Menu       /n");
		System.out.println("Please select the parameters of your search");
		System.out.println("If you wish to select a combination of the following /n then enter all the numbers that correspond to parameters you want to search..");
		System.out.println("Example (If you wish to search by price and by address simply enter 12");
		System.out.println("However if you wish to search multiple things but not necessarily in combination.");
		System.out.println("Simply type a space between the numbers you wish to search. Example 1 2");
		System.out.println("");
		System.out.println("1. Price Range");
		System.out.println("2. Address(city)");
		System.out.println("2. Address(state)");
		System.out.println("4. Name by Keywords");
		System.out.println("5. Category");
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
		for(Integer param : searchPArray)//get price range
		{
			String input= null;
			if(param.toString().contains("1"))
			{
			
				
				input=null;
				int out=-1;
				System.out.println("Please enter the low price range: ");
				while ((input = in.readLine()) == null || input.length() == 0)
					;
				try 
				{
					out = Integer.parseInt(input);
				} 
				catch (Exception e)
				{
					System.out.println("Please enter valid number");
				}
				priceLow=out;
				
				System.out.println("Please enter the high price range: ");
				while ((input = in.readLine()) == null || input.length() == 0)
					;
				try 
				{
					out = Integer.parseInt(input);
				} 
				catch (Exception e)
				{
					System.out.println("Please enter valid number");
				}
				priceHigh=out;
			}
			else if(param.toString().contains("2"))
			{
				input=null;
				//get city from user
				System.out.println("Please enter the city you wish to search for: ");
				while ((input = in.readLine()) == null || input.length() == 0)
					;
				city=input;
				
			}
			else if(param.toString().contains("3"))
			{
				input=null;
				//get State from user
				System.out.println("Please enter the state you wish to search for: ");
				while ((input = in.readLine()) == null || input.length() == 0)
					;
				state=input;
			}
			else if(param.toString().contains("4"))
			{
				//get keyWords from user
				input=null;
				
				System.out.println("Please enter the keyword you wish to search for: ");
				while ((input = in.readLine()) == null || input.length() == 0)
					;
				//keywords=input.split(",");
				keyWord=input;
			}
			else if(param.toString().contains("5"))
			{
				//get category from user
				input=null;
				System.out.println("Please enter the category you wish to search for: ");
				while ((input = in.readLine()) == null || input.length() == 0)
					;
				
				category=input;
			}
		}
		
		String filter;
		System.out.println("Sort by ");
		System.out.println("1. Price");
		System.out.println("2. Average feedback score");
		System.out.println("3. Averge trusted user feedback score");
		while ((filter = in.readLine()) == null || filter.length() == 0)
			;
		int c=1;
		try 
		{
			c = Integer.parseInt(filter);
		} 
		catch (Exception e)
		{
			System.out.println("Please enter valid number");
		}
		filterType=c;
		
		String filter2;
		System.out.println("Sort ");
		System.out.println("1. Ascending");
		System.out.println("2. Descending");		
		while ((filter2 = in.readLine()) == null || filter2.length() == 0)
			;
		int c2=1;
		try 
		{
			c = Integer.parseInt(filter);
		} 
		catch (Exception e)
		{
			System.out.println("Please enter valid number");
		}
		filterMode=c2;
//********************The Query*****************************************//
// Below I have left some tips in how I might go about doing this. 
// If you no like then you can always do it however you want
// I was just trying to be helpful.
		
		//I would start with some sort of select
		//where you join all the tables 
		
		StringBuilder Select = new StringBuilder();
		StringBuilder From = new StringBuilder();
		StringBuilder Where = new StringBuilder();
		StringBuilder OrderBy= new StringBuilder();
		
		Select.append("SELECT *");
		From.append("FROM TH ");
		Where.append("WHERE ");
		OrderBy.append("Order By ");
		
		
		//deciding if its AND or OR 
		for(Integer param : searchPArray)
		{
			if(param.toString().length()>1)
			{
				String paramString = param.toString();
				//its an and
				for(char paramChar : paramString.toCharArray())
				{
					
					if(paramChar == '1')
					{
					//this means query for price
					//example sql line 
					//sql += t.price >= priceLow AND t.price <= priceHigh
					
					}
					
					else if(paramChar == '2')
					{
						//this means query for city
						//here stone suggests use 
						// "LIKE %" + city + "%"
						//the above line is not a complete statement
					}
					else if(paramChar == '3')
					{
						//this means query for State
						//use similar Like statement as city
					}
					else if(paramChar == '4')
					{
						//this means keyword
						//I have no tips here 
					}
					else if(paramChar == '5')
					{
						//this is category
						//I don't really have a tip here either
						//I don't invision this one being tough though
					}
					
					if(paramString.indexOf(paramChar) != paramString.length()-1)
					{
						//if this were true it would be the last of our
						//AND query so we do this check to see if we need
						//to append AND
						//maybe something like
						//sql += "AND"
					}
				}
				 
			}
			else
				//this is the or statement part
			{
				if(param == 1)
				{
				//this means query for price
				//example sql line 
				//sql += t.price >= priceLow AND t.price <= priceHigh
				
				}
				
				else if(param == 2)
				{
					//this means query for city
					//here stone suggests use 
					// "LIKE %" + city + "%"
					//the above line is not a complete statement
				}
				else if(param== 3)
				{
					//this means query for State
					//use similar Like statement as city
				}
				else if(param == 4)
				{
					//this means keyword
					//I have no tips here 
				}
				else if(param== 5)
				{
					//this is category
					//I don't really have a tip here either
					//I don't invision this one being tough though
				}
				if(searchPArray.indexOf(param) != searchPArray.size()-1)
				{
					//if this were true it would be the last of our
					//OR query so we do this check to see if we need
					//to append OR
					//maybe something like
					//sql += "OR"
				}
			}
		}
		
		//Now we apply the search filter
		if(filter.equals(1))
		{
			//this means sort by price
			//I would assume easiest way to do this would be 
			//"ORDER BY t.price DESC" 
			//I believe this will do it by price in descending order
		}
		else if(filter.equals(2))
		{
			//this means sort by average feedback score
			//I would assume this will be as easy as the price
			//"ORDER BY AVG(feedback score) DESC"
			// this line isn't exact because I don't remember what we called
			//feedback score
		}
		else if(filter.equals(3))
		{
			//this is sort by average feedback score from only trusted users
			//You will have to figure out how to get the average feedback
			//from only trusted users
			// I would think you would want to do some sort of select where
			//you only take user feedback if they are trusted. 
			//maybe you do this check earlier when you are doing your original
			//select and you only join it with the feedback table where you
			//selected only trusted users. If you did this then you wouldn't 
			//need this if statement and could just use the one above this for
			//both cases 2 and 3
			//I don't know what will be easiest.
			//These are only my suggestions. 
		}
		
		
		//now execute query.
		//you will want to save the result set here
		//ArrayList<TH> thlist = new ArrayList<TH>();
		//At this moment I don't have a TH class made yet.
//		try{
//			//execute the query
			//iterate throught the result set and create new TH objects
			//for each line in the result set
//		}
//		catch(Exception e)
//		{
//			System.out.println("cannot execute query: " + sql);
//		}
//		finally{
//			try{
//				if(rs != null && !rs.isClosed())
//				{
//					rs.close();
//				}
//			}
//			catch(Exception e)
//			{
//				System.out.println("cannot close resultset");
//			}
//		}
		
		// at this point we should have all TH in that array list and we 
		//call the following function to display
		//viewTH(thList,con, user);
		
		
	}
	
	
	
	public static void viewTH(ArrayList<TH> allTH, Connector con, User user) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			System.out.println("     Temporary Houses      ");
			int count = 1;
			for(TH th: allTH)
			{
				//we might need to change how this prints
				//and get rid of any info that we think
				//might not be necessary
				System.out.println(count + th.toString());
				count++;
			}
			System.out.println("Enter the number that of the house you wish to view or enter 0 to return");
			while(true)
			{
				String input = null;
				while((input = in.readLine()) == null && input.length() == 0)
					;
				int inputInt = -1;
				try{
					inputInt = Integer.parseInt(input);
				}
				catch(Exception e)
				{
					System.out.println("Please input valid number");
					continue;
				}
				if(inputInt == 0)
				{
					enterApplication(con,user);
				}
				if(inputInt <=0 || inputInt > allTH.size())
				{
					System.out.println("Please enter valid number");
				}
				else
				{
					thAction(allTH.get(inputInt-1), con, user);
					break;
				}
			}
		}
	}
	
	public static void thAction(TH currentTH, Connector con, User user) throws IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("You have selected TH: " + currentTH.toString());
		while(true)
		{
			System.out.println("   TH Actions    ");
			System.out.println("1. Make a reservation");
			System.out.println("2. View Feedback");
			System.out.println("3. Give Feedback");
			//there is probably more we need to be able to do here
			//add to the bottom of this list if your thing isn't listed.
			System.out.println("Enter the Action number or 0 to return");
			String input = null;
			while((input = in.readLine()) == null || input.length() == 0)
				;
			int inputInt = -1;
			try{
				inputInt = Integer.parseInt(input);
			}
			catch(Exception e)
			{
				System.out.println("Please enter valid number");
				continue;
			}
			if(inputInt == 0)
			{
				return;
			}
			if(inputInt == 1)
			{
				//make reservation
			}
			if(inputInt == 2)
			{
				//view feedback
			}
			if(inputInt == 3)
			{
				//give feedback
			}
			
		}
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
		System.out.println("6. Degrees of Seperation");
		System.out.println("8. View stats");

	}


}
