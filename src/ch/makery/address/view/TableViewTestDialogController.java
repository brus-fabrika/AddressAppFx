package ch.makery.address.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ch.makery.address.model.LogEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class TableViewTestDialogController {

	@FXML
	private TableView<LogEntry> testTable;
	@FXML
	private TableColumn<LogEntry, String> payloadColumn;
	
	private Stage dialogStage;
	
	public TableViewTestDialogController() {
		
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private void initialize() {
		payloadColumn.setCellValueFactory(cellData -> cellData.getValue().payloadProperty());
		
		ObservableList<LogEntry> logs = FXCollections.observableArrayList();
		
		try(BufferedReader logFile = new BufferedReader(new FileReader("c:\\Windows\\System32\\LogFiles\\BASE150527pm1.log"))){
			String logLine = logFile.readLine();
			while(logLine != null) {
				logs.add(new LogEntry(logLine));
				logLine = logFile.readLine();
			}
		} catch(IOException ignore) {
			ignore.printStackTrace();
		}
		
		testTable.setItems(logs);
	}

}
