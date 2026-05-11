package aaa;
import java.util.Random;
public class user {
    private String name;
    private String password;
    private String id;
    private boolean status;
    public String createId() {
        StringBuilder sb = new StringBuilder("heima");
        for (int i = 0; i <= 4; i++) {
            Random r = new Random();
            int num = r.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }
    public user(String name, String password, String id, boolean status) {
        this.password = password;
        this.name = name;
        id = createId();
        status = true;
    }
    public user() {
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
