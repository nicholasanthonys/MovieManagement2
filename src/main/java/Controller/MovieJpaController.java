/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Contoller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Model.Director;
import Model.Movie;
import java.util.ArrayList;
import java.util.List;
import Model.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ASUS
 */
public class MovieJpaController implements Serializable {

    public MovieJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movie movie) {
        if (movie.getDirectorList() == null) {
            movie.setDirectorList(new ArrayList<Director>());
        }
        if (movie.getPersonList() == null) {
            movie.setPersonList(new ArrayList<Person>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Director> attachedDirectorList = new ArrayList<Director>();
            for (Director directorListDirectorToAttach : movie.getDirectorList()) {
                directorListDirectorToAttach = em.getReference(directorListDirectorToAttach.getClass(), directorListDirectorToAttach.getId());
                attachedDirectorList.add(directorListDirectorToAttach);
            }
            movie.setDirectorList(attachedDirectorList);
            List<Person> attachedPersonList = new ArrayList<Person>();
            for (Person personListPersonToAttach : movie.getPersonList()) {
                personListPersonToAttach = em.getReference(personListPersonToAttach.getClass(), personListPersonToAttach.getUsername());
                attachedPersonList.add(personListPersonToAttach);
            }
            movie.setPersonList(attachedPersonList);
            em.persist(movie);
            for (Director directorListDirector : movie.getDirectorList()) {
                directorListDirector.getMovieList().add(movie);
                directorListDirector = em.merge(directorListDirector);
            }
            for (Person personListPerson : movie.getPersonList()) {
                personListPerson.getMovieList().add(movie);
                personListPerson = em.merge(personListPerson);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Movie movie) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movie persistentMovie = em.find(Movie.class, movie.getId());
            List<Director> directorListOld = persistentMovie.getDirectorList();
            List<Director> directorListNew = movie.getDirectorList();
            List<Person> personListOld = persistentMovie.getPersonList();
            List<Person> personListNew = movie.getPersonList();
            List<Director> attachedDirectorListNew = new ArrayList<Director>();
            for (Director directorListNewDirectorToAttach : directorListNew) {
                directorListNewDirectorToAttach = em.getReference(directorListNewDirectorToAttach.getClass(), directorListNewDirectorToAttach.getId());
                attachedDirectorListNew.add(directorListNewDirectorToAttach);
            }
            directorListNew = attachedDirectorListNew;
            movie.setDirectorList(directorListNew);
            List<Person> attachedPersonListNew = new ArrayList<Person>();
            for (Person personListNewPersonToAttach : personListNew) {
                personListNewPersonToAttach = em.getReference(personListNewPersonToAttach.getClass(), personListNewPersonToAttach.getUsername());
                attachedPersonListNew.add(personListNewPersonToAttach);
            }
            personListNew = attachedPersonListNew;
            movie.setPersonList(personListNew);
            movie = em.merge(movie);
            for (Director directorListOldDirector : directorListOld) {
                if (!directorListNew.contains(directorListOldDirector)) {
                    directorListOldDirector.getMovieList().remove(movie);
                    directorListOldDirector = em.merge(directorListOldDirector);
                }
            }
            for (Director directorListNewDirector : directorListNew) {
                if (!directorListOld.contains(directorListNewDirector)) {
                    directorListNewDirector.getMovieList().add(movie);
                    directorListNewDirector = em.merge(directorListNewDirector);
                }
            }
            for (Person personListOldPerson : personListOld) {
                if (!personListNew.contains(personListOldPerson)) {
                    personListOldPerson.getMovieList().remove(movie);
                    personListOldPerson = em.merge(personListOldPerson);
                }
            }
            for (Person personListNewPerson : personListNew) {
                if (!personListOld.contains(personListNewPerson)) {
                    personListNewPerson.getMovieList().add(movie);
                    personListNewPerson = em.merge(personListNewPerson);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movie.getId();
                if (findMovie(id) == null) {
                    throw new NonexistentEntityException("The movie with id " + id + " no longer exists.");
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
            Movie movie;
            try {
                movie = em.getReference(Movie.class, id);
                movie.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movie with id " + id + " no longer exists.", enfe);
            }
            List<Director> directorList = movie.getDirectorList();
            for (Director directorListDirector : directorList) {
                directorListDirector.getMovieList().remove(movie);
                directorListDirector = em.merge(directorListDirector);
            }
            List<Person> personList = movie.getPersonList();
            for (Person personListPerson : personList) {
                personListPerson.getMovieList().remove(movie);
                personListPerson = em.merge(personListPerson);
            }
            em.remove(movie);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Movie> findMovieEntities() {
        return findMovieEntities(true, -1, -1);
    }

    public List<Movie> findMovieEntities(int maxResults, int firstResult) {
        return findMovieEntities(false, maxResults, firstResult);
    }

    private List<Movie> findMovieEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movie.class));
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

    public Movie findMovie(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movie.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovieCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movie> rt = cq.from(Movie.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
