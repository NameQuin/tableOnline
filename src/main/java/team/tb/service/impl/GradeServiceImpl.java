package team.tb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.tb.dao.GradeMapper;
import team.tb.pojo.Grade;
import team.tb.service.GradeService;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public List<Grade> getGrade() {
        return gradeMapper.selectAll();
    }
}
