package com.qa.util;


import com.qa.config.ApiInfoConfig;
import com.qa.config.ApiParamsConfig;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.qa.pojo.ApiInfo;
import com.qa.pojo.ApiParams;
import com.qa.pojo.CellData;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * excel读写工具类
 *
 * @ClassName ExcelUtil
 * @Author vinson.hu
 * @Date 2020/9/24
 * @Version V1.0
 **/
public class ExcelUtil {

    /**
     * 收集要写的数据的容器，容器中就是一个一个的要写的CellData对象
     */
    public static List<CellData> dataToWriteList = new ArrayList<CellData>();


    /**
     * @param excelPath  文件路径 (类路径的根路径：/开头)
     * @param sheetIndex sheet的索引
     * @param startRow   开始行
     * @param endRow     结束行（excel中的行号）
     * @param startCell  开始列
     * @param endCell    结束列（excel中的列号）
     * @return 二维数组
     */
    @Deprecated
    public static Object[][] readExcel2(String excelPath, int sheetIndex, int startRow, int endRow, int startCell,
                                        int endCell) {
        //创建一个二维数组
        //第2行到第7行  第1列到第4列
        //1到2有个几个数字--》2 --》（2-1） + 1
        //2到5有几个数字--》4个数字 --》（5-2） + 1
        Object[][] datas = new Object[endRow - startRow + 1][endCell - startCell + 1];

        //1：获得Excel文件输入流（加载资源作为流） -->"/register.xlsx"
        InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
        try {
            //2:获得工作簿-->面向接口编程
            Workbook workbook = WorkbookFactory.create(is);
            //3：得到sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //4：循环所有的行
            for (int i = startRow; i <= endRow; i++) {
                //6：得到每一行
                Row row = sheet.getRow(i - 1); //
                //7:循环每一行的每一列
                for (int j = startCell; j <= endCell; j++) {
                    //8：得到当前遍历的cell
                    Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    //9：把所有列的类型都设置为String类型
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();

                    datas[i - startRow][j - startCell] = cellValue;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 读取excel，包含行号
     *
     * @param excelPath
     * @param sheetIndex
     * @param startRow
     * @param endRow
     * @param startCell
     * @param endCell
     * @return
     */
    public static Object[][] readExcelWithRowNum(String excelPath, int sheetIndex, int startRow, int endRow,
                                                 int startCell, int endCell) {
        //创建一个二维数组
        //第2行到第7行  第1列到第4列
        //1到2有个几个数字--》2 --》（2-1） + 1
        //2到5有几个数字--》4个数字 --》（5-2） + 1
        Object[][] datas = new Object[endRow - startRow + 1][endCell - startCell + 2];

        //1：获得Excel文件输入流（加载资源作为流） -->"/register.xlsx"
        InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
        try {
            //2:获得工作簿-->面向接口编程
            Workbook workbook = WorkbookFactory.create(is);
            //3：得到sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //4：循环所有的行
            for (int i = startRow; i <= endRow; i++) {
                //6：得到每一行
                Row row = sheet.getRow(i - 1); //
                //把每行的号设置到每一行的数组的第一个元素
                datas[i - startRow][0] = i;

                //7:循环每一行的每一列
                for (int j = startCell; j <= endCell; j++) {
                    //8：得到当前遍历的cell
                    Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    //9：把所有列的类型都设置为String类型
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();

                    //当i等于startRow，j等于startCell--》datas[0][0]
                    //当i等于startRow，j等于startCell+1 --》datas[0][1]

                    datas[i - startRow][j - startCell + 1] = cellValue;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    public static void read(String excelPath, int sheetIndex, Class<?> clazz) {
        //1：获得Excel文件输入流（加载资源作为流） -->"/register.xlsx"
        InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
        try {
            //获得工作簿-->面向接口编程
            Workbook workbook = WorkbookFactory.create(is);
            //得到sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //1：第一行是相当于该对象的各个属性，只要得到第一行
            Row firstRow = sheet.getRow(0);
            //得到最大的列号
            int lastCellNum = firstRow.getLastCellNum();

            //遍历第一行的每一列
            String[] fieldArray = new String[lastCellNum];

            for (int i = 0; i < lastCellNum; i++) {
                Cell cell = firstRow.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                //得到每列的值
                String cellValue = cell.getStringCellValue();
                fieldArray[i] = cellValue.substring(0, cellValue.indexOf("("));
                System.out.print(fieldArray[i] + ",   ");
            }

            System.out.println("-------------------------");
            //2：除了第一行：ApiInfo对象、ApiParams对象，遍历除了第一行的其他所有行
            //首先要得到最大的行号
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {//i:表示行的索引:i=1表示为第2行
                //得到了除了第一行的每一行-->ApiInfo或者ApiParams或者是其他的sheet对应的对象
                Object object = clazz.newInstance();
                String setNumMethodStr = "setRowNum";
                Method setNumMethod = clazz.getMethod(setNumMethodStr, int.class);
                setNumMethod.invoke(object, i + 1);
                Row row = sheet.getRow(i);
                //遍历每一列
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    //得到每列的值
                    String cellValue = cell.getStringCellValue();
                    //把这每列的值设值到这个对象中

                    //属性从哪来？？？属性的名称从哪里来-->从上面的fieldArray中获取
                    String fieldName = fieldArray[j];
                    //得到setter方法名
                    String setter = "set" + fieldName;
                    //获得这个方法
                    Method setterMothod = clazz.getMethod(setter, String.class);
                    //反射调用这个setter方法，完成属性的设值
                    setterMothod.invoke(object, cellValue);
                }
                //ApiInfo-->ApiInfo列表--》容器 --》全局只有一份容器
                //如果说传入的类型是apiInfo的话
                if (object instanceof ApiInfo) {
                    ApiInfo apiInfo = (ApiInfo) object;
                    ApiInfoConfig.getApiInfoList().add(apiInfo);
                } else if (object instanceof ApiParams) {
                    ApiParams apiParams = (ApiParams) object;
                    ApiParamsConfig.getApiParamsList().add(apiParams);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取excel某个sheet的所有数据
     *
     * @param excelPath
     * @param sheetIndex
     */
    public static void readExcelAllData(String excelPath, int sheetIndex) {

        InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
        XSSFWorkbook workbook = null;
        try {
            //2:获得工作簿-->面向接口编程
            workbook = new XSSFWorkbook(is);
            //3：得到sheet
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            //3:得到最大行数
            int lastRowNum = sheet.getLastRowNum();
            //5：循环所有的行
            for (int i = 0; i <= lastRowNum; i++) {
                //得到每一行
                XSSFRow row = sheet.getRow(i);
                //得到当前行的最大列号（有几列就输出几）
                int lastCellNum = row.getLastCellNum();
                //遍历每行的所有列,
                for (int j = 0; j < lastCellNum; j++) {
                    XSSFCell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    //					System.out.print("[" + cellValue + "] - ");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入结果到某个excel的某一列
     *
     * @param filePath   excel的路径
     * @param sheetIndex sheet的索引
     * @param caseId     测试用例的id：通过这个id可以匹配到某一行
     * @param cellNum    列号，明确要写的cell
     * @param resultStr  回写的结果
     */
    @Deprecated
    public static void writeExcel(String filePath, int sheetIndex, String caseId, int cellNum, String resultStr) {
        Workbook workbook = null;
        InputStream is = ExcelUtil.class.getResourceAsStream(filePath);
        OutputStream stream = null;
        try {
            //创建workbook对象
            workbook = WorkbookFactory.create(is);
            //获得要操作sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //确定哪一行--》（遍历每一行，如果这行的第一列的值等于caseId，这就是我要找的行）
            //得到最大行数
            int lastRowNum = sheet.getLastRowNum();
            //循环所有的行
            for (int i = 0; i < lastRowNum; i++) {
                //拿到当前行
                Row row = sheet.getRow(i);
                //拿到该行的第一列
                Cell cell = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                //得到该列的值
                String cellValue = cell.getStringCellValue();
                //找到了该行
                if (cellValue.equals(caseId)) {
                    //在该行的cellNum列回写结果
                    //需要写数据的cell
                    Cell cellToBeWrite = row.getCell(cellNum - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cellToBeWrite.setCellType(CellType.STRING);
                    //设置该行的数据
                    cellToBeWrite.setCellValue(resultStr);
                    //已经找到此行，跳出整个循环
                    break;
                }
            }
            stream = new FileOutputStream(new File("target/com.qa.testcase.test-classes/rest_info.xlsx"));//相对于我们类路径
            workbook.write(stream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 批量写回
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标路径
     * @param sheetIndex sheet索引
     */
    public static void batchWriteData(String sourcePath, String targetPath, int sheetIndex) {
        Workbook workbook = null;
        InputStream is = ExcelUtil.class.getResourceAsStream(sourcePath);
        OutputStream stream = null;
        try {
            //创建workbook对象
            workbook = WorkbookFactory.create(is);
            //获得要操作sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //获得这个apiParam的的信息（主要是想通过caseId得到rowNum）
            Map<String, ApiParams> apiParamsMap = ApiParamUtil.getApiParamsMap();
            //循环容器中间所有数据
            for (CellData cellData : dataToWriteList) {
                //cellData:这个是要往某一行某一列写入某数据的对象
                ApiParams apiParams = apiParamsMap.get(cellData.getCaseId());
                //得到我要写行号
                int rowNum = apiParams.getRowNum();
                //拿到当前行
                Row row = sheet.getRow(rowNum - 1);
                //需要写数据的cell
                Cell cellToBeWrite = row.getCell(cellData.getCellNum() - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cellToBeWrite.setCellType(CellType.STRING);
                //设置该行的数据
                cellToBeWrite.setCellValue(cellData.getDataStr());
            }

            stream = new FileOutputStream(new File(targetPath));//相对于我们类路径
            workbook.write(stream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        read("/api_test_info.xlsx", 0, ApiInfo.class);
    }

}
