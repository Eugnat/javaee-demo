package main.java;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class Hello implements Serializable {

    @EJB
    private HelloEJBBean helloEJBBean;

    private String name;
    private int number;

    public Hello() {

        this.name = "Eugene";

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {

        helloEJBBean.readMessage();

        return name;
    }

    public void setName(String name) {

        String uppercaseName = helloEJBBean.setUpperCase(name);
        this.name = uppercaseName;
    }

}
