package dao;

import entidades.Mesa;
import java.util.List;
import javax.persistence.EntityManager;

//Operaciones CRUD de Mesa
public class MesaDAO {

    //Inserta una mesa
    public Mesa insertar(Mesa mes) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            //Se hace merge porque la mesa apunta a un restaurante que ya existe
            Mesa mes1 = em.merge(mes);
            em.getTransaction().commit();
            return mes1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    //Actualiza una mesa
    public Mesa actualizar(Mesa mes) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            Mesa mes1 = em.merge(mes);
            em.getTransaction().commit();
            return mes1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    //Elimina una mesa
    public boolean eliminar(Long id) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Mesa.class, id));
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

    //Busca una mesa por su ID
    public Mesa buscarPorId(Long id) {
        EntityManager em = Conexion.crearEM();
        try {
            return em.find(Mesa.class, id);
        } finally {
            em.close();
        }
    }

    //Regresa todas las mesas
    public List<Mesa> consultarTodos() {
        EntityManager em = Conexion.crearEM();
        try {
            return em.createQuery("SELECT m FROM Mesa m", Mesa.class).getResultList();
        } finally {
            em.close();
        }
    }
}
