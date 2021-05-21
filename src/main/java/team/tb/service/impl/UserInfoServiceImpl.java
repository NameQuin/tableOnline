package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.UserInfoMapper;
import team.tb.pojo.UserInfo;
import team.tb.service.UserInfoService;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void insertWithoutKey(UserInfo userInfo) {
        userInfoMapper.insertWithoutKey(userInfo);
    }

    @Override
    public UserInfo getUserInfoById(Integer keyId, Integer userId) {
        return userInfoMapper.getUserInfoByKeyId(keyId, userId);
    }

    @Override
    public UserInfo getUserInfoWithReference(Integer keyId, Integer userId) {
        if(keyId.equals(5)){
            return userInfoMapper.getUserGradeInfo(keyId, userId);
        }else if(keyId.equals(4)){
            return userInfoMapper.getUserDepartmentInfo(keyId, userId);
        }else if(keyId.equals(10)){
            return userInfoMapper.getUserMajorInfo(keyId, userId);
        }else if(keyId.equals(6)){
            return userInfoMapper.getUserClassInfo(keyId, userId);
        }
        return null;
    }

    @Override
    public Integer updateValue(UserInfo userInfo) {
        return userInfoMapper.updateValue(userInfo);
    }

}
