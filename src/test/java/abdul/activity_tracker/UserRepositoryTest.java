package abdul.activity_tracker;

import abdul.activity_tracker.enums.Gender;
import abdul.activity_tracker.model.User;
import abdul.activity_tracker.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)

public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;
    @Test
    public void testSignup(){
        User user = new User();
        user.setFirstName("Abdulganiyu");
        user.setLastName("Abubakar");
        user.setEmail("Abdul@gmail");
        user.setPassword("abu112233");
        user.setGender(Gender.MALE);

        User savedUser = userRepository.save(user);
        org.assertj.core.api.Assertions.assertThat(savedUser).isNotNull();
        org.assertj.core.api.Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }

}
