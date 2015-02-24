/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.hibernate.Session;

/**
 *
 * @author dalmir
 */
public abstract class DAOFactory {
    
    public abstract void setSession(Session session);

    public abstract GenericDAO getNewDAO(Class daoClass);
}