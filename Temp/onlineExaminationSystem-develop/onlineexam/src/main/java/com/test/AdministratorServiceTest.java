package com.test;

import com.pojo.Administrator;
import com.service.AdministratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AdministratorServiceTest{
    @Autowired
    private AdministratorService administratorService;

    @Test
    public void insertAdministrator() throws IOException {
        cleanAdministrator();
        initAdministrator();
        printAllAdministrators();
    }

    @Test
    public void deleteAdministrator() throws IOException {
        cleanAdministrator();
        initAdministrator();
        Administrator administrator = administratorService.findAdministratorById("aaaaaa");
        administratorService.deleteAdministrator(administrator);
        printAllAdministrators();
    }

    @Test
    public void deleteAdministratorById() throws IOException {
        cleanAdministrator();
        initAdministrator();
        administratorService.deleteAdministratorById("bbbbbb");
        printAllAdministrators();
    }

    @Test
    public void findAdministratorById() throws IOException {
        cleanAdministrator();
        initAdministrator();
        Administrator administrator = administratorService.findAdministratorById("cccccc");
        System.out.println(administrator.toString());
    }

    @Test
    public void findAllAdministrator() throws IOException {
        cleanAdministrator();
        initAdministrator();
        printAllAdministrators();
    }
    @Test

    public void updateAdministrator() throws IOException {
        cleanAdministrator();
        initAdministrator();
        Administrator administrator = new Administrator("cccccc", "rrrrrr", "dddddd", "ffffff");
        administratorService.updateAdministrator(administrator);
        System.out.println(administratorService.findAdministratorById("cccccc").toString());
    }

    public void initAdministrator(){
        Administrator administrator1 = new Administrator("aaaaaa", "ssssss", "dddddd", "ffffff");
        Administrator administrator2 = new Administrator("bbbbbb", "nnnnnn", "mmmmmm", "bbbbbb");
        Administrator administrator3 = new Administrator("cccccc", "ssssss", "dddddd", "ffffff");
        administratorService.insertAdministrator(administrator1);
        administratorService.insertAdministrator(administrator2);
        administratorService.insertAdministrator(administrator3);
    }

    public void printAllAdministrators(){
        List<Administrator> administratorList = administratorService.findAllAdministrator();
        for (int i = 0; i < administratorList.size(); i++){
            System.out.println(administratorList.get(i).toString());
        }
    }

    public void cleanAdministrator(){
        List<Administrator> administratorList = administratorService.findAllAdministrator();
        for (int i = 0; i < administratorList.size(); i++){
            administratorService.deleteAdministrator(administratorList.get(i));
        }
    }

}