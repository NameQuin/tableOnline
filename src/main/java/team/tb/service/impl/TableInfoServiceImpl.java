package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.common.FormInfo;
import team.tb.dao.TableInfoMapper;
import team.tb.pojo.TableInfo;
import team.tb.service.TableInfoService;

import java.util.List;

@Service
public class TableInfoServiceImpl implements TableInfoService {
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

    @Override
    public TableInfo getFormById(Integer formId) {
        return tableInfoMapper.getFormById(formId);
    }

    @Override
    public void insertInfoForId(TableInfo tableInfo) {
        tableInfoMapper.insertInfoForId(tableInfo);
    }

    @Override
    public Integer updateFlag(Integer id, Integer flag) {
        return tableInfoMapper.updateFlag(id, flag);
    }

    @Override
    public Integer updateTableInfo(TableInfo tableInfo) {
        return tableInfoMapper.updatePartTableInfo(tableInfo);
    }

    @Override
    public TableInfo getTableInfoById(Integer formId, Integer formStatus) {
        return tableInfoMapper.selectByPrimaryKey(formId, formStatus);
    }

    @Override
    public Integer deleteForm(Integer formId) {
        return tableInfoMapper.updateFormStatus(formId);
    }

    @Override
    public List<TableInfo> selectAllForms() {
        return tableInfoMapper.selectALlForms();
    }

    @Override
    public List<TableInfo> searchFormByCurUser(String formTitle, String startTime, String endTime) {
        return tableInfoMapper.searchFormByCurUser(formTitle, startTime, endTime);
    }

    @Override
    public List<TableInfo> getFormListByRoot(Integer page, Integer limit) {
        return tableInfoMapper.getFormListByRoot(page, limit);
    }

    @Override
    public Integer getFormCountByRoot() {
        return tableInfoMapper.getFormCountByRoot();
    }

    @Override
    public List<TableInfo> searchFormByRoot(String formTitle, String startTime, String endTime, Integer page, Integer limit) {
        return tableInfoMapper.searchFormByRoot(formTitle, startTime, endTime, page, limit);
    }

    @Override
    public Integer getSearchFormCountByRoot(String formTitle, String startTime, String endTime) {
        return tableInfoMapper.getSearchFormCountByRoot(formTitle, startTime, endTime);
    }

    @Override
    public List<TableInfo> getDeleteForm(Integer page, Integer limit) {
        return tableInfoMapper.getDeleteForm(page, limit);
    }

    @Override
    public Integer getDeleteFormCount() {
        return tableInfoMapper.getDeleteFormCount();
    }

    @Override
    public List<TableInfo> searchDeleteForm(String formTitle, String startTime, String endTime, Integer page, Integer limit) {
        return tableInfoMapper.searchDeleteForm(formTitle, startTime, endTime, page, limit);
    }

    @Override
    public int searchDeleteFormCount(String formTitle, String startTime, String endTime) {
        return tableInfoMapper.searchDeleteFormCount(formTitle, startTime, endTime);
    }

    @Override
    public int resetDeleteForm(Integer formId) {
        return tableInfoMapper.resetDeleteForm(formId);
    }


}
