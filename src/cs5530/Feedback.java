package cs5530;

public class Feedback {
	public int fid;
	public int thid;
	public int score;
	public String text;
	public String date;
	public String login;
	
	@Override
	public String toString()
	{
		return "Feedback[fid: " + fid + " thid: " + thid + " score: " + score + " text: \"" + text + "\" date " + date
				+ " login " + login;
	}
	public Feedback(int fid, int thid, int score, String text, String date, String login)
	{
		this.fid = fid;
		this.thid = thid;
		this.score = score;
		this.text = text;
		this.date = date;
		this.login = login;
	}

}
