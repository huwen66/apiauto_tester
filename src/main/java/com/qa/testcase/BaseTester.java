package com.qa.testcase;

import com.qa.util.ExcelUtil;
import org.testng.annotations.AfterSuite;

import static com.qa.constants.Constants.EXCLE_PATH;
import static com.qa.constants.Constants.EXCLE_WRITE_BACK_PATH;


public class BaseTester {

    @AfterSuite
    public void afterSuite() {
        //批量回写数据
//        ExcelUtil.batchWriteData(EXCLE_PATH, "target/classes/rest_info.xlsx", 1);
        ExcelUtil.batchWriteData(EXCLE_PATH, EXCLE_WRITE_BACK_PATH, 1);
    }

}
