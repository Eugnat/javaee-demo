package main.java;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;


@Stateful
public class HelloEJBBean {

    @PersistenceContext
    private EntityManager em;


    public String setUpperCase(String name) {

        HelloEntity entity = new HelloEntity();

        entity.setName(name);

        em.persist(entity);

        return name.toUpperCase();
    }

}
