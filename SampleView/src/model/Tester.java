 package model;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Tester {
	public static void DDBS(Project p)
	{
		System.out.println("The details of Data Base Store are");
		ArrayList a=new ArrayList();
		a=p.getDataBaseStore();
		Iterator it=a.iterator();
		while(it.hasNext())
		{
			Object o=it.next();
			System.out.println("\tThe Details of "+((DataBaseStore)o).getName()+" Data Base Store \n\tThe handle is "+ o.toString());
			System.out.println("\tKey : "+((DataBaseStore)o).getKey()+"\n\tDescription : "+((DataBaseStore)o).getDescription());
			ArrayList ar=new ArrayList();
			ar=((DataBaseStore)o).getDataBaseTable();
			Iterator itr=ar.iterator();
			while(itr.hasNext())
			{
				Object ob=itr.next();
				System.out.println("\t\tThe Details of "+((DataBaseTables)ob).getName()+" Data Base Table \n\t\tThe handle is "+ob.toString());
				System.out.println("\t\tKey : "+((DataBaseTables)ob).getKey());
				System.out.println("\t\tField : "+((DataBaseTables)ob).getField());
				System.out.println("\t\tDescription : "+((DataBaseTables)ob).getDescription());
				System.out.println("\t\tHas PKey : "+((DataBaseTables)ob).getHasPKey());
			}
		}
	}
	public static void DUIS(Project p)
	{
		System.out.println("The details of UISet are");
		ArrayList a1=new ArrayList();
		a1=p.getUISet();
		Iterator it1=a1.iterator();
		while(it1.hasNext())
		{
			Object o1=it1.next();
			System.out.println("\tThe Details of "+((UISet)o1).getName()+" UISet \n\tThe handle is "+ o1.toString());
			ArrayList ar1=new ArrayList();
			ar1=((UISet)o1).getUIView();
			Iterator itr1=ar1.iterator();
			while(itr1.hasNext())
			{
				Object ob1=itr1.next();
				System.out.println("\t\tThe Details of "+((UIView)ob1).getName()+" UIView \n\t\tThe handle is "+ob1.toString());
				ArrayList ar2=new ArrayList();
				ar2=((UIView)ob1).getControl();
				Iterator itr2=ar2.iterator();
				while(itr2.hasNext())
				{
					Object ob2=itr2.next();
					System.out.println("\t\t\tThe Details of "+((Control)ob2).getName()+" Control \n\t\t\the handle is "+ob2.toString());
					System.out.println("\t\t\tLabel "+((Control)ob2).getLabel());
				}
			}
		}
	}
	public static void main(String[] args)throws Exception {
		ArrayList pr=new ArrayList();
		Project pt=new Project();
		int choice=0;
		while(choice!=3)
		{
			System.out.println("Enter 1 to Create a Project");
			System.out.println("Enter 2 to view the details of a Project");
			System.out.println("Enter 3 to Exit\n");
			BufferedReader get=new BufferedReader(new InputStreamReader(System.in));
			choice=Integer.parseInt(get.readLine());
			switch(choice)
			{
			case 1: {
						System.out.println("\tEnter the name of the Project\n\t");
						Project p=new Project(get.readLine());
						pr.add(p);
						int ch=0;
						while(ch!=7)
						{
							System.out.println("\tEnter 1 for Adding a DataBaseStore");
							System.out.println("\tEnter 2 for Adding a UISet");
							System.out.println("\tEnter 3 for deleting a DataBaseStore");
							System.out.println("\tEnter 4 for deleting a UISet");
							System.out.println("\tEnter 5 for viewing the details of Data Base Store");
							System.out.println("\tEnter 6 for viewing the details of UISet");
							System.out.println("\tEnter 7 to get out of "+ p.getName()+" Project\n\t");
							ch=Integer.parseInt(get.readLine());
							switch(ch)
							{
							case 1:	{
										System.out.println("\t\tEnter the Name of DataBaseStore\n\t\t");
										String dbsname=get.readLine();
										p.addDataBaseStore(dbsname);
										DataBaseStore db=p.getDataBaseStore(dbsname);
										System.out.println("\t\tEnter the the Key of "+db.getName()+" Data Base Store\n\t\t");
										db.setKey(get.readLine());
										System.out.println("\t\tEnter the the Description of "+db.getName()+" Data Base Store\n\t\t");
										db.setDescription(get.readLine());
										int ch1=0;
										while(ch1!=3)
										{
											System.out.println("\t\t\tEnter 1 for Adding a DataBaseTable");
											System.out.println("\t\t\tEnter 2 for Deleting a DataBaseTable");
											System.out.println("\t\t\tEnter 3 to get out of "+db.getName()+" DataBaseStore \n\t\t\t");
											ch1=Integer.parseInt(get.readLine());
											switch(ch1)
											{
											case 1: System.out.println("\t\t\tEnter the Name of DataBaseTable\n\t\t\t");
													String dbtname=get.readLine();
													db.addDataBaseTable(dbtname);
													DataBaseTables dbt=db.getDataBaseTable(dbtname);
													System.out.println("\t\t\tEnter the Descrition of "+dbt.getName() +" DataBase Table\n\t\t\t");
													dbt.setDescription(get.readLine());
													System.out.println("\t\t\tEnter the Key of "+dbt.getName()+" DataBase Table\n\t\t\t");
													dbt.setKey(get.readLine());
													System.out.println("\t\t\tEnter the Field of "+dbt.getName()+" DataBase Table\n\t\t\t");
													dbt.setField(get.readLine());
													System.out.println("\t\t\tEnter whether PKey exists or not either true or false for "+dbt.getName()+" DataBase Table\n\t\t\t");
													dbt.setHasPKey(Boolean.parseBoolean(get.readLine()));
													break;
													
											case 2: System.out.println("\t\t\tEnter the Name of DataBaseTable to be removed\n\t\t\t");
													if(db.removeDataBaseTable(get.readLine()))
														System.out.println("\t\t\tSuccessfully removed the table");
													else
														System.out.println("\t\t\tTable with the specified name doesnot exist");
													break;
											case 3: continue;
											default: System.out.println("\t\t\tInvalid Input! Enter the Correct Choice");
											}
										}										
									}
									break;
									
							case 2: {
										System.out.println("\t\tEnter the Name of UISet\n\t\t");
										String uisetname=get.readLine();
										p.addUIset(uisetname);
										UISet ui=(UISet)p.getUISet(uisetname);
										int ch2=0;
										while(ch2!=3)
										{
											System.out.println("\t\t\tEnter 1 for Adding a UIView");
											System.out.println("\t\t\tEnter 2 for Deleting a UIView");
											System.out.println("\t\t\tEnter 3 to get out of "+ui.getName() +"UISet\n\t\t\t ");
											ch2=Integer.parseInt(get.readLine());
											switch(ch2)
											{
											case 1: System.out.println("\t\t\tEnter the name of the UIView\n\t\t\t");
													String uiviewname=get.readLine();
													ui.addUIView(uiviewname);
													UIView uv=ui.getUIView(uiviewname);
													int ch3=0;
													while(ch3!=3)
													{
														System.out.println("\t\t\t\tEnter 1 for Adding a Control");
														System.out.println("\t\t\t\tEnter 2 for Deleting a Control");
														System.out.println("\t\t\t\tEnter 3 to get out of "+uv.getName() + " UIView\n\t\t\t\t");
														ch3=Integer.parseInt(get.readLine());
														switch(ch3)
														{
														case 1: System.out.println("\t\t\t\tEnter the name of the Control\n\t\t\t\t");
																String controlname=get.readLine(); 
																uv.addControl(controlname);
																Control ct=uv.getControl(controlname);
																System.out.println("\t\t\t\tEnter the label of "+ct.getName()+" Control\n\t\t\t\t");
																ct.setLabel(get.readLine());
																break;
														case 2: System.out.println("\t\t\t\tEnter the name of the Control to be removed\n\t\t\t\t");
																if(uv.removeControl(get.readLine()))
																	System.out.println("\t\t\t\tSuccessfully removed the Control");
																else
																	System.out.println("\t\t\t\tControl with the specified name doesnot exist");
															    break;
														case 3: continue;
														}														
													}
													break;
													
											case 2: System.out.println("\t\t\tEnter the name of the UIView to be removed\n\t\t\t");
													if(ui.removeUIView(get.readLine()))
														System.out.println("\t\t\tSuccessfully removed the UIView");
													else
														System.out.println("\t\t\tUIView with the specified name doesnot exist");												
													break;
											case 3: continue;												 
											}
										}
									}
									break;
									
							case 3:	{								    
								    System.out.println("\t\tEnter the Name of DataBaseStore to be removed\n\t\t");
								    if(p.removeDataBaseStore(get.readLine()))
								    		System.out.println("\t\tSuccessful! The DataBase Store has been Removed");
								    else
								    		System.out.println("\t\tFailed! The DataBase Store with the specified name does not exist");																	
									}
									break;
									
							case 4: {
									System.out.println("\t\tEnter the Name of UISet to be removed\n\t\t");
									if(p.removeUISet(get.readLine()))
										System.out.println("\t\tSuccessful! The UISet has been Removed");
									else
							    		System.out.println("\t\tFailed! The UISet with the specified name does not exist");								
									}
									break;
									
							case 5:	DDBS(p);
									break;
							case 6: DUIS(p);
									break;
							case 7: continue;	
							default: System.out.println("\t\tInvalid Input! Enter the Correct Choice");
									 break;
							}
						}
					}
					break;
			case 2: System.out.println("\tEnter the Name of Project to View its Details\n\t");
					String pname=get.readLine();
					int flag=0;
					Iterator itr=pr.iterator();
					while(itr.hasNext())
					{
						Object ob=itr.next();
						if(((Project)ob).getName().equals(pname))
						{
							pt=(Project)ob;flag=1;
						}
					}					
					if(flag==0)
						System.out.println("\tSpecified Project Doesnot exist");
					else
					{
						System.out.println("\t Succesful! The "+pt.getName()+" Project exists");
						DDBS(pt);
						DUIS(pt);
					}
					break;
			case 3: continue;
					
			default: System.out.println("\tInvalid Input! Enter the Correct Choice");
			 		 break;
			}
		}
		System.out.println("Thank U");
	}
}