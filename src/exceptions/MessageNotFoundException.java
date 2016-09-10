package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="MNF. Message Not Found")
public class MessageNotFoundException extends RuntimeException{

}
