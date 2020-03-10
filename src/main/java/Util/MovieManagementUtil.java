/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author NICHOLAS ANTHONY SUHARTONO 1118049
 */
public class MovieManagementUtil {
    private static final EntityManagerFactory emf;
    
    static {
        emf = Persistence.createEntityManagerFactory("MovieManagementPersistence");
    }
    
    private MovieManagementUtil(){};
    
    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
    
    
}
