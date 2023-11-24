package cn.wolfcode.item.barn.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.item.barn.domain.Store;
import cn.wolfcode.item.barn.service.IStoreService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 仓库信息Controller
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/barn/barn")
public class StoreController extends BaseController
{
    @Autowired
    private IStoreService storeService;

    /**
     * 查询仓库信息列表
     */
    @PreAuthorize("@ss.hasPermi('barn:barn:list')")
    @GetMapping("/list")
    public TableDataInfo list(Store store)
    {
        startPage();
        List<Store> list = storeService.selectStoreList(store);
        return getDataTable(list);
    }



    /**
     * 获取仓库信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('barn:barn:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(storeService.selectStoreById(id));
    }

    /**
     * 新增仓库信息
     */
    @PreAuthorize("@ss.hasPermi('barn:barn:add')")
    @Log(title = "仓库信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Store store)
    {
        return toAjax(storeService.insertStore(store));
    }

    /**
     * 修改仓库信息
     */
    @PreAuthorize("@ss.hasPermi('barn:barn:edit')")
    @Log(title = "仓库信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Store store)
    {
        return toAjax(storeService.updateStore(store));
    }

    /**
     * 删除仓库信息
     */
    @PreAuthorize("@ss.hasPermi('barn:barn:remove')")
    @Log(title = "仓库信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(storeService.deleteStoreById(id));
    }
}
