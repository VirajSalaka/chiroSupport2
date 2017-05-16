package com.mycompany.chiroSupport.util;



import java.util.Properties;

import com.mycompany.chiroSupport.employee.Employee;
import com.mycompany.chiroSupport.patientCase.*;
import com.mycompany.chiroSupport.patientCase.objective.*;
import com.mycompany.chiroSupport.patientProfile.Appointment;
import com.mycompany.chiroSupport.patientProfile.Patient;
import com.mycompany.chiroSupport.patientProfile.PatientQueueItem;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    //XML based configuration
    private static SessionFactory sessionFactory;

    //Annotation based configuration
    private static SessionFactory sessionAnnotationFactory;

    //Property based configuration
    private static SessionFactory sessionJavaConfigFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            System.out.println("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Patient.class);
            configuration.addAnnotatedClass(VitalsReport.class);
            configuration.addAnnotatedClass(MedicalHx.class);
            configuration.addAnnotatedClass(PatientQueueItem.class);
            configuration.addAnnotatedClass(PatientCase.class);
            configuration.addAnnotatedClass(Examination.class);
            configuration.addAnnotatedClass(Analysis.class);
            configuration.addAnnotatedClass(Subjective.class);
            configuration.addAnnotatedClass(DiagnosticStudy.class);
            configuration.addAnnotatedClass(MusclePower.class);
            configuration.addAnnotatedClass(NeurologicalStudy.class);
            configuration.addAnnotatedClass(Observation.class);
            configuration.addAnnotatedClass(Palpation.class);
            configuration.addAnnotatedClass(Rom.class);
            configuration.addAnnotatedClass(SpecialTest.class);
            configuration.addAnnotatedClass(Treatment.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Appointment.class);
            configuration.configure("hibernateannotation.cfg.xml");
            System.out.println("Hibernate Annotation Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate Annotation serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionJavaConfigFactory() {
        try {
            Configuration configuration = new Configuration();

            //Create Properties, can be read from property files too
            Properties props = new Properties();
            props.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            props.put("hibernate.connection.url", "jdbc:mysql://localhost/chirosupport");
            props.put("hibernate.connection.username", "root");
            props.put("hibernate.connection.password", "root123");
            props.put("hibernate.current_session_context_class", "thread");

            configuration.setProperties(props);

            //we can set mapping file or class with annotation
            //addClass(Employee1.class) will look for resource
            // com/journaldev/hibernate/model/Employee1.hbm.xml (not good)
            configuration.addAnnotatedClass(Patient.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate Java Config serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

//    public static SessionFactory getSessionFactory() {
//        if(sessionFactory == null) sessionFactory = buildSessionFactory();
//        return sessionFactory;
//    }

    public static SessionFactory getSessionAnnotationFactory() {
        if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }

    public static SessionFactory getSessionJavaConfigFactory() {
        if(sessionJavaConfigFactory == null) sessionJavaConfigFactory = buildSessionJavaConfigFactory();
        return sessionJavaConfigFactory;
    }

}