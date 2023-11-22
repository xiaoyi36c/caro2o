package cn.wolfcode.client.controller;

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
import cn.wolfcode.client.domain.CustomerLinkmane;
import cn.wolfcode.client.service.ICustomerLinkmaneService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 客户联系人Controller
 * 
 * @author BaiShuai
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/client/linkmane")
public class CustomerLinkmaneController extends BaseController
{
    @Autowired
    private ICustomerLinkmaneService customerLinkmaneService;

    /**
     * 查询客户联系人列表
     */
    @PreAuthorize("@ss.hasPermi('client:linkmane:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerLinkmane customerLinkmane)
    {
        startPage();
        List<CustomerLinkmane> list = customerLinkmaneService.selectCustomerLinkmaneList(customerLinkmane);
        return getDataTable(list);
    }

    /**
     * 导出客户联系人列表
     */
    @PreAuthorize("@ss.hasPermi('client:linkmane:export')")
    @Log(title = "客户联系人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerLinkmane customerLinkmane)
    {
        List<CustomerLinkmane> list = customerLinkmaneService.selectCustomerLinkmaneList(customerLinkmane);
        ExcelUtil<CustomerLinkmane> util = new ExcelUtil<CustomerLinkmane>(CustomerLinkmane.class);
        util.exportExcel(response, list, "客户联系人数据");
    }

    /**
     * 获取客户联系人详细信息
     */
    @PreAuthorize("@ss.hasPermi('client:linkmane:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(customerLinkmaneService.selectCustomerLinkmaneById(id));
    }

    /**
     * 新增客户联系人
     */
    @PreAuthorize("@ss.hasPermi('client:linkmane:add')")
    @Log(title = "客户联系人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerLinkmane customerLinkmane)
    {
        return toAjax(customerLinkmaneService.insertCustomerLinkmane(customerLinkmane));
    }

    /**
     * 修改客户联系人
     */
    @PreAuthorize("@ss.hasPermi('client:linkmane:edit')")
    @Log(title = "客户联系人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerLinkmane customerLinkmane)
    {
        return toAjax(customerLinkmaneService.updateCustomerLinkmane(customerLinkmane));
    }

    /**
     * 删除客户联系人
     */
    @PreAuthorize("@ss.hasPermi('client:linkmane:remove')")
    @Log(title = "客户联系人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(customerLinkmaneService.deleteCustomerLinkmaneByIds(ids));
    }
}
