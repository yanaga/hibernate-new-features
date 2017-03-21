package me.yanaga.hibernate;

import me.yanaga.domain.model.Address;
import me.yanaga.domain.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BootstrapTest {

    private static SessionFactory sessionFactory;

    @BeforeClass
    public static void setup() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Address.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder()
                .build();
    }

    @Test
    public void testAutomaticAssociationManagement() {
        try (Session session = sessionFactory.openSession()) {
            Person edson = new Person("Edson");
            session.persist(edson);
            Address marieta = new Address("Marieta");
            session.persist(marieta);
            edson.setAddresses(Arrays.asList(marieta));

            marieta.getPerson().getName();
        }
    }

}
