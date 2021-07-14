package com.qa.config;

import com.qa.pojo.ApiInfo;
import com.qa.pojo.ApiParams;
import com.qa.pojo.PrepData;
import com.qa.pojo.PublicVariables;
import com.qa.util.ApiInfoUtil;
import com.qa.util.ApiParamUtil;
import com.qa.util.ExcelUtil;
import com.qa.util.PublicVariablesUtil;

import java.util.List;

import static com.qa.constants.Constants.EXCLE_PATH;

/**
 * @ClassName ExcelDataConfig
 * @author: vinson.hu
 * @Description Excel中接口相关数据的配置
 * @date 2021/1/4 16:48
 * @Version 1.0版本
 */
public class ExcelDataConfig {
    private static List<ApiInfo> apiInfoList;
    private static List<ApiParams> apiParamsList;
    private static List<PublicVariables> pubVarsList;
    private static List<PrepData> prepDataList;

    //加载excel中的数据，存在各种的对象中
    static {
        apiInfoList = (List<ApiInfo>) ExcelUtil.read(EXCLE_PATH, 0, ApiInfo.class);
        apiParamsList = (List<ApiParams>) ExcelUtil.read(EXCLE_PATH, 1, ApiParams.class);
        pubVarsList = (List<PublicVariables>) ExcelUtil.read(EXCLE_PATH, 2, PublicVariables.class);
        prepDataList = (List<PrepData>) ExcelUtil.read(EXCLE_PATH, 3, PrepData.class);
    }


    /**
     * 获取接口信息，返回list
     *
     * @return java.util.List<com.qa.pojo.ApiInfo>
     * @author vinson.hu
     * @date 2021/1/4 17:44
     */

    public static List<ApiInfo> getApiInfoList() {
        return apiInfoList;
    }


    /**
     * 获取用例参数，返回list
     * 过滤掉不执行的用例
     *
     * @return java.util.List<com.qa.pojo.ApiParams>
     * @author vinson.hu
     * @date 2021/1/4 17:43
     */

    public static List<ApiParams> getApiParamsList() {
        return apiParamsList;
    }


    /**
     * 获取公共变量，返回list
     *
     * @param
     * @return java.util.List<com.qa.pojo.PublicVariables>
     * @author vinson.hu
     * @date 2021/5/17 15:34
     */

    public static List<PublicVariables> getPubVarsList() {
        return pubVarsList;
    }


    /**
     * 获取前置接口执行数据，返回list
     *
     * @param
     * @return java.util.List<com.qa.pojo.PrepData>
     * @author vinson.hu
     * @date 2021/5/21 16:50
     */

    public static List<PrepData> getPrepDataList() {
        return prepDataList;
    }

    /**
    *   获取前置处理接口的数据
    * @param
    * @return java.lang.Object[][]
    * @author vinson.hu
    * @date 2021/5/21 17:15
    */

    public static Object[][] getPrepData() {
        int size = getPrepDataList().size();
        Object[][] datas = new Object[size][4]; //数据提供者
        for (int i = 0; i < size; i++) {
            PrepData prepData = getPrepDataList().get(i);
            //二位数组中的每一个元素
            datas[i][0] = PublicVariablesUtil.getPubValueByKey("ip");
            datas[i][1] = PublicVariablesUtil.getPubValueByKey("token");
            datas[i][2] = PublicVariablesUtil.getPubValueByKey("appKey");
            datas[i][3] = prepData;
        }
        return datas;
    }


    /**
     * 获取所有用例相关信息（返回指定字段）
     *
     * @return java.lang.Object[][]
     * @author vinson.hu
     * @date 2021/1/4 17:44
     */

    public static Object[][] getApiParameters() {
        int size = getApiParamsList().size();
        Object[][] datas = new Object[size][5]; //数据提供者
        for (int i = 0; i < size; i++) {
            ApiParams apiParams = getApiParamsList().get(i);
            //二位数组中的每一个元素
            datas[i][0] = apiParams.getCaseId();
            datas[i][1] = apiParams.getCaseName();
            datas[i][2] = apiParams.getApiId();
            datas[i][3] = apiParams.getRequestData();
            datas[i][4] = apiParams.getExpectedReponseData();
        }
        return datas;
    }

    /**
     * 获取所有用例相关信息（返回对象）
     *
     * @return java.lang.Object[][]
     * @author vinson.hu
     * @date 2021/1/4 17:44
     */
    public static Object[][] getAllCase() {
        int size = ApiParamUtil.getApiParamsList().size();
        Object[][] datas = new Object[size][2]; //数据提供者
        for (int i = 0; i < size; i++) {
            ApiParams apiParams = ApiParamUtil.getApiParamsList().get(i);
            datas[i][0] = ApiInfoUtil.getApiInfoByApiId(apiParams.getApiId());
            datas[i][1] = apiParams;
        }
        return datas;
    }


    /**
     * 根据apiId获取用例相关信息
     *
     * @param apiId
     * @return java.lang.Object[][]
     * @author vinson.hu
     * @date 2021/1/15 11:53
     */

    public static Object[][] getCaseByApiId(String apiId) {
        int size = ApiParamUtil.getApiParamByApiId(apiId).size();

        Object[][] datas = new Object[size][2]; //数据提供者
        for (int i = 0; i < size; i++) {
            datas[i][0] = ApiInfoUtil.getApiInfoByApiId(apiId);                 //存入apiInfo
            datas[i][1] = ApiParamUtil.getApiParamByApiId(apiId).get(i);        //存入apiParam
        }
        return datas;
    }

    /**
     * 通过caseId获取用例相关信息
     *
     * @param caseId
     * @return java.lang.Object[][]
     * @author vinson.hu
     * @date 2021/1/18 18:37
     */

    public static Object[][] getCaseByCaseId(String caseId) {

        Object[][] datas = new Object[1][2];
        String apiId = ApiParamUtil.getApiParamsMap().get(caseId).getApiId();
        datas[0][0] = ApiInfoUtil.getApiInfoByApiId(apiId);                 //存入apiInfo
        datas[0][1] = ApiParamUtil.getApiParamByCaseId(caseId);             //存入apiParam
        return datas;
    }

    /**
     * 测试方法
     *
     * @param args
     * @return void
     * @author vinson.hu
     * @date 2021/1/4 17:42
     */

    public static void main(String[] args) {
//        for (PublicVariables publicVariables : pubVarsList) {
//            System.out.println(publicVariables);
//        }
//        for (ApiInfo apiInfo : apiInfoList) {
//            System.out.println(apiInfo);
//        }
//        for (ApiParams apiParams : apiParamsList) {
//            System.out.println(apiParams);
//        }


//        int size = getApiParamsList().size();
//        System.out.println("长度" + size);
//        for (int i = 0; i < size; i++) {
//            System.out.println(getApiParamsList().get(i));
//
//        }

    }


}
