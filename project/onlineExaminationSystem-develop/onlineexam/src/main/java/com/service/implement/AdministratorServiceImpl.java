package com.service.implement;

import com.mapper.AdministratorMapper;
import com.pojo.Administrator;
import com.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorMapper administratorMapper;

    /*
    增加一个管理员
     */
    @Override
    public int insertAdministrator(Administrator administrator) {
        return administratorMapper.insert(administrator);
    }

    @Override
    public int deleteAdministrator(Administrator administrator) {
        return administratorMapper.delete(administrator);
    }

    /*

     */
    @Override
    public int deleteAdministratorById(String ad_id) {
        return administratorMapper.deleteById(ad_id);
    }

    /*
    查找指定id的管理员
    输入：要查找的管理员id
    输出：查找到的管理员
     */
    @Override
    public Administrator findAdministratorById(String ad_id) {
        return administratorMapper.findById(ad_id);
    }

    /*
    显示所有的管理员
    输入：无
    输出：一张包含所有管理员的列表
     */
    @Override
    public List<Administrator> findAllAdministrator(){
        return administratorMapper.find();
    }

    /*
    修改指定管理员的信息
    输入：要修改的管理员
     */
    @Override
    public int updateAdministrator(Administrator administrator) {
        return administratorMapper.update(administrator);
    }
}
