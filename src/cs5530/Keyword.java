package cs5530;

public class Keyword {
	public int wid;
	public String word;
	public Keyword(int wid, String word)
	{
		this.wid = wid;
		this.word = word;
	}
	@Override
	public String toString()
	{
		return "id: " + wid + " word: " + word;
	}
}
