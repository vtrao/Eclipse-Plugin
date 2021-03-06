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

public class AddUISetPage extends WizardPage {
	private Text uISetText;
	private ISelection selection;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public AddUISetPage(ISelection selection) {
		super("wizardPage");
		setTitle("Add New UISet");
		setDescription("This wizard creates a new UISet");
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
		label.setText("UISet Name");
		uISetText = new Text(container, SWT.BORDER | SWT.SINGLE);
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    uISetText.setLayoutData(gd);
	    uISetText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
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
		String projectName = getUISetName();
		if (projectName.length() == 0) {
			updateStatus("UISet Name must be specified");
			return;
		}
		if (projectName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("UISet name must be valid");
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	public String getUISetName() {
		return uISetText.getText();
	}
}	

