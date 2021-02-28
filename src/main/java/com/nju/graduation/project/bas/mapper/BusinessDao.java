package com.nju.graduation.project.bas.mapper;

import com.nju.graduation.project.bas.domain.LandmarkPO;
import com.nju.graduation.project.bas.domain.TradingAreaEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shanhe
 * @className BusinessDao
 * @date 2020-09-23 21:28
 **/
public interface BusinessDao {

    String POI_LANDMARK_TABLE = "biz_analysis_poi_landmark_dis ";//门店地标分布表
    String LANDMARK_TABLE = "biz_analysis_landmark_avg ";//地标商圈均值表

    String LANDMARK_FULL_COLUMN = "id, poi_id, city, aor_id, second_type_id, landmark_name, order_num, avg_order_num, date ";
    String HOT_LANDMARK_COLUMN = "landmark_name, sum(order_num) as order_num, sum(avg_order_num) as avg_order_num ";
    String LANDMARK_AVG_COLUMN = "landmark_name, sum(avg_order_num) as avg_order_num";
    String TRADING_AREA_COLUMN = "city, aor_id, second_type_id";

    @Select("SELECT " + POI_LANDMARK_TABLE +
            "FROM " + HOT_LANDMARK_COLUMN +
            "WHERE poi_id = #{poiId} AND date BETWEEN #{start} AND #{end} " +
            "GROUP BY city, aor_id, second_type_id, landmark_name " +
            "ORDER BY order_num")
    List<LandmarkPO> getLandmarkByIdAndDate(@Param("poiId") long poiId,
                                            @Param("start") String start,
                                            @Param("end") String end);

    @Select("SELECT " + LANDMARK_TABLE +
            "FROM " + TRADING_AREA_COLUMN +
            "WHERE poi_id = #{poiId} AND date = #{date} ")
    TradingAreaEntity getTradingAreaById(@Param("poiId") long poiId,
                                         @Param("date") String date);

    @Select("SELECT " + LANDMARK_TABLE +
            "FROM " + LANDMARK_AVG_COLUMN +
            "WHERE city=#{tradingArea.city} AND aor_id=#{tradingArea.aor_id} AND second_type_id=#{tradingArea.second_type_id} AND date BETWEEN #{start} AND #{end} " +
            "GROUP BY city, aor_id, second_type_id, landmark_name ")
    List<LandmarkPO> getLandmarkAvgNumByTradingAreaAndDate(@Param("tradingArea") TradingAreaEntity tradingAreaEntity,
                                                           @Param("start") String start,
                                                           @Param("end") String end);

}
