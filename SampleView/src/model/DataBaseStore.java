package model;

import java.util.ArrayList;
import java.util.Iterator;

public class DataBaseStore {
	private String dataBaseStoreName;
	private String key;
	private String description;
	private ArrayList dbtablelist=new ArrayList();
	
//set methods-----------------------------------------
	public void setName(String x)
	{
		dataBaseStoreName=x;
	}
	public void setKey(String x)
	{
		key=x;
	}
	public void setDescription(String x)
	{
		description=x;
	}
	public void addDataBaseTable(String x)
	{
		DataBaseTables dbts=new DataBaseTables(x);
		dbtablelist.add(dbts);
	}
	
//getmethods------------------------------------------
	public String getName()
	{
		return(dataBaseStoreName);
	}
	public String getKey()
	{
		return(key);
	}
	public String getDescription()
	{
		return(description);
	}
	public DataBaseTables getDataBaseTable(String x)
	{
		Iterator itr=dbtablelist.iterator();
		while(itr.hasNext())
		{
			Object ob=itr.next();
			if(((DataBaseTables)(ob)).getName().equals(x))
			{
				return((DataBaseTables)ob);
			}
		}
		return(null);//returns the list of tables of data base store of project
	}
	public ArrayList getDataBaseTable()
	{
		return(dbtablelist);//returns the list of tables of data base store of project
	}
//remove methods---------------------------------------
	public boolean removeDataBaseTable(String x)
	{
		Iterator itr=dbtablelist.iterator();
		while(itr.hasNext())
		{
			if(((DataBaseTables)(itr.next())).getName().equals(x))
			{
				itr.remove();//use Iterator here
				return(true);
			}
		}
		return(false);
	}
//constructors-----------------------------------------
	public DataBaseStore()
	{
		
	}
    public DataBaseStore(String x)
    {
    		setName(x);   		
    }
}
