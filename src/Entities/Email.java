package Entities;


public class Email {
    private String body;
    private String to;
    private String from;
    private String subject;
    private String host;
    private EmailTypes type;
    private int accountId;
    private int id;

    public Email() { }

    public Email(String body, String to, String from, String subject, String host, EmailTypes type, int accountId, int id){
        this(from, to, subject, body, host, type, accountId);
        this.id = id;
    }

    public Email(String from, String to,String subject, String body, String host, EmailTypes type, int accountId){
        this(to, body, subject, type, accountId);
        this.from = from;
        this.host = host;
    }

    public Email(String to, String body, String subject, EmailTypes type, int accountId){
        this.body = body;
        this.to = to;
        this.subject = subject;
        this.type = type;
        this.accountId = accountId;
    }

    public boolean isEmpty(){
        return this.body == null || this.subject == null || this.to == null;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public EmailTypes getType() {
        return type;
    }
    public void setType(EmailTypes type) {
        this.type = type;
    }
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
