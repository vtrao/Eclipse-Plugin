package sampleView;

import java.util.ArrayList;
import java.util.Iterator;
import model.*;

import org.eclipse.core.runtime.IAdaptable;

public class TreeObject implements IAdaptable {
	
	//The member Variables of TreeObject
	public int type;
	private String name;
	private TreeParent parent;
    private ArrayList elements;// to Store the handles of its elements
    private ArrayList elements1;
    private String label=null;
    private String description=null;
    private String key=null;
    private String field=null;
    private boolean hasPKey=false;
    	    
    
    
	//The Constructor of This Class Used to create the object and setting its name----------------------------
	public TreeObject(String name) {
		this.name = name;
		elements=new ArrayList();
		elements1=new ArrayList();
	}		
	
	//The Setter Methods of TreeObject------------------------------------------------------------------------
	public void setParent(TreeParent parent) {
		this.parent = parent;
	}
	
	public void setElements(ArrayList elements) {
		this.elements=elements;
	}		

	public void setElements1(ArrayList elements1) {
		this.elements1=elements1;
	}		
	public void setLabel(String label)
	{
		this.label=label;
	}
	public void setDescription(String description)
	{
		this.description=description;
	}
	public void setKey(String key)
	{
		this.key=key;
	}
	public void setField(String field)
	{
		this.field=field;
	}
	public void setHasPKey(boolean hasPKey)
	{
		this.hasPKey=hasPKey;
	}
	public void addElement(Object obj) {
		elements.add(obj);
	}		
	
	public void addElement1(Object obj) {
		elements1.add(obj);
	}
	
	public void setType(int type,Object obj) {
		this.type=type;
		if(type==1) // 1 for Project
		{
			Project p=new Project(name);
			((TreeObject)obj).addElement(p); // to add project handles to invisible root Tree object
			showMessage("Project Successfully Created as "+name+"\n"+p);
			return;
		}
		else if(type==2) // 2 for DatabaseStore
		{
			ArrayList a;
			
			a=((TreeObject)obj).getParent().getElements();//invisibleRoot.getElements();
			Iterator itr=a.iterator();
			for(;itr.hasNext();) {
				Object ob=itr.next();
				if(((TreeObject)obj).getName().equals(((Project)ob).getName())) {
					((Project)ob).addDataBaseStore(name); //to add the newly created databaseStore to corresponding project
					((TreeObject)obj).addElement(((Project)ob).getDataBaseStore(name));
					((Project)ob).getDataBaseStore(name).setDescription(description);
					((Project)ob).getDataBaseStore(name).setKey(key);
					showMessage("DataBaseStore Successfully Created   as "+name+"\n"+((Project)ob).getDataBaseStore(name)
							+"\nwith Description "+((Project)ob).getDataBaseStore(name).getDescription()
							+"\nwith key "+((Project)ob).getDataBaseStore(name).getKey());
					return;
				}
			}				
		}
		else if(type==3) // 3 for DatabaseTable
		{				
			TreeObject a;
			a=((TreeParent)obj).getParent();
			ArrayList al=((TreeObject)a).getElements();
			Iterator itr=al.iterator();
			for(;itr.hasNext();) {
				Object ob=itr.next();
				if(((DataBaseStore)ob).getName().equals(((TreeObject)obj).getName())) {
					((DataBaseStore)ob).addDataBaseTable(name);
					((TreeObject)obj).addElement(((DataBaseStore)ob).getDataBaseTable(name));
					((DataBaseStore)ob).getDataBaseTable(name).setDescription(description);
					((DataBaseStore)ob).getDataBaseTable(name).setField(field);
					((DataBaseStore)ob).getDataBaseTable(name).setHasPKey(hasPKey);
					((DataBaseStore)ob).getDataBaseTable(name).setKey(key);
					showMessage("DataBaseTable Successfully Created as "+name+"\n"+((DataBaseStore)ob).getDataBaseTable(name)
							+"\nwith Description "+((DataBaseStore)ob).getDataBaseTable(name).getDescription()
							+"\nwith Field "+((DataBaseStore)ob).getDataBaseTable(name).getField()
							+"\nwith Key "+((DataBaseStore)ob).getDataBaseTable(name).getKey()
							+"\nhasPkey is "+((DataBaseStore)ob).getDataBaseTable(name).getHasPKey());
					return;
				}
			}					
		}
		else if(type==4) // 4 for UISet
		{
			ArrayList a;
			a=((TreeObject)obj).getParent().getElements();//invisibleRoot.getElements();
			Iterator itr=a.iterator();
			for(;itr.hasNext();) {
				Object ob=itr.next();
				if(((TreeObject)obj).getName().equals(((Project)ob).getName()))
				{
					((Project)ob).addUIset(name); //to add the newly created databaseStore to corresponding project
					((TreeObject)obj).addElement1(((Project)ob).getUISet(name));
					showMessage("UISet Successfully Created as "+name+"\n"+((Project)ob).getUISet(name));
					return;
				}
			}
		}
		else if(type==5) // 5 for UIView
		{
			TreeObject a;
			a=((TreeParent)obj).getParent();
			ArrayList al=((TreeObject)a).getElements1();
			Iterator itr=al.iterator();
			for(;itr.hasNext();) {
				Object ob=itr.next();
				if(((UISet)ob).getName().equals(((TreeObject)obj).getName())) {
					((UISet)ob).addUIView(name);
					((TreeObject)obj).addElement(((UISet)ob).getUIView(name));
					showMessage("UIView Successfully Created as "+name+"\n"+((UISet)ob).getUIView(name));
					return;
				}
			}				
		}
		else if(type==6) // 6 for Control
		{
			TreeObject a;
			a=((TreeParent)obj).getParent();
			ArrayList al=((TreeObject)a).getElements();
			Iterator itr=al.iterator();
			for(;itr.hasNext();) {
				Object ob=itr.next();
				if(((UIView)ob).getName().equals(((TreeObject)obj).getName())) {
					((UIView)ob).addControl(name);
					((TreeObject)obj).addElement(((UIView)ob).getControl(name));
					((UIView)ob).getControl(name).setLabel(label);
					showMessage("Control Successfully Created as "+name+"\n"+((UIView)ob).getControl(name)+
							    "\nwith label "+((UIView)ob).getControl(name).getLabel());
					return;
				}
			}
		}
	}		
	
	private void showMessage(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
		
	}

	//The Getter Methods of TreeObject-----------------------------------------------------------------------
	public int getType() {
		return type;
	}		
	
	public ArrayList getElements() {
		return elements;
	}
	
	public String getName() {
		return name;
	}

	public TreeParent getParent() {
		return parent;
	}		

	public ArrayList getElements1() {
		return elements1;
	}
	
	public Object getAdapter(Class key) {
		return null;
	}
	public boolean isNameAlreadyExists(String name,int type1)
	{
		Iterator itr=elements.iterator();
		for(;itr.hasNext();)
		{
			Object obj=itr.next();
			switch(type1)
			{				
			case 1: if(((Project)obj).getName().equals(name))
						return false;
					break;
			case 2: if(((DataBaseStore)obj).getName().equals(name))
						return false;
					break;
			case 3: if(((DataBaseTables)obj).getName().equals(name))
						return false;
			        break;
			case 5: if(((UIView)obj).getName().equals(name))
						return false;
					break;
			case 6: if(((Control)obj).getName().equals(name))
						return false;
					break;
			}
		}
		return true;
	}
	public boolean isNameAlreadyExistsforUISet(String name)
	{
		Iterator itr=elements1.iterator();
		for(;itr.hasNext();)
		{
			Object obj=itr.next();
			if(((UISet)obj).getName().equals(name))
				return false;
		}
		return true;
	}
	//The Remove methods of TreeObject-----------------------------------------------------------------------
	public void removeElement(String name1,int type1){
		Iterator ir=elements.iterator();
		if(type1==1)
		{
			for(;ir.hasNext();)
			{
				Object ob=ir.next();
				if(((Project)ob).getName().equals(name1))
				{
					elements.remove(ob); showMessage("Removed Object is "+ob + name1);
					return;
				}
			}
		}
		else if(type1==2)
		{
			for(;ir.hasNext();)
			{
				Object ob=ir.next();
				if(((DataBaseStore)ob).getName().equals(name1))
				{
					elements.remove(ob); showMessage("Removed Object is "+ob + name1);
					return;
				}
			}
			/*Object obc=this.getParent();
			ArrayList l=((TreeObject)obc).getElements();
			Iterator ii=l.iterator();
			for(;ii.hasNext();)
			{
				Object op=ii.next();
				if(((DataBaseStore)op).getName().equals(this.name))
				{
					((Project)op).removeDataBaseStore(name1);
				}
			}*/						
		}
		else if(type1==3)
		{
			for(;ir.hasNext();)
			{
				Object ob=ir.next();
				if(((DataBaseTables)ob).getName().equals(name1))
				{
					elements.remove(ob); showMessage("Removed Object is "+ob + name1);
					return;
				}
			}
		}
		else if(type1==5)
		{
			for(;ir.hasNext();)
			{
				Object ob=ir.next();
				if(((UIView)ob).getName().equals(name1))
				{
					elements.remove(ob); showMessage("Removed Object is "+ob + name1);
					return;
				}
			
			}
		}
		else if(type1==6)
		{
			for(;ir.hasNext();)
			{
				Object ob=ir.next();
				if(((Control)ob).getName().equals(name1))
				{
					elements.remove(ob); showMessage("Removed Object is "+ob + name1);
					return;
				}
			}
		}
	}		
	public void removeElement1(String name1,int type1){
		Iterator ir=elements1.iterator();
		if(type1==4)
		{
			for(;ir.hasNext();)
			{
				Object ob=ir.next();
				if(((UISet)ob).getName().equals(name1))
				{
					elements1.remove(ob);  showMessage("Removed Object is "+ob + name1);
					return;
				}
			}
		}			
	}	

	public String toString() {
		return getName();
	}
}



