package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//Crea la conexion con la base de datos
public class Conexion {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("connectionPU");

    public static EntityManager crearEM() {
        return emf.createEntityManager();
    }
}
