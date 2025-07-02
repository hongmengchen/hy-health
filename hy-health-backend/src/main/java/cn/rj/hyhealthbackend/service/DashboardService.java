package cn.rj.hyhealthbackend.service;

import cn.rj.hyhealthbackend.domain.DrugCompany;
import cn.rj.hyhealthbackend.domain.Sale;
import cn.rj.hyhealthbackend.mapper.CompanyMapper;
import cn.rj.hyhealthbackend.mapper.DoctorMapper;
import cn.rj.hyhealthbackend.mapper.DrugMapper;
import cn.rj.hyhealthbackend.mapper.SaleMapper;
import cn.rj.hyhealthbackend.model.*;
import cn.rj.hyhealthbackend.param.DoctorParam;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陈亮
 * <p>
 * DashboardService
 */
@Service
public class DashboardService {
    @Autowired
    DoctorMapper doctorMapper;
    @Autowired
    DrugMapper drugMapper;
    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    SaleMapper saleMapper;
    @Autowired
    MaterialService materialService;
    @Autowired
    CompanyPolicyService companyPolicyService;

    public ObjectNode getDashboardData() {
        List<DoctorModel> allDoctor = doctorMapper.getAllDoctor(new DoctorParam());
        //        获取控制面板药品、医师、药店和公司数量
        List<DrugModel> allDrug = drugMapper.getAllDrug("");
        List<DrugCompany> allCompany = companyMapper.getAllCompany("");
        List<Sale> allSale = saleMapper.getAllSale("");
        int doctorNumb = allDoctor.size();
        int drugNumb = allDrug.size();
        int companyNumb = allCompany.size();
        int saleNumb = allSale.size();
//        获取每个doctor等级
        int docLevel1 = 0, docLevel2 = 0, docLevel3 = 0;
        List<TreatTypeModel> allTreatType = doctorMapper.getAllTreatType();
        HashMap<String, Integer> treatMap = new HashMap<>();
//        构建Map获取所有的诊治类型
        for (TreatTypeModel treat : allTreatType) {
            treatMap.put(treat.getName(), 0);
        }
//        通过医生信息获取所有的医生级别和诊治类型医生数量
        for (DoctorModel doc : allDoctor) {
            if (doc.getLevelId() == 1) {
                docLevel1++;
            } else if (doc.getLevelId() == 2) {
                docLevel2++;
            }
            if (doc.getLevelId() == 3) {
                docLevel3++;
            }
            treatMap.put(doc.getTreatType(), treatMap.get(doc.getTreatType()) + 1);
        }
        ;
        List<MaterialModel> allMaterial = materialService.getFirstMaterialWithPage();
        List<CompanyPolicyModel> allPolicy = companyPolicyService.getFirstPolicyWithPage();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("doctorNumb", doctorNumb);
        objectNode.put("drugNumb", drugNumb);
        objectNode.put("companyNumb", companyNumb);
        objectNode.put("saleNumb", saleNumb);
        ObjectNode docLevelNode = objectMapper.createObjectNode();
        docLevelNode.put("l1", docLevel1);
        docLevelNode.put("l2", docLevel2);
        docLevelNode.put("l3", docLevel3);
        objectNode.put("docLevel", docLevelNode);
        JsonNode treatNode = objectMapper.convertValue(treatMap, JsonNode.class);
        objectNode.put("treatMap", treatNode);
        JsonNode materialNode = objectMapper.valueToTree(allMaterial);
        JsonNode policyNode = objectMapper.valueToTree(allPolicy);
        objectNode.put("materials", materialNode);
        objectNode.put("policys", policyNode);
        return objectNode;
    }
}
