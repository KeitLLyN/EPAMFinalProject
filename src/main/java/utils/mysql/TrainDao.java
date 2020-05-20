package utils.mysql;

import entity.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.dao.JDBCDao;
import utils.dao.interfaces.Identified;
import utils.dao.interfaces.JdbcConstants;
import entity.Wagon;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TrainDao extends JDBCDao<Train> implements JdbcConstants {
    private static final Logger LOG = LogManager.getLogger(UserDao.class);

    public TrainDao(){
        super();
    }
    public TrainDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Train> findBy(String[] args) {
        List<Train> trains = new ArrayList<>();
        LOG.info("trying to connect to database");
        String sql = getSelectQueryByDate(args[0], args[1], args[2]);
        trains = prepareStatementFindByParam(sql,trains);
        trains.sort(Comparator.comparing(Train::getStartTime));
        return trains;
    }

    protected String getSelectQueryByDate(String from, String to, String date) {
        return String.format("SELECT * FROM project.trains WHERE from_country = '%s'" +
                " and to_country = '%s' and date_ = '%s'", from, to, date);
    }

    @Override
    protected String getSelectQuery() {
        return "select * from trains";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into trains (from_country, to_country, date_, start_time, finish_time) values (?,?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE trains SET from_country = ?, to_country = ?, date_ = ?" +
                "start_time = ?, finish_time = ? WHERE train_id = ? values(?,?,?,?,?,?)";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from trains where train_id = ? values(?)";
    }

    @Override
    protected List<Train> parseResultSet(ResultSet result) {
        List<Train> trains = new ArrayList<>();
        try {
            while (result.next()){
                Train train = new Train();
                train.setId(result.getInt("train_id"));
                train.setFrom(result.getString("from_country"));
                train.setTo(result.getString("to_country"));
                train.setStartTime(result.getString("start_time"));
                train.setFinishTime(result.getString("finish_time"));
                train.setDate(result.getString("date_"));
                List<Wagon> wagons = new WagonDAO(connection).findBy(new String[]{"train_id", String.valueOf(train.getId())});
                if (wagons.size() > 0) {
                    train.setWagons(wagons);
                    train.makeMapsOfData();
                    trains.add(train);
                }
            }
        } catch (SQLException e) {
            LOG.error("Couldn't parse data");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        return trains;
    }

    @Override
    protected void prepareStatementInsert(PreparedStatement statement, Train train) {
        try {
            statement.setString(1, train.getFrom());
            statement.setString(2, train.getTo());
            statement.setString(3, train.getDate());
            statement.setString(4, train.getStartTime());
            statement.setString(5, train.getFinishTime());
        } catch (SQLException e) {
            LOG.error("Couldn't prepare statement for insert");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void prepareStatementUpdate(PreparedStatement statement, Train train) {
        try {
            statement.setString(1, train.getFrom());
            statement.setString(2, train.getTo());
            statement.setString(3, train.getDate());
            statement.setString(4, train.getStartTime());
            statement.setString(5, train.getFinishTime());
            statement.setInt(6, train.getId());
        } catch (SQLException e) {
            LOG.error("Couldn't prepare statement for update");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
