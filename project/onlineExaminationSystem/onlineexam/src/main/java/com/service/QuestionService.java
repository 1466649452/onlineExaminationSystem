package com.service;

import com.pojo.Administrator;

import java.util.List;

public interface AdministratorService {
    /*
    增加一个管理员
     */
    public int insertAdministrator(Administrator administrator);

    /*
    删除指定id的管理员
    输入：要删除的管理员的id
     */
    public int deleteAdministratorById(String ad_id);

    /*
    查找指定id的管理员
    输入：要查找的管理员id
    输出：查找到的管理员
     */
    public Administrator findAdministratorById(String ad_id);

    /*
    显示所有的管理员
    输入：无
    输出：一张包含所有管理员的列表
     */
    public List<Administrator> findAllAdministrator();

    /*
    修改指定id的管理员的信息
    输入：要修改的管理员id
     */
    public int updateAdministrator(Admini