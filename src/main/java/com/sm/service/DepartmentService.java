package com.sm.service;

import com.sm.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> selectAll();
    void deleteDepartment(int id);

    /**
     * 新增院系
     * @param department
     * @return int
     */
    int addDepartment(Department department);
}