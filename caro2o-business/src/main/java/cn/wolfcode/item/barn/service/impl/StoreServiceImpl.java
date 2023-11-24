package cn.wolfcode.item.barn.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.item.barn.mapper.StoreMapper;
import cn.wolfcode.item.barn.domain.Store;
import cn.wolfcode.item.barn.service.IStoreService;
import org.springframework.util.Assert;

/**
 * 仓库信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@Service
public class StoreServiceImpl implements IStoreService 
{
    @Autowired
    private StoreMapper storeMapper;

    /**
     * 查询仓库信息
     * 
     * @param id 仓库信息主键
     * @return 仓库信息
     */
    @Override
    public Store selectStoreById(Long id)
    {
        return storeMapper.selectStoreById(id);
    }

    /**
     * 查询仓库信息列表
     * 
     * @param store 仓库信息
     * @return 仓库信息
     */
    @Override
    public List<Store> selectStoreList(Store store)
    {
        return storeMapper.selectStoreList(store);
    }

    /**
     * 新增仓库信息
     * 
     * @param store 仓库信息
     * @return 结果
     */
    @Override
    public int insertStore(Store store)
    {
        Assert.state(store.getStoreName().length()<50,"仓库名称不能超过50个字符");
        Assert.state(store.getStoreAddress().length()<250,"仓库地址不能超过250个字符");

        return storeMapper.insertStore(store);
    }

    /**
     * 修改仓库信息
     * 
     * @param store 仓库信息
     * @return 结果
     */
    @Override
    public int updateStore(Store store)
    {
        Store store1 = storeMapper.selectStoreById(store.getId());
        Assert.notNull(store1,"参数错误");
        Assert.state(store1.getState()==Store.STATE_DELETE,"参数错误");
        Assert.state(store.getStoreName().length()<50,"仓库名称不能超过50个字符");
        Assert.state(store.getStoreAddress().length()<250,"仓库地址不能超过250个字符");
        return storeMapper.updateStore(store);
    }

    /**
     * 批量删除仓库信息
     * 
     * @param ids 需要删除的仓库信息主键
     * @return 结果
     */
    @Override
    public int deleteStoreByIds(Long[] ids)
    {
        return storeMapper.deleteStoreByIds(ids);
    }

    /**
     * 删除仓库信息信息
     * 
     * @param id 仓库信息主键
     * @return 结果
     */
    @Override
    public int deleteStoreById(Long id)
    {
        return storeMapper.deleteStoreById(id);
    }
}
