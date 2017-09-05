package model;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * @author Venkiiiiiiiiiiiii
**/

public class Project {
	
	private String name;
	private ArrayList dbstorelist=new ArrayList();
	private ArrayList uisetlist=new ArrayList();
	
//set methods-----------------------------------------------------
	public void setName(String x)
	{
		name=x;
	}
	public void addDataBaseStore(String x)   // Iterator in util itr.next 
	{
		DataBaseStore db=new DataBaseStore(x);
		dbstorelist.add(db);
	}
	public void addUIset(String x)
	{
		UISet us=new UISet(x);
		uisetlist.add(us);		
	}
	
//get methods------------------------------------------------------
	public String getName()
	{
		return(name);	
	}
	
	public DataBaseStore getDataBaseStore(String x) // returns the handle of a databasestore with specified name x
	{
		Iterator itr=dbstorelist.iterator();
		while(itr.hasNext())
		{
			Object ob=itr.next();
			if(((DataBaseStore)(ob)).getName().equals(x))
			{
			return((DataBaseStore)ob);
			}
		}
		return(null);
	}
	
	public ArrayList getDataBaseStore()//returns the list of Data Base Stores in the Project
	{
		return(dbstorelist);
	}
	
	public UISet getUISet(String x)// returns the handle of a UISet with specified name x
	{
		Iterator itr=uisetlist.iterator();
		while(itr.hasNext())
		{
			Object ob=itr.next();
			if(((UISet)(ob)).getName().equals(x))
			{
				return((UISet)ob);
			}
		}
		return(null);		
	}
	
	public ArrayList getUISet()//returns the list of User Interface Sets in the Project
	{
		return(uisetlist);
	}
	
//remove methods----------------------------------------------------
	public boolean removeDataBaseStore(String x)
	{
		Iterator itr=dbstorelist.iterator();
		while(itr.hasNext())
		{
			if(((DataBaseStore)(itr.next())).getName().equals(x))
			{
			itr.remove();
			return(true);
			}
		}
		return(false);
	}
	public boolean removeUISet(String x)
	{
		Iterator itr=uisetlist.iterator();
		while(itr.hasNext())
		{
			if(((UISet)(itr.next())).getName().equals(x))
			{
				itr.remove();
				return(true);
			}
		}
		return(false);
	}
	
//constructors------------------------------------------------------
	public Project() // Default Constructor
	{
		
	}
	public Project(String x) // Constructor to set the name of Project Directly
	{
		setName(x);
	}
}
