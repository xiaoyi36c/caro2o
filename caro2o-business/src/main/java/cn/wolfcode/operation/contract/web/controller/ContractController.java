package cn.wolfcode.operation.contract.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.operation.contract.domain.Contract;
import cn.wolfcode.operation.contract.service.IContractService;
import org.apache.xmlbeans.impl.common.IOUtil;
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
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 合同信息Controller
 * 
 * @author ruoyi
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/system/contract")
public class ContractController extends BaseController
{
    @Autowired
    private IContractService contractService;

    /**
     * 查询合同信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(Contract contract)
    {
        startPage();
        List<Contract> list = contractService.selectContractList(contract);
        return getDataTable(list);
    }

    /**
     * 导出合同信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:contract:export')")
    @Log(title = "合同信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Contract contract)
    {
        List<Contract> list = contractService.selectContractList(contract);
        ExcelUtil<Contract> util = new ExcelUtil<Contract>(Contract.class);
        util.exportExcel(response, list, "合同信息数据");
    }

    /**
     *  下载合同信息
     */

    @PreAuthorize("@ss.hasPermi('system:contract:download')")
    @Log(title = "合同信息", businessType = BusinessType.EXPORT)
    @GetMapping("/download/{id}")
    public void download(HttpServletResponse response, @PathVariable("id") String id)
    {
        InputStream inputStream = contractService.download(id);
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
       try {
            IOUtil.copyCompletely(inputStream, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取合同信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:contract:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(contractService.selectContractById(id));
    }

    /**
     * 新增合同信息
     */
    @PreAuthorize("@ss.hasPermi('system:contract:add')")
    @Log(title = "合同信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Contract contract)
    {
        return toAjax(contractService.insertContract(contract));
    }

    /**
     * 修改合同信息
     */
    @PreAuthorize("@ss.hasPermi('system:contract:edit')")
    @Log(title = "合同信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Contract contract)
    {
        return toAjax(contractService.updateContract(contract));
    }

    /**
     * 删除合同信息
     */
    @PreAuthorize("@ss.hasPermi('system:contract:remove')")
    @Log(title = "合同信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(contractService.deleteContractByIds(ids));
    }

    /**
     * 审核合同
     */
    @PreAuthorize("@ss.hasPermi('system:contract:audit')")
    @Log(title = "合同信息", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}/{type}")
    public AjaxResult putAudit(@PathVariable Long id, @PathVariable String type)
    {
        return toAjax(contractService.putAudit(id,type));
    }

    /**
     * 作废合同
     */
    @PreAuthorize("@ss.hasPermi('system:contract:contract')")
    @Log(title = "合同信息", businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    public AjaxResult putContract(@PathVariable Long id)
    {
        return toAjax(contractService.putContract(id));
    }

    /**
     * 确认盖章
     */
    @PreAuthorize("@ss.hasPermi('system:contract:stamp')")
    @Log(title = "合同信息", businessType = BusinessType.UPDATE)
    @PutMapping("/stamp/{id}")
    public AjaxResult putStamp(@PathVariable Long id)
    {
        return toAjax(contractService.putStamp(id));
    }

}
