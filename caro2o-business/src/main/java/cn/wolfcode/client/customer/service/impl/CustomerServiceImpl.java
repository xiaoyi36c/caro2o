package cn.wolfcode.client.customer.service.impl;

import java.util.Date;
import java.util.List;

import cn.wolfcode.client.customer.domain.vo.CustomerVo;
import cn.wolfcode.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.client.customer.mapper.CustomerMapper;
import cn.wolfcode.client.customer.domain.Customer;
import cn.wolfcode.client.customer.service.ICustomerService;
import org.springframework.util.Assert;

/**
 * 客户信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-22
 */
@Service
public class CustomerServiceImpl implements ICustomerService
{
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户信息
     *
     * @param id 客户信息主键
     * @return 客户信息
     */
    @Override
    public Customer selectCustomerById(String id)
    {
        return customerMapper.selectCustomerById(id);
    }

    /**
     * 查询客户信息列表
     *
     * @param customer 客户信息
     * @return 客户信息
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer)
    {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 新增客户信息
     *
     * @param customerVo 客户信息
     * @return 结果
     */
    @Override
    public int insertCustomer(CustomerVo customerVo)
    {
        Assert.notNull(customerVo,"参数错误");
        //企业名字不能重复；
        String customerName = customerVo.getCustomerName();
        Long id = customerMapper.selectcustomerName(customerName);
        Assert.state(id==null,"当前企业已存在");
        //企业名称：必填 100字以内

        //法定代表人：必填 30字以内

        //成立日期：必填，xxxx年xx月xx日

        //经营状态：必选，（开业、注销、破产）新增时默认开业

        //所属省份：必选（全国所有省份选择）

        //注册资本：必填、正数，保留两位小数（单位：万元）

        //所属行业：必填 30 字以内

        //注册地址：500字以内

        //经营范围：500字以内

        Customer customer = new Customer();
        //将前端接受到的数据复制给完整实体类
        BeanUtils.copyProperties(customerVo,customer);
        //设置录入时间
        customer.setInputTime(new Date());
        //查询到登录用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户昵称
        String LoginUsername = customerMapper.selelctLoginUsername(userId);
        customer.setInputUser(LoginUsername);
        return customerMapper.insertCustomer(customer);
    }

    /**
     * 修改客户信息
     *
     * @param customerVo 客户信息
     * @return 结果
     */
    @Override
    public int updateCustomer(CustomerVo customerVo)
    {
        Customer customer = new Customer();
        //将前端接受到的数据复制给完整实体类
        BeanUtils.copyProperties(customerVo,customer);
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 批量删除客户信息
     *
     * @param ids 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerByIds(String[] ids)
    {
        return customerMapper.deleteCustomerByIds(ids);
    }

    /**
     * 删除客户信息信息
     *
     * @param id 客户信息主键
     * @return 结果
     */
    @Override
    public int deleteCustomerById(String id)
    {
        return customerMapper.deleteCustomerById(id);
    }
}
