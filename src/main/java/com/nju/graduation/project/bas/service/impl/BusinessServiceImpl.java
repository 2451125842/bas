package com.nju.graduation.project.bas.service.impl;

import com.nju.graduation.project.bas.mapper.BusinessDao;
import com.nju.graduation.project.bas.domain.LandmarkPO;
import com.nju.graduation.project.bas.domain.LandmarkVO;
import com.nju.graduation.project.bas.domain.TradingAreaEntity;
import com.nju.graduation.project.bas.service.BusinessService;
import com.nju.graduation.project.bas.utils.DateUtils;
import com.nju.graduation.project.bas.utils.ListUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author shanhe
 * @className BusinessServiceImpl
 * @date 2020-09-23 17:37
 **/
@Service
public class BusinessServiceImpl implements BusinessService {

    private static final Logger LOG = LoggerFactory.getLogger(BusinessServiceImpl.class);
    private static final int DISPLAY_NUM = 10;

    @Autowired
    private BusinessDao businessDao;

    @Override
    public List<LandmarkVO> getHotLandmarks(long poiId, String start, String end) {
        //获取时间
        String[] date, preDate;
        try {
            date = DateUtils.formateDate(start, end);
            preDate = DateUtils.getFormatePreDate(start, end);

            //获取本期数据与对比数据
            List<LandmarkPO> data = businessDao.getLandmarkByIdAndDate(poiId, date[0], date[1]);
            List<LandmarkPO> preData = businessDao.getLandmarkByIdAndDate(poiId, preDate[0], preDate[1]);

            //数据处理，生成VO
            return generateLandmarkVO(
                        data.subList(0, DISPLAY_NUM),
                        preData.subList(0, DISPLAY_NUM)
                   );

        } catch (Exception e) {
            LOG.error("BusinessServiceImpl.getHotLandmarks error, id={}, start={}, end={}", poiId, start, end, e);
            return null;
        }

    }

    private List<LandmarkVO> generateLandmarkVO(List<LandmarkPO> data, List<LandmarkPO> preData) {
        ArrayList<LandmarkVO> res = ListUtil.newArrayList();
        for (int i = 0; i < DISPLAY_NUM; i++) {
            LandmarkVO vo = new LandmarkVO();
            vo.setLandmarkName(data.get(i).getLandmark_name());
            vo.setOrderNum(data.get(i).getOrder_num());
            vo.setAvgOrderNum(data.get(i).getAvg_order_num());
            for (int j = 0; j < DISPLAY_NUM; j++) {
                if (preData.get(j).getLandmark_name().equals(data.get(i).getLandmark_name())) {
                    vo.setLandmarkName(preData.get(j).getLandmark_name());
                    vo.setVariation(j-i);//设置排名变化，上期排名-本期排名
                    break;
                }
            }
            if (StringUtils.isEmpty(vo.getLandmarkName())) {
                vo.setLandmarkName(data.get(i).getLandmark_name());
                vo.setVariation(1);//默认增加
            }
        }

        return res;
    }

    @Override
    public List<LandmarkVO> getPotentialLandmarks(long poiId, String start, String end) {
        String[] date, preDate;
        try {
            //获取时间
            date = DateUtils.formateDate(start, end);
            preDate = DateUtils.getFormatePreDate(start, end);

            //获取全部门店数据
            List<LandmarkPO> data = getPotentialDataByIdAndDate(poiId, date[0], date[1]);
            List<LandmarkPO> preData = getPotentialDataByIdAndDate(poiId, preDate[0], preDate[1]);

            return generateLandmarkVO(data, preData);


        } catch (Exception e) {
            LOG.error("BusinessServiceImpl.getPotentialLandmarks error id={}, start={}, end={}", poiId, start, end, e);
        }
        return null;
    }

    private List<LandmarkPO> getPotentialDataByIdAndDate(long poiId, String start, String end) {
        List<LandmarkPO> poiData = businessDao.getLandmarkByIdAndDate(poiId, start, end);

        TradingAreaEntity tradingAreaEntity = businessDao.getTradingAreaById(poiId, end);
        List<LandmarkPO> aorData = businessDao.getLandmarkAvgNumByTradingAreaAndDate(tradingAreaEntity, start, end);

        //数据处理 计算潜力地标
        poiData = calculatePotentialLandmarks(poiData, aorData);

        return poiData;
    }

    private List<LandmarkPO> calculatePotentialLandmarks(List<LandmarkPO> poiData, List<LandmarkPO> aorData) {
        Map<String, LandmarkPO> baseData = ListUtil.parseHashMap(poiData);
        for (LandmarkPO po :
                aorData) {
            LandmarkPO basePo = baseData.get(po.getLandmark_name());
            if (null == basePo) {
                continue;
            }
            po.setAvg_order_num(basePo.getOrder_num());
        }

        //排序
        Collections.sort(aorData, ListUtil.potentialLandmarkComparator);

        return aorData.subList(0, DISPLAY_NUM);
    }
}
