package cn.wolfcode.client.contact.mapper;

import java.util.List;
import cn.wolfcode.client.contact.domain.CustomerLinkmane;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户联系人Mapper接口
 * 
 * @author BaiShuai
 * @date 2023-11-22
 */

@Mapper
public interface CustomerLinkmaneMapper 
{
    /**
     * 查询客户联系人
     * 
     * @param id 客户联系人主键
     * @return 客户联系人
     */
    public CustomerLinkmane selectCustomerLinkmaneById(Long id);

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
    public int deleteCustomerLinkmaneById(Long id);

    /**
     * 批量删除客户联系人
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerLinkmaneByIds(Long[] ids);

    List<CustomerLinkmane> selectCustomerPhoneById(Long id);
}
