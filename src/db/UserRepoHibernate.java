package db;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


import objects.User;
import web.AuthorityManager;
@Profile("hibernate")
@Repository
public class UserRepoHibernate implements UserRepository {
	
	@Autowired
	private SessionFactory  sessionFactory;
	
	public void setSessionFactory(SessionFactory  sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public long count(){
		return this.findAll().size();
	}
	
	@Override
	public User save(User user) {
		try{
			Session session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			Date date = new Date();
			Long id = (Long) session.save(new User(null, user.getUsername(), user.getPassword(), user.getFirstName(),
					user.getLastName(), user.getEmail(),"user",date));
			transaction.commit();
			session.close();
			user.setRole("user");
			user.setRegistration_date(date);
			user.setId(id);
			
			return user;
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public User findOne(long id) {
		try{
			Session session = sessionFactory.openSession();
			User user = session.get(User.class, id);
			session.close();
			return user;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
			}
	}

	@Override
	public User findByLogin(String username) {
		try{
				Session session = sessionFactory.openSession();
				DetachedCriteria dc = DetachedCriteria.forClass(User.class);
				dc.add(Restrictions.eq("username", username));
				User user = (User) dc.getExecutableCriteria(session).uniqueResult();
				session.close();
				return user;
			}catch(HibernateException e){
				e.printStackTrace();
				return null;
			}
	}
	@SuppressWarnings("unchecked")
	public List<User> findAll(){
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(User.class);
			List<User> userList = dc.getExecutableCriteria(session).list();
			session.close();
			return userList;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(Long id) {
		AuthorityManager.instance().hasAuthorities(new String[] {"root"});
		try{
			Session session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			User userToDelete = new User();
			userToDelete.setId(id);
			session.delete(userToDelete);
			transaction.commit();
			session.close();
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}

	@Override
	public User update(User user) {
		try{
			Session session = this.sessionFactory.openSession();
			session.update(user);
			session.beginTransaction().commit();
			session.close();
			return user;
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}
	
}
