package fall2018.csc2017.slidingtiles;

/**
 * The account for the sliding puzzle tile game.
 * Each account contains the username, password.
 */
public class Account {
    /**
     * Account's username and password.
     */
    private String name, pass;

    /**
     * Set username for the account.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Return username for the account.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set password for the account.
     */
    void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return Return password for the account.
     */
    String getPass() {
        return this.pass;
    }


}
