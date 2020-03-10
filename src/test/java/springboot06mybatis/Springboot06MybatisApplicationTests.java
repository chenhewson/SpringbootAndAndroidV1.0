package springboot06mybatis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class Springboot06MybatisApplicationTests {
    @Autowired
    private DataSource datasource;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(datasource.getClass());
        Set set=new HashSet();
        Connection con=datasource.getConnection();
        System.out.println(con);
        con.close();
    }


}
