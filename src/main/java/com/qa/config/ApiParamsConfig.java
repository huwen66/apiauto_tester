package com.qa.config;

import com.qa.pojo.ApiParams;
import com.qa.util.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

import static com.qa.constants.Constants.EXCLE_PATH;


public class ApiParamsConfig {

    private static List<ApiParams> apiParamsList;

    static {
        apiParamsList = new ArrayList<ApiParams>();

        //加载出所有的apiInfo对象
        ExcelUtil.read(EXCLE_PATH, 1, ApiParams.class);
    }

    public static void main(String[] args) {

        Object[][] datas = getApiParameters();
        for (Object[] objects : datas) {
            for (Object object : objects) {
                System.out.print(object + ",   ");
            }
            System.out.println();
        }
    }

    public static List<ApiParams> getApiParamsList() {
        return apiParamsList;
    }

    //获得数据提供者需要的参数
    public static Object[][] getApiParameters() {
        int size = apiParamsList.size();
        Object[][] datas = new Object[size][6]; //容器--》数据提供者

        for (int i = 0; i < size; i++) {
            ApiParams apiParams = apiParamsList.get(i);
            //二位数组中的每一个元素
            datas[i][0] = apiParams.getCaseId();
            datas[i][1] = apiParams.getApiId();
            datas[i][2] = apiParams.getRequestData();
            datas[i][3] = apiParams.getExpectedReponseData();
            datas[i][4] = apiParams.getPreValidateSql();
            datas[i][5] = apiParams.getAfterValidateSql();
        }

        return datas;
    }


}
