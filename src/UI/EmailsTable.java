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
        table = new JTable(model); //instancio una nueva JTable con el modelo de filas creado anteriormente 
        scrollPane = new JScrollPane(table); //a la tabla agregale un scrollbar
        this.panel.add(scrollPane);


        List<Email> list = new ArrayList<>();
        try{
            EmailsService serv = new EmailsService();
            switch (listToShow.ordinal()){
                case 0: list = serv.getOutbox(manager.getUser()); break;  //invoca al email service para traer la informacion que se quiere mostrar en la tabla
                case 1: list = serv.getAllSentByUserId(manager.getUser().getName()); break; //invoca al email service para traer la informacion que se quiere mostrar en la tabla
                case 2: list = serv.getAllRecievedByUserId(manager.getUserId()); break; //invoca al email service para traer la informacion que se quiere mostrar en la tabla
                case 3: list = serv.getAllPendingByUserId(manager.getUserId()); break; //invoca al email service para traer la informacion que se quiere mostrar en la tabla
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        model.setContent(list); //le seteo el contenido a la tabla (private List<Email> content;)
        model.fireTableDataChanged(); //ni puta idea, hace que se muestren las cosas xD

        panel.validate();//ni puta idea, es necesario para que funcionen las pantallas. 
        panel.repaint();//ni puta idea, es necesario para que funcionen las pantallas. 
        return this.panel; //duvuelve la cosa para que el frame lo pueda mostrar.
    }


    //cuando se clickea en una fila de la tabla, me muestra la pantalla con un detalle mas extensivo.PQTP
    public void activateActionListener(){
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                panel.validate();
                panel.repaint();
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0) {
                    //INSTANCIA Un nuevo objeto de la clase Detail (pagina) para mostrar los detalles del mail seleccionado
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
