package HRM.FinalProject.DTO;


public class RegisterRequest {

    private String email;
    private String username;
    private String password;
    private String mobile_no;

    // Constructors
    public RegisterRequest() {
    }

    public RegisterRequest(String email, String username, String password, String mobile_no) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.mobile_no = mobile_no;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
