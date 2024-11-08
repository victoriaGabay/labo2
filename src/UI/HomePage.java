package UI;

import Entities.EmailTypes;
import Exceptions.ServiceException;
import Services.EmailsService;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JTree implements ActionListener {

    private JMenuBar menuBar;
    private JMenuItem sendMenu;
    private JMenuItem recieveMenu;
    private JMenuItem viewContactsMenu;
    private JMenuItem viewConfigurationMenu;
    private DefaultMutableTreeNode main;
    private DefaultMutableTreeNode recieved;
    private DefaultMutableTreeNode pending;
    private DefaultMutableTreeNode sent;
    private DefaultMutableTreeNode outBox;
    private JPanel treePanel = loadJTree();
    private JPanel menuPanel = loadMenu();
    private EmailsTable table;
    private ActionButton btnReload;
    private FrameManager manager;

    public HomePage(FrameManager manager){
        this.manager = manager;
        this.table = new EmailsTable(manager);
    }

    public JPanel buildHomePage(EmailTypes listToShow){
        this.menuPanel.add(this.table.armarPanel(listToShow), BorderLayout.CENTER); //arma el panel para el cosito seleccionado (enviados/borradores/etc)
        this.table.activateActionListener();
        this.treePanel.add(this.menuPanel, BorderLayout.CENTER);

        //obligo al boton a implementar el metodo doSomething utilizado por la clase ACtionButton (la que contine el Jbutton)
        this.btnReload = new ActionButton("Cerrar Sesión") {
            @Override
            public void doSomething() {
                manager.setUser(null);
                manager.buildLogin();
            }
        };

        this.treePanel.add(btnReload.get(), BorderLayout.SOUTH);

        return this.treePanel;
    }

    private JPanel loadMenu(){
        JPanel pane = new JPanel(new BorderLayout());

        this.menuBar = new JMenuBar();
        this.sendMenu = new JMenuItem("Escribir");
        this.recieveMenu = new JMenuItem("Enviar y Recibir");
        this.viewContactsMenu = new JMenuItem("Contactos");
        this.viewConfigurationMenu = new JMenuItem("Configuración");

        this.menuBar.add(sendMenu);
        this.menuBar.add(recieveMenu);
        this.menuBar.add(viewContactsMenu);
        this.menuBar.add(viewConfigurationMenu);

        this.sendMenu.addActionListener(this);
        this.recieveMenu.addActionListener(this);
        this.viewContactsMenu.addActionListener(this);
        this.viewConfigurationMenu.addActionListener(this);

        pane.add(menuBar, BorderLayout.NORTH);
        return pane;
    }

    private JPanel loadJTree(){

        JPanel panel = new JPanel(new BorderLayout());
        this.main = new DefaultMutableTreeNode("Inbox");
        this.recieved = new DefaultMutableTreeNode("Recibidos");
        this.sent= new DefaultMutableTreeNode("Enviados");
        this.pending = new DefaultMutableTreeNode("Borradores");
        this.outBox = new DefaultMutableTreeNode("Salida");
        this.main.add(this.recieved);
        this.main.add(this.sent);
        this.main.add(this.pending);
        this.main.add(this.outBox);
        JTree jt=new JTree(this.main);
        panel.add(jt, BorderLayout.WEST);

        jt.addTreeSelectionListener(evt -> jTree1ValueChanged(evt)); //genera el action listener para que cuando apretes el boton(el cosito) hsgs slgo
        return panel;
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource().equals(this.sendMenu)){
            this.manager.showSendEmailView();
        } else if (ae.getSource().equals(this.recieveMenu)) {
            sendAndRecieve();
            this.manager.showHomePage(EmailTypes.Recieved);
        }else if (ae.getSource().equals(this.viewContactsMenu)) {
            manager.buildContactsPage();
        }
        else if(ae.getSource().equals(this.viewConfigurationMenu)){
            manager.buildConfigurationPage();
        }
    }

    public void jTree1ValueChanged( TreeSelectionEvent tse ) {
        String node = tse.getNewLeadSelectionPath().getLastPathComponent().toString(); //logica native de jtree para saber que cosito se apreto
        if(node.equals("Recibidos")) {
            manager.showHomePage(EmailTypes.Recieved);
        }
        else if(node.equals("Enviados")) {
            manager.showHomePage(EmailTypes.Sent);
        }
        else if(node.equals("Borradores")) {
            manager.showHomePage(EmailTypes.Pending);
        }
        else if(node.equals("Salida")) {
            manager.showHomePage(EmailTypes.Outbox);
        }
    }

    private void sendAndRecieve(){
        EmailsService service = new EmailsService();
        try{
            service.sendAndRecieve(this.manager.getUser());
        } catch (ServiceException e) {
           JOptionPane.showMessageDialog(manager.getFrame(), e.getMessage(), "Error" ,JOptionPane.ERROR_MESSAGE);
        }
    }
}
