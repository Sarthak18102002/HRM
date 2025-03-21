
package HRM.FinalProject.UserEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
@Entity
@Table(name = "userdata")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String mobileNo;

    private boolean emailVerified = false;

    // Profile fields
    private String technology;
    private String age;
    private String bloodGroup;
    private String experience;
    private String education;




    public String getTechnology() { return technology; }
    public void setTechnology(String technology) { this.technology = technology; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    // Add other getters/setters as needed for email, password, etc.
}
