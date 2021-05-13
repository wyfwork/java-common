package com.wyf.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.wyf.common.domain.Organization;

import java.util.List;
import java.util.Map;

public class TreeUtils {

    /***
     * 递归获取集合Map实例，
     * 可根据自身需要返回不同数据类型集合数组
     * @param resultMap
     * @param organizationList
     * @return
     */
    public static Map<Long, Organization> getOrgList(Map<Long, Organization> resultMap, List<Organization> organizationList){

        if (CollectionUtil.isNotEmpty(organizationList)){
            for (Organization testEntity : organizationList) {
                resultMap.put(testEntity.getId(),testEntity);
                if (CollectionUtil.isNotEmpty(testEntity.getChild())) {
                    getOrgList(resultMap, testEntity.getChild());
                }
            }
        }
        return resultMap;
    }
}
