package Apps.TimeLog.Windows;

import javafx.scene.control.TextField;

public class DoubleTextField extends TextField{
    
    public double getDouble() {
    	if (super.getText() == null)
    		return 0;
    	else
    		return Double.parseDouble(super.getText());
    }
    
    public void SetDouble(double d) {
    	super.setText(String.valueOf(d));
    }
    
//    @Override
//    public void replaceText(int start, int end, String text)
//    {
//        if (validate(text))
//        {
//            super.replaceText(start, end, text);
//        }
//    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }
    
    private boolean validate(String text)
    {
        return text.matches("[0-9]*");
    }

}
