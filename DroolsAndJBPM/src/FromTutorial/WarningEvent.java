package FromTutorial;

public class WarningEvent {
	int severity;
	String name;

	public WarningEvent(int severity, String name) {
		super();
		this.severity = severity;
		this.name = name;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
