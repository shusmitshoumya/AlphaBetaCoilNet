import javax.swing.text.*;

public class AminoAcidDocumentFilter extends DocumentFilter{
	@Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string != null && isValidInput(string)) {
            super.insertString(fb, offset, string.toUpperCase(), attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && isValidInput(text)) {
            super.replace(fb, offset, length, text.toUpperCase(), attrs);
        }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    private boolean isValidInput(String text) {
        return text.toUpperCase().matches("[ACDEFGHIKLMNPQRSTVWY]*"); // Only allows the amino acid letters
    }
}
