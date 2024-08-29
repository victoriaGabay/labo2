package Entities;

public class Contact {

    public int contactId1;
    public static int contactId2;

    public Contact(int contactId1, int contactId2){
        this.contactId1 = contactId1;
        this.contactId2 = contactId2;
    }

    public int getContactId1() {
        return contactId1;
    }
    public int getContactId2() {
        return contactId2;
    }

}
