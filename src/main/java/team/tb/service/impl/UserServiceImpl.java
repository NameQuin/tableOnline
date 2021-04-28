package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.UserMapper;
import team.tb.pojo.User;
import team.tb.service.UserService;

import java.util.List;

@Service

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsernameAndPwd(User user) {
        return userMapper.findUserByUsernameAndPwd(user);
    }

    @Override
    public List<User> getUserList(Integer page, Integer limit) {
        return userMapper.getUserList(page, limit);
    }

    @Override
    public int getCount() {
        return userMapper.getCount();
    }

    @Override
    public List<User> getUserOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username, Integer page, Integer limit) {
        return userMapper.getUserOnCondition(grade, department, major, clazz, username, page, limit);
    }

    @Override
    public Integer getUserCountOnCondition(Integer grade, Integer department, Integer major, Integer clazz, String username) {
        return userMapper.getUserCountOnCondition(grade, department, major, clazz, username);
    }

    @Override
    public List<User> getUserByClass(Integer[] clazzs) {
        return userMapper.getUserByClass(clazzs);
    }
}
