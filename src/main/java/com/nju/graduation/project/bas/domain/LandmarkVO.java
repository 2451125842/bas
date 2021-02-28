package com.nju.graduation.project.bas.domain;

import lombok.Data;

/**
 * @author shanhe
 * @className LandmarkVo
 * @date 2020-09-23 23:01
 **/
@Data
public class LandmarkVO {
    private String landmarkName;
    private int orderNum;
    private int avgOrderNum;
    private int variation;
}
