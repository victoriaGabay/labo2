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
        this.frame = new JFrame("Mails"); //nueva instancia de clase
        this.frame.setBounds(100, 100, 500, 500); //atributos de la clase JFrame, cuyo objetos this.frame
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//atributos de la clase JFrame, cuyo objetos this.frame
        this.homePage = new HomePage(this);// nueva instancia de clase
        this.newEmailPage = new NewEmailPage(this);// nueva instancia de clase
        this.loginPage = new LoginPage(this);// nueva instancia de clase
        this.registerPage = new RegisterNewUserPage(this);// nueva instancia de clase
        this.contactsPage = new ContactsPage(this);// nueva instancia de clase
        this.configuration = new Configuration(this);// nueva instancia de clase

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
