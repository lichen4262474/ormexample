package org.perscholas.ormexample;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.perscholas.ormexample.models.Employee;

/**
 * Hello world!
 *
 */
public class App {
	
	private static ServiceRegistry registry;
	private static SessionFactory factory;
	
    public static void main( String[] args ){
    	
    	try {
    		
    		// configuration
    			Configuration conf = new Configuration().
    					configure(new File
    							("src/main/java/org/perscholas/ormexample/hibernate.cfg.xml"));
    		conf.addAnnotatedClass(Employee.class);
    		// registry
    		registry = new StandardServiceRegistryBuilder()
    				.applySettings(conf.getProperties())
    				.build();
    		
    		factory = conf.buildSessionFactory(registry);
    		
    	} catch (Throwable ex) {
    		ex.printStackTrace();
    	}
    	
    
    	Transaction tx = null;
    	Session session = factory.openSession();
    	try{
    		
    		tx = session.beginTransaction();
    		// transient mode
    		Employee e = new Employee("jafer", new Date(),"password");
    		Employee e2 = new Employee("jafer", new Date(),"password");

    		// persist mode
    		session.persist(e);
    		session.persist(e2);
    		
    		tx.commit();
    		
    		
    		List<Employee> list = session.createQuery("from Employee", Employee.class).getResultList();
    		System.out.println(list);
    		// detached mode 
    		
    		
       		
    		
    		
    		
    	} catch (HibernateException ex) {
    		
    		ex.printStackTrace();
    		tx.rollback();
    		
    	} finally {
    		session.close();

    	}
    	
    	
        
    }
}
