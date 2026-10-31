package dao;

import entidades.Cliente;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author HP
 */
//Operaciones CRUD de Cliente y ClientePremium
public class ClienteDAO {

    //Inserta un cliente
    public Cliente insertar(Cliente cl) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            em.persist(cl);
            em.getTransaction().commit();
            return cl;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    //Actualiza un cliente
    public Cliente actualizar(Cliente cl) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            Cliente cl1 = em.merge(cl);
            em.getTransaction().commit();
            return cl1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    //El cliente se marca como inactivo
    public boolean eliminar(Long id) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            em.find(Cliente.class, id).setActivo(false);
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

    //Busca un cliente por su ID
    public Cliente buscarPorId(Long id) {
        EntityManager em = Conexion.crearEM();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    //Regresa solo los clientes activos
    public List<Cliente> consultarTodos() {
        EntityManager em = Conexion.crearEM();
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.activo = true", Cliente.class).getResultList();
        } finally {
            em.close();
        }
    }
}
