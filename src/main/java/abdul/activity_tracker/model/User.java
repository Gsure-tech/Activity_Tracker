package abdul.activity_tracker.model;

import abdul.activity_tracker.enums.Gender;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length = 30)
    private String firstName;
   @Column(nullable = false, length = 30)
    private String lastName;
   @Column(nullable = false, length = 30)
    private String email;
   @Column(nullable = false, length=10)
    private String password;
    @Column(nullable = false, length = 10)
    private Gender gender;
}
