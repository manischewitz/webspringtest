package exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.CONFLICT, reason="AD. Access denied.")
public class AccessDeniedException extends RuntimeException{
	


}
