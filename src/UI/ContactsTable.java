package UI;

import Entities.User;
import Exceptions.ServiceException;
import Services.ContactsService;
import Services.UsersService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContactsTable {

    private JTable table;
    private ContactsTableModel model;
    private FrameManager manager;
    private JScrollPane scrollPane;
    private JPanel panel;

    public ContactsTable(FrameManager manager) {
        super();
        this.manager = manager;
    }

    public JPanel buildPanel() {

        this.panel = new JPanel(new BorderLayout());

        model = new ContactsTableModel();
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        this.panel.add(scrollPane);

        addMouseListener();

        List<User> list = new ArrayList<>();
        try{
            UsersService usersService = new UsersService();
            list = usersService.getAllAContacts(this.manager.getUserId());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        model.setContent(list);
        model.fireTableDataChanged();

        panel.validate();
        panel.repaint();
        return this.panel;
    }

    private void delete(User user){
        ContactsService contactsService = new ContactsService();
        try{
            contactsService.delete(user.getId(), manager.getUserId());
            this.manager.buildContactsPage();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(manager.getFrame(), e.getMessage(), "Error" ,JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addMouseListener(){
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 2) {
                    User user = model.getContent().get(row);
                    delete(user);
                }
            }
        });
    }
}
