package com.nju.graduation.project.bas.service;

import com.nju.graduation.project.bas.domain.LandmarkVO;

import java.util.List;

/**
 * @author shanhe
 * @className BusinessService
 * @date 2020-09-23 17:32
 **/
public interface BusinessService {

    List<LandmarkVO> getHotLandmarks(long id, String start, String end);

    List<LandmarkVO> getPotentialLandmarks(long id, String start, String end);

}
