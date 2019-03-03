/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cviko2_u2;

import java.util.List;
import java.lang.Object;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.sdo.helper.extension.SDOUtil;

/**
 *
 * @author Eduardo Martinez
 */
public class Cviko2_U2 {
    static private EntityManager em;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("P1");

        Cviko2_U2 tmp = new Cviko2_U2(emf);
        tmp.showAllPersons(em);     //NativeQuery
        tmp.showAllPersonsJPQL(em); //JPQL
        
        System.out.println(addPerson("Edinko",80f));
        
        Person foundPerson = findPerson(Long.valueOf(151));
        if(foundPerson == null){
            System.out.println("Person with this ID wasnt found");
        }else{
            System.out.println(foundPerson.toString());
        }
    }
    
    static void showAllPersons(EntityManager em){
        Query q = em.createNativeQuery("SELECT o.id, o.meno, o.narodena, o.vaha FROM T_Osoba o", Person.class);
        List<Person> people = q.getResultList();

        for (Person person : people) {
            System.out.println(person.toString());
        }
    }

    static void showAllPersonsJPQL(EntityManager em){
        List<Person> q = em.createQuery("SELECT o FROM Person o").getResultList();

        for (Person person : q) {
            System.out.println(person.toString());
        }
    }
    
    static Long addPerson(String meno,Float vaha){
        Person person1 = new Person(meno,vaha);
        em.getTransaction().begin();
        em.persist(person1);
        em.flush();
        Long id = person1.getId();
        em.getTransaction().commit();
        
        return id;
    }
    
    static Person findPerson(Long id){
        Person person = (Person) em.find(Person.class, id);
        if (person == null){
            return null;
        }else{
            return person;
        }
    }
    public Cviko2_U2(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }
    
    
}
