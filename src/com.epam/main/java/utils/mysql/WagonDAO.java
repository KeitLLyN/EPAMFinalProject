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
    public List<Wagon> findBy(String ... strings){
        List<Wagon> wagons = new ArrayList<>();
        LOG.info("trying to get wagons from DB via findBy");
        String sql = getSelectQuery() + String.format(" where %s = '%s'",strings[0],strings[1]);
        wagons = prepareStatementFindByParam(sql,wagons);
        return wagons;
    }

    @Override
    protected String getSelectQuery() {
        return "select * from wagons";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into wagons (train_id, number_of_seats, price_for_one_place, service_class) values (?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE wagons SET number_of_seats = ?, price_for_one_place = ?, service_class = ? " +
                "WHERE wagon_id = ?";
    }

    @Override
    protected String getDeleteQuery() {
        return "delete from wagons where wagon_id = ?";
    }

    @Override
    protected List<Wagon> parseResultSet(ResultSet result) {
        LOG.info("trying to parse result set");
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
        LOG.info("trying to set prepare statement for INSERT method");
        try {
            statement.setInt(1, wagon.getTrainId());
            statement.setInt(2, wagon.getNumberOfSeats());
            statement.setInt(3, wagon.getPrice());
            statement.setString(4, wagon.getServiceClass());
        } catch (SQLException e) {
            LOG.error("Couldn't prepare statement for INSERT");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    protected void prepareStatementUpdate(PreparedStatement statement, Wagon wagon) {
        LOG.info("trying to set prepare statement for UPDATE method");
        try {
            statement.setInt(1, wagon.getNumberOfSeats());
            statement.setInt(2, wagon.getPrice());
            statement.setString(3, wagon.getServiceClass());
            statement.setInt(5, wagon.getId());
        } catch (SQLException e) {
            LOG.error("Couldn't prepare statement for UPDATE");
            LOG.error(e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}
