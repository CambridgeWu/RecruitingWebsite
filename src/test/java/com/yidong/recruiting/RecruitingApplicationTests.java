package com.yidong.recruiting;

import com.yidong.recruiting.mappers.Usermapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class RecruitingApplicationTests {

    @Autowired
    DataSource dataSource;
    @Autowired
    Usermapper usermapper;
    @Test
    void contextLoads() throws SQLException {
       int i = usermapper.saveUser("吴剑桥", "1234567", "13286658135", "面试官");
        System.out.println(i);
    }

}
