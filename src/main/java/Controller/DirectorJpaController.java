/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Contoller.exceptions.NonexistentEntityException;
import Model.Director;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ASUS
 */
public class DirectorJpaController implements Serializable {

    public DirectorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Director director) {
        if (director.getMovieList() == null) {
            director.setMovieList(new ArrayList<Movie>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Movie> attachedMovieList = new ArrayList<Movie>();
            for (Movie movieListMovieToAttach : director.getMovieList()) {
                movieListMovieToAttach = em.getReference(movieListMovieToAttach.getClass(), movieListMovieToAttach.getId());
                attachedMovieList.add(movieListMovieToAttach);
            }
            director.setMovieList(attachedMovieList);
            em.persist(director);
            for (Movie movieListMovie : director.getMovieList()) {
                movieListMovie.getDirectorList().add(director);
                movieListMovie = em.merge(movieListMovie);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Director director) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Director persistentDirector = em.find(Director.class, director.getId());
            List<Movie> movieListOld = persistentDirector.getMovieList();
            List<Movie> movieListNew = director.getMovieList();
            List<Movie> attachedMovieListNew = new ArrayList<Movie>();
            for (Movie movieListNewMovieToAttach : movieListNew) {
                movieListNewMovieToAttach = em.getReference(movieListNewMovieToAttach.getClass(), movieListNewMovieToAttach.getId());
                attachedMovieListNew.add(movieListNewMovieToAttach);
            }
            movieListNew = attachedMovieListNew;
            director.setMovieList(movieListNew);
            director = em.merge(director);
            for (Movie movieListOldMovie : movieListOld) {
                if (!movieListNew.contains(movieListOldMovie)) {
                    movieListOldMovie.getDirectorList().remove(director);
                    movieListOldMovie = em.merge(movieListOldMovie);
                }
            }
            for (Movie movieListNewMovie : movieListNew) {
                if (!movieListOld.contains(movieListNewMovie)) {
                    movieListNewMovie.getDirectorList().add(director);
                    movieListNewMovie = em.merge(movieListNewMovie);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = director.getId();
                if (findDirector(id) == null) {
                    throw new NonexistentEntityException("The director with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Director director;
            try {
                director = em.getReference(Director.class, id);
                director.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The director with id " + id + " no longer exists.", enfe);
            }
            List<Movie> movieList = director.getMovieList();
            for (Movie movieListMovie : movieList) {
                movieListMovie.getDirectorList().remove(director);
                movieListMovie = em.merge(movieListMovie);
            }
            em.remove(director);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Director> findDirectorEntities() {
        return findDirectorEntities(true, -1, -1);
    }

    public List<Director> findDirectorEntities(int maxResults, int firstResult) {
        return findDirectorEntities(false, maxResults, firstResult);
    }

    private List<Director> findDirectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Director.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Director findDirector(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Director.class, id);
        } finally {
            em.close();
        }
    }

    public int getDirectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Director> rt = cq.from(Director.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
