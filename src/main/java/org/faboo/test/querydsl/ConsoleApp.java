package org.faboo.test.querydsl;

import com.mysema.query.Tuple;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLQueryImpl;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.types.QTuple;
import org.faboo.test.querydsl.generated.beans.eddie2.Player2;
import org.faboo.test.querydsl.generated.qtypes.eddie2.QPlayer2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * User: br
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

    private static void queryWithBeanProjection(Connection connection, SQLTemplates dialect) {
        SQLQuery query = new SQLQueryImpl(connection, dialect);

        QPlayer2 p = QPlayer2.player;
        List<Player2> players = query.from(p).where(p.gameid.eq(2209)).list(p);

        for(Player2 player : players) {
            System.out.print(player);

        }
    }

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
}
