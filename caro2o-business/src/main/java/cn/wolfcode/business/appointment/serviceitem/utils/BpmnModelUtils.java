package cn.wolfcode.business.appointment.serviceitem.utils;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;

@Component
public class BpmnModelUtils {
    @Autowired
    private RepositoryService repositoryService;

    public BigDecimal getLimitDiscountPrice(String definationId, String definationKey) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(definationId);
        Process process = bpmnModel.getProcessById(definationKey);
        Collection<FlowElement> flowElements = process.getFlowElements();
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof SequenceFlow) {
                SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
                String conditionExpression = sequenceFlow.getConditionExpression();
                if (StringUtils.hasLength(conditionExpression) && conditionExpression.contains("discountPrice")) {
                    //System.out.println(conditionExpression);
                    //${discountPrice>=3000}
                    int index = conditionExpression.indexOf("=");
                    String discountPrice = conditionExpression.substring(index + 1, conditionExpression.length() - 1);
                    return new BigDecimal(discountPrice);
                }
            }
        }
        return BigDecimal.ZERO;
    }
}
