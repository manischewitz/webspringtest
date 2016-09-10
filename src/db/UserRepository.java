package db;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import objects.User;

public interface UserRepository {

	@CachePut(value="userCache",key="#result.id")
	User save(User user);
	
	@Cacheable("userCache")
	User findOne(long id);
	
	User findByLogin(String username);
	
	public default long count(){
		return this.findAll().size();
	};
	
	List<User> findAll();
	
	@CacheEvict("userCache")
	boolean delete(Long id);
	
	@CachePut(value="userCache",key="#result.id")
	User update(User user);
	
	
}
