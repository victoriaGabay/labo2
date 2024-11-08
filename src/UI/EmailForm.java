package UI;

import Entities.Email;
import Entities.EmailTypes;
import Entities.User;
import Exceptions.ServiceException;
import Services.UsersService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class EmailForm {
    private JLabel lblFrom = new JLabel("De");
    private JLabel to = new JLabel("Para");
    private JLabel lblHost = new JLabel("Host");
    private JLabel subject = new JLabel("Asunto");
    private JLabel body = new JLabel("Cuerpo");

    private JTextField txtFrom;
    private JTextField txtTo = new JTextField();
    private JTextField txtBody = new JTextField();
    private JTextField txtSubject = new JTextField();
    private JTextField txtHost;
    private ActionButton btnSave;
    private int userId;
    private JComboBox contactsCombo;
    private ArrayList<String> contacts;

    protected JPanel showForm(int accountId, String host, String from) {

        this.btnSave = new ActionButton("Enviar") {
            @Override
            public void doSomething() {
                String to = (String) contactsCombo.getItemAt(contactsCombo.getSelectedIndex()); //la poronga que hace JCombo para traerte el item que seleccionaste
                Email email = new Email(from ,to, txtSubject.getText(),txtBody.getText(), host, EmailTypes.Outbox, userId); //nueva instancia de email. Mediante el constructor con parametros le indico que valores deben tomar los atributos.
                saveChanges(email);
            }
        };

        this.txtHost = new JTextField(host);
        this.txtFrom = new JTextField(from);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Detalle del mail"));
        panel.setLayout(new GridLayout(6, 5));
        this.userId = accountId;
        this.txtHost.setEditable(false);
        this.txtFrom.setEditable(false);

        this.getContactsUserName(accountId);
        contactsCombo = new JComboBox(this.contacts.toArray());
        contactsCombo.setBounds(50, 50, 90, 20);

        panel.add(lblFrom);
        panel.add(txtFrom);
        panel.add(to);
        panel.add(contactsCombo);
        panel.add(subject);
        panel.add(txtSubject);
        panel.add(body);
        panel.add(txtBody);
        panel.add(lblHost);
        panel.add(txtHost);
        panel.add(btnSave.get());

        return panel;
    }

    protected Email returnEmail(){
        return new Email(txtTo.getText(), txtBody.getText(),  txtSubject.getText(), EmailTypes.Sent, this.userId);
    }

    protected void clean(){
        this.txtBody.setText("");
        this.txtTo.setText("");
        this.txtSubject.setText("");
    }

    protected void setToViewDetails(boolean canEdit) {
        if(!canEdit) {
            this.txtBody.setEditable(false); //pelotudeces de la libreria Java swing para que no puedas editar un input xD
            this.txtTo.setEditable(false); //pelotudeces de la libreria Java swing para que no puedas editar un input xD
            this.txtSubject.setEditable(false); //pelotudeces de la libreria Java swing para que no puedas editar un input xD
            this.btnSave.get().setEnabled(false); //pelotudeces de la libreria Java swing para que no puedas editar un input xD
            this.contactsCombo.setEnabled(false); //pelotudeces de la libreria Java swing para que no puedas editar un input xD
        }
    }

    protected void showData(Email email){
        this.txtBody = new JTextField(email.getBody()); //pelotudeces de java swing para mostrar los datos
        this.txtTo = new JTextField(email.getTo()); //pelotudeces de java swing para mostrar los datos
        this.txtSubject = new JTextField(email.getSubject()); //pelotudeces de java swing para mostrar los datos
        this.txtFrom = new JTextField(email.getFrom()); //pelotudeces de java swing para mostrar los datos
        this.txtHost = new JTextField(email.getHost()); //pelotudeces de java swing para mostrar los datos
    }

    protected void modifyCombo(String to, boolean clean){ //metodo para popular el dropwdown con los valores correspondientes :)
        if(clean) {
            this.contactsCombo.removeAllItems();
            this.contactsCombo.addItem(new ComboItem("0", to));
            this.contactsCombo.setSelectedItem(to);
        }
        else{
            contactsCombo.removeAllItems();
            contacts.forEach(contact -> {
                contactsCombo.addItem(contact);
            });
        }
    }

    protected void harcodeCombo(String to){
        if(this.contacts.size() != 0){
            contacts.forEach(contact -> {
                if(contact.equals(to))
                this.contactsCombo.setSelectedItem(to);
            });
        }
    }

    public abstract void saveChanges(Email email);

    private void getContactsUserName(int accountId){ //me traigo todos los contactos asociados al usuario
        ArrayList<String> usernames = new ArrayList<>();

        try{
            UsersService usersService = new UsersService();
            ArrayList users = usersService.getAllAContacts(accountId);

            for(int i = 0; i < users.size(); i++) {
                usernames.add(users.get(i).toString());
            }

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        this.contacts = usernames;
    }
}

class ComboItem{
    private String value;
    private String label;

    public ComboItem(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return label;
    }
}

