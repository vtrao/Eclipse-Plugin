package TableEditor;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;

/**
 * This sample class demonstrates how to use the TableViewerExample 
 * inside a workbench view. The view is essentially a wrapper for
 * the TableViewerExample. It handles the Selection event for the close 
 * button.
 */

public class TableEditorView extends ViewPart {
	private TableViewerExample viewer;

	/**
	 * The constructor.
	 */
	public TableEditorView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TableViewerExample(parent,1,new Object());
		viewer.getCloseButton().addSelectionListener(new SelectionAdapter() {
       	
			// Close the view i.e. dispose of the composite's parent
			public void widgetSelected(SelectionEvent e) {
				handleDispose();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	/**
	 * Handle a 'close' event by disposing of the view
	 */

	public void handleDispose() {	
	}
	}
	