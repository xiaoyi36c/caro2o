//package cn.wolfcode;
//
//import org.activiti.bpmn.model.BpmnModel;
//import org.activiti.bpmn.model.FlowElement;
//import org.activiti.bpmn.model.Process;
//import org.activiti.bpmn.model.SequenceFlow;
//import org.activiti.engine.RepositoryService;
//import org.apache.poi.ss.formula.functions.T;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.util.StringUtils;
//
//import java.util.Collection;
//
//@SpringBootTest
//public class BpmnModelTest {
//    @Autowired
//    private RepositoryService repositoryService;
//
//    @Test
//    public void testBpmnModelTest() {
//        BpmnModel bpmnModel = repositoryService.getBpmnModel("bus_carPackage_audit:10:20004");
//        Process process = bpmnModel.getProcessById("bus_carPackage_audit");
//        Collection<FlowElement> flowElements = process.getFlowElements();
//        for (FlowElement flowElement : flowElements) {
//            if (flowElement instanceof SequenceFlow) {
//                SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
//                String conditionExpression = sequenceFlow.getConditionExpression();
//                if (StringUtils.hasLength(conditionExpression) && conditionExpression.contains("discountPrice")) {
//                    //System.out.println(conditionExpression);
//                    //${discountPrice>=3000}
//                    int index = conditionExpression.indexOf("=");
//                    String discountPrice = conditionExpression.substring(index + 1, conditionExpression.length() - 1);
//                    System.out.println(discountPrice);
//                    return;
//                }
//            }
//        }
//    }
//}
