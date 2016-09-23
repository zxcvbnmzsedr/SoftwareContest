package jframe;

import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TextDialog extends Timer {

	private static final long serialVersionUID = 2791827603307165823L;

	public TextDialog(int arg0, ActionListener arg1) {
		super(arg0, arg1);
	}

	public long getSerialVersionUID() {
		return serialVersionUID;
	}

}
