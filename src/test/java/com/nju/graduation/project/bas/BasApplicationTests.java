package com.nju.graduation.project.bas;

import com.nju.graduation.project.bas.utils.CommonUtils;
import com.nju.graduation.project.bas.utils.JsonUtils;
import com.nju.graduation.project.bas.utils.TimeUtils;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
@MapperScan("com.nju.graduation.project.bas.mapper")
class BasApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testJson() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("code", CommonUtils.FAIL_CODE);
        response.put("message", CommonUtils.JSON_ERROR);
        String res = JsonUtils.object2Json(response);
        System.out.println(res);
    }

    @Test
    void testTimestap() {

        System.out.println(System.currentTimeMillis());

    }

    @Test
    void testTimeUtils() {
        long current = TimeUtils.getCurrentUnixTime();
        System.out.println(current);
        System.out.println(TimeUtils.Unix2Date(current));
    }


}
