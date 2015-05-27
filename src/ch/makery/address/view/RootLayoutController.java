package ch.makery.address.view;

import ch.makery.address.MainApp;
import javafx.fxml.FXML;

public class RootLayoutController {

	// Reference to the main application.
	private MainApp mainApp;
	
	@FXML
	private void handleAboutMenu() {
		mainApp.showTableView();
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
