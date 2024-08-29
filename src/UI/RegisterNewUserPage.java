package UI;

import Entities.User;
import Exceptions.ServiceException;
import Services.UsersService;

import javax.swing.*;

public class RegisterNewUserPage extends UserManagerPage{

    private FrameManager manager;
    private ActionButton btn;

    public RegisterNewUserPage(FrameManager manager){
        super("Registrar nuevo usuario", "Ingrese un nombre de usuario (su nombre sera la dirección a la que se enviaran los correos)", "Ingrese una contraseña");
        this.manager = manager;

        this.btn = new ActionButton("Cancelar") {
            @Override
            public void doSomething() {
                manager.buildLogin();
            }
        };
    }

    public JPanel build(){
        JPanel panel =  super.buildPanel("Registrar nuevo usuario");
        panel.add(btn.get());
        return panel;
    }


    @Override
    public void execute() {
        UsersService service = new UsersService();
        try {
            User user = new User(super.txtUsername.getText(), String.valueOf(super.txtPassword.getPassword()));
            service.create(user);
            manager.buildLogin();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(manager.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
