package Entities;

public class User {
    private String name;
    private String password;
    private String host;
    private String hostPassword;
    private String port;
    private String hostUserName;
    private String popHost;
    private String popHostPort;
    private int id;

    public User(String name, String password) {
        this(name);
        this.password = password;
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String host, String hostPassword, String port, String hostUserName, String popHost, String popHostPort, int id) {
        this.name = name;
        this.host = host;
        this.hostPassword = hostPassword;
        this.port = port;
        this.hostUserName = hostUserName;
        this.popHost = popHost;
        this.popHostPort = popHostPort;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostPassword() {
        return hostPassword;
    }

    public void setHostPassword(String hostPassword) {
        this.hostPassword = hostPassword;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHostUserName() {
        return hostUserName;
    }
    public void setHostUserName(String hostUserName) {
        this.hostUserName = hostUserName;
    }

    public String getPopHost() {
        return popHost;
    }

    public void setPopHost(String popHost) {
        this.popHost = popHost;
    }

    public String getPopHostPort() {
        return popHostPort;
    }

    public void setPopHostPort(String popHostPort) {
        this.popHostPort = popHostPort;
    }
}
