package com.mapper;

import com.pojo.Administrator;

import java.util.List;

public interface AdministratorMapper {
    public Administrator findById(String ad_id);

    int update(Administrator administrator);

    int insert(Administrator administrator);

    int deleteById(String ad_id);

    List<Administrator> find();

    int delete(Administrator administrator);
}
