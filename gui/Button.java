package gui;

import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Button extends JButton {

    private static final long serialVersionUID = 1L;
    private boolean[] isEnabled;

    public Button(String text, TextField... fields) {
        this.setText(text);
        this.setEnabled(false);

        isEnabled = new boolean[fields.length];

        fillFalse();
        bindFields(fields);
    }

    private void bindFields(TextField[] fields) {
        var self = this;

        for (int i = 0; i < fields.length; i++) {
            final int id = i;
            fields[id].getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    var text = fields[id].getText();
                    isEnabled[id] = !text.equals(fields[id].getEt()) && !text.equals("");
                    if (enabled())
                        self.setEnabled(true);
                    else
                        self.setEnabled(false);
                }
            });
        }
    }

    private void fillFalse() {
        for (int i = 0; i < isEnabled.length; i++)
            isEnabled[i] = false;
    }

    private boolean enabled() {
        for (boolean b : isEnabled)
            if (!b)
                return false;
        return true;
    }
}
