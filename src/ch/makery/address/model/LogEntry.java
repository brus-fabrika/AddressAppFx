package ch.makery.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LogEntry {
	private final StringProperty payload;

	public LogEntry() {
		this(null);
	}

	public LogEntry(String payload) {
		this.payload = new SimpleStringProperty(payload);
	}

	public String getPayload() {
		return payload.get();
	}

	public void setPayload(String payload) {
		this.payload.set(payload);
	}

	public StringProperty payloadProperty() {
		return payload;
	}
}
