package db;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import objects.Message;

public interface MessagesRepository {

	List<Message> findLastMessages(long max, int count);
	
	/*
	 * Indicates that Spring should look in a cache for the method’s return value
	before invoking the method. If the value is found, the cached value is returned.
	If not, then the method is invoked and the return value is put in the cache.
*/
	@Cacheable("messageCache")
	Message findOne(long id);
	/*
	 * Indicates that Spring should put the method’s return value in a cache. The
	cache isn’t checked prior to method invocation, and the method is always
	invoked.
*/
	
	@CachePut(value="messageCache",key="#result.id")
	Message save(Message message);
	//Indicates that Spring should evict one or more entries from a cache.

	@CacheEvict("messageCache")
	boolean delete(long id);
	
	List<Message> getMessagesForUser(String username);
	
	@CachePut(value="messageCache",key="#result.id")
	Message updateMessage(Message updatedMessage);
}
