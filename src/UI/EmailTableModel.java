package UI;

import Entities.Email;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmailTableModel extends AbstractTableModel {

    private static final int FROM_COLUMN = 0;
    private static final int SUBJECT_COLUMN = 1;
    private static final int BODY_COLUMN = 2;
    private static final int HOST_COLUMN = 3;


    private String[] colNames = {"De", "Asunto", "Mensaje", "Host"};
    private Class[] colTypes = {String.class, String.class, String.class, String.class};
    private List<Email> content;


    public EmailTableModel() {
        content = new ArrayList<Email>();
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public int getRowCount() {
        return content.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        Email u = content.get(rowIndex);

        Object result = null;
        switch(columnIndex) { //row 0: col from: content[0].from
            case FROM_COLUMN:
                result = u.getFrom();
                break;
            case SUBJECT_COLUMN:
                result = u.getSubject();
                break;
            case BODY_COLUMN:
                result = u.getBody();
                break;
            case HOST_COLUMN:
                result = u.getHost();
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

    public List<Email> getContent() {
        return content;
    }

    public void setContent(List<Email> content) {
        this.content = content;
    }

}
