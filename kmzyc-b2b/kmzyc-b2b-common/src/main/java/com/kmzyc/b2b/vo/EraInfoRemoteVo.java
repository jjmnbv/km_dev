package com.kmzyc.b2b.vo;

/**
 * 时代会员接口
 */
public class EraInfoRemoteVo {

    private String ID; //会员ID
    private String Number; //会员编号
    private String LoginPass; //登录密码
    private String Score; //积分
    private String Level; //等级名称
    private String Direct; //推荐人编号
    private String Birthday; //生日
    private String Discount; //等级折扣率百分比
    private String Name; //真实姓名
    private String PetName; //昵称
    private String PaperType; //证件类型
    private String PaperNumber; //证件号码
    private String MobileTele; //联系电话
    private String Sex; //性别
    private String Operation; //更新类型

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getLoginPass() {
        return LoginPass;
    }

    public void setLoginPass(String loginPass) {
        LoginPass = loginPass;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getDirect() {
        return Direct;
    }

    public void setDirect(String direct) {
        Direct = direct;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPetName() {
        return PetName;
    }

    public void setPetName(String petName) {
        PetName = petName;
    }

    public String getPaperType() {
        return PaperType;
    }

    public void setPaperType(String paperType) {
        PaperType = paperType;
    }

    public String getPaperNumber() {
        return PaperNumber;
    }

    public void setPaperNumber(String paperNumber) {
        PaperNumber = paperNumber;
    }

    public String getMobileTele() {
        return MobileTele;
    }

    public void setMobileTele(String mobileTele) {
        MobileTele = mobileTele;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }
}
