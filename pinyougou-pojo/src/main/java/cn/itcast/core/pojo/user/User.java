package cn.itcast.core.pojo.user;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date created;

    private Date updated;

    /**
     * 会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat
     */
    private String sourceType;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 使用状态（Y正常 N非正常）
     */
    private String status;

    /**
     * 头像地址
     */
    private String headPic;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 账户余额
     */
    private Long accountBalance;

    /**
     * 手机是否验证 （0否  1是）
     */
    private String isMobileCheck;

    /**
     * 邮箱是否检测（0否  1是）
     */
    private String isEmailCheck;

    /**
     * 性别，1男，2女
     */
    private String sex;

    /**
     * 会员等级
     */
    private Integer userLevel;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 经验值
     */
    private Integer experienceValue;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
//省
    private String province1;
//市
    private String city1;
//区
    private String district1;

    private String profession;
    private String year;

    private String month;
    private String day;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    private static final long serialVersionUID = 1L;

    public String getProvince1() {
        return province1;
    }

    public void setProvince1(String province1) {
        this.province1 = province1;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getDistrict1() {
        return district1;
    }

    public void setDistrict1(String district1) {
        this.district1 = district1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic == null ? null : headPic.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public Long getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getIsMobileCheck() {
        return isMobileCheck;
    }

    public void setIsMobileCheck(String isMobileCheck) {
        this.isMobileCheck = isMobileCheck == null ? null : isMobileCheck.trim();
    }

    public String getIsEmailCheck() {
        return isEmailCheck;
    }

    public void setIsEmailCheck(String isEmailCheck) {
        this.isEmailCheck = isEmailCheck == null ? null : isEmailCheck.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getExperienceValue() {
        return experienceValue;
    }

    public void setExperienceValue(Integer experienceValue) {
        this.experienceValue = experienceValue;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", created=").append(created);
        sb.append(", updated=").append(updated);
        sb.append(", sourceType=").append(sourceType);
        sb.append(", nickName=").append(nickName);
        sb.append(", name=").append(name);
        sb.append(", status=").append(status);
        sb.append(", headPic=").append(headPic);
        sb.append(", qq=").append(qq);
        sb.append(", accountBalance=").append(accountBalance);
        sb.append(", isMobileCheck=").append(isMobileCheck);
        sb.append(", isEmailCheck=").append(isEmailCheck);
        sb.append(", sex=").append(sex);
        sb.append(", userLevel=").append(userLevel);
        sb.append(", points=").append(points);
        sb.append(", experienceValue=").append(experienceValue);
        sb.append(", birthday=").append(birthday);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", province1=").append(province1);
        sb.append(", city1=").append(city1);
        sb.append(", district1=").append(district1);
        sb.append(", profession=").append(profession);
        sb.append(", year=").append(year);
        sb.append(", month=").append(month);
        sb.append(", day=").append(day);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getCreated() == null ? other.getCreated() == null : this.getCreated().equals(other.getCreated()))
                && (this.getUpdated() == null ? other.getUpdated() == null : this.getUpdated().equals(other.getUpdated()))
                && (this.getSourceType() == null ? other.getSourceType() == null : this.getSourceType().equals(other.getSourceType()))
                && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getHeadPic() == null ? other.getHeadPic() == null : this.getHeadPic().equals(other.getHeadPic()))
                && (this.getQq() == null ? other.getQq() == null : this.getQq().equals(other.getQq()))
                && (this.getAccountBalance() == null ? other.getAccountBalance() == null : this.getAccountBalance().equals(other.getAccountBalance()))
                && (this.getIsMobileCheck() == null ? other.getIsMobileCheck() == null : this.getIsMobileCheck().equals(other.getIsMobileCheck()))
                && (this.getIsEmailCheck() == null ? other.getIsEmailCheck() == null : this.getIsEmailCheck().equals(other.getIsEmailCheck()))
                && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
                && (this.getUserLevel() == null ? other.getUserLevel() == null : this.getUserLevel().equals(other.getUserLevel()))
                && (this.getPoints() == null ? other.getPoints() == null : this.getPoints().equals(other.getPoints()))
                && (this.getExperienceValue() == null ? other.getExperienceValue() == null : this.getExperienceValue().equals(other.getExperienceValue()))
                && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
                && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()))
                && (this.getProvince1() == null ? other.getProvince1() == null : this.getProvince1().equals(other.getProvince1()))
                && (this.getCity1() == null ? other.getCity1() == null : this.getCity1().equals(other.getCity1()))
                && (this.getDistrict1() == null ? other.getDistrict1() == null : this.getDistrict1().equals(other.getDistrict1()))
                && (this.getProfession() == null ? other.getProfession() == null : this.getProfession().equals(other.getProfession()))
                && (this.getYear() == null ? other.getYear() == null : this.getYear().equals(other.getYear()))
                && (this.getMonth() == null ? other.getMonth() == null : this.getMonth().equals(other.getMonth()))
                && (this.getDay() == null ? other.getDay() == null : this.getDay().equals(other.getDay()));
    }

        @Override
        public int hashCode () {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
            result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
            result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
            result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
            result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
            result = prime * result + ((getCreated() == null) ? 0 : getCreated().hashCode());
            result = prime * result + ((getUpdated() == null) ? 0 : getUpdated().hashCode());
            result = prime * result + ((getSourceType() == null) ? 0 : getSourceType().hashCode());
            result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
            result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
            result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
            result = prime * result + ((getHeadPic() == null) ? 0 : getHeadPic().hashCode());
            result = prime * result + ((getQq() == null) ? 0 : getQq().hashCode());
            result = prime * result + ((getAccountBalance() == null) ? 0 : getAccountBalance().hashCode());
            result = prime * result + ((getIsMobileCheck() == null) ? 0 : getIsMobileCheck().hashCode());
            result = prime * result + ((getIsEmailCheck() == null) ? 0 : getIsEmailCheck().hashCode());
            result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
            result = prime * result + ((getUserLevel() == null) ? 0 : getUserLevel().hashCode());
            result = prime * result + ((getPoints() == null) ? 0 : getPoints().hashCode());
            result = prime * result + ((getExperienceValue() == null) ? 0 : getExperienceValue().hashCode());
            result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
            result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
            result = prime * result + ((getProvince1() == null) ? 0 : getProvince1().hashCode());
            result = prime * result + ((getCity1() == null) ? 0 : getCity1().hashCode());
            result = prime * result + ((getDistrict1() == null) ? 0 : getDistrict1().hashCode());
            result = prime * result + ((getProfession() == null) ? 0 : getProfession().hashCode());
            result = prime * result + ((getYear() == null) ? 0 : getYear().hashCode());
            result = prime * result + ((getMonth() == null) ? 0 : getMonth().hashCode());
            result = prime * result + ((getDay() == null) ? 0 : getDay().hashCode());
            return result;
        }
    }
