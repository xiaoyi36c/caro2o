package cn.wolfcode.client.mapper;

import java.util.List;
import cn.wolfcode.client.domain.CustomerLinkmane;

/**
 * 客户联系人Mapper接口
 * 
 * @author BaiShuai
 * @date 2023-11-22
 */
public interface CustomerLinkmaneMapper 
{
    /**
     * 查询客户联系人
     * 
     * @param id 客户联系人主键
     * @return 客户联系人
     */
    public CustomerLinkmane selectCustomerLinkmaneById(String id);

    /**
     * 查询客户联系人列表
     * 
     * @param customerLinkmane 客户联系人
     * @return 客户联系人集合
     */
    public List<CustomerLinkmane> selectCustomerLinkmaneList(CustomerLinkmane customerLinkmane);

    /**
     * 新增客户联系人
     * 
     * @param customerLinkmane 客户联系人
     * @return 结果
     */
    public int insertCustomerLinkmane(CustomerLinkmane customerLinkmane);

    /**
     * 修改客户联系人
     * 
     * @param customerLinkmane 客户联系人
     * @return 结果
     */
    public int updateCustomerLinkmane(CustomerLinkmane customerLinkmane);

    /**
     * 删除客户联系人
     * 
     * @param id 客户联系人主键
     * @return 结果
     */
    public int deleteCustomerLinkmaneById(String id);

    /**
     * 批量删除客户联系人
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerLinkmaneByIds(String[] ids);
}
