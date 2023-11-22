package cn.wolfcode.business.appointment.statement.mapper;

import java.util.List;
import cn.wolfcode.business.appointment.statement.domain.StatementItem;

/**
 * 结算单明细Mapper接口
 * 
 * @author li
 * @date 2023-11-07
 */
public interface StatementItemMapper 
{
    /**
     * 查询结算单明细
     * 
     * @param id 结算单明细主键
     * @return 结算单明细
     */
    public StatementItem selectStatementItemById(Long id);

    /**
     * 查询结算单明细列表
     * 
     * @param statementItem 结算单明细
     * @return 结算单明细集合
     */
    public List<StatementItem> selectStatementItemList(StatementItem statementItem);

    /**
     * 新增结算单明细
     * 
     * @param statementItem 结算单明细
     * @return 结果
     */
    public int insertStatementItem(List<StatementItem> statementItem);

    /**
     * 修改结算单明细
     * 
     * @param statementItem 结算单明细
     * @return 结果
     */
    public int updateStatementItem(StatementItem statementItem);

    /**
     * 删除结算单明细
     * 
     * @param id 结算单明细主键
     * @return 结果
     */
    public int deleteStatementItemById(Long id);

    /**
     * 批量删除结算单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStatementItemByIds(Long[] ids);

    List<StatementItem> selectStatementItemByStatementId(Long statementId);

    int deleteStatementItemBStatementId(Long statementId);
}
