package ch.makery.address.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ch.makery.address.model.LogEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class LogViewTableViewController {

	@FXML
	private TableView<LogEntry> mLogTable;
	@FXML
	private TableColumn<LogEntry, String> mPayloadColumn;
	@FXML
	private TableColumn<LogEntry, String> mTimeColumn;
	
	@FXML
	private void initialize() {
		mPayloadColumn.setCellValueFactory(cellData -> cellData.getValue().payloadProperty());
		mTimeColumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());
	}

	public void loadLogData(File logFile) {
		ObservableList<LogEntry> logs = FXCollections.observableArrayList();
		
		try(BufferedReader logFileReader = new BufferedReader(new FileReader(logFile.getAbsolutePath()))){
			String logLine = logFileReader.readLine();
			while(logLine != null) {
				logs.add(new LogEntry(logLine));
				logLine = logFileReader.readLine();
			}
		} catch(IOException ignore) {
			ignore.printStackTrace();
		}
		
		mLogTable.setItems(logs);
	}

}
