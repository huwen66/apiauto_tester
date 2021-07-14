package com.qa.util;


import com.qa.pojo.ApiParams;
import com.qa.pojo.CellData;
import com.qa.pojo.ExcelObject;
import com.qa.pojo.PublicVariables;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
     * 收集回写用例数据的容器
     */
    public static List<CellData> dataToWriteList = new ArrayList<>();

    /**
     * 收集回写公共参数的容器
     */
    public static List<CellData>  publicVarsList= new ArrayList<>();

    private static Logger logger = Logger.getLogger(ExcelUtil.class);

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
        Object[][] datas = new Object[endRow - startRow + 1][endCell - startCell + 2];
        //获得Excel文件输入流（加载资源作为流）
        InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
        try {
            //获得工作簿
            Workbook workbook = WorkbookFactory.create(is);
            //得到sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //循环所有的行
            for (int i = startRow; i <= endRow; i++) {
                //得到每一行
                Row row = sheet.getRow(i - 1);
                //把每行的号设置到每一行的数组的第一个元素
                datas[i - startRow][0] = i;
                //循环每一行的每一列
                for (int j = startCell; j <= endCell; j++) {
                    //得到当前遍历的cell
                    Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    //把所有列的类型都设置为String类型
                    cell.setCellType(CellType.STRING);
                    String cellValue = cell.getStringCellValue();
                    datas[i - startRow][j - startCell + 1] = cellValue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 读取excel指定sheet的数据，传入字节码对象，返回list的字节码对象
     *
     * @param excelPath  文件的路径
     * @param sheetIndex 文件的sheet页下标
     * @param clazz      字节码对象（apiinfo、apiparams、publicVariables）
     * @return java.util.List<? extends com.qa.pojo.ExcelObject>
     * @author vinson.hu
     * @date 2021/1/5 0:08
     */

    public static List<? extends ExcelObject> read(String excelPath, int sheetIndex, Class<? extends ExcelObject> clazz) {
        //获得Excel文件输入流（加载资源作为流）
        InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
        List<ExcelObject> excelObjectList = new ArrayList<>();
        try {
            //获得工作簿-->面向接口编程
            Workbook workbook = WorkbookFactory.create(is);
            //得到sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //第一行是相当于该对象的各个属性，只要得到第一行
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
            }
            //除了第一行：ApiInfo对象、ApiParams对象，遍历除了第一行的其他所有行
            //首先要得到最大的行号
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                //得到了除了第一行的每一行-->ApiInfo或者ApiParams或者是其他的sheet对应的对象
                ExcelObject object = clazz.newInstance();
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
                    String fieldName = fieldArray[j];
                    //得到setter方法名
                    String setter = "set" + fieldName;
                    //获得这个方法
                    Method setterMothod = clazz.getMethod(setter, String.class);
                    //反射调用这个setter方法，完成属性的设值
                    setterMothod.invoke(object, cellValue);
                }//如果是公共变量对象，则把值存入到公共参数中
                if (object instanceof PublicVariables) {
                    PublicVariables publicVariables = (PublicVariables) object;
                    ParamUtil.addGlobalData(publicVariables.getKey(), publicVariables.getValue());
                    publicVariables.getValue();
                }
                excelObjectList.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelObjectList;
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
            //获得工作簿
            workbook = new XSSFWorkbook(is);
            //得到sheet
            XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            //得到最大行数
            int lastRowNum = sheet.getLastRowNum();
            //循环所有的行
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
     * @param key        固定第一行（通过匹配到某一行）
     * @param cellNum    列号（写入的列）
     * @param value      回写的结果
     */
    public static void writeExcel(String filePath, int sheetIndex, String key, int cellNum, String value) {
        Workbook workbook = null;
        InputStream is = ExcelUtil.class.getResourceAsStream(filePath);
        OutputStream stream = null;
        try {
            workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 0; i < lastRowNum; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String cellValue = cell.getStringCellValue();
                //找到了该行
                if (cellValue.equals(key)) {
                    //在该行的cellNum列回写结果
                    Cell cellToBeWrite = row.getCell(cellNum - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cellToBeWrite.setCellType(CellType.STRING);
                    //设置该行的数据
                    cellToBeWrite.setCellValue(value);
                    logger.info("写入的key:"+key+",value:"+value);
                    //已经找到此行，跳出整个循环
                    break;
                }
            }
            stream = new FileOutputStream(new File("src/main/resources" + filePath));//类路径
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
            //获得apiParam的的信息（通过caseId得到rowNum）
            Map<String, ApiParams> apiParamsMap = ApiParamUtil.getApiParamsMap();
            //循环容器中间所有数据
            for (CellData cellData : dataToWriteList) {
                //cellData:拿到写数据的那一行
                ApiParams apiParams = apiParamsMap.get(cellData.getCaseId());
                //得到写的行号
                int rowNum = apiParams.getRowNum();
                //拿到当前行
                Row row = sheet.getRow(rowNum - 1);
                //需要写数据的cell
                Cell cellToBeWrite = row.getCell(cellData.getCellNum() - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cellToBeWrite.setCellType(CellType.STRING);
                //设置该行的数据
                cellToBeWrite.setCellValue(cellData.getDataStr());
            }
            stream = new FileOutputStream(new File(targetPath));//类路径
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
     * 批量写回公共变量
     *
     * @param sourcePath 源文件路径
     * @param sheetIndex sheet索引
     */
    public static void batchWriteVars(String sourcePath ,int sheetIndex) {
        Workbook workbook = null;
        InputStream is = ExcelUtil.class.getResourceAsStream(sourcePath);
        OutputStream stream = null;
        try {
            //创建workbook对象
            workbook = WorkbookFactory.create(is);
            //获得要操作sheet
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            //获得公共变量的的信息（通过key得到rowNum）
            Map<String, PublicVariables> pubVarsMap = PublicVariablesUtil.getPubVarsMap();
            //循环容器中间所有数据
            for (CellData cellData : publicVarsList) {
                //cellData:拿到写数据的那一行
              PublicVariables publicVariables=  pubVarsMap.get(cellData.getCaseId());
                //得到写的行号
                int rowNum = publicVariables.getRowNum();
                //拿到当前行
                Row row = sheet.getRow(rowNum - 1);
                //需要写数据的cell
                Cell cellToBeWrite = row.getCell(cellData.getCellNum() - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //设置该行的数据
                cellToBeWrite.setCellValue(cellData.getDataStr());
            }
            stream = new FileOutputStream(new File("src/main/resources" + sourcePath));//类路径
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
//        read("/api_test_info.xlsx", 0, ApiParams.class);
        writeExcel("/api_test_info.xlsx", 2, "phone", 2, "13800000001");
    }
}
