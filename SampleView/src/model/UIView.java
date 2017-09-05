package model;

import java.util.ArrayList;
import java.util.Iterator;

public class UIView {
	private String name;
	private ArrayList controllist=new ArrayList();

//set methods-------------------------------------	
	public void setName(String x)
	{
		name=x;	
	}
	public void addControl(String x)
	{
		Control cl=new Control(x);
		controllist.add(cl);
	}

//get methods-------------------------------------
	public String getName()
	{
		return(name);
	}
	public Control getControl(String x)
	{
		Iterator itr=controllist.iterator();
		while(itr.hasNext())
		{
			Object ob=itr.next();
			if(((Control)(ob)).getName().equals(x))
			{
				return((Control)ob);
			}
		}
		return(null);//returns the list of controls of user interface view of user interface set of project
	}
	public ArrayList getControl()
	{
		return(controllist);//returns the list of controls of user interface view of user interface set of project
	}
	
//remove methods----------------------------------
	public boolean removeControl(String x)
	{
		Iterator itr=controllist.iterator();
		while(itr.hasNext())
		{
			if(((Control)(itr.next())).getName().equals(x))
			{
				itr.remove();
				return(true);
			}
		}
		return(false);
	}

//constructors------------------------------------
	public UIView()
	{
		
	}
	public UIView(String x)
	{
		setName(x);
	}
}
