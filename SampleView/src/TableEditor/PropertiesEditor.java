package TableEditor;
import java.util.Arrays;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import TableEditor.TableViewerExample.ExampleContentProvider;

public class PropertiesEditor {
	
//	private Shell shell;
	private int type; // specifies the type whether 1 for project or 2 for databasestore and so on
	private Object obj;
	private Table table;
	private TableViewer tableViewer;
	private TableColumn column;
	private Button closeButton;
	private String columnNames[];
	private final String NAME_COLUMN 		= "name";
	private final String DESCRIPTION_COLUMN 	= "description";
	private final String LABEL_COLUMN 			= "label";
	private final String FIELD_COLUMN 		= "field";
	private final String HASPKEY_COLUMN 		= "haspkey";
	private final String KEY_COLUMN 			= "key";
	private PropertiesData propertiesData;
	public PropertiesEditor(Composite parent,int type,Object obj)
	{
		this.type=type;
		this.obj=obj;
		this.addChildControls(parent);
		if(type==2) // for database store
		{
			columnNames=new String[3];
			columnNames[0]=NAME_COLUMN;
			columnNames[1]=DESCRIPTION_COLUMN;
			columnNames[2]=KEY_COLUMN;
		}
		else if(type==3) // for data base table
		{
			columnNames=new String[5];
			columnNames[0]=NAME_COLUMN;
			columnNames[1]=DESCRIPTION_COLUMN;
			columnNames[2]=KEY_COLUMN;
			columnNames[3]=FIELD_COLUMN;
			columnNames[4]=HASPKEY_COLUMN;
		}
		else if(type==4) // for Control
		{
			columnNames=new String[2];
			columnNames[0]=NAME_COLUMN;
			columnNames[1]=LABEL_COLUMN;	
		}
		
	}
	/**
	 * Run and wait for a close event
	 * @param shell Instance of Shell
	 */
	public void run(Shell shell) {
		
		// Add a listener for the close button
		closeButton.addSelectionListener(new SelectionAdapter() {
       	
			// Close the view i.e. dispose of the composite's parent
			public void widgetSelected(SelectionEvent e) {
				table.getParent().getParent().dispose();
			}
		});
		
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	/**
	 * Release resources
	 */
	public void dispose() {
		
		// Tell the label provider to release its ressources
		tableViewer.getLabelProvider().dispose();
	}
	/**
	 * Create a new shell, add the widgets, open the shell
	 * @return the shell that was created	 
	 */
	private void addChildControls(Composite composite) {
		
//		 Create a composite to hold the children
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
		composite.setLayoutData (gridData);

		// Set numColumns to 3 for the buttons 
		GridLayout layout = new GridLayout(3, false);
		layout.marginWidth = 5;
		composite.setLayout (layout);
		
//		 Create the table 
		createTable(composite);
		
		// Create and setup the TableViewer
		createTableViewer();
		
		tableViewer.setContentProvider(new PropertiesContentProvider(type));//new ExampleContentProvider());
		tableViewer.setLabelProvider(new PropertiesLabelProvider(type));//new ExampleLabelProvider());
		// The input for the table viewer is the instance of ExampleTaskList
		propertiesData=new PropertiesData(type);
		tableViewer.setInput(propertiesData);

		// Add the buttons
		createButtons(composite);

	}
	/**
	 * Create the Table
	 */
	private void createTable(Composite parent) {
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | 
					SWT.FULL_SELECTION | SWT.HIDE_SELECTION;
		table = new Table(parent, style);
		
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);		
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

//		 1st column with image/checkboxes - NOTE: The SWT.CENTER has no effect!!
		column = new TableColumn(table, SWT.CENTER, 0);

		column.setText("Name");
		column.setWidth(100);
		if(type==6)
		{
// 			2nd column with task Description
			column = new TableColumn(table, SWT.LEFT, 1);
			column.setText("Label");
			column.setWidth(100);
			// 	Add listener to column so tasks are sorted by description when clicked 
			column.addSelectionListener(new SelectionAdapter() {       	
				public void widgetSelected(SelectionEvent e) {
					//tableViewer.setSorter(new ExampleTaskSorter(ExampleTaskSorter.DESCRIPTION));
				}
			});	
		}
		if(type==2 || type==3)
		{
			
// 			2nd column with task Description
			column = new TableColumn(table, SWT.LEFT, 1);
			column.setText("Description");
			column.setWidth(200);
			// 	Add listener to column so tasks are sorted by description when clicked 
			column.addSelectionListener(new SelectionAdapter() {       	
				public void widgetSelected(SelectionEvent e) {
					//tableViewer.setSorter(new ExampleTaskSorter(ExampleTaskSorter.DESCRIPTION));
				}
			});
		
// 			3rd column with task Owner
			column = new TableColumn(table, SWT.LEFT, 2);
			column.setText("Key");
			column.setWidth(100);
			// Add listener to column so tasks are sorted by owner when clicked
			column.addSelectionListener(new SelectionAdapter() {
				
				public void widgetSelected(SelectionEvent e) {
					//tableViewer.setSorter(new ExampleTaskSorter(ExampleTaskSorter.OWNER));
				}
			});
		}
		
		if(type==3)
		{
//			 4th column with field property 
			column = new TableColumn(table, SWT.CENTER, 3);
			column.setText("Field");
			column.setWidth(100);
			//  Add listener to column so tasks are sorted by percent when clicked
			column.addSelectionListener(new SelectionAdapter() {
       	
				public void widgetSelected(SelectionEvent e) {
					tableViewer.setSorter(new ExampleTaskSorter(ExampleTaskSorter.PERCENT_COMPLETE));
				}
			});
		
// 			5th column with task PercentComplete 
			column = new TableColumn(table, SWT.CENTER, 4);
			column.setText("Has Primary Key");
			column.setWidth(50);
			//  Add listener to column so tasks are sorted by percent when clicked
			column.addSelectionListener(new SelectionAdapter() {
       	
				public void widgetSelected(SelectionEvent e) {
					//tableViewer.setSorter(new ExampleTaskSorter(ExampleTaskSorter.PERCENT_COMPLETE));
				}
			});
		}		
	}
	/**
	 * Create the TableViewer 
	 */
	private void createTableViewer() {
		tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);
		tableViewer.setColumnProperties(columnNames);

		// Create the cell editors
		CellEditor[] editors = new CellEditor[columnNames.length];

		// Column 1 : Name (Textbox)
		TextCellEditor textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(40);
		editors[0] = textEditor;

		// Column 2 : Description (Free text) or Label (Free Text)
		textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(60);
		editors[1] = textEditor;

		if(type==2||type==3)// for dbs and dbt
		{
			// Column 3 : Key (Free Text) 
			textEditor = new TextCellEditor(table);
			((Text) textEditor.getControl()).setTextLimit(60);
			editors[2] = textEditor;
			if(type==3)
			{
				// 	Column 4 : Field (Text with digits only)	
				textEditor = new TextCellEditor(table);
				((Text) textEditor.getControl()).setTextLimit(60);
				editors[3] = textEditor;
				// column 5 : HASPKEY
				editors[4] = new ComboBoxCellEditor(table,propertiesData.getOwners(), SWT.READ_ONLY);
			}
		}
		// Assign the cell editors to the viewer 
		tableViewer.setCellEditors(editors);
		// Set the cell modifier for the viewer
		tableViewer.setCellModifier(new PropertiesCellModifier(this));
	
	}
	/*
	 * Close the window and dispose of resources
	 */
	public void close() {
		Shell shell = table.getShell();

		if (shell != null && !shell.isDisposed())
			shell.dispose();
	}
	/**
	 * Add the "Save", "Cancel" and "Close" buttons
	 * @param parent the parent composite
	 */
	private void createButtons(Composite parent) {
		
		// Create and configure the "Add" button
		Button save = new Button(parent, SWT.PUSH | SWT.CENTER);
		save.setText("Save");
		
		GridData gridData = new GridData (GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.widthHint = 80;
		save.setLayoutData(gridData);
		save.addSelectionListener(new SelectionAdapter() {
       		// Add a task to the ExampleTaskList and refresh the view
			public void widgetSelected(SelectionEvent e) {
				//taskList.addTask();
			}
		});
		
		//	Create and configure the "Delete" button
		Button cancel = new Button(parent, SWT.PUSH | SWT.CENTER);
		cancel.setText("Cancel");
		gridData = new GridData (GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.widthHint = 80; 
		cancel.setLayoutData(gridData); 

		cancel.addSelectionListener(new SelectionAdapter() {
       	
			//	Remove the selection and refresh the view
			public void widgetSelected(SelectionEvent e) {
				ExampleTask task = (ExampleTask) ((IStructuredSelection) 
						tableViewer.getSelection()).getFirstElement();
				if (task != null) {
					//taskList.removeTask(task);
				} 				
			}
		});
		
		//	Create and configure the "Close" button
		closeButton = new Button(parent, SWT.PUSH | SWT.CENTER);
		closeButton.setText("Close");
		gridData = new GridData (GridData.HORIZONTAL_ALIGN_END);
		gridData.widthHint = 80; 
		closeButton.setLayoutData(gridData); 		
	}

	/**
	 * Return the column names in a collection
	 * 
	 * @return List  containing column names
	 */
	public java.util.List getColumnNames() {
		return Arrays.asList(columnNames);
	}

	/**
	 * @return currently selected item
	 */
	public ISelection getSelection() {
		return tableViewer.getSelection();
	}

	/**
	 * Return the ExampleTaskList
	 */
	public ExampleTaskList getTaskList() {
		return taskList;	
	}

	/**
	 * Return the parent composite
	 */
	public Control getControl() {
		return table.getParent();
	}

	/**
	 * Return the 'close' Button
	 */
	public Button getCloseButton() {
		return closeButton;
	}
}
