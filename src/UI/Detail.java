package UI;

import Entities.Email;
import Entities.EmailTypes;
import Entities.User;
import Exceptions.ServiceException;
import Services.EmailsService;

import javax.swing.*;

public class Detail extends EmailForm{
    private Email email;
    private ActionButton btnDelete;
    private FrameManager manager;
    public Detail(Email email, FrameManager manager){
        this.email = email;
        this.manager = manager;
    }

    public JPanel buildDetail(){

        showData(this.email);

        this.btnDelete = new ActionButton("Eliminar") {
            @Override
            public void doSomething() {
                EmailsService service = new EmailsService();
                try{
                    service.delete(email.getId());
                    manager.showHomePage(EmailTypes.Sent);
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(manager.getFrame(), ex.getMessage());
                }
            }
        };

        User user = manager.getUser();
        JPanel panel = showForm(user.getId(), user.getHost(), email.getFrom());

        panel.add(btnDelete.get());

        if(this.email.getType() == EmailTypes.Pending) {
            setToViewDetails(true);
            modifyCombo(null, false);
        }else {
            setToViewDetails(false);
            modifyCombo(email.getTo(), true);
        }

        harcodeCombo(email.getTo());
        return panel;
    }

    @Override
    public void saveChanges(Email email) {
        email.setId(this.email.getId());
        email.setAccountId(this.manager.getUserId());
        EmailsService service = new EmailsService();
        try{
            email.setType(EmailTypes.Outbox);
            service.update(email.getId(), email);
            manager.showHomePage(EmailTypes.Pending);
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(manager.getFrame(), ex.getMessage());

        }
    }
}
