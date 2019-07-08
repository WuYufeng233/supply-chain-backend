package cn.edu.scut.sse.supply.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yukino Yukinoshita
 */

public class EnterpriseUtil {

    /**
     * 企业代码 -> 企业名称 映射表
     */
    private static Map<Integer, String> enterpriseCodeNameMap = new HashMap<>();

    /**
     * 从映射表读取企业名称
     *
     * @param code 企业代码
     * @return 企业名称， null if not present
     */
    public static String getEnterpriseNameByCode(int code) {
        return enterpriseCodeNameMap.getOrDefault(code, null);
    }

    public static void putCodeName(int code, String name) {
        enterpriseCodeNameMap.put(code, name);
    }

}
