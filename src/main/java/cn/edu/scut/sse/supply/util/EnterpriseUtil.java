package cn.edu.scut.sse.supply.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yukino Yukinoshita
 */

public class EnterpriseUtil {

    private static Map<Integer, String> enterpriseCodeNameMap = new HashMap<>();

    public static String getEnterpriseNameByCode(int code) {
        return enterpriseCodeNameMap.getOrDefault(code, null);
    }

    public static void putCodeName(int code, String name) {
        enterpriseCodeNameMap.put(code, name);
    }

}
