import javax.swing.text.*;

public class UppercaseFilter extends DocumentFilter {
	@Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text != null) {
            super.insertString(fb, offset, text.toUpperCase(), attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null) {
            super.replace(fb, offset, length, text.toUpperCase(), attrs);
        }
    }
}
