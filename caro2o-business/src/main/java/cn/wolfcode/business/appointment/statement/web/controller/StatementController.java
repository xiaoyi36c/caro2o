package cn.wolfcode.business.appointment.statement.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.appointment.statement.domain.vo.StatementItemVO;
import cn.wolfcode.business.appointment.statement.domain.vo.StatementVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import cn.wolfcode.business.appointment.statement.domain.Statement;
import cn.wolfcode.business.appointment.statement.service.IStatementService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 结算单Controller
 * 
 * @author li
 * @date 2023-11-04
 */
@RestController
@RequestMapping("/business/statement")
public class StatementController extends BaseController
{
    @Autowired
    private IStatementService statementService;

    /**
     * 查询结算单列表
     */
    @PreAuthorize("@ss.hasPermi('business:statement:list')")
    @GetMapping("/list")
    public TableDataInfo list(Statement statement)
    {
        startPage();
        List<Statement> list = statementService.selectStatementList(statement);
        return getDataTable(list);
    }

    /**
     * 导出结算单列表
     */
    @PreAuthorize("@ss.hasPermi('business:statement:export')")
    @Log(title = "结算单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Statement statement)
    {
        List<Statement> list = statementService.selectStatementList(statement);
        ExcelUtil<Statement> util = new ExcelUtil<Statement>(Statement.class);
        util.exportExcel(response, list, "结算单数据");
    }

    /**
     * 获取结算单详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:statement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(statementService.selectStatementById(id));
    }

    /**
     * 新增结算单
     */
    @PreAuthorize("@ss.hasPermi('business:statement:add')")
    @Log(title = "结算单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated StatementVO statementVO)
    {
        return toAjax(statementService.insertStatement(statementVO));
    }

    /**
     * 修改结算单
     */
    @PreAuthorize("@ss.hasPermi('business:statement:edit')")
    @Log(title = "结算单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated StatementVO statementVO)
    {
        return toAjax(statementService.updateStatement(statementVO));
    }

    /**
     * 删除结算单
     */
    @PreAuthorize("@ss.hasPermi('business:statement:remove')")
    @Log(title = "结算单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(statementService.deleteStatementById(id));
    }



    /**
     * 新增结算单明细
     */
    @PreAuthorize("@ss.hasPermi('business:statementItem:add')")
    @Log(title = "结算单明细", businessType = BusinessType.INSERT)
    @PostMapping("/item")
    public AjaxResult add(@RequestBody StatementItemVO statementItemVO)
    {
        return toAjax(statementService.insertStatementItem(statementItemVO));
    }
    /**
     * 获取结算单明细
     */
    @PreAuthorize("@ss.hasPermi('business:statement:query')")
    @GetMapping(value = "/items/{statementId}")
    public AjaxResult getStatementItems(@PathVariable("statementId") Long statementId)
    {
        return success(statementService.selectStatementItemByStatementId(statementId));
    }
    /**
     * 结算单支付
     */
    @PreAuthorize("@ss.hasPermi('business:statement:doPaid')")
    @Log(title = "结算单支付", businessType = BusinessType.UPDATE)
    @PutMapping("/paid/{statementId}")
    public AjaxResult doPaid(@PathVariable("statementId") Long statementId)
    {
        return toAjax(statementService.paid(statementId));
    }

    /**
     * 创建预约结算单
     */
    @PreAuthorize("@ss.hasPermi('business:statement:doCreateStatement')")
    @GetMapping(value = "/doCreateStatement/{appointmentId}")
    public AjaxResult doCreateStatement(@PathVariable("appointmentId") Long appointmentId)
    {
        return success(statementService.doCreateStatement(appointmentId));
    }

}
