
package HRM.FinalProject.DTO;
public class UserDto {

    private Long id;
    private String email;
    private String username;
    private String mobileNo;
    private Boolean emailVerified;
    private Integer age;
    private String bloodGroup;
    private String education;
    private String experience;
    private String technology;

    // Constructor
    public UserDto(Long id, String email, String username, String mobileNo,
                   Boolean emailVerified, Integer age, String bloodGroup,
                   String education, String experience, String technology) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.mobileNo = mobileNo;
        this.emailVerified = emailVerified;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.education = education;
        this.experience = experience;
        this.technology = technology;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
