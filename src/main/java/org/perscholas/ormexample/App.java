package org.perscholas.ormexample;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.perscholas.ormexample.models.Department;
import org.perscholas.ormexample.models.Employee;

import jakarta.persistence.TypedQuery;

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
    		conf.addAnnotatedClass(Department.class);
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
    		Employee e2 = new Employee("tim", new Date(),"password");
    		Department d = new Department(1,"marketing");
    		Department d2 = new Department(2,"IT");

    		// persist mode
    		session.persist(d);
    		session.persist(d2);
    		session.persist(e);
    		session.persist(e2);
    		
    		
    		
    		tx.commit();
    		System.out.println("-----");
    		System.out.println(tx.getStatus());
    		System.out.println("-----");

    		tx = session.beginTransaction();
    		e.addDepartment(d2);
    		session.merge(e);
    		tx.commit();
    		System.out.println("-----");
    		System.out.println(tx.getStatus());
    		System.out.println("-----");
    		List<Object[]> obj = session.createQuery("select name from Employee", Object[].class).getResultList();
    		
    		
    		List<Employee> list = session.createQuery("from Employee", Employee.class)
    				.getResultList();
    		
    		
    		Query<Employee> q = session
    				.createQuery("from Employee where id = :userId", Employee.class)
    				.setParameter("userId", 1001);
    		List<Employee> eeee = q.getResultList();
    		
    		
    		
    		System.out.println(list);
    		// detached mode 
    		Employee e3 =session.createNamedQuery("getById", Employee.class)
    				.setParameter("id", 1)
    				.getSingleResult();
    		System.out.println(e3);
    		
    	
    		
       		
    		
    		
    		
    	} catch (HibernateException ex) {
    		
    		ex.printStackTrace();
    		tx.rollback();
    		
    	} finally {
    		session.close();

    	}
    	
    	
        
    }
}
