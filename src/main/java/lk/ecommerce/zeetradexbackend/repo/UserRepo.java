package lk.ecommerce.zeetradexbackend.repo;

import lk.ecommerce.zeetradexbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//DAO impl= Using this repository user can add update remove will our manage data in database
public interface UserRepo extends JpaRepository<User, Long> {
    // mona repository tada me class eka hadanne
    //user kiyana User repo eka mona class ekatada use karanne and e class eke unique identifier
    User findByEmail(String email);
}
