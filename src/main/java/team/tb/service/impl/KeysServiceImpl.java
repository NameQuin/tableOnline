package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.KeysMapper;
import team.tb.pojo.Keys;
import team.tb.pojo.UserInfo;
import team.tb.service.KeysService;

import java.util.List;
@Service
public class KeysServiceImpl implements KeysService {
    @Autowired
    private KeysMapper keysMapper;

    @Override
    public List<Keys> getAllKeys() {
        return keysMapper.selectAll();
    }

    @Override
    public Keys selectLastOne() {
        return keysMapper.selectLastOne();
    }

    @Override
    public void insertForKey(Keys key) {
        keysMapper.insertForKey(key);
    }

    @Override
    public List<Keys> getUserAllInfo(Integer uid) {
        return keysMapper.getUserAllInfo(uid);
    }
}
