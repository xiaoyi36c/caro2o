package cn.wolfcode.item.bills.controller;

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
import cn.wolfcode.item.bills.domain.StockBillItem;
import cn.wolfcode.item.bills.service.IStockBillItemService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 出入库单据明细Controller
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/item/bills")
public class StockBillItemController extends BaseController
{
    @Autowired
    private IStockBillItemService stockBillItemService;

    /**
     * 查询出入库单据明细列表
     */
    @PreAuthorize("@ss.hasPermi('item:bills:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockBillItem stockBillItem)
    {
        startPage();
        List<StockBillItem> list = stockBillItemService.selectStockBillItemList(stockBillItem);
        return getDataTable(list);
    }

    /**
     * 导出出入库单据明细列表
     */
    @PreAuthorize("@ss.hasPermi('item:bills:export')")
    @Log(title = "出入库单据明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockBillItem stockBillItem)
    {
        List<StockBillItem> list = stockBillItemService.selectStockBillItemList(stockBillItem);
        ExcelUtil<StockBillItem> util = new ExcelUtil<StockBillItem>(StockBillItem.class);
        util.exportExcel(response, list, "出入库单据明细数据");
    }

    /**
     * 获取出入库单据明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('item:bills:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(stockBillItemService.selectStockBillItemById(id));
    }

    /**
     * 新增出入库单据明细
     */
    @PreAuthorize("@ss.hasPermi('item:bills:add')")
    @Log(title = "出入库单据明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockBillItem stockBillItem)
    {
        return toAjax(stockBillItemService.insertStockBillItem(stockBillItem));
    }

    /**
     * 修改出入库单据明细
     */
    @PreAuthorize("@ss.hasPermi('item:bills:edit')")
    @Log(title = "出入库单据明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockBillItem stockBillItem)
    {
        return toAjax(stockBillItemService.updateStockBillItem(stockBillItem));
    }

    /**
     * 删除出入库单据明细
     */
    @PreAuthorize("@ss.hasPermi('item:bills:remove')")
    @Log(title = "出入库单据明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(stockBillItemService.deleteStockBillItemByIds(ids));
    }
}
