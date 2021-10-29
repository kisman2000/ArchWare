package fun.archware.base.altmanager;

/**
 * Created by 1 on 01.04.2021.
 */
public class Alt {

    String name, password;

    public Alt(String name, String password) {
        this.name = name;
        this.password = password;
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

}
