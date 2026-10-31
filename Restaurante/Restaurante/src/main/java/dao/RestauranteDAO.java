package dao;

import entidades.Restaurante;
import java.util.List;
import javax.persistence.EntityManager;

//Operaciones CRUD de Restaurante
public class RestauranteDAO {

    //Inserta un restaurante
    public Restaurante insertar(Restaurante res) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            em.persist(res);
            em.getTransaction().commit();
            return res;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    //Actualiza un restaurante
    public Restaurante actualizar(Restaurante res) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            Restaurante res1 = em.merge(res);
            em.getTransaction().commit();
            return res1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    //Elimina un restaurante en cascada, su direccion y sus mesas
    public boolean eliminar(Long id) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Restaurante.class, id));
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    //Busca un restaurante por su ID
    public Restaurante buscarPorId(Long id) {
        EntityManager em = Conexion.crearEM();
        try {
            return em.find(Restaurante.class, id);
        } finally {
            em.close();
        }
    }

    //Regresa todos los restaurantes
    public List<Restaurante> consultarTodos() {
        EntityManager em = Conexion.crearEM();
        try {
            return em.createQuery("SELECT r FROM Restaurante r", Restaurante.class).getResultList();
        } finally {
            em.close();
        }
    }
}
