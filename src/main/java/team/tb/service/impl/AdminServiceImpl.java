package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.TableInfoMapper;
import team.tb.pojo.TableInfo;
import team.tb.service.AdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private TableInfoMapper tableInfoMapper;

    @Override
    public List<TableInfo> getFormListByAdminId(Integer id, Integer page, Integer limit) {
        return tableInfoMapper.getFormListByAdminId(id, page, limit);
    }

    @Override
    public int getFormCount(Integer id) {
        return tableInfoMapper.getFormCount(id);
    }

    @Override
    public List<TableInfo> searchForm(Integer id, String formTitle, String startTime, String endTime, Integer page, Integer limit) {
        return tableInfoMapper.searchForm(id, formTitle, startTime, endTime, page, limit);
    }

    @Override
    public Integer getFormCountOnCondition(Integer id, String formTitle, String startTime, String endTime) {
        return tableInfoMapper.getFormCountOnCondition(id, formTitle, startTime, endTime);
    }


}
