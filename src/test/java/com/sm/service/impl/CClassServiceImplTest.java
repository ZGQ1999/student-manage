package com.sm.service.impl;

import com.sm.entity.CClass;
import com.sm.entity.Department;
import com.sm.factory.ServiceFactory;
import com.sm.service.CClassService;
import com.sm.service.DepartmentService;
import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class CClassServiceImplTest {
    private CClassService cClassService = ServiceFactory.getCClassServiceInstance();
    @Test
    public void selectByDepartmentId () {
        List<CClass> cClassList = cClassService.selectByDepartmentId(3);
        cClassList.forEach(cClass-> System.out.println(cClass));
    }

    @Test
    public void deleteClassById() {
        int id  =18;
       cClassService.deleteClassById(id);
    }
}