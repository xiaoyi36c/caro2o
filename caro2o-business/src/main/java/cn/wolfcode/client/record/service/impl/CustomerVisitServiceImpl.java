package cn.wolfcode.client.record.service.impl;

import java.util.Date;
import java.util.List;

import cn.wolfcode.client.contact.domain.CustomerLinkmane;
import cn.wolfcode.client.contact.service.ICustomerLinkmaneService;
import cn.wolfcode.client.customer.domain.Customer;
import cn.wolfcode.client.customer.service.ICustomerService;
import cn.wolfcode.client.record.domain.vo.ClientAndLinkman;
import cn.wolfcode.client.record.domain.vo.CustomerVisitVo;
import cn.wolfcode.common.constant.UserConstants;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.bean.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.client.record.mapper.CustomerVisitMapper;
import cn.wolfcode.client.record.domain.CustomerVisit;
import cn.wolfcode.client.record.service.ICustomerVisitService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 拜访信息Service业务层处理
 * 
 * @author li
 * @date 2023-11-22
 */
@Service
public class CustomerVisitServiceImpl implements ICustomerVisitService 
{
    @Autowired
    private CustomerVisitMapper customerVisitMapper;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICustomerLinkmaneService customerLinkmaneService;

    /**
     * 查询拜访信息
     * 
     * @param id 拜访信息主键
     * @return 拜访信息
     */
    @Override
    public CustomerVisit selectCustomerVisitById(Long id)
    {
        return customerVisitMapper.selectCustomerVisitById(id);
    }

    /**
     * 查询拜访信息列表
     * 
     * @param customerVisit 拜访信息
     * @return 拜访信息
     */
    @Override
    public List<CustomerVisit> selectCustomerVisitList(CustomerVisit customerVisit)
    {
        return customerVisitMapper.selectCustomerVisitList(customerVisit);
    }

    /**
     * 新增拜访信息
     * 
     * @param customerVisitVo 拜访信息
     * @return 结果
     */
    @Override
    public int insertCustomerVisit(CustomerVisitVo customerVisitVo)
    {
        Assert.notNull(customerVisitVo,"参数错误");
        //拜访原因：必填，100字以内
        Assert.state(customerVisitVo.getVisitReason().length()<=100,"请输入100字以内");
        //交流情况：必填、1000字以内
        Assert.state(customerVisitVo.getContent().length()<=1000,"请不要超过1000个字符");
        //拜访时间：默认当前日期，不可超过当天
        Assert.state(customerVisitVo.getVisitDate().compareTo(new Date())<0,"拜访时间不能超过当前时间");

        CustomerVisit customerVisit = new CustomerVisit();
        customerVisit.setInputUser(SecurityUtils.getUsername());
        customerVisit.setInputTime(new Date());
        BeanUtils.copyBeanProp(customerVisit,customerVisitVo);
        return customerVisitMapper.insertCustomerVisit(customerVisit);
    }

    /**
     * 修改拜访信息
     * 
     * @param customerVisitVo 拜访信息
     * @return 结果
     */
    @Override
    public int updateCustomerVisit(CustomerVisitVo customerVisitVo)
    {
        Assert.notNull(customerVisitVo,"参数错误");
        //拜访原因：必填，100字以内
        Assert.state(customerVisitVo.getVisitReason().length()<=100,"请输入100字以内");
        //交流情况：必填、1000字以内
        Assert.state(customerVisitVo.getContent().length()<=1000,"请不要超过1000个字符");
        //拜访时间：默认当前日期，不可超过当天
        Assert.state(customerVisitVo.getVisitDate().compareTo(new Date())<0,"拜访时间不能超过当前时间");
        return customerVisitMapper.updateCustomerVisit(customerVisitVo);
    }

    /**
     * 批量删除拜访信息
     * 
     * @param ids 需要删除的拜访信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerVisitByIds(Long[] ids)
    {
        return customerVisitMapper.deleteCustomerVisitByIds(ids);
    }

    /**
     * 删除拜访信息信息
     * 
     * @param id 拜访信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerVisitById(Long id)
    {
        return customerVisitMapper.deleteCustomerVisitById(id);
    }

    @Override
    public ClientAndLinkman selectClientLinkman() {
        List<Customer> seeClient = customerVisitMapper.selectCustomerList();
        List<CustomerLinkmane> clientLinkman = customerVisitMapper.selectCustomerLinkmaneList();
        ClientAndLinkman clientAndLinkman = new ClientAndLinkman();
        clientAndLinkman.setClientLinkman(clientLinkman);
        clientAndLinkman.setSeeClient(seeClient);
        return clientAndLinkman;
    }

    @Override
    public List<CustomerVisit> selectCustomerrecById(Long id) {
        return customerVisitMapper.selectCustomerrecById(id);
    }
}
