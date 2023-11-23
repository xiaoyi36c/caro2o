package cn.wolfcode.item.barn.mapper;

import java.util.List;
import cn.wolfcode.item.barn.domain.Store;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-23
 */

@Mapper
public interface StoreMapper 
{
    /**
     * 查询仓库信息
     * 
     * @param id 仓库信息主键
     * @return 仓库信息
     */
    public Store selectStoreById(Long id);

    /**
     * 查询仓库信息列表
     * 
     * @param store 仓库信息
     * @return 仓库信息集合
     */
    public List<Store> selectStoreList(Store store);

    /**
     * 新增仓库信息
     * 
     * @param store 仓库信息
     * @return 结果
     */
    public int insertStore(Store store);

    /**
     * 修改仓库信息
     * 
     * @param store 仓库信息
     * @return 结果
     */
    public int updateStore(Store store);

    /**
     * 删除仓库信息
     * 
     * @param id 仓库信息主键
     * @return 结果
     */
    public int deleteStoreById(Long id);

    /**
     * 批量删除仓库信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStoreByIds(Long[] ids);
}
