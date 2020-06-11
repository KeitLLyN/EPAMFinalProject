package utils.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.dao.interfaces.GenericDao;
import utils.dao.interfaces.Identified;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Абстрактный класс предоставляющий базовую реализацию операций с использованием JDBC.
 *
 * @param <T>  тип объекта
 */
public abstract class JDBCDao<T extends Identified> implements GenericDao<T> {
    private static final Logger LOG = LogManager.getLogger(JDBCDao.class);
    protected Connection connection;

    public JDBCDao(){}
    public JDBCDao(Connection connection){
        this.connection = connection;
    }
    /**
     * Возвращает sql запрос для получения всех записей.
     * <p/>
     * SELECT * FROM [Table]
     */
    protected abstract String getSelectQuery();
    /**
     * Возвращает sql запрос для вставки новой записи в базу данных.
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    protected abstract String getInsertQuery();
    /**
     * Возвращает sql запрос для обновления записи.
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     */
    protected abstract String getUpdateQuery();
    /**
     * Возвращает sql запрос для удаления записи из базы данных.
     * <p/>
     * DELETE FROM [Table] WHERE id= ?;
     */
    protected abstract String getDeleteQuery();

    /**
     * Разбирает ResultSet и возвращает список объектов соответствующих содержимому ResultSet.
     */
    protected abstract List<T> parseResultSet(ResultSet resultSet);
    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     */
    protected abstract void prepareStatementInsert(PreparedStatement statement, T object);
    /**
     * Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
     */
    protected abstract void prepareStatementUpdate(PreparedStatement statement, T object);
    /**
     * Устанавливает аргументы findBy запроса в соответствии со значением полей объекта object.
     */
    protected List<T> prepareStatementFindByParam(String sql, List<T> objects){
        ResultSet result = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            result = statement.executeQuery();
            objects = parseResultSet(result);
        }catch (SQLException e){
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }finally {
            try {
                if (result != null) result.close();
            }catch (SQLException e){
                LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            }
        }
        return objects;
    }

    /**Получение списка элементов по конкретным параметрам
     * @param strings (User)[email, password], (Train)[from,to,date], (Wagon)[train_id - как название колонки, train_id - как значение]
     *
     * @return Список пользователей*/
    @Override
    public abstract List<T> findBy(String ...strings);

    @Override
    public T getByKey(String id, int key) {
        T object = null;
        String sql = getSelectQuery();
        sql += String.format(" where %s = %d",id,key);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            object = parseResultSet(resultSet).get(0);
        } catch (SQLException e) {
            LOG.error("Couldn't connect to database");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        return object;
    }

    @Override
    public void update(T object) {
        String sql = getUpdateQuery();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            prepareStatementUpdate(statement,object);
            statement.executeUpdate();
        }catch (SQLException e){
            LOG.error("Couldn't connect to database");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void delete(T object) {
        String sql = getDeleteQuery();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, object.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            LOG.error("Couldn't connect to database");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public List<T> getAll() {
        ResultSet resultSet = null;
        List<T> list = null;
        String sql = getSelectQuery();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            resultSet = statement.executeQuery();
            list = parseResultSet(resultSet);
        }catch (SQLException e){
            LOG.error("Couldn't parse data");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        finally {
            try {
                if (resultSet != null) resultSet.close();
            }catch (SQLException e){
                LOG.error("Couldn't close result set");
                LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            }
        }
        return list;
    }

    @Override
    public void insert(T object){
        String sql = getInsertQuery();
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            prepareStatementInsert(statement,object);
            statement.executeUpdate();
        }catch (SQLException e){
            LOG.error("Couldn't parse data");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
