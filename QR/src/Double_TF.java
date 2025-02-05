import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.*;

public class Double_TF extends JTextField {
    public Double_TF() {
        setSize(55, 35);
        setHorizontalAlignment(JTextField.CENTER);
        setFont(new Font("Times New Roman", Font.PLAIN, 15));
        ((AbstractDocument) getDocument()).setDocumentFilter(new DoubleFilter());
        setBorder(new LineBorder(Color.GRAY, 1, true));
    }

    private class DoubleFilter extends DocumentFilter {

        boolean dot = false;

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            int dot_count = 0;
            for (int i = 0; i < text.length(); i++) {
                if (Character.isDigit(text.charAt(i))) {
                    continue;
                } else if (text.charAt(i) == '.') {
                    dot_count++;
                } else if (text.charAt(i) == '-' && getText().length() == 0) {
                    continue;
                } else {
                    return;
                }
                if (dot && dot_count >= 1 || dot_count > 1) {
                    return;
                } else if (!dot && dot_count == 1) {
                    dot = true;
                }
            }
            super.replace(fb, offset, length, text, attrs);

        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String textToRemove = currentText.substring(offset, offset + length);
            if (textToRemove.contains(".")) {
                dot = false;
            }
            super.remove(fb, offset, length);
        }
    }

    public double getValue() throws IllegalArgumentException {
        if (getText() == "") {
            throw new IllegalArgumentException();
        }
        return (Double.parseDouble(getText()));
    }
}
