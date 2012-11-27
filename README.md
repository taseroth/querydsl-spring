Playground project for queryDSL
-------------------------------

I 'm trying out queryDSL to access SQL databases. It may be using Spring Data in the future.

Things I' m likely to try in here:

* Different projections from the ResultSet back to POJOs
* Batch support
* Data migration / consolidation
* integration with spring data


In general: nothing production ready, but giving queryDSL a spin.

One of my possible use cases might be the data consolidation from different datasources. therefore, the maven plugin
is configured to connect to 2 postgres databases and build querytypes and beans.

The needed tables can be constructed via:

```sql
create table server (
    id serial,
    name varchar(10),
    primary key (id)
);

create table player (
    serverid integer,
    gameid integer,
    name varchar(50),
    closed date,
    guildid integer,
    upgraded date,
    PRIMARY key(serverid,gameid),
    foreign key (serverid) references server(id)
);
```
