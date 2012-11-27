package org.faboo.test.querydsl;

import com.mysema.query.Tuple;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.types.Expression;
import com.mysema.query.types.MappingProjection;
import com.mysema.query.types.QTuple;
import org.faboo.test.querydsl.generated.beans.eddie2.Player2;
import org.faboo.test.querydsl.generated.qtypes.eddie2.QPlayer2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * First take at QueryDSL, loading players from a database, looking at different ways of constructing domain objects.
 *
 * User: bert
 */
public class ConsoleApp {

    public static void main(String[] args) {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/querydsl", "test", "geheim");
            SQLTemplates dialect = new PostgresTemplates();

            queryWithTuples(connection, dialect);
            queryWithBeanProjection(connection,dialect);
            queryWithMappingProjection(connection,dialect);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Query for players and project (construct) the player from the query result via MappingProjection.
     *
     * @param connection JDBC Connection
     * @param dialect the dialect / template to use on the connection
     */
    private static void queryWithMappingProjection(Connection connection, SQLTemplates dialect) {

        SQLQuery query = new SQLQueryImpl(connection, dialect);

        final QPlayer2 p2 = QPlayer2.player;

        List<Player2> player2s = query.from(p2).where(p2.gameid.eq(2209)).list(
                new Player2MappingProjection(p2.gameid, p2.serverid, p2.name,p2.closed,p2.guildid, p2.upgraded));

        System.out.print(" **** mapped via mapping projection ****\n");
        for(Player2 player : player2s) {
            System.out.print(player + "\n");
        }

    }


    /**
     * Query for players and project (construct) the player from the query result via Bean Projection.
     *
     * The bean to project to must have the same properties as the generated QClass.
     *
     * @param connection JDBC Connection
     * @param dialect the dialect / template to use on the connection
     */
    private static void queryWithBeanProjection(Connection connection, SQLTemplates dialect) {

        SQLQuery query = new SQLQueryImpl(connection, dialect);

        QPlayer2 p = QPlayer2.player;
        List<Player2> players = query.from(p).where(p.gameid.eq(2209)).list(p);

        System.out.print(" **** mapped via bean projection ****\n");
        for(Player2 player : players) {
            System.out.print(player + "\n");
        }
    }

    /**
     * Query for players and project (construct) the player from the query result via Tuples.
     *
     * Tupes are type refactoring save versions of ResultSets.
     *
     * @param connection JDBC Connection
     * @param dialect the dialect / template to use on the connection
     */
    private static void queryWithTuples(Connection connection, SQLTemplates dialect) {

        SQLQuery query = new SQLQueryImpl(connection, dialect);

        QPlayer2 p = new QPlayer2("p");
        List<Tuple> tuples = query.from(p).where(p.gameid.eq(2209))
                .list(new QTuple(p.gameid, p.name, p.serverid, p.guildid));

        for(Tuple t : tuples) {
            System.out.print("gameid=" + t.get(p.gameid) + "\n");
            System.out.print("name=" + t.get(p.name) + "\n");
            System.out.print("serverid=" + t.get(p.serverid) + "\n");
            System.out.print("guildid=" + t.get(p.guildid) + "\n");
            System.out.print("****************************\n");
        }
    }


    /**
     * Mapping class to project from the query result back to the domain object.
     *
     */
    private static class Player2MappingProjection extends MappingProjection<Player2> {


        public Player2MappingProjection(Expression<?>...args) {
            super(Player2.class, args);
        }

        @Override
        protected Player2 map(Tuple row) {

            QPlayer2 p2 = QPlayer2.player;

            Player2 player2 = new Player2();
            player2.setClosed(row.get(p2.closed));
            player2.setGameid(row.get(p2.gameid));
            player2.setGuildid(row.get(p2.guildid));
            player2.setName(row.get(p2.name));
            player2.setServerid(row.get(p2.serverid));
            player2.setUpgraded(row.get(p2.upgraded));
            return player2;
        }
    }
}
