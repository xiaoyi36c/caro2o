package cn.wolfcode.item.goods.controller;

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
import cn.wolfcode.item.goods.domain.Goods;
import cn.wolfcode.item.goods.service.IGoodsService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 物品信息Controller
 * 
 * @author Baishuai
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/item/goods")
public class GoodsController extends BaseController
{
    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询物品信息列表
     */
    @PreAuthorize("@ss.hasPermi('item:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(Goods goods)
    {
        startPage();
        List<Goods> list = goodsService.selectGoodsList(goods);
        return getDataTable(list);
    }

    /**
     * 导出物品信息列表
     */
    @PreAuthorize("@ss.hasPermi('item:goods:export')")
    @Log(title = "物品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Goods goods)
    {
        List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        util.exportExcel(response, list, "物品信息数据");
    }

    /**
     * 获取物品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('item:goods:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(goodsService.selectGoodsById(id));
    }

    /**
     * 新增物品信息
     */
    @PreAuthorize("@ss.hasPermi('item:goods:add')")
    @Log(title = "物品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Goods goods)
    {
        return toAjax(goodsService.insertGoods(goods));
    }

    /**
     * 修改物品信息
     */
    @PreAuthorize("@ss.hasPermi('item:goods:edit')")
    @Log(title = "物品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Goods goods)
    {
        return toAjax(goodsService.updateGoods(goods));
    }

    /**
     * 删除物品信息
     */
    @PreAuthorize("@ss.hasPermi('item:goods:remove')")
    @Log(title = "物品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsService.deleteGoodsByIds(ids));
    }
}
