package com.sm.dao.impl;

import com.sm.dao.CClassDAO;
import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.factory.DAOFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CClassDAOImplTest {
    private CClassDAO cClassDAO = DAOFactory.getCClassDAOInstance();
    @Test
    public void selectByDepartmentId() {
        List<CClass> cClassList = null;
        try {
            cClassList = cClassDAO.selectByDepartmentId(2);
        }catch (SQLException e){
            e.printStackTrace();
        }
        cClassList.forEach(cClass -> System.out.println(cClass));
    }
    @Test
    public void insertCClass(){
        CClass cClass = new CClass();
        cClass.setClassName("机自1811");
        cClass.setDepartmentId(1);
        try {
            int n = cClassDAO.insertCClass(cClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCClassById() throws SQLException {
        System.out.println(cClassDAO.deleteCClassById(17));
    }
}