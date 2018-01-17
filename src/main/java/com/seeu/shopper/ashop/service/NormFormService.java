package com.seeu.shopper.ashop.service;

import com.seeu.shopper.ashop.domain.detail.NormItem;
import com.seeu.shopper.ashop.domain.detail.NormMap;
import com.seeu.shopper.product.model.ProductNorm;
import com.seeu.shopper.product.service.ProductNormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 03/09/2017.
 */
@Service
public class NormFormService {

    @Resource
    ProductNormService productNormService;

    /**
     * 将链状数据处理为 map 三维数据
     *
     * @param normList
     * @return
     */
    public ArrayList<NormMap> formNorms(List<ProductNorm> normList) {
        ArrayList<NormMap> normMaps = new ArrayList<>();
        for (ProductNorm norm : normList) { // 添加组名信息
            if (-1 == norm.getTeamId()) { // 组名
                String normColName = norm.getNormName();
                Integer normColId = norm.getId();
                NormMap normMap = new NormMap();
                normMap.setNormColId(normColId);
                normMap.setNormColName(normColName);
                normMap.setNormItems(new ArrayList<>());
                normMaps.add(normMap);
            }
        }
        for (ProductNorm norm : normList) { // 添加成员信息
            if (-1 != norm.getTeamId()) { // 非组名
                String normName = norm.getNormName();
                Integer normId = norm.getId();
                NormItem item = new NormItem();
                item.setNormId(normId);
                item.setNormName(normName);
                // 添加到组内
                for (NormMap map : normMaps) {
                    if (map.getNormColId().equals(norm.getTeamId())) {
                        map.getNormItems().add(item);
                    }
                }
            }
        }
        return normMaps;
    }

    /**
     * 根据 normids 换取对应的名字
     * <p>
     * 传入值必须是不为空
     */
    // TODO 优化查询！！
    public Map<Integer, String> getNormName(String normIDs) {
        // 转为 int
        List<ProductNorm> norms = productNormService.findByIds(normIDs.replaceAll("_", ","));
        HashMap<Integer, String> map = new HashMap<>();
        for (ProductNorm norm : norms) {
            map.put(norm.getId(), norm.getNormName());
        }
        return map;
    }
}
