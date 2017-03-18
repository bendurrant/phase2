package cs5530;

public class driver {

	public driver() {}
		/**
		 * @param args
		 */
		public static void displayMenu()
		{
			 System.out.println("		Welcome to the UTrack System     ");
	    	 System.out.println("1. search a course by cname and dname:");
	    	 System.out.println("2. enter your own query:");
	    	 System.out.println("3. exit:");
	    	 System.out.println("pleasse enter your choice:");
		}
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			System.out.println("Example for cs5530");
			Connector con=null;
	         try
			 {
				//remember to replace the password
				 	 con= new Connector();
				 	 System.out.println ("Database connection established");
				 	 con.stmt.close();
				 	 
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
	

}
