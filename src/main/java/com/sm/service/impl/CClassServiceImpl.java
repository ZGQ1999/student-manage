package com.sm.service.impl;
import com.sm.dao.CClassDAO;
import com.sm.entity.CClass;
import com.sm.factory.DAOFactory;
import com.sm.service.CClassService;

import java.sql.SQLException;
import java.util.List;

public class CClassServiceImpl implements CClassService {
    private CClassDAO cClassDAO = DAOFactory.getCClassDAOInstance();
    @Override
    public List<CClass> selectByDepartmentId (int departmentId) {
        List<CClass> cClassList = null;
        try {
            cClassList = cClassDAO.selectByDepartmentId(departmentId);
        } catch (SQLException e) {
            System.err.print("查询院系信息出现异常");
        }
        return cClassList;
    }

    @Override
    public int addCClass(CClass cClass) {
        int n = 0;
        try {
            n = cClassDAO.insertCClass(cClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    @Override
    public int deleteClassById(int id) {
        int n= 0;
        try {
            n = cClassDAO.deleteCClassById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  n;
    }
}
