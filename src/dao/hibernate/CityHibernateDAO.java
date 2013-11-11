package dao.hibernate;

import dao.CityDAO;
import entity.City;

public class CityHibernateDAO
        extends GenericHibernateDAO<City, Long>
        implements CityDAO {
}