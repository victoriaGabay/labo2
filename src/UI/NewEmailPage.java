package UI;

import Entities.Email;
import Entities.EmailTypes;
import Entities.User;
import Exceptions.ServiceException;
import Services.EmailsService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEmailPage extends EmailForm{

    private FrameManager manager;
    private JButton btnBack;
    public NewEmailPage(FrameManager manager){
        this.manager = manager;
        this.btnBack = new JButton("Volver (se guardara el mail como borrador)");

        this.btnBack.addActionListener(new ActionListener() { //para que haga algo cuando se lo clickea
            @Override
            public void actionPerformed(ActionEvent e) {
                EmailsService service = new EmailsService();
                Email email = returnEmail();
                if(!email.isEmpty()) {
                    try {
                        email.setType(EmailTypes.Pending);
                        email.setFrom(manager.getUser().getName());
                        email.setHost(manager.getUser().getHost());
                        service.create(email); //Guardo el mail como borrador en la base de datos SHERLOCK
                        manager.showHomePage(EmailTypes.Sent);
                    } catch (ServiceException ex) {
                        JOptionPane.showMessageDialog(manager.getFrame(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public JPanel show(){
        this.clean();

        User user = this.manager.getUser();
        JPanel panel = showForm(user.getId(), user.getHost(), user.getHostUserName()); //instancio un nuevo JPanel con el metodo showForm de la clase padre
        panel.add(this.btnBack); //lo agrego al paneL
        return panel;
    }

    @Override
    public void saveChanges(Email email) {
        EmailsService service = new EmailsService();
        try
        {
            email.setFrom(manager.getUser().getName()); //Setea el atributo FROM del objeto email.
            service.create(email); //invoca al metodo create del emailsservice
            manager.showHomePage(EmailTypes.Sent); //muestro la pantalla con el listado de los mails enviados
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(manager.getFrame(), e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
