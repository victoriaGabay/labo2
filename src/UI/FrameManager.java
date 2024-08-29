package UI;

import Entities.EmailTypes;
import Entities.User;

import javax.swing.*;

public class FrameManager {
    private HomePage homePage;
    private NewEmailPage newEmailPage;
    private JFrame frame;
    private LoginPage loginPage;
    private RegisterNewUserPage registerPage;
    private ContactsPage contactsPage;
    private Configuration configuration;
    private User user;

    public void setManager(){
        this.frame = new JFrame("Mails");
        this.frame.setBounds(100, 100, 500, 500);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.homePage = new HomePage(this);
        this.newEmailPage = new NewEmailPage(this);
        this.loginPage = new LoginPage(this);
        this.registerPage = new RegisterNewUserPage(this);
        this.contactsPage = new ContactsPage(this);
        this.configuration = new Configuration(this);

        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "Ok");
        UIManager.put("OptionPane.yesButtonText", "Sí");
    }

    public void showFrame() {
        this.frame.setVisible(true);
    }

    public void showHomePage(EmailTypes type){
        this.frame.getContentPane().removeAll();
        this.homePage = new HomePage(this);
        this.frame.getContentPane().add(this.homePage.buildHomePage(type));
        this.frame.getContentPane().validate();
        this.frame.getContentPane().repaint();
    }

    public void showSendEmailView(){
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(this.newEmailPage.show());
        this.frame.getContentPane().validate();
        this.frame.getContentPane().repaint();
    }

    public void buildLogin(){
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(this.loginPage.build());
        this.frame.getContentPane().validate();
        this.frame.getContentPane().repaint();
    }

    public void buildRegisterPage(){
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(this.registerPage.build());
        this.frame.getContentPane().validate();
        this.frame.getContentPane().repaint();
    }

    public void buildContactsPage(){
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(this.contactsPage.buildContactsPage());
        this.frame.getContentPane().validate();
        this.frame.getContentPane().repaint();
    }

    public void buildConfigurationPage(){
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(this.configuration.buildConfiguration());
        this.frame.getContentPane().validate();
        this.frame.getContentPane().repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId(){
        return user.getId();
    }

}
