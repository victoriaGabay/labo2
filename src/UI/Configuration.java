package UI;

import Entities.EmailTypes;
import Entities.User;
import Exceptions.ServiceException;
import Services.UsersService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Configuration {

    private FrameManager manager;

    private JLabel lblHost;
    private JTextField txtHost;

    private JLabel lblPort;
    private JTextField txtPort;

    private JLabel lblHostEmail;
    private JTextField txthostEmail;

    private JLabel lblHostPassword;
    private JTextField txthostPassword;

    private JLabel lblPopHost;
    private JTextField txtPopHost;

    private JLabel lblPopHostPort;
    private JTextField txtPopHostPort;

    private ActionButton btnSumbit;
    private ActionButton btnBack;

    public Configuration(FrameManager manager){
        this.manager = manager;

        this.lblHost = new JLabel("Host");
        this.txtHost = new JTextField();

        this.lblPort = new JLabel("Port");
        this.txtPort = new JTextField();

        this.lblHostEmail = new JLabel("Cuenta del host");
        this.txthostEmail = new JTextField();

        this.lblHostPassword = new JLabel("Contraseña de la cuenta del host");
        this.txthostPassword = new JTextField();

        this.lblPopHost = new JLabel("Host pop");
        this.txtPopHost = new JTextField();

        this.lblPopHostPort = new JLabel("Host pop port");
        this.txtPopHostPort = new JTextField();

        this.btnSumbit = new ActionButton("Guardar cambios") {
            @Override
            public void doSomething() {
                UsersService service = new UsersService();
                try{
                    User user = manager.getUser();
                    update(user);
                    service.updateHostConfiguration(user);
                    manager.showHomePage(EmailTypes.Recieved);
                } catch (ServiceException e) {
                    JOptionPane.showMessageDialog(manager.getFrame(), e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        this.btnBack = new ActionButton("Volver") {
            @Override
            public void doSomething() {
                manager.showHomePage(EmailTypes.Recieved);
            }
        };
    }

    public JPanel buildConfiguration(){

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("BIENVENIDO,  "  + manager.getUser().getName() + ". Aca podras configurar el host que quieras usar para el envio y recibo de mails"));
        panel.setLayout(new GridLayout(7, 1));

        User user = this.manager.getUser();

        this.txtHost.setText(user.getHost());
        this.txtPort.setText(user.getPort());
        this.txthostEmail.setText(user.getHostUserName());
        this.txthostPassword.setText(user.getHostPassword());
        this.txtPopHost.setText(user.getPopHost());
        this.txtPopHostPort.setText(user.getPopHostPort());

        panel.add(this.lblHost);
        panel.add(this.txtHost);
        panel.add(this.lblPort);
        panel.add(this.txtPort);
        panel.add(this.lblHostEmail);
        panel.add(this.txthostEmail);
        panel.add(this.lblHostPassword);
        panel.add(this.txthostPassword);
        panel.add(this.lblPopHost);
        panel.add(this.txtPopHost);
        panel.add(this.lblPopHostPort);
        panel.add(this.txtPopHostPort);

        panel.add(this.btnSumbit.get());
        panel.add(this.btnBack.get());

        JOptionPane.showMessageDialog(manager.getFrame(), "ATENCIÓN: SI CAMBIA LAS CONFIGURACIONES DEL HOST-PUERTO ES POSIBLE QUE EL ENVIÓ DE MAILS DEJE DE FUNCIONAR DEBIDO A CUESTIONES DE SEGURIDAD DE LOS HOSTS", "Atención", JOptionPane.WARNING_MESSAGE);
        return panel;
    }

    private void update(User user){
        user.setHost(this.txtHost.getText());
        user.setPort(this.txtPort.getText());
        user.setHostUserName(this.txthostEmail.getText());
        user.setHostPassword(this.txthostPassword.getText());
        user.setPopHost(this.txtPopHost.getText());
        user.setPopHostPort(this.txtPopHostPort.getText());
    }
}
