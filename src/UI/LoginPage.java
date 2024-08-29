package UI;

import Entities.EmailTypes;
import Entities.User;
import Exceptions.ServiceException;
import Services.UsersService;

import javax.swing.*;

public class LoginPage extends UserManagerPage {

    private FrameManager manager;
    private ActionButton btnRegister;
    public LoginPage(FrameManager manager){
        super("Iniciar Sesión", "Usuario", "Contraseña");
        this.manager = manager;

        this.btnRegister = new ActionButton("Registrar nuevo usuario") {
            @Override
            public void doSomething() {
                manager.buildRegisterPage();
            }
        };
    }

    public JPanel build(){
        JPanel panel = super.buildPanel("Iniciar Sesión");
        panel.add(this.btnRegister.get());
        return panel;
    }

    @Override
    public void execute() {
        UsersService service = new UsersService();
        try {
            User result = service.logIn(super.txtUsername.getText(), String.valueOf(super.txtPassword.getPassword()));
            manager.setUser(result);
            manager.showHomePage(EmailTypes.Recieved);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(manager.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
