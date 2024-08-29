package UI;

import Entities.Email;
import Entities.EmailTypes;
import Exceptions.ServiceException;
import Services.EmailsService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmailsTable {

    private JTable table;
    private EmailTableModel model;
    private FrameManager manager;
    private JScrollPane scrollPane;
    private JPanel panel;
    public EmailsTable(FrameManager manager) {
        super();
        this.manager = manager;
    }

    public JPanel armarPanel(EmailTypes listToShow) {

        this.panel = new JPanel(new BorderLayout());

        model = new EmailTableModel();
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        this.panel.add(scrollPane);


        List<Email> list = new ArrayList<>();
        try{
            EmailsService serv = new EmailsService();
            switch (listToShow.ordinal()){
                case 0: list = serv.getOutbox(manager.getUser()); break;
                case 1: list = serv.getAllSentByUserId(manager.getUser().getName()); break;
                case 2: list = serv.getAllRecievedByUserId(manager.getUserId()); break;
                case 3: list = serv.getAllPendingByUserId(manager.getUserId()); break;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        model.setContent(list);
        model.fireTableDataChanged();

        panel.validate();
        panel.repaint();
        return this.panel;
    }


    public void activateActionListener(){
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                panel.validate();
                panel.repaint();
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0) {
                    Email email = model.getContent().get(selectedRow);
                    JPanel detailPanel = new Detail(email, manager).buildDetail();
                    panel.add(detailPanel, BorderLayout.PAGE_END);
                    panel.validate();
                    panel.repaint();
                }
            }
        });
    }
}
