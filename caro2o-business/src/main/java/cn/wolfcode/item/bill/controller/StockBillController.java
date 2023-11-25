package cn.wolfcode.item.bill.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.item.bill.domain.vo.StockBillVo;
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
import cn.wolfcode.item.bill.domain.StockBill;
import cn.wolfcode.item.bill.service.IStockBillService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 出入库单据Controller
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/item/bill")
public class StockBillController extends BaseController
{
    @Autowired
    private IStockBillService stockBillService;

    /**
     * 查询出入库单据列表
     */
    @PreAuthorize("@ss.hasPermi('item:bill:list')")
    @GetMapping("/list")
    public TableDataInfo list(StockBill stockBill)
    {
        startPage();
        List<StockBill> list = stockBillService.selectStockBillList(stockBill);
        return getDataTable(list);
    }

    /**
     * 导出出入库单据列表
     */
    @PreAuthorize("@ss.hasPermi('item:bill:export')")
    @Log(title = "出入库单据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StockBill stockBill)
    {
        List<StockBill> list = stockBillService.selectStockBillList(stockBill);
        ExcelUtil<StockBill> util = new ExcelUtil<StockBill>(StockBill.class);
        util.exportExcel(response, list, "出入库单据数据");
    }

    /**
     * 获取出入库单据详细信息
     */
    @PreAuthorize("@ss.hasPermi('item:bill:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stockBillService.selectStockBillById(id));
    }

    /**
     * 新增出入库单据
     */
    @PreAuthorize("@ss.hasPermi('item:bill:add')")
    @Log(title = "出入库单据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StockBill stockBill)
    {
        return toAjax(stockBillService.insertStockBill(stockBill));
    }

    /**
     * 修改出入库单据
     */
    @PreAuthorize("@ss.hasPermi('item:bill:edit')")
    @Log(title = "出入库单据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StockBill stockBill)
    {
        return toAjax(stockBillService.updateStockBill(stockBill));
    }
    /**
     * 出库
     */
    @Log(title = "出库", businessType = BusinessType.UPDATE)
    @PutMapping("/out")
    public AjaxResult out(@RequestBody StockBillVo stockBillVo)
    {
        return toAjax(stockBillService.Out(stockBillVo));
    }

    //点击作废按钮修改状态
    @Log(title = "出入库作废", businessType = BusinessType.UPDATE)
    @PutMapping("/void/{id}")
    public AjaxResult voidBill(@PathVariable("id") Long id)
    {
        return toAjax(stockBillService.voidBill(id));
    }

    /**
     * 删除出入库单据
     */
    @PreAuthorize("@ss.hasPermi('item:bill:remove')")
    @Log(title = "出入库单据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stockBillService.deleteStockBillByIds(ids));
    }
}
