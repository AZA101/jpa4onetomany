package cn.itcast4.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户的实体类
 * @author admin
 * 1实体类和表的映射关系
 * @Eitity (声明实体类)
 * @Table  （表明实体类和表的映射关系）
 * 2类中属性和表中字段的映射关系
 * @Id （声明主键）
 * @GeneratedValue （主键生成策略）
 * @Column （实体类变量和表中字段的映射关系）
 */
@Entity
@Table(name = "cst_customer")
//@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;
    @Column(name = "cust_name")
    private String custName;
    @Column(name = "cust_source")
    private String custSource;
    @Column(name = "cust_industry")
    private String custIndustry;
    @Column(name = "cust_level")
    private String custLevel;
    @Column(name = "cust_address")
    private String custAddress;
    @Column(name = "cust_phone")
    private String custPhone;

    /*配置客户和联系人之间的关系（一对多关系），客户中包含一个联系人的集合，
    * Set集合的元素不能重复，使用注解的方式来配置一对多的关系
    * 1声明关系 : @OneToMany 表示配置一对多关系 targetEntity 对方实体类字节码
    * 2配置外键（中间表）@JoinColumn 配置外键 name 外键字段名称 referencedColumnName 参照主表的主键字段名称
    * 在客户的实体类上（即一的一方）添加了外键配置，所以对于客户而言，也具备了维护外键的作用*/
    /*@OneToMany(targetEntity = LinkMan.class)
    @JoinColumn(name = "lkm_cust_id",referencedColumnName = "cust_id")*/
    /*放弃外键维护 mappedBy：对方配置关系的属性名称（即创建的对象）  cascade :级联属性 CascadeType.All 代表可以操作的权限如更新，删除
    * ALL 所有 MERGE 更新 PERSIST 保存 REMOVE 删除
    * fetch : 配置关联对象的加载方式
    *     EAGER：立即加载
    *     LAZY :延迟加载
    * */
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<LinkMan> linkManSet=new HashSet<LinkMan>();

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public Set<LinkMan> getLinkManSet() {
        return linkManSet;
    }

    public void setLinkManSet(Set<LinkMan> linkManSet) {
        this.linkManSet = linkManSet;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }
}
