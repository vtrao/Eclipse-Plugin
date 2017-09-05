package Wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
 

public class NewProjectWizard extends Wizard implements INewWizard{
	//Wizard Pages------------------------------------------------------------------------------------------------
	private ProjectWizardPage projectPage;
	private AddDataBaseStorePage dBSPage;
	private AddDataBaseTablePage dBTPage;
	private AddUISetPage uISPage;
	private AddUIViewPage uIVPage;
	private AddControlPage cPage;
	
	public String entityName; // entity can be project, dbs, dbt, uis,uiv,control
	public String entityDescription;
	public String entityKey;
	public String entityLabel;
	public String entityField;
	public boolean hasPKey;
	
	//WorkBench Selection when the wizard was started
	protected ISelection selection;
	private int type;
	//private String pName;
	
	public NewProjectWizard(int type)//,String pName)// type =1 for project , 2 for dbs, 3 for dbt, 4 for uis, 5 for uiv,6 for control
	{
		super();
		this.type=type;
		//this.pName=pName;
		setNeedsProgressMonitor(true);
	}
	public void addPages()
	{
		switch(type)
		{
		case 1: projectPage=new ProjectWizardPage(selection);
				addPage(projectPage);
				break;
		case 2: dBSPage=new AddDataBaseStorePage(selection);//,pName);
				addPage(dBSPage);
				break;
		case 3: dBTPage=new AddDataBaseTablePage(selection);//,pName);
				addPage(dBTPage);
				break;
		case 4: uISPage=new AddUISetPage(selection);//,pName);
				addPage(uISPage);
				break;
		case 5: uIVPage=new AddUIViewPage(selection);//,pName);
				addPage(uIVPage);
				break;
		case 6: cPage=new AddControlPage(selection);//,pName);
				addPage(cPage);
				break;
		}
	}
	
	/**
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) 
	{
		this.selection = selection;
	}

	
	
	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard.
	 */
	public boolean performFinish()
		{
		switch(type)
		{
			case 1: entityName=projectPage.getProjectName(); break;	// for Project			
			
			case 2: entityName=dBSPage.getDataBaseStoreName();        // for DataBaseStore
					entityDescription=dBSPage.getDataBaseStoreDescription();
					entityKey=dBSPage.getDataBaseStoreKey();
					break;
			
			case 3: entityName=dBTPage.getDataBaseTableName();       // for DataBaseTable
					entityDescription=dBTPage.getDataBaseTableDescription();
					entityField=dBTPage.getDataBaseTableField();
					entityKey=dBTPage.getDataBaseTableKey();
					hasPKey=Boolean.getBoolean(dBTPage.getDataBaseTableHasPKey());
					break;

			case 4: entityName=uISPage.getUISetName(); break;// for UISet
			
			case 5: entityName=uIVPage.getUIVIewName(); break;// for UIView
			
			case 6: entityName=cPage.getControlName();  // for Control
					entityLabel=cPage.getLabel();
					break;
		}			
		return true;
		
	}
	
}
