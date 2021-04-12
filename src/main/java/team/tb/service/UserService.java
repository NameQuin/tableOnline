package team.tb.service;

import team.tb.pojo.User;

public interface UserService {

    User findUserByUsernameAndPwd(User user);
}
