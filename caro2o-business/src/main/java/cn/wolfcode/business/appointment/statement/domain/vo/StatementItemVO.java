package cn.wolfcode.business.appointment.statement.domain.vo;

import cn.wolfcode.business.appointment.statement.domain.StatementItem;
import cn.wolfcode.common.core.domain.BaseEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 * 结算单对象 bus_statement
 * 
 * @author li
 * @date 2023-11-04
 */
public class StatementItemVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long statementId;//结算单id
    private BigDecimal discountAmount;//优患金额
    private List<StatementItem> items;//明细集合

    @Override
    public String toString() {
        return "StatementItemVO{" +
                "statementItemId=" + statementId +
                ", discountAmount=" + discountAmount +
                ", items=" + items +
                '}';
    }

    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public List<StatementItem> getItems() {
        return items;
    }

    public void setItems(List<StatementItem> items) {
        this.items = items;
    }



}
