package cn.itcast4.test;

import cn.itcast4.dao.CustomerDao;
import cn.itcast4.dao.LinkManDao;
import cn.itcast4.domain.Customer;
import cn.itcast4.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 对象导航查询 查询一个对象时通过此对象查询它的关联对象
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectQueryTest {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LinkManDao linkManDao;

    /*对象导航查询（查询一个对象时候，通过此对象查询所有的关联对象）*/
    //could not initialize proxy - no Session
    @Test
    @Transactional //解决java代码中no session 问题
    public void testQuery1() {
        //通过编号查询客户  getOne()延迟加载 返回的是一个引用，即代理对象，数据不存在则直接抛异常
        Customer customer = customerDao.getOne(2l);
        //对象导航查询，此客户下的所有联系人  调用getLinkManSet方法不会立即查询，而是在使用关联对象时候才会查询
        Set<LinkMan> linkManSet = customer.getLinkManSet();
        for (LinkMan linkMan : linkManSet) {
            System.out.println(linkMan);
        }

    }

    /**
     * 对象导航查询
     * 默认是使用延迟加载的方式查询
     * 调用getLinkManSet方法不会立即查询，而是在使用关联对象时候才会查询
     * 延迟加载
     * 修改配置，将延迟加载改为立即加载
     * fetch  需要配置到多表映射关系的注解上
     */
    @Test
    @Transactional //解决java代码中no session 问题
    public void testQuery2() {
        //通过编号查询客户  findOne()立即加载
        Customer customer = customerDao.findOne(2l);
        //对象导航查询，此客户下的所有联系人
        Set<LinkMan> linkManSet = customer.getLinkManSet();
        for (LinkMan linkMan : linkManSet) {
            System.out.println(linkMan);
        }
    }

    /**
     * 从联系人对象导航查询到他所属的客户
     *    默认是立即加载
     *    要设置延迟加载的话 也是在多表映射的注解上进行操作
     */
    @Test
    @Transactional //解决java代码中no session 问题
    public void testQuery3() {
        //通过编号查询客户  findOne()立即加载
        LinkMan linkMan1 = linkManDao.findOne(2l);
        //对象导航查询，此客户下的所有联系人
        Customer customer = linkMan1.getCustomer();
        System.out.println(customer);

    }
}
