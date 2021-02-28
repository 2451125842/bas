package com.nju.graduation.project.bas.web.controller;

import com.nju.graduation.project.bas.domain.LandmarkVO;
import com.nju.graduation.project.bas.domain.eu.LandmarkReturnType;
import com.nju.graduation.project.bas.service.BusinessService;
import com.nju.graduation.project.bas.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author shanhe
 * @className BusinessController
 * @date 2020-09-22 11:10
 **/
@Controller
@RequestMapping("/business")
public class BusinessController {
    private static final Logger LOG = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessService businessService;


    /**
     * 根据门店Id，需要计算的时间长度，返回参数
     * @param poiId 门店id
     * @param returnType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/landmark", method = RequestMethod.POST)
    public Object landmarkDis(long poiId, int returnType, String start, String end) {
        //参数校验
        if (poiId < 0 ||
                LandmarkReturnType.getPotentialLandmarksByValue(returnType) == null) {
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.ILLEGAL_PARAMS, null);
        }

        //功能实现
        try {
            List<LandmarkVO> data;
            if (LandmarkReturnType.HOT_LANDMARKS.getValue() == returnType) {
                data = businessService.getHotLandmarks(poiId, start, end);
            } else {
                data = businessService.getPotentialLandmarks(poiId, start, end);
            }
            if (data == null) {
                return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
            }
            return CommonUtils.getResponse(CommonUtils.SUCCESS_CODE, CommonUtils.SUCCESS_MESSAGE, data);
        } catch (Exception e) {
            LOG.error("BusinessController.landmarkDis error, poiId={}, returnType={}, start={}, end={}", poiId, returnType, start, end, e);
            return CommonUtils.getResponse(CommonUtils.FAIL_CODE, CommonUtils.SYSTEM_ERROR, null);
        }

    }

}
