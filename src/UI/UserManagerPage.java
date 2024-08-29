package UI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public abstract class UserManagerPage {
    private ActionButton btnSubmit;
    private JLabel lblUsername;
    protected JTextField txtUsername;
    private JLabel lblpassword;
    protected JPasswordField txtPassword;

    public UserManagerPage(String buttonText, String lblUsername, String lblPassword){
        this.lblpassword = new JLabel(lblPassword);
        this.lblUsername = new JLabel(lblUsername);
        this.txtUsername = new JTextField();
        this.txtPassword = new JPasswordField();

        this.btnSubmit = new ActionButton(buttonText) {
            @Override
            public void doSomething() {
                execute();
            }
        };
    }

    public JPanel buildPanel(String panelTitle){
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(panelTitle));
        panel.setLayout(new GridLayout(3, 1));

        panel.add(this.lblUsername);
        panel.add(this.txtUsername);
        panel.add(this.lblpassword);
        panel.add(this.txtPassword);
        panel.add(this.btnSubmit.get());

        return panel;
    }

    public abstract void execute();
}
