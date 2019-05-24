package com.sm.dao;

import com.sm.entity.Department;

import java.sql.SQLException;
import java.util.List;

/**
 * 院系DAO接口
 */
public interface DepartmentDAO {
    /**
     * 查询所有院系
     * @return List<Department>
     * @throws SQLException
     */
    List<Department> getAll() throws SQLException;

    /***
     *
     * @param department
     * @return
     */
    int insertDepartment(Department department) throws SQLException;


    /**
     *
     * @param id
     * @return
     * @throws SQLException
     */
    int deleteDepartmentById (int id) throws SQLException;

}

