package UI;

import Entities.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ContactsTableModel extends AbstractTableModel {

    private static final int ID_COLUMN = 0;
    private static final int NAME_COLUMN = 1;
    private static final int DELETE_COLUMN = 2;

    private String[] colNames = { "Id de usuario", "Nombre", "Acciones" };
    private Class[] colTypes = { String.class, String.class, String.class };
    private List<User> content;


    public ContactsTableModel() {
        this.content = new ArrayList<>();
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public int getRowCount() {
        return content.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = content.get(rowIndex);

        Object result;
        switch(columnIndex) {
            case ID_COLUMN:
                result = user.getId();
                break;
            case NAME_COLUMN:
                result = user.getName();
                break;
            case DELETE_COLUMN:
                result = "Eliminar Contacto";
                break;
            default:
                result = new String("");
        }

        return result;
    }


    public String getColumnName(int col) {
        return colNames[col];
    }

    public Class getColumnClass(int col) {
        return colTypes[col];
    }

    public List<User> getContent() {
        return content;
    }

    public void setContent(List<User> content) {
        this.content = content;
    }
}
