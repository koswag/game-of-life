package gui;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextField extends JTextField {

    private static final long serialVersionUID = 1L;
    private String text;
    private String et;

    public TextField(String et) {
        this.text = "";
        this.et = et;

        this.setText(et);
        this.setForeground(Color.GRAY);

        setOnFocus();
        setOnChange();
    }

    private void setOnFocus() {
        var self = this;
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (self.getText().equals(et)) {
                    self.setForeground(Color.BLACK);
                    self.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (self.getText().equals("")) {
                    self.setForeground(Color.GRAY);
                    self.setText(et);
                }
            }
        });
    }

    private void setOnChange() {
        var self = this;
        this.getDocument().addDocumentListener(new DocumentListener() {
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
                if (!self.getText().equals(et)) {
                    text = self.getText();
                }
            }
        });
    }

    public String getFieldText() {
        return text;
    }

    public String getEt() {
        return et;
    }

}