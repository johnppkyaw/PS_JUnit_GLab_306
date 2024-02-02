package com.perscholas.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.perscholas.model.Person;


/**
 * Unit test for simple App.
 */
public class AppTest
{
    SessionFactory factory;
    Session session;


    @BeforeEach
    public void loadUp() {
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        session = factory.openSession();
    }


    @Test
    public void testPersonLookUp() {

        //SessionFactory factory = new Configuration().configure().buildSessionFactory();


        Transaction t = session.beginTransaction();

        //========================
        // GOAL:   Test that a Person Object exists in the database
        // with name="Bill Board"
        //=========================
        String sql = "SELECT name FROM Person WHERE name ='Bill Board'";
        TypedQuery<Object[]> query = session.createQuery(sql,
                Object[].class);
        List<Object[]> results = query.getResultList();
        if(results.isEmpty()){
            assertEquals("no", "rows found");
        }

        for (Object[] a : results) {
            //System.out.printf("%s. %s%n", a[0], a[1]);
            Person testPerson = new Person();
            testPerson.setName("Bill Board");
            assertEquals(a[0].toString(), testPerson.getName());
        }
        //===========================================
        /*String sql = "SELECT name FROM Person WHERE name ='Bill Board'";
        Query query = session.createNativeQuery(sql);
        List<Object[]> person = query.getResultList();
        if(person.isEmpty()){
            assertEquals("no", "rows found");
        }
        Object tmp = person.get(0);
        Person testPerson = new Person();
        testPerson.setName("Bill Board");
        assertEquals(tmp.toString(), testPerson.getName()); */
        t.commit();
        System.out.println("Session Closed");
    }

    @Test
    public void testAddressLookUp() {
        //========================
        // GOAL:   Test that a Address Object exists in the database
        // with city="nyc"
        //=========================
        String sql = "SELECT city FROM Address WHERE city ='nyc'";
        TypedQuery<Object[]> query = session.createQuery(sql,
                Object[].class);
        List<Object[]> results = query.getResultList();
        assertFalse(results.isEmpty(), "The results should not be empty");

        for (Object[] a : results) {
            //System.out.printf("%s. %s%n", a[0], a[1]);
            Address testPerson = new Address();
            testPerson.setCity("nyc");
            assertEquals(a[0].toString(), testPerson.getCity());
        }

    }

    @AfterEach
    void cleanUp() {
        session.close();
        factory.close();
    }
}
