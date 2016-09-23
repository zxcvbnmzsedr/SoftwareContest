package run;

public class Run {
	private static Start start;

	private Run() {

	}

	public static void main(String[] args) {
		setStart(new Start());
		getStart().init();
	}

	public static Start getStart() {
		return start;
	}

	public static void setStart(Start start) {
		Run.start = start;
	}

}
