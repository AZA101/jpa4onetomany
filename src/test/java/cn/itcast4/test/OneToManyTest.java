package cn.itcast4.test;

import cn.itcast4.dao.CustomerDao;
import cn.itcast4.dao.LinkManDao;
import cn.itcast4.domain.Customer;
import cn.itcast4.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToManyTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    /*保存一个客户、一个联系人*/
    @Test
    @Transactional  //开启事务
    @Rollback(value = false)//设置不回滚
    public void testAdd(){
        Customer  customer=new Customer();
        customer.setCustName("天上人间");
        LinkMan linkMan=new LinkMan();
        linkMan.setLkmName("茉莉");
        /*配置了客户到联系人之间的关系 采用add方法添加 set()方法为更新*/
        customer.getLinkManSet().add(linkMan);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }
    @Test
    @Transactional  //开启事务
    @Rollback(value = false)//设置不回滚
    public void testAdd1(){
        Customer  customer=new Customer();
        customer.setCustName("美丽天堂");
        LinkMan linkMan=new LinkMan();
        linkMan.setLkmName("悠悠");
        /*配置联系人到客户的关系(多对一)
        *  只发送了两条insert语句*/
       linkMan.setCustomer(customer);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    @Test
    @Transactional  //开启事务
    @Rollback(value = false)//设置不回滚
    public void testAdd2(){
        Customer  customer=new Customer();
        customer.setCustName("任何客队");
        LinkMan linkMan=new LinkMan();
        linkMan.setLkmName("公牛");

        /**
         * 报错提示和使用的@Data注解有关
         * 两方都对外键进行维护的时候，两个一起用时候会报堆栈溢出错误，如不需要两方都对外键进行维护，可以设置一的一方放弃
         * 外键的维护
         */
        linkMan.setCustomer(customer);
        customer.getLinkManSet().add(linkMan);
        customerDao.save(customer);
        linkManDao.save(linkMan);
    }

    /*级联添加 保存一个客户的同时，保存客户的所有联系人 需要在操作主体的实体类上，配置casacde属性*/
    @Test
    @Transactional  //开启事务
    @Rollback(value = false)//设置不回滚
    public void  testCascadeAdd(){
        Customer  customer=new Customer();
        customer.setCustName("华为");
        LinkMan linkMan=new LinkMan();
        linkMan.setLkmName("职员");
        linkMan.setCustomer(customer);
        customer.getLinkManSet().add(linkMan);
        customerDao.save(customer);
    }

    /*级联删除 删除客户的同时，删除客户的所有联系人，需要在操作主体的实体类上，配置casacde属性*/
    @Test
    @Transactional  //开启事务
    @Rollback(value = false)//设置不回滚
    public void  testCascadeRemove(){
        //1查询出需要删除的数据
        Customer one = customerDao.findOne(1l);
        //2删除数据
        customerDao.delete(one);
    }

}
