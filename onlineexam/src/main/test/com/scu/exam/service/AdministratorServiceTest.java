package com.scu.exam.service;

import com.scu.exam.pojo.Administrator;
import org.junit.Before;
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

    @Before
    public void setUp() throws Exception {
        cleanAdministrator();
        initAdministrator();
    }

    @Test
    public void insertAdministrator() throws IOException {
        System.out.println("开始测试insertAdministrator()：");
        printAllAdministrators();
        System.out.println("测试结束");
    }

    @Test
    public void deleteAdministrator() throws IOException {
        System.out.println("开始测试deleteAdministrator()：");
        Administrator administrator = administratorService.findAdministratorById("aaaaaa");
        administratorService.deleteAdministrator(administrator);
        printAllAdministrators();
        System.out.println("测试结束");
    }

    @Test
    public void deleteAdministratorById() throws IOException {
        System.out.println("开始测试deleteAdministratorById()：");
        administratorService.deleteAdministratorById("bbbbbb");
        printAllAdministrators();
        System.out.println("测试结束");
    }

    @Test
    public void findAdministratorById() throws IOException {
        System.out.println("开始测试findAdministratorById()：");
        Administrator administrator = administratorService.findAdministratorById("cccccc");
        System.out.println("查找到的Administrator: "+administrator.toString());
        printAllAdministrators();
        System.out.println("测试结束");
    }

    @Test
    public void findAllAdministrator() throws IOException {
        System.out.println("开始测试findAllAdministrator()：");
        System.out.println("查找到的所有Administrator: ");
        List<Administrator> administratorList = administratorService.findAllAdministrator();
        for (int i = 0; i < administratorList.size(); i++){
            System.out.println(administratorList.get(i).toString());
        }
        System.out.println("测试结束");
    }

    @Test
    public void updateAdministrator() throws IOException {
        System.out.println("开始测试updateAdministrator()：");
        Administrator administrator = new Administrator("cccccc", "rrrrrr", "dddddd", "ffffff");
        administratorService.updateAdministrator(administrator);
        System.out.print("修据后的数据：");
        System.out.println(administratorService.findAdministratorById("cccccc").toString());
        printAllAdministrators();
        System.out.println("测试结束");
    }

    @Test
    public void findAdminByName(){
        System.out.println("开始测试findAdminByName()：");
        String ad_name = "bbbbbb";
        Administrator administrator = administratorService.findAdminByName(ad_name);
        System.out.println("查找到的Administrator: "+administrator.toString());
        printAllAdministrators();
        System.out.println("测试结束");
    }

    public void initAdministrator(){
        Administrator administrator1 = new Administrator("aaaaaa", "ssssss", "dddddd", "ffffff");
        Administrator administrator2 = new Administrator("bbbbbb", "nnnnnn", "mmmmmm", "bbbbbb");
        Administrator administrator3 = new Administrator("cccccc", "ssssss", "dddddd", "ffffff");
        administratorService.insertAdministrator(administrator1);
        administratorService.insertAdministrator(administrator2);
        administratorService.insertAdministrator(administrator3);
    }

    public void cleanAdministrator(){
        List<Administrator> administratorList = administratorService.findAllAdministrator();
        for (int i = 0; i < administratorList.size(); i++){
            administratorService.deleteAdministrator(administratorList.get(i));
        }
    }

    public void printAllAdministrators(){
        System.out.println("数据库中的所有数据：");
        List<Administrator> administratorList = administratorService.findAllAdministrator();
        for (int i = 0; i < administratorList.size(); i++){
            System.out.println(administratorList.get(i).toString());
        }
    }

}