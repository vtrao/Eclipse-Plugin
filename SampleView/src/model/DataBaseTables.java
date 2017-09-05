package model;

public class DataBaseTables {
	private String name;
	private String field;
	private String desc;
	private boolean hasPKey;
	private String key;

//set methods---------------------------------------------
	public void setName(String x)
	{
		name=x;
	}
	public void setKey(String x)
	{
		key=x;
	}
	public void setField(String x)
	{
		field=x;
	}
	public void setDescription(String x)
	{
		desc=x;
	}
	public void setHasPKey(boolean x)
	{
		hasPKey=x;
	}
	
//get methods------------------------------------------------	
	public String getName()
	{
		return(name);
	}
	public String getField()
	{
		return(field);
	}
	public String getDescription()
	{
		return(desc);
	}
	public String getKey()
	{
		return(key);
	}
	public boolean getHasPKey()
	{
		return(hasPKey);
	}
	
//constructors--------------------------------------------------
	public DataBaseTables()
	{
		
	}
	public DataBaseTables(String x)
	{
		setName(x);
	}	
}