
package HRM.FinalProject.Repository;

import HRM.FinalProject.UserEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

package HRM.FinalProject.Repository
import HRM.FinalProject.UserEntity.User;

import HRM.FinalProject.UserEntity.Otp;
import HRM.FinalProject.UserEntity.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findByEmailVerified(Boolean emailVerified);
}

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    Optional<User> findByMobileNo(String mobileNo);
}
    Optional<User> findBymobileNo(String mobileNo);
}



