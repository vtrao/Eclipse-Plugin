package sampleView;

import java.util.ArrayList;

public class TreeParent extends TreeObject {
//	The member Variable of TreeParent---------------------------------------------------------------------
	private ArrayList children;
	
	
	//The Constructor of TreeParent-------------------------------------------------------------------------
	public TreeParent(String name) {
		super(name);
		children = new ArrayList();
	}
	
	
	//The Setter methods of TreeParent----------------------------------------------------------------------
	public void addChild(TreeObject child) {
		children.add(child);
		child.setParent(this);
	}

	
	//The Getter Methods of TreeParent----------------------------------------------------------------------
	public TreeObject [] getChildren() {
		return (TreeObject [])children.toArray(new TreeObject[children.size()]);
	}
	
	
	//The Remove Methods of TreeParent----------------------------------------------------------------------
	public void removeChild(TreeObject child) {
		children.remove(child);
		child.setParent(null);
	}
	
	
	//The Boolean methods of TreeParent---------------------------------------------------------------------
	public boolean hasChildren() {
		return children.size()>0;
	}

}
