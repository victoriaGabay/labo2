package UI;

import Entities.Contact;
import Entities.EmailTypes;
import Entities.User;
import Exceptions.ServiceException;
import Services.ContactsService;
import Services.UsersService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ContactsPage {

    private FrameManager manager;
    private ContactsTable table;
    private ActionButton backToHomePageBtn;
    private ActionButton btnAddContact;

    public ContactsPage(FrameManager manager){
        this.manager = manager;
        this.table = new ContactsTable(manager);

        this.backToHomePageBtn = new ActionButton("Volver") {
            @Override
            public void doSomething() {
                manager.showHomePage(EmailTypes.Recieved);
            }
        };

        this.btnAddContact = new ActionButton("Agregar Contacto") {
            @Override
            public void doSomething() {
                String userName = JOptionPane.showInputDialog(manager.getFrame(), "Ingrese el nombre del usuario al que quiere agregar a sus contactos");
                if(userName != null && userName != ""){
                    try {
                        UsersService usersService = new UsersService();
                        ContactsService contactsService = new ContactsService();

                        User result = usersService.getByName(userName);
                        Contact contact = new Contact(manager.getUserId(), result.getId());

                        contactsService.create(contact);
                        manager.buildContactsPage();
                    }
                    catch(ServiceException e) {
                        JOptionPane.showMessageDialog(manager.getFrame(), e.getMessage(), "Error" ,JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };

    }

    public JPanel buildContactsPage(){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Contactos"));
        panel.setLayout(new GridLayout(2,0));
        panel.add(this.table.buildPanel());

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setLayout(new GridLayout(1,1));

        panel2.add(this.backToHomePageBtn.get());
        panel2.add(this.btnAddContact.get());

        panel.add(panel2, BorderLayout.CENTER);

        return panel;
    }
}
