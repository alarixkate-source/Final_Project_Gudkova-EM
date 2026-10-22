package models;

public class SignupRequest {
    private String email;
    private String password;
    private String submitPassword;

    public SignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.submitPassword = password;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSubmitPassword() { return submitPassword; }
    public void setSubmitPassword(String submitPassword) { this.submitPassword = submitPassword; }
}