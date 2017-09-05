package model;

import java.util.ArrayList;
import java.util.Iterator;

public class UISet {
	private String name;
	private ArrayList uiviewlist=new ArrayList();
	
//set methods-----------------------------------
	public void setName(String x)
	{
		name=x;	
	}
	public void addUIView(String x)
	{
		UIView uiv=new UIView(x);
		uiviewlist.add(uiv);
	}
//get methods-----------------------------------
	public String getName()
	{
		return(name);
	}
	public UIView getUIView(String x)
	{
		Iterator itr=uiviewlist.iterator();
		while(itr.hasNext())
		{
			Object ob=itr.next();
			if(((UIView)(ob)).getName().equals(x))
			{
				return((UIView)ob);
			}
		}
		return(null);
	}
	public ArrayList getUIView()
	{
		return(uiviewlist);//returns the list of user Interface Views of User Interface set of Project
	}
	
//remove methods--------------------------------
	public boolean removeUIView(String x)
	{
		Iterator itr=uiviewlist.iterator();
		while(itr.hasNext())
		{
			if(((UIView)(itr.next())).getName().equals(x))
			{
				itr.remove();
				return(true);
			}
		}
		return(false);
	}
	
//constructors----------------------------------
	public UISet()
	{
		
	}
	public UISet(String x)
	{
		setName(x);
	}
}
