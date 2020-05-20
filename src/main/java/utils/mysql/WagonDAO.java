package utils.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.dao.JDBCDao;
import utils.dao.interfaces.JdbcConstants;
import entity.Wagon;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WagonDAO extends JDBCDao<Wagon> implements JdbcConstants {
    private static final Logger LOG = LogManager.getLogger(WagonDAO.class);

    public WagonDAO(){super();}
    public WagonDAO(Connection connection){
        super(connection);
    }

    @Override
    public List<Wagon> findBy(String[] args){
        List<Wagon> wagons = new ArrayList<>();
        LOG.info("trying to get wagons from database");
        String sql = getSelectQuery() + String.format(" where %s = '%s'",args[0],args[1]);
        wagons = prepareStatementFindByParam(sql,wagons);
        return wagons;
    }

    @Override
    protected String getSelectQuery() {
        return "select * from wagons";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into wagons (wagon_id, train_id, number_of_seats, price_for_one_place, service_class) values (?,?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE wagons SET number_of_seats = ?, price_for_one_place = ?, service_class = ?" +
                "WHERE wagon_id = ? values(?,?,?,?)";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from wagons where wagon_id = ? values(?)";
    }

    @Override
    protected List<Wagon> parseResultSet(ResultSet result) {
        List<Wagon> wagons = new ArrayList<>();
        try {
            while (result.next()){
                Wagon wagon = new Wagon();
                wagon.setId(result.getInt("wagon_id"));
                wagon.setPrice(result.getInt("price_for_one_place"));
                wagon.setNumberOfSeats(result.getInt("number_of_seats"));
                wagon.setServiceClass(result.getString("service_class"));
                wagon.setTrainId(result.getInt("train_id"));

                if (wagon.getNumberOfSeats() > 0)
                    wagons.add(wagon);
            }
        } catch (SQLException e) {
            LOG.error("Couldn't parse data");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
        return wagons;
    }

    @Override
    protected void prepareStatementInsert(PreparedStatement statement, Wagon wagon) {
        try {
            statement.setInt(1, wagon.getId());
            statement.setInt(2, wagon.getTrainId());
            statement.setInt(3, wagon.getNumberOfSeats());
            statement.setInt(4, wagon.getPrice());
            statement.setString(5, wagon.getServiceClass());
        } catch (SQLException e) {
            LOG.error("Couldn't prepare statement for insert");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void prepareStatementUpdate(PreparedStatement statement, Wagon wagon) {
        try {
            statement.setInt(1, wagon.getNumberOfSeats());
            statement.setInt(2, wagon.getPrice());
            statement.setString(3, wagon.getServiceClass());
            statement.setInt(4, wagon.getId());
        } catch (SQLException e) {
            LOG.error("Couldn't prepare statement for update");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
