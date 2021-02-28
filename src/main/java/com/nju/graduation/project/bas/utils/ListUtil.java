package com.nju.graduation.project.bas.utils;

import com.nju.graduation.project.bas.domain.LandmarkPO;

import java.util.*;

/**
 * @author shanhe
 * @className ListUtils
 * @date 2020-09-23 17:55
 **/
public class ListUtil {

    public static final Comparator potentialLandmarkComparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            LandmarkPO po1 = (LandmarkPO) o1, po2 = (LandmarkPO) o2;
            int comparator1 = po1.getAvg_order_num()-po1.getOrder_num(), comparator2 = po2.getAvg_order_num()-po2.getOrder_num();
            return comparator1-comparator2;
        }
    };

    public static boolean isEmpty(List list) {
        return null == list || list.size() == 0;
    }

    public static  ArrayList newArrayList() {
        return new ArrayList();
    }

    public static Map<String, LandmarkPO> parseHashMap(List<LandmarkPO> poiData) {
        HashMap<String, LandmarkPO> res = new HashMap<>();
        for (LandmarkPO po :
                poiData) {
            res.put(po.getLandmark_name(), po);
        }
        return res;
    }
}
