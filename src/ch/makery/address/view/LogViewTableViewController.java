package ch.makery.address.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.makery.address.model.FileTailer;
import ch.makery.address.model.FileTailerListener;
import ch.makery.address.model.LogEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogViewTableViewController implements FileTailerListener{

	@FXML
	private TableView<LogEntry> mLogTable;
	@FXML
	private TableColumn<LogEntry, String> mPayloadColumn;
	@FXML
	private TableColumn<LogEntry, String> mTimeColumn;
	@FXML
	private TextField mRegexFilterText;
	
	private ArrayList<LogEntry> mLogsList = new ArrayList<>();
	
	private ObservableList<LogEntry> mLogs = FXCollections.observableArrayList();
	
	private String mRegex;
	
	FileTailer mLogFileTailer;
	
	@FXML
	private void initialize() {
		mPayloadColumn.setCellValueFactory(cellData -> cellData.getValue().payloadProperty());
		mTimeColumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());
	}
	
	@FXML
	private void onRegexUpdate() {
		System.out.println("LogViewTableViewController.onRegexUpdate()");
		
		if(mRegexFilterText.getText().isEmpty()) return;
		if(mRegexFilterText.getText().equals(mRegex)) return;
		
		mRegex = "(" + mRegexFilterText.getText() + ")";
		
		System.out.println("LogViewTableViewController.onRegexUpdate(): Regex = " + mRegex);
		
		Pattern pattern = Pattern.compile(mRegex);
		
		mLogs.clear();
		
		for(LogEntry log: mLogsList) {
			Matcher m = pattern.matcher(log.getPayload());
			if(m.find()) {
				mLogs.add(log);
			}
		}
	}

	public void loadLogData(File logFile) {
		mRegexFilterText.clear();
		mLogs.clear();
		
		mLogsList = new ArrayList<>();
		
//		mLogs.addAll(mLogsList);
		mLogTable.setItems(mLogs);
		
		mLogFileTailer = new FileTailer(logFile);
		mLogFileTailer.addLogFileTailerListener(this);
		
		mLogFileTailer.start();
		
//		try(BufferedReader logFileReader = new BufferedReader(new FileReader(logFile.getAbsolutePath()))){
//			String logLine = logFileReader.readLine();
//			while(logLine != null) {
//				mLogsList.add(new LogEntry(logLine));
//				//mLogs.add(new LogEntry(logLine));
//				logLine = logFileReader.readLine();
//			}
//		} catch(IOException ignore) {
//			ignore.printStackTrace();
//		}

		
	}

	public ObservableList<LogEntry> getLogs() {
		return mLogs;
	}

	@Override
	public void newFileLine(String line) {
		System.out.println( line );
		LogEntry e = new LogEntry(line);
		mLogsList.add(e);
		mLogs.add(e);
	}
}
