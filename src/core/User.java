package core;

public class User {
    private String username;
    private String password;
    private boolean firstOrderDone;

    public User(String username, String password, boolean firstOrderDone) {
        this.username = username;
        this.password = password;
        this.firstOrderDone = firstOrderDone;
    }

    public boolean isFirstOrderDone() {
        return firstOrderDone;
    }

    public void setFirstOrderDone(boolean firstOrderDone) {
        this.firstOrderDone = firstOrderDone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
