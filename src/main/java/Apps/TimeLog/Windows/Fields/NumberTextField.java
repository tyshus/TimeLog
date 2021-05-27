package Apps.TimeLog.Windows.Fields;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField {

	public int getint() {
		if (super.getText() == null || super.getText().length() == 0)
			return 0;
		else
			return Integer.parseInt(super.getText());
	}

	public void SetInt(int i) {
		super.setText(String.valueOf(i));
	}

	public void SetLong(long l) {
		super.setText(String.valueOf(l));
	}

	public void SetDouble(double d) {
		super.setText(String.valueOf(d));
	}

	@Override
	public void replaceText(int start, int end, String text) {
		if (validate(text)) {
			super.replaceText(start, end, text);
		}
	}

	@Override
	public void replaceSelection(String text) {
		if (validate(text)) {
			super.replaceSelection(text);
		}
	}

	private boolean validate(String text) {
		return text.matches("[0-9]*");
	}
}