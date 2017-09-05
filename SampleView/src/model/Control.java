package model;

public class Control {
	private String name;
	private String label;

//set methods-------------------------------------------
	public void setName(String x)
	{
		name=x;
	}
	public void setLabel(String x)
	{
		label=x;
	}
	
//get methods-------------------------------------------
	public String getName()
	{
		return(name);
	}	
	public String getLabel()
	{
		return(label);
	}
	
//constructors-------------------------------------------
	public Control()
	{
		
	}
	public Control(String x)
	{
		setName(x);
	}
}
