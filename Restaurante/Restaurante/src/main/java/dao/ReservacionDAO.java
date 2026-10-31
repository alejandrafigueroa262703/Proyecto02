package dao;

import entidades.Reservacion;
import java.util.List;
import javax.persistence.EntityManager;

//Operaciones CRUD de Reservacion
public class ReservacionDAO {

    //Inserta una reservacion
    public Reservacion insertar(Reservacion rsv) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            //Se hace merge porque la reservacion apunta a una mesa y un cliente que ya existen
            Reservacion rsv1 = em.merge(rsv);
            em.getTransaction().commit();
            return rsv1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    //Actualiza una reservacion
    public Reservacion actualizar(Reservacion rsv) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            Reservacion rsv1 = em.merge(rsv);
            em.getTransaction().commit();
            return rsv1;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            em.close();
        }
    }

    //Elimina una reservacion
    public boolean eliminar(Long id) {
        EntityManager em = Conexion.crearEM();
        try {
            em.getTransaction().begin();
            em.remove(em.find(Reservacion.class, id));
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

    //Busca una reservacion por su ID
    public Reservacion buscarPorId(Long id) {
        EntityManager em = Conexion.crearEM();
        try {
            return em.find(Reservacion.class, id);
        } finally {
            em.close();
        }
    }

    //Regresa todas las reservaciones
    public List<Reservacion> consultarTodos() {
        EntityManager em = Conexion.crearEM();
        try {
            return em.createQuery("SELECT r FROM Reservacion r", Reservacion.class).getResultList();
        } finally {
            em.close();
        }
    }
}
