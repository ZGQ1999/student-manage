package com.sm.service;

import com.sm.utils.ResultEntity;

/**
 * 管理员业务逻辑接口
 */
public interface AdminService {
    ResultEntity adminLogin(String account, String password);
}