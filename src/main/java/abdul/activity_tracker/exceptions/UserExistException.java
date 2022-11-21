package abdul.activity_tracker.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExistException  extends RuntimeException{
   private String debugMessage;

    public UserExistException(String message) {
        super(message);

    }

    public UserExistException(String message, String debugMessage) {
       super(message);
       this.debugMessage = debugMessage;
    }
}
