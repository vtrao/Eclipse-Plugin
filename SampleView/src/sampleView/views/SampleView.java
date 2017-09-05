package sampleView.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;

import Wizards.NewProjectWizard;
import model.*;
import TableEditor.*;
import sampleView.*;


/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class SampleView extends ViewPart {

	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action doubleClickAction;
	TreeParent p1 = new TreeParent("Parent 1");
	TreeObject oo=new TreeObject("ss");
	private TreeParent invisibleRoot;
	
	private Action addProject;
	private Action removeProject;
	private Action addDataBaseStore; 
	private Action removeDataBaseStore; 
	private Action addDataBaseTable; 
	private Action removeDataBaseTable; 
	private Action addUISet; 
	private Action removeUISet; 
	private Action addUIView; 
	private Action removeUIView; 
	private Action addControl; 
	private Action removeControl; 
	Composite parent1;

	/*
	 * The content provider class is responsible for
	 * providing objects to the view. It can wrap
	 * existing objects in adapters or simply return
	 * objects as-is. These objects may be sensitive
	 * to the current input of the view, or ignore
	 * it and always show the same content 
	 * (like Task List, for example).
	 */
	 
	class ViewContentProvider implements IStructuredContentProvider, 
										   ITreeContentProvider {
		

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {		
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {//1st showMessage("In get Elements");
			if (parent.equals(getViewSite())) {				
				if (invisibleRoot==null) initialize();
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}
		public Object getParent(Object child) {//showMessage("In get Parent");
			if (child instanceof TreeObject) {
				return ((TreeObject)child).getParent();
			}
			return null;
		}
		public Object [] getChildren(Object parent) {//3rd showMessage("In getChildren");
			if (parent instanceof TreeParent) {
				return ((TreeParent)parent).getChildren();
			}
			return new Object[0];
		}
		public boolean hasChildren(Object parent) {//4th showMessage("In has Children");
			if (parent instanceof TreeParent) 
				return ((TreeParent)parent).hasChildren();
			return false;
		}
/*
 * We will set up a dummy model to initialize tree heararchy.
 * In a real code, you will connect to a real model and
 * expose its hierarchy.
 */
	private void initialize() {
			invisibleRoot = new TreeParent("");	
		}
	}
	class ViewLabelProvider extends LabelProvider {
             
		public String getText(Object obj) {
			return obj.toString();
		}
		public Image getImage(Object obj) {
			String imageKey = new String();//ISharedImages..IMG_OBJ_FILE;//.IMG_OBJ_ELEMENT;
			if (((TreeObject)obj).type==1) // Project
			   imageKey = ISharedImages.IMG_OBJ_PROJECT;
			else if (((TreeObject)obj).type==2) // Data Base Store
				   imageKey = ISharedImages.IMG_TOOL_NEW_WIZARD;//.IMG_TOOL_NEW_WIZARD;//.IMG_OBJ_PROJECT;
			else if (((TreeObject)obj).type==3) // Data Base Tables
				   imageKey = ISharedImages.IMG_DEF_VIEW;//.IMG_OBJ_PROJECT;
			else if (((TreeObject)obj).type==4) // UISet
				   imageKey = ISharedImages.IMG_OPEN_MARKER;//.IMG_TOOL_NEW_WIZARD;//IMG_DEF_VIEW;//.IMG_OBJ_PROJECT;
			else if (((TreeObject)obj).type==5) // UIView
				   imageKey = ISharedImages.IMG_OBJ_FILE;//.IMG_OPEN_MARKER;//.IMG_OBJ_PROJECT;
			else if (((TreeObject)obj).type==6) // Control
				   imageKey = ISharedImages.IMG_OBJ_ELEMENT;//.IMG_OBJ_PROJECT;			
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}
	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public SampleView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {// this is like the main
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars(); 
		this.parent1=parent;
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SampleView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(addProject);
	}

	private void fillContextMenu(IMenuManager manager) {
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		if(viewer.getSelection().isEmpty())
		{
			manager.add(addProject);
			manager.add(new Separator());
		    drillDownAdapter.addNavigationActions(manager);
		}
		else if(((TreeObject)obj).getType()==1)
		{
			manager.add(addDataBaseStore);
			manager.add(addUISet);
			manager.add(new Separator());
			manager.add(removeProject);
			manager.add(new Separator());
		    drillDownAdapter.addNavigationActions(manager);
			
		}
		else if(((TreeObject)obj).getType()==2)
		{
			manager.add(addDataBaseTable);
			manager.add(new Separator());
			manager.add(removeDataBaseStore);
			manager.add(new Separator());
		    drillDownAdapter.addNavigationActions(manager);
		}
		else if(((TreeObject)obj).getType()==3)
		{
			manager.add(removeDataBaseTable);
			manager.add(new Separator());
		    drillDownAdapter.addNavigationActions(manager);
		}
		else if(((TreeObject)obj).getType()==4)
		{
			manager.add(addUIView);
			manager.add(new Separator());
			manager.add(removeUISet);
			manager.add(new Separator());
		    drillDownAdapter.addNavigationActions(manager);
		}
		else if(((TreeObject)obj).getType()==5)
		{
			manager.add(addControl);
			manager.add(new Separator());
			manager.add(removeUIView);
			manager.add(new Separator());
		    drillDownAdapter.addNavigationActions(manager);
		}
		else if(((TreeObject)obj).getType()==6)
		{
			manager.add(removeControl);
			manager.add(new Separator());
		    drillDownAdapter.addNavigationActions(manager);
			//add correspondong context menus
		}				
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(addProject);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {		
		addProject=new Action(){
			public void run(){
				NewProjectWizard wizard=new NewProjectWizard(1);//,null);
				Shell sh=new Shell(SWT.CENTER);
				WizardDialog wg=new WizardDialog(sh, wizard);
				wg.create();
				wg.open();				
				if(wizard.entityName!=null) {
					if(invisibleRoot.isNameAlreadyExists(wizard.entityName,1)) {
						TreeParent p=new TreeParent(wizard.entityName);
						p.setType(1,invisibleRoot);
						p.setParent(invisibleRoot);
						invisibleRoot.addChild(p);
						viewer.refresh();
					}
					else {
						showMessage("Project with the specified name already exists");
					}
				}
			}
		};
		addProject.setText("Add Project");		
		
		addDataBaseStore=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				NewProjectWizard wizard=new NewProjectWizard(2);//,null);
				Shell sh=new Shell(SWT.CENTER);
				WizardDialog wg=new WizardDialog(sh, wizard);
				wg.create();
				wg.open();	
				if(wizard.entityName!=null)	{
					if(((TreeObject)obj).isNameAlreadyExists(wizard.entityName,2)) {
						TreeParent p=new TreeParent(wizard.entityName);
						p.setDescription(wizard.entityDescription);
						p.setKey(wizard.entityKey);
						p.setParent(((TreeParent)obj));
						p.setType(2,obj);				
						((TreeParent)obj).addChild(p);
						viewer.refresh();
						viewer.expandToLevel(2);
					}
					else {
						showMessage("DataBaseStore with the specified name already exists");
					}
				}
			}
		};
		addDataBaseStore.setText("Add Data Base Store");

		
		
		addDataBaseTable=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				NewProjectWizard wizard=new NewProjectWizard(3);//,null);
				Shell sh=new Shell(SWT.CENTER);
				WizardDialog wg=new WizardDialog(sh, wizard);
				wg.create();
				wg.open();
				if(wizard.entityName!=null)
				{
					if(((TreeObject)obj).isNameAlreadyExists(wizard.entityName,3)) {
						TreeParent p=new TreeParent(wizard.entityName);
						p.setDescription(wizard.entityDescription);
						p.setField(wizard.entityField);
						p.setHasPKey(wizard.hasPKey);
						p.setKey(wizard.entityKey);
						p.setParent(((TreeParent)obj));
						p.setType(3,obj);
						((TreeParent)obj).addChild(p);
						viewer.refresh();
						viewer.expandToLevel(3);
					}
					else {
						showMessage("DataBaseTable with the specified name already exists");
					}
				}
			}
		};
		addDataBaseTable.setText("Add Data BaseTable");

		
		addUISet=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				NewProjectWizard wizard=new NewProjectWizard(4);//,null);
				Shell sh=new Shell(SWT.CENTER);
				WizardDialog wg=new WizardDialog(sh, wizard);
				wg.create();
				wg.open();
				if(wizard.entityName!=null)
				{
					if(((TreeObject)obj).isNameAlreadyExistsforUISet(wizard.entityName)) {
						showMessage("venki1");
						TreeParent p=new TreeParent(wizard.entityName);
						p.setParent(((TreeParent)obj));
						p.setType(4,obj);
						((TreeParent)obj).addChild(p);
						viewer.refresh();
						viewer.expandToLevel(2);
					}
					else {
						showMessage("UISet with the specified name already exists");
					}
				}
			}
		};
		addUISet.setText("Add UISet");

		
		addUIView=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				NewProjectWizard wizard=new NewProjectWizard(5);//,null);
				Shell sh=new Shell(SWT.CENTER);
				WizardDialog wg=new WizardDialog(sh, wizard);
				wg.create();
				wg.open();
				if(wizard.entityName!=null)
				{
					if(((TreeObject)obj).isNameAlreadyExists(wizard.entityName,5)) {
						TreeParent p=new TreeParent(wizard.entityName);
						p.setParent(((TreeParent)obj));
						p.setType(5,obj);
						((TreeParent)obj).addChild(p);
						viewer.refresh();
						viewer.expandToLevel(3);
					}
					else {
						showMessage("UIView with the specified name already exists");
					}
				}
			}
		};
		addUIView.setText("Add UIView");
		
		addControl=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				NewProjectWizard wizard=new NewProjectWizard(6);//,null);
				Shell sh=new Shell(SWT.CENTER);
				WizardDialog wg=new WizardDialog(sh, wizard);
				wg.create();
				wg.open();
				if(wizard.entityName!=null)
				{
					if(((TreeObject)obj).isNameAlreadyExists(wizard.entityName,6)) {
						TreeParent p=new TreeParent(wizard.entityName);
						p.setLabel(wizard.entityLabel);
						p.setParent(((TreeParent)obj));
						p.setType(6,obj);
						((TreeParent)obj).addChild(p);
						viewer.refresh();
						viewer.expandToLevel(4);
					}
					else {
						showMessage("UIView with the specified name already exists");
					}
				}
			}
		};
		addControl.setText("Add Control");

		
		
		removeProject=new Action() {
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				Object ar[]=invisibleRoot.getChildren();
				for(int i=0;ar.length!=0;i++)
				{
					if(((TreeObject)obj).getName().equals(((TreeObject)ar[i]).getName())) {
							invisibleRoot.removeElement(((TreeObject)obj).getName(),1);
							invisibleRoot.removeChild((TreeObject)ar[i]);//showMessage(" The Project in run Removed is Name "+ ar[i]);
							viewer.refresh();
							return;
					}					
				}
			}
		};
		removeProject.setText("Remove Project");

		
		
		removeDataBaseStore=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				TreeParent parent=((TreeObject)obj).getParent();
				Object ar[]=parent.getChildren();
				for(int i=0;ar.length!=0;i++)
				{
					if(((TreeObject)obj).getName().equals(((TreeObject)ar[i]).getName()))
					{
						parent.removeElement(((TreeObject)ar[i]).getName(),2);
						parent.removeChild((TreeObject)ar[i]);//showMessage(" The DataBaseStore Removed is Name "+ ar[i]);
						viewer.refresh();
						return;
					}
				}				
			}
		};
		removeDataBaseStore.setText("Remove Data base Store");

		
		
		removeDataBaseTable=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				TreeParent parent=((TreeObject)obj).getParent();
				Object ar[]=parent.getChildren();
				for(int i=0;ar.length!=0;i++)
				{
					if(((TreeObject)obj).getName().equals(((TreeObject)ar[i]).getName()))
					{
						parent.removeElement(((TreeObject)ar[i]).getName(),3);
						parent.removeChild((TreeObject)ar[i]);//showMessage(" The DataBaseStore Removed is Name "+ ar[i]);
						viewer.refresh();
						return;
					}
				}
			}
		};
		removeDataBaseTable.setText("Remove Data Base Table");

		
		
		removeUISet=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				TreeParent parent=((TreeObject)obj).getParent();
				Object ar[]=parent.getChildren();
				for(int i=0;ar.length!=0;i++)
				{
					if(((TreeObject)obj).getName().equals(((TreeObject)ar[i]).getName()))
					{
						parent.removeElement1(((TreeObject)ar[i]).getName(),4);
						parent.removeChild((TreeObject)ar[i]);//showMessage(" The DataBaseStore Removed is Name "+ ar[i]);
						viewer.refresh();
						return;
					}
				}
			}
		};
		removeUISet.setText("Remove UISet");


		
		
		removeUIView=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				TreeParent parent=((TreeObject)obj).getParent();
				Object ar[]=parent.getChildren();
				for(int i=0;ar.length!=0;i++)
				{
					if(((TreeObject)obj).getName().equals(((TreeObject)ar[i]).getName()))
					{
						parent.removeElement(((TreeObject)ar[i]).getName(),5);
						parent.removeChild((TreeObject)ar[i]);//showMessage(" The DataBaseStore Removed is Name "+ ar[i]);
						viewer.refresh();
						return;
					}
				}
			}
		};
		removeUIView.setText("Remove UIView");

		
		
		removeControl=new Action(){
			public void run(){
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				TreeParent parent=((TreeObject)obj).getParent();
				Object ar[]=parent.getChildren();
				for(int i=0;ar.length!=0;i++)
				{
					if(((TreeObject)obj).getName().equals(((TreeObject)ar[i]).getName()))
					{
						parent.removeElement(((TreeObject)ar[i]).getName(),6);
						parent.removeChild((TreeObject)ar[i]);//showMessage(" The DataBaseStore Removed is Name "+ ar[i]);
						viewer.refresh();
						return;
					}
				}
			}
		};
		removeControl.setText("Remove Control");

		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				//showMessage("To Enable the Editor for "+obj.toString());
				if(((TreeObject)obj).getType()==1)
				{
					if(viewer.getExpandedState(obj))
					{
						viewer.collapseToLevel(obj,3);
					}
					else
					{
						viewer.expandToLevel(obj,3);
					}
				}
				else if(((TreeObject)obj).getType()==2)
				{
					getTableEditor(((TreeObject)obj).getType(),obj);	
				}
				else if(((TreeObject)obj).getType()==3)
				{
					getTableEditor(((TreeObject)obj).getType(),obj);
				}
				else if(((TreeObject)obj).getType()==4)
				{
					getTableEditor(((TreeObject)obj).getType(),obj);
				}
				else if(((TreeObject)obj).getType()==5)
				{
					getTableEditor(((TreeObject)obj).getType(),obj);
				}
				else if(((TreeObject)obj).getType()==6)
				{
					getTableEditor(((TreeObject)obj).getType(),obj);
				}
					
				//showMessage("To Enable the Editor for "+obj.toString());
			}
			public void getTableEditor(int type,Object obj)
			{
				final TableViewerExample tableViewerExample;;
				Shell shell = new Shell();
				

				// Set layout for shell
				GridLayout layout = new GridLayout();
				shell.setLayout(layout);
				
				// Create a composite to hold the children
				Composite composite = new Composite(shell, SWT.NONE);
				tableViewerExample = new TableViewerExample(composite,type,obj);
				tableViewerExample.getControl().addDisposeListener(new DisposeListener() {
					public void widgetDisposed(DisposeEvent e) {
						tableViewerExample.dispose();			
					}					
				});
				if(type==2)
					{ 
						shell.setText("Data Base Store Deetails");
					}
				else if(type==3)
					{ 
						shell.setText("Data Base Table Details");
					}
				else if(type==4)
					{ 
						shell.setText("User Interface Set Details");
					}
				else if(type==5)
					{ 
						shell.setText("User Interface View Details");
					}
				else if(type==6)
					{
						shell.setText("Controls details");
					}
				// Ask the shell to display its content
				shell.open();
				tableViewerExample.run(shell);
			}			
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();//define open editor and tree compress and expand feature for double click
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Sample View",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}