package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="DM. Duplicate Message")
public class DuplicateMessageException extends RuntimeException{

}
