package cn.wolfcode.item.sort.controller;

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
import cn.wolfcode.item.sort.domain.GoodsCategory;
import cn.wolfcode.item.sort.service.IGoodsCategoryService;
import cn.wolfcode.common.utils.poi.ExcelUtil;

/**
 * 物品分类信息Controller
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/sort/sort")
public class GoodsCategoryController extends BaseController
{
    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    /**
     * 查询物品分类信息列表
     */
    @PreAuthorize("@ss.hasPermi('sort:sort:list')")
    @GetMapping("/list")
    public AjaxResult list(GoodsCategory goodsCategory)
    {
        List<GoodsCategory> list = goodsCategoryService.selectGoodsCategoryList(goodsCategory);
        return success(list);
    }

    /**
     * 导出物品分类信息列表
     */
    @PreAuthorize("@ss.hasPermi('sort:sort:export')")
    @Log(title = "物品分类信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsCategory goodsCategory)
    {
        List<GoodsCategory> list = goodsCategoryService.selectGoodsCategoryList(goodsCategory);
        ExcelUtil<GoodsCategory> util = new ExcelUtil<GoodsCategory>(GoodsCategory.class);
        util.exportExcel(response, list, "物品分类信息数据");
    }

    /**
     * 获取物品分类信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('sort:sort:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(goodsCategoryService.selectGoodsCategoryById(id));
    }

    /**
     * 新增物品分类信息
     */
    @PreAuthorize("@ss.hasPermi('sort:sort:add')")
    @Log(title = "物品分类信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsCategory goodsCategory)
    {
        return toAjax(goodsCategoryService.insertGoodsCategory(goodsCategory));
    }

    /**
     * 修改物品分类信息
     */
    @PreAuthorize("@ss.hasPermi('sort:sort:edit')")
    @Log(title = "物品分类信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsCategory goodsCategory)
    {
        return toAjax(goodsCategoryService.updateGoodsCategory(goodsCategory));
    }

    /**
     * 删除物品分类信息
     */
    @PreAuthorize("@ss.hasPermi('sort:sort:remove')")
    @Log(title = "物品分类信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String id)
    {
        return toAjax(goodsCategoryService.deleteGoodsCategoryByIds(id));
    }

    /**
     * 迁移
     */
    @PreAuthorize("@ss.hasPermi('sort:sort:dateSort')")
    @Log(title = "物品分类信息", businessType = BusinessType.UPDATE)
    @PutMapping("/{id1}/{id2}")
    public AjaxResult putPuIn( @PathVariable Long id1, @PathVariable Long id2)
    {
        return toAjax(goodsCategoryService.putPuIn(id1,id2));
    }
}
