package cs5530;

public class TH {
	public int thid;
	public String category;
	public String url;
	public String name;
	public String address;
	public String phone;
	public String yearBuilt;
	public int price;
	public String login;
	
	public TH(int thid, String category, String url, String name, String address, String phone, String yearBuilt, int price, String login )
	{
		this.thid = thid;
		this.category = category;
		this.url = url;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.yearBuilt = yearBuilt;
		this.price = price;
		this.login = login;
	}
	
	@Override
	public String toString()
	{
		return "[thid: "+ String.valueOf(thid) +", category: " + category + ", url: " 
				+ url + ", name: " + name + ", address: " + address + ", phone: "
				+ phone + ", year built: " + yearBuilt + ", price: " + price + ", login: "
				+ login + "]";
	}
	
	

}
