package ru.coddvrn.Application.Repository;

public class Query {
    public String getObjectQuery() {
        return "SELECT o.name_ AS state, car_brand.cb_name_ AS brand, o.year_release_ AS year_," +
                "car_type_.name_ AS type, SUBSTRING (100 + EXTRACT (DAY FROM o.last_time_) FROM 2 FOR 2)||'.'" +
                "   || SUBSTRING (100 + EXTRACT(MONTH FROM o.last_time_) FROM 2 FOR 2)||'.'" +
                "   || EXTRACT (YEAR FROM o.last_time_)||' '" +
                "   || SUBSTRING (100 + EXTRACT (HOUR FROM o.last_time_)FROM 2 FOR 2)||':'" +
                "   || SUBSTRING (100 + EXTRACT(MINUTE FROM o.last_time_) FROM 2 FOR 2)||':'" +
                "   || SUBSTRING (100 + EXTRACT(SECOND FROM o.last_time_) FROM 2 FOR 2) AS lastTime, " +
                "o.last_speed_ AS lastspeed, routs.name_ AS rout, " +
                "   SUBSTRING (100 + EXTRACT (DAY FROM  o.last_station_time_) FROM 2 FOR 2)||'.'" +
                "   || SUBSTRING (100 + EXTRACT(MONTH FROM  o.last_station_time_) FROM 2 FOR 2)||'.'" +
                "   || EXTRACT (YEAR FROM  o.last_station_time_)||' '" +
                "   || SUBSTRING (100 + EXTRACT (HOUR FROM  o.last_station_time_)FROM 2 FOR 2)||':'" +
                "   || SUBSTRING (100 + EXTRACT(MINUTE FROM  o.last_station_time_) FROM 2 FOR 2)||':'" +
                "   || SUBSTRING (100 + EXTRACT(SECOND FROM  o.last_station_time_) FROM 2 FOR 2) AS lastTimeStation, " +
                "p.name_ AS carrier, providers.name_ AS installer, " +
                "   SUBSTRING (100 + EXTRACT (DAY FROM  o.date_inserted_) FROM 2 FOR 2)||'.'" +
                "   || SUBSTRING (100 + EXTRACT(MONTH FROM  o.date_inserted_) FROM 2 FOR 2)||'.'" +
                "   || EXTRACT (YEAR FROM  o.date_inserted_) AS registrDate," +
                "       CASE o.obj_output_" +
                "         WHEN 1 THEN 'Выведен'" +
                "         ||' ('" +
                "         ||EXTRACT (DAY FROM o.obj_output_date_)|| '.'" +
                "         ||EXTRACT(MONTH FROM o.obj_output_date_)||'.'" +
                "         ||EXTRACT(year from o.obj_output_date_)||')'" +
                "         WHEN 0 THEN 'Активен'" +
                "       END AS status," +
                "       o.phone_ AS phone, o.user_comment_ AS comment " +
                "FROM objects o " +
                "LEFT JOIN car_brand ON o.car_brand_ = car_brand.cb_id_ " +
                "LEFT JOIN car_type_ ON o.vehicle_type_ = car_type_.ct_id_ " +
                "LEFT JOIN routs ON o.last_rout_ = routs.id_ " +
                "LEFT JOIN projects p ON o.proj_id_ = p.id_ " +
                "LEFT JOIN providers ON o.provider_ = providers.id_ ";
    }
}
