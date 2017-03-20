package cs5530;

public class User {
	public String login;
	public String password;
	public boolean isAdmin;
	
	public User(String login, String password, boolean isAdmin)
	{
		//UserType is whether the user is admin or not
		this.login = login;
		this.password = password;
		this.isAdmin = isAdmin;
	}
}
