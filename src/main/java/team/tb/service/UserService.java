package team.tb.service;

import team.tb.pojo.User;

import java.util.List;

public interface UserService {

    User findUserByUsernameAndPwd(User user);

    List<User> getUserList(Integer page, Integer limit);

    int getCount();
}
