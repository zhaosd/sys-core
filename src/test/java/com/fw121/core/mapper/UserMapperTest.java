package com.fw121.core.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mvt-zhaosandong-mac on 2018/7/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    SysUserMapper mapper;

    @Test
    @Rollback
    public void test() {
        System.out.println("just test");
        System.out.println(mapper.getClass());
        Assert.assertEquals(mapper.selectAll().size(), 4);
    }

}