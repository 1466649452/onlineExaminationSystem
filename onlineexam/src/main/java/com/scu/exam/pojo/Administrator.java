package com.scu.exam.pojo;

public class Administrator {
    /*
    管理员的帐户ID：主键，非空
     */
    private String ad_id;

    /*
    管理员的姓名：默认为空
     */
    private String ad_name;

    /*
    管理员的帐户密码：默认为空
     */
    private String ad_password;

    /*
    管理员的头像
     */
    private String ad_image;

    public Administrator(String ad_id, String ad_name, String ad_password, String ad_image) {
        this.ad_id = ad_id;
        this.ad_name = ad_name;
        this.ad_password = ad_password;
        this.ad_image = ad_image;
    }

    public Administrator() {
    }

    public String getAd_id() {
        return ad_id;
    }

    public void setAd_id(String ad_id) {
        this.ad_id = ad_id;
    }

    public String getAd_name() {
        return ad_name;
    }

    public void setAd_name(String ad_name) {
        this.ad_name = ad_name;
    }

    public String getAd_password() {
        return ad_password;
    }

    public void setAd_password(String ad_password) {
        this.ad_password = ad_password;
    }

    public String getAd_image() {
        return ad_image;
    }

    public void setAd_image(String ad_image) {
        this.ad_image = ad_image;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "ad_id='" + ad_id + '\'' +
                ", ad_name='" + ad_name + '\'' +
                ", ad_password='" + ad_password + '\'' +
                ", ad_image='" + ad_image + '\'' +
                '}';
    }
}
