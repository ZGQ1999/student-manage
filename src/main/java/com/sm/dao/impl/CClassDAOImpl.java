package com.sm.dao.impl;

import com.sm.dao.CClassDAO;
import com.sm.entity.CClass;
import com.sm.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CClassDAOImpl implements CClassDAO {
    @Override
    public List <CClass> selectByDepartmentId (int departmentId) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "SELECT * FROM t_class WHERE department_id=? ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(departmentId));
        ResultSet rs = pstmt.executeQuery();
        List <CClass> ccLassList = new ArrayList <>();
        while (rs.next()) {
            CClass cClasst = new CClass();
            cClasst.setId(rs.getInt("id"));
            cClasst.setId(rs.getInt("department_id"));
            cClasst.setClassName(rs.getString("class_name"));
            ccLassList .add(cClasst);
        }
        rs.close();
        pstmt.close();
        jdbcUtil.closeConnection();
        return ccLassList;
    }

    @Override
    public int insertCClass(CClass cClass) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "INSERT INTO t_class (department_id,class_name) VALUES (?,?) ";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, cClass.getDepartmentId());
        pstmt.setString(2,cClass.getClassName());
        int n = pstmt.executeUpdate();
        pstmt.close();
        connection.close();
        return n;
    }


    @Override
    public int deleteCClassById(int id) throws SQLException {
        JDBCUtil jdbcUtil = JDBCUtil.getInitJDBCUtil();
        Connection connection = jdbcUtil.getConnection();
        String sql = "DELETE FROM t_class WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1,id);
        return pstmt.executeUpdate();



    }

    @Override
    public void insertCClass(int cClass) throws  SQLException {



    }
}
