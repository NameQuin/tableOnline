package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.KeysMapper;
import team.tb.pojo.Keys;
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
}
