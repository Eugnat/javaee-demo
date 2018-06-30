package main.java;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Logger;


@Stateful
public class HelloEJBBean {

    private static final Logger logger = Logger.getLogger("main.java.HelloEJBBean");

    @PersistenceContext
    private EntityManager em;

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/MyQueue")
    private Queue queue;

    @Inject
    private MessageBean messageBean;


    public String setUpperCase(String name) {

        HelloEntity entity = new HelloEntity();

        entity.setName(name);

        em.persist(entity);

        addMessage(name);

        return name.toUpperCase();
    }

    private void addMessage(String name) {

        try (JMSContext context = connectionFactory.createContext();)
        {
            context.createProducer().send(queue, name);
            logger.info("Added message to the queue: " + name);
        }
        catch (JMSRuntimeException e)
        {
            logger.warning("JMSRunTimeException: " + e.getMessage());
        }

    }

    public void readMessage() {

        try (JMSContext context = connectionFactory.createContext();)
        {
            Message message = context.createConsumer(queue).receive(5000);

            if (message != null && message instanceof TextMessage) {
                messageBean.setMessage(message.getBody(String.class));
                logger.info("Read message from the queue: " + message.getBody(String.class));
            }

        }
        catch (JMSException e)
        {
            logger.warning("JMSException: " + e.getMessage());
        }
    }

}
