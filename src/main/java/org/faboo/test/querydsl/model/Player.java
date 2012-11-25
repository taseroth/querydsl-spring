package org.faboo.test.querydsl.model;

import java.util.Date;

/**
 * User: br
 */
public class Player {

    private Long gameID;
    private String server;
    private String name;
    private Date upgraded;
    private Long guildID;

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpgraded() {
        return upgraded;
    }

    public void setUpgraded(Date upgraded) {
        this.upgraded = upgraded;
    }

    public Long getGuildID() {
        return guildID;
    }

    public void setGuildID(Long guildID) {
        this.guildID = guildID;
    }

    @Override
    public String toString() {
        return "Player{" +
                "gameID=" + gameID +
                ", server='" + server + '\'' +
                ", name='" + name + '\'' +
                ", upgraded=" + upgraded +
                ", guildID=" + guildID +
                '}';
    }
}
