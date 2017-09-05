package Wizards;


import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This page queries for the name of the project
 */

public class AddDataBaseStorePage extends WizardPage {
	private Text dataBaseStoreText;
	private Text dataBaseStoreDescription;
	private Text dataBaseStoreKey;
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public AddDataBaseStorePage(ISelection selection) { //,String pName) {
		super("wizardPage");
		setTitle("Add DatBaseStore to ");//+pName);
		setDescription("This wizard adds a new Database Store to Project ");//+pName);
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
		dataBaseStoreText = new Text(container, SWT.BORDER | SWT.SINGLE);
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    dataBaseStoreText.setLayoutData(gd);
	    dataBaseStoreText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
	    Label label1 = new Label(container, SWT.NULL);
	    label1.setText("Description");
	    dataBaseStoreDescription=  new Text(container, SWT.BORDER | SWT.SINGLE);
	    gd = new GridData(GridData.FILL_HORIZONTAL);
	    dataBaseStoreDescription.setLayoutData(gd);
	   // dataBaseStoreDescription.addModifyListener(new ModifyListener() {
		//	public void modifyText(ModifyEvent e) {
			//	dialogChanged();
		//	}
	//	});
	    Label label2 = new Label(container, SWT.NULL);
	    label2.setText("Key");
	    dataBaseStoreKey=  new Text(container, SWT.BORDER | SWT.SINGLE);
	    gd = new GridData(GridData.FILL_HORIZONTAL);
	    dataBaseStoreKey.setLayoutData(gd);
	  //  dataBaseStoreKey.addModifyListener(new ModifyListener() {
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
		String dBSName = getDataBaseStoreName();
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
	public String getDataBaseStoreDescription() {
		return dataBaseStoreDescription.getText();
	}
	public String getDataBaseStoreKey() {
		return dataBaseStoreKey.getText();
	}
	public String getDataBaseStoreName() {
		return dataBaseStoreText.getText();
	}
}	


