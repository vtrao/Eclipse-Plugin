package Wizards;


import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This page queries for the name of the project
 */

public class AddDataBaseTablePage extends WizardPage {
	private Text dataBaseTableText;
	private Text dataBaseTableDescription;
	private Text dataBaseTableKey;
	private Text dataBaseTableField;
	private Combo dataBaseTableHasPKey;
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public AddDataBaseTablePage(ISelection selection) { //,String pName) {
		super("wizardPage");
		setTitle("Add DataBaseTable to Database Store");//+pName);
		setDescription("This wizard adds a new Database Table to DataBaseStore ");//+pName);
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("DatabaseStore Name");
		dataBaseTableText = new Text(container, SWT.BORDER | SWT.SINGLE);
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    dataBaseTableText.setLayoutData(gd);
	    dataBaseTableText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
	    Label label1 = new Label(container, SWT.NULL);
	    label1.setText("Description");
	    dataBaseTableDescription=  new Text(container, SWT.BORDER | SWT.SINGLE);
	    gd = new GridData(GridData.FILL_HORIZONTAL);
	    dataBaseTableDescription.setLayoutData(gd);
	   // dataBaseTableDescription.addModifyListener(new ModifyListener() {
		//	public void modifyText(ModifyEvent e) {
			//	dialogChanged();
		//	}
	//	});
	    Label label2 = new Label(container, SWT.NULL);
	    label2.setText("Key");
	    dataBaseTableKey=  new Text(container, SWT.BORDER | SWT.SINGLE);
	    gd = new GridData(GridData.FILL_HORIZONTAL);
	    dataBaseTableKey.setLayoutData(gd);
	  //  dataBaseTableKey.addModifyListener(new ModifyListener() {
		//	public void modifyText(ModifyEvent e) {
			//	dialogChanged();
		//	}
	//	});
	    Label label3 = new Label(container, SWT.NULL);
	    label3.setText("Field");
	    dataBaseTableField=  new Text(container, SWT.BORDER | SWT.SINGLE);
	    gd = new GridData(GridData.FILL_HORIZONTAL);
	    dataBaseTableField.setLayoutData(gd);
	  //  dataBaseTableField.addModifyListener(new ModifyListener() {
		//	public void modifyText(ModifyEvent e) {
			//	dialogChanged();
		//	}
	//	});
	    Label label4 = new Label(container, SWT.NULL);
	    label4.setText("Has PKey");
	    dataBaseTableHasPKey=  new Combo(container, SWT.BORDER | SWT.SINGLE);
	    gd = new GridData(GridData.FILL_HORIZONTAL);
	    dataBaseTableHasPKey.setLayoutData(gd);
	  //  dataBaseTableHasPKey.addModifyListener(new ModifyListener() {
		//	public void modifyText(ModifyEvent e) {
			//	dialogChanged();
		//	}
	//	});
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		String dBSName = getDataBaseTableName();
		if (dBSName.length() == 0) {
			updateStatus("Data Base Store Name must be specified");
			return;
		}
		if (dBSName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("Data base Store Name must be valid");
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	public String getDataBaseTableHasPKey() {
		return dataBaseTableHasPKey.getText();
	}
	public String getDataBaseTableField() {
		return dataBaseTableField.getText();
	}
	public String getDataBaseTableDescription() {
		return dataBaseTableDescription.getText();
	}
	public String getDataBaseTableKey() {
		return dataBaseTableKey.getText();
	}
	public String getDataBaseTableName() {
		return dataBaseTableText.getText();
	}
}	
