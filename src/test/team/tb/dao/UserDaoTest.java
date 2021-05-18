package team.tb.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.tb.log.Logger;
import team.tb.pojo.User;

@RunWith(SpringJUnit4ClassRunner.class)//替换运行期，就是将main函数替换为spring提供的，不再需要自己写一个main方法
@ContextConfiguration("classpath:spring-mybatis.xml")
public class UserDaoTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01(){
        System.out.println(userMapper.selectAllNormalUserId().toArray(new User[0]));
    }
}
