## onlineExaminationSystem

2020's practice training  --  project for online exam

---
注意：项目采用Spring的方式搭建，而非SringBoot

--- 

### 数据库
数据库采用8.0以上版本  
数据库名称：examsystem  
数据库密码：12345678  
建议导出数据库时采用数据+结构的方式

### git指引

可以参考：[廖雪峰的git教程](https://www.liaoxuefeng.com/wiki/896043488029600)

#### git安装基本流程
1. 安装git[下载地址](https://git-scm.com/downloads)和git可视化工具[sourceTree](https://www.sourcetreeapp.com)

2. 生成ssh-key来验证身份，流程：  
    a) 创建ssh key：再用户主目录下查看有无.ssh文件夹（注意为隐藏文件夹），再看其下有无id_rsa和id_rsa.pub，若有进入c，否则进行b  
    b) 在shell窗口输入'ssh-keygen -t rsa -C "youremail@example.com"'   （自己的邮件地址，不用设置特殊密码，一直回车就好）  
    c) 进入.ssh文件夹查看，id_rsa为私钥，id_rsa.pub为公钥。  
    d) 登陆[github](https://github.com), 在accout setting中，SSH and GPG keys子目录中新建一个SSH key，将c中id_rsa.pub文件中的内容复制到key中，填写title即可  
3. 打开shell，导航至想保存代码的目录，使用git clone （项目git地址）即可拉取该项目到本地。注：系统会自动创建（onlineExaminationSystem）文件夹来放置所有文件。  
   
### git常用指令（当然也可以用可视化工具进行管理）
   1. 将本地和远程库关联：`git remote add origin git@github.com:1466649452/onlineExaminationSystem.git`
   
   2. 创建并切换到develop分支：`git checkout -b develop`
   3. 创建新的develop分支：`git branch develop`
   4. 切换到develop分之上：`git checkout develop`
   
   5. 查看已有分支：`git branch`
   6. 自动在本地创建分支来跟踪远程的serverfix分支：`git checkout --track origin/serverfix`
    
    7. 查看当前分支状态： `git status`
    8. 拉取远程库同名分支： `git fetch`
    9. 拉取并自动合并远程库同名分支：`git pull`
    10. 将当前分支推送到远程同名分支：`git push`
    11. 将develop分支合并到当前分支上：`git merge develop`
   
### git分支管理原则
   1. master分支：系统始终可以运行的版本
   2. 所有人的开发应该自己创建一个属于自己的分支进行开发，测试通过后才能合并到master上
   3. 对于两人或两人以上开发的功能，建议对这个功能单独建立分支进行开发
   4. 建议将个人分支也同步到远程库中
   

### 部署引导
依赖部署总共分为：**spirng**、**mybatis**、**AOP**、**servlet、jsp、jstl**、**springmvc**、**json**、**日志**，需要添加新的可以写好注释添加到对应位置


### 前段框架Vue
详细使用方法：[vue官方教程](https://cn.vuejs.org/v2/guide/)  
引入vue方法  
a. 在html的head内添加：`<script src="statics/js/vue.js" type="text/javascript" charset="utf-8"></script>`，其中statis/js/vue.js为文件地址.  
b. cdn方式：添加`<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>`

