package db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import objects.Message;
import objects.User;
import web.AuthorityManager;
@Profile("hibernate")
@Repository
public class MessagesRepoHibernate implements MessagesRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> findLastMessages(long max, int count) {
		try{
			Session session = sessionFactory.openSession();
			DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
		    dc.add(Restrictions.le("id", max));
		   List<Message> allMessages = (List<Message>) dc.
				   						getExecutableCriteria(session).
				   						setMaxResults(count).
				   						addOrder(Order.desc("id")).
				   						list();
		  session.close();
			return allMessages;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Message findOne(long id) {
		try{
			Session session = sessionFactory.openSession();
			Message message = session.get(Message.class, id);
			session.close();
			return message;
		}catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Message save(Message message) {
		AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		try{
			Session session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Long id = (Long) session.save(new Message(
					message.getMessage(),
					new Date(),
					message.getLatitude(),
					message.getLongitude(),
					message.getUsername()));
			transaction.commit();
			session.close();
			message.setId(id);
			return message;
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(long id) {
		AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		try{
			Session session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Message messageToDelete = new Message();
			messageToDelete.setId(id);
			session.delete(messageToDelete);
			transaction.commit();
			session.close();
			return true;
		}catch(HibernateException he){
			he.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getMessagesForUser(String username)  {
		try{
			Session session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			DetachedCriteria dc = DetachedCriteria.forClass(Message.class);
			dc.add(Restrictions.eq("posted_by", username));
			
			List<Message> userMessages = ((List<Message>) dc.getExecutableCriteria(session).addOrder(Order.desc("id")).list());
			transaction.commit();
			session.close();
			return userMessages;
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public Message updateMessage(Message updatedMessage) {
		AuthorityManager.instance().hasAuthorities(new String[] {"user","root"});
		try{
			Session session = this.sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(updatedMessage);
			transaction.commit();
			session.close();
			return updatedMessage;
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

}
