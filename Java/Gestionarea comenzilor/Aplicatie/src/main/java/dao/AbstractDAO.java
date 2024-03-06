package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Comanda;

/**
 * Clasa AbstractDAO este oimplementare DAO generic (Data Access Object)
 * care oferă operațiuni de bază CRUD pentru lucrul cu o bază de date.
 * @param <T> Tipul obiectelor gestionate de DAO
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Construiește un obiect AbstractDAO.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**@
     * Metoda construiește și returnează o interogare SELECT bazată pe numele
     * unui câmp.
     * @param field Câmpul utilizat pentru filtrarea rezultatelor.
     * @return Sirul de interogare SELECT.
     * */

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Metoda returnează o listă cu toate obiectele din tabelul
     * corespunzător clasei AbstractDAO.
     *
     * @return O listă cu toate obiectele de tipul T.
     * */

    public List<T> findAll() {
        // TODO:
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * Metoda returnează un obiect din tabelul corespunzător
     * clasei AbstractDAO pe baza unui ID și a numelui coloanei.
     *
     * @param id ID-ul obiectului care trebuie găsit.
     * @param nameC Numele coloanei utilizate pentru filtrare.
     * @return Obiectul de tip T cu ID-ul specificat sau nul dacă null este găsit.
     * */
    public T findById(int id, String nameC) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(nameC);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**@
     * Metoda creează și returnează o listă de obiecte
     * pe baza unui rezultat al interogării.
     *
     * @param resultSet Set rezultat care conține datele din baza de date.
     * @return O listă de obiecte de tip T.
     * */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *  Metoda inserează un obiect în tabelul corespunzător clasei AbstractDAO.
     * @param t Obiectul care trebuie inserat.
     * @return Obiectul inserat de tip T.
     */
    public T insert(T t) {
        // TODO:
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT ");
        sb.append("INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");

        int k=0;
        int campuri=type.getDeclaredFields().length;
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true); //sa le fac public
            String fieldName = field.getName();
            if(k==campuri-1){
                sb.append(fieldName);
            }
            else {
                sb.append(fieldName);
                sb.append(", ");
            }
            k++;
        }
        sb.append(" ) VALUES (");
        k=0;
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true); //sa le fac public
            if(k==campuri-1){
                try {
                    sb.append("'");
                    sb.append(field.get(t));
                    sb.append("'");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    sb.append("'");
                    sb.append(field.get(t));
                    sb.append("', ");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            k++;
        }
        sb.append( ");");
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Metoda care actualizează un obiect în tabelul corespunzător clasei AbstractDAO
     * pe baza unui ID și a numelui coloanei.
     *
     * @param t Obiectul care trebuie actualizat.
     * @param id ID-ul obiectului care trebuie actualizat.
     * @param nameColumn Numele coloanei de utilizat pentru filtrare.
     * @return Obiectul actualizat de tip T.
     * */

    public T update(T t, int id, String nameColumn) {
        // TODO:
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        int k=0;
        int campuri=type.getDeclaredFields().length;
        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true); //sa le fac public
            String fieldName = field.getName();
            if(k==campuri-1){
                sb.append(fieldName);
                sb.append(" = ");
                try {
                    sb.append("'");
                    sb.append(field.get(t));
                    sb.append("'");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                if(k > 0) {
                    sb.append(fieldName);
                    sb.append(" = ");
                    try {
                        sb.append("'");
                        sb.append(field.get(t));
                        sb.append("'");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    sb.append(", ");
                }
            }
            k++;
        }
        sb.append(" WHERE ");
        sb.append(nameColumn);
        sb.append(" = ");
        sb.append("'");
        sb.append(id);
        sb.append("'");
        System.out.println(sb);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     *  Metoda care șterge un obiect din tabelul corespunzător clasei AbstractDAO
     *  pe baza unui ID și a numelui coloanei.
     *  @param t Obiectul care trebuie șters.
     *  @param id ID-ul obiectului care trebuie șters.
     *  @param nameColumn Numele coloanei de utilizat pentru filtrare.
     *  @return Obiectul șters de tip T.
     * */

    public T delete(T t, int id, String nameColumn) {
        // TODO:
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append(nameColumn);
        sb.append(" = ");
        sb.append("'");
        sb.append(id);
        sb.append("'");
                System.out.println(sb);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            statement.executeUpdate();
            return t;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     *  Metoda care cauta id-ul maxim din tabela Comanda.
     *
     *  @param nameColumn Numele coloanei de utilizat pentru filtrare.
     *  @return o valoare int care reprezintă id-ul comenzii.
     * */
    public int maxId(String nameColumn) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append("max(");
        sb.append(nameColumn);
        sb.append(")");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sb.toString());
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:maxId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;

    }

}
