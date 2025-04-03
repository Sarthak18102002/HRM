
package SRMS.FinalProject.DTO;

public class UserDto {

    private Long id;
    private String email;
    private String username;
    private String mobileNo;
    private Boolean emailVerified;
    private String DOB;  // Keeping DOB instead of age
    private String bloodGroup;
    private String technology;
    private String firstName;
    private String middleName;
    private String lastName;

    // Experience Fields
    private String companyName;
    private String startDate;
    private String endDate;
    private String message;

    //Education Fields

    // Constructor
    public UserDto(Long id, String email, String username, String mobileNo, Boolean emailVerified, String DOB,
                   String bloodGroup, String technology, String firstName, String middleName, String lastName) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.mobileNo = mobileNo;
        this.emailVerified = emailVerified;
        this.DOB = DOB;
        this.bloodGroup = bloodGroup;
        this.technology = technology;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.companyName = companyName;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String dob) {
        this.DOB = dob;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
