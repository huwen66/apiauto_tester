package com.qa.util;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import static com.qa.constants.Constants.JDBC_FILE_PATH;

/**
 * jdbc的工具类
 *
 * @author vinson
 * @date 2018年5月11日
 * @desc
 */
public class JDBCUtil {

    /**
     * jdbc的连接字符串
     */
    private static String url;

    /**
     * 连接数据库的用户名
     */
    private static String user;

    /**
     * 连接数据库的密码
     */
    private static String password;

    /**
     * 静态代码块
     * 只会在类被加载到jvm时运行一次，适合放一些只需要操作一次的业务逻辑
     */
    static {
        try {

            Properties properties = new Properties();
            properties.load(JDBCUtil.class.getResourceAsStream(JDBC_FILE_PATH));
            String driverName = properties.getProperty("jdbc.driver");
            Class.forName(driverName);
            url = properties.getProperty("jdbc.url");
            user = properties.getProperty("jdbc.user");
            password = properties.getProperty("jdbc.password");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得一个连接的方法
     *
     * @return 数据库连接
     */
    private static Connection getConnection() {
        Connection conn = null;
        try {
            //2：获得一个数据库连接
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 增、删、改操作
     *
     * @param sql
     * @param params
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void excute(String sql, Object... params) {
        //得到一个连接
        Connection conn = null;
        //4:预编译的陈述对象(防止sql注入)
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            //参数占位符的功能
            int length = params.length;
            for (int i = 0; i < length; i++) {
                //拿到每一个参数
                Object paramObj = params[i];
                //占位符参数的设置
                pstmt.setObject(i + 1, paramObj);
            }

            //5：语句的执行
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt);
        }
    }

    /**
     * 查询
     *
     * @param sql
     * @param params
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static List<Map<String, String>> query(String sql, Object... params) {

        //保存结果集的list
        List<Map<String, String>> recordsList = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            //得到一个连接
            conn = getConnection();
            //4:陈述对象
            pstmt = conn.prepareStatement(sql);
            //参数占位符的功能
            int length = params.length;
            for (int i = 0; i < length; i++) {
                //拿到每一个参数
                Object paramObj = params[i];
                //占位符参数的设置
                pstmt.setObject(i + 1, paramObj);
            }

            //5：语句的执行:得到结果集
            resultSet = pstmt.executeQuery(sql);
            //结果记录数、记录的字段数是不确定的

            //把从数据库查询出来的记录保存到一个list中间
            /**
             * （重点！！！！！！）
             * 一个excel中一行 = 数据库中的一行 = 一个java自定义类对象 = hashMap = json字典  = xml
             */
            recordsList = new ArrayList<Map<String, String>>();

            //获得结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //列数
            int columnCount = metaData.getColumnCount();

            //当我们的结果集中间还有下一条记录时
            while (resultSet.next()) {
                //方法：每条记录封装到一个map去
                Map<String, String> recordMap = new HashMap<String, String>();
                //recordMap.put(列名, 对应的值);
                //字段数不确定，是不是有方法能够拿到字段数
                for (int i = 1; i <= columnCount; i++) {
                    //从元数据得到列名(性能问题，可以放到外面去一次性把字段名收集起来)
                    String columnName = metaData.getColumnName(i);
                    //从结果集得到该列的值
                    String columnValue = resultSet.getString(i);
                    //把该列值以键值对方式put到map
                    recordMap.put(columnName, columnValue);
                }
                //添加当前记录到list
                recordsList.add(recordMap);
            }
            //6：资源的关闭
            pstmt.close();
            conn.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //资源的释放和关闭
            close(conn, pstmt, resultSet);
        }

        return recordsList;
    }

    /**
     * 关闭资源
     *
     * @param conn      连接
     * @param pstmt     陈述对象
     * @param resultSet 结果集
     */
    private static void close(Connection conn, PreparedStatement pstmt, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(conn, pstmt);
    }

    /**
     * 关闭资源
     *
     * @param conn  连接
     * @param pstmt 陈述对象
     */
    private static void close(Connection conn, PreparedStatement pstmt) {
        //6：资源的关闭
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insert() {
        String sql = "insert into member(regName,pwd,mobilePhone) values(?,?,?);";
        JDBCUtil.excute(sql, "jack", "654321", "13333333333");
    }

    private static void delete() {
        String sql = "delete from member where id=?";
        JDBCUtil.excute(sql, 3);
    }

    private static void update() {
        String sql = "update member set leaveAmount=? where id=?";
        JDBCUtil.excute(sql, 1000000.0, 2);
    }

    private static void query() {
        String sql = "select * from member;";
        List<Map<String, String>> memberList = JDBCUtil.query(sql);
        for (Map<String, String> memberMap : memberList) {
            System.out.println(memberMap);
        }

    }

    public static void main(String[] args) {
//		insert();
//		delete();
//		update();
//		query();
        List<Map<String, String>> recordsList = null;
//		System.out.println(JSONObject.toJSONString(recordsList));
        System.out.println(recordsList.toString());
    }
}
