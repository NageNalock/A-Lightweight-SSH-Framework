package sc.ustc.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Conversation {
    // JDBC部分
    private String driver_class;
    private String url_path;
    private String db_username;
    private String db_userpassword;
    private Configuration configuration;
    // CLass部分
    private String class_Name;
    private String class_table;
    private String class_ID; // 主键名
    private ArrayList<HashMap> propertiesList;
    // 传进来Bean名,用来与xml做比对并且决定了查询的Object返回类型
    private String classNameFromBean;
    private String classNamePath;  // 完整的名称,用来反射

    public Conversation(String classPathFromBean)
    {
        System.out.println("~~~~~~~~~~以下是Conversation~~~~~~~~~~");
        // water.ustc.bean.UserBean,完整类名,用于反射
        this.classNamePath = classPathFromBean;
        // 路径中截取类名
        String nameFromPath = classPathFromBean.substring(classPathFromBean.lastIndexOf(".")+1);
        System.out.println("传入的类名为"+nameFromPath);
        this.classNameFromBean = nameFromPath;
        this.configuration = new Configuration();
        setJBDC();
        setClass();
    }

    public QueryResult query(Object beanObject, String beanPropertyName)
    {
        System.out.println("111111111111111111111111111111111111111");
        System.out.println("开始执行query方法");
        // 获得属性的getter
        String getterString = "get" + captureName(beanPropertyName);
        System.out.println("query中的getter为"+getterString);

        // 要查询的bean对象,要查询的bean中的属性名(不是值,是属性名)
        // 只做了单值查询,查询属性必须是String
        try {
            Class beanClass = Class.forName(classNamePath);
            // 反射出getter方法,并执行方法,得到属性值
            Method getterMethod = beanClass.getDeclaredMethod(getterString);
            String getPropertyString = (String) getterMethod.invoke(beanObject);

            // 得到对应的column
            String propertyColumn = "";
            for (HashMap<String,String> property:propertiesList)
            {
                if (property.get("name").equals(beanPropertyName))
                {
                    propertyColumn = property.get("column");
                    System.out.println(getPropertyString+"所在列为:"+propertyColumn);
                }
            }

            // 拼接sql语句
            String sql = "SELECT * FROM " + class_table + " WHERE  "+ propertyColumn+" = '" + getPropertyString +"'";
            System.out.println("sql语句为:"+sql);

            // 判断是否延迟加载
            boolean lazyFlag = false;  // 是否延迟加载
            for (HashMap<String,String> property:propertiesList)
            {
                if (property.get("name").equals(beanPropertyName) && property.get("lazy").equals("true"))
                {
                    lazyFlag = true;
                }
            }

            QueryResult queryResult = new QueryResult(lazyFlag);

            if (lazyFlag)
            {
                // 需要延迟加载
                System.out.println(beanPropertyName+"需要延迟加载");
                // return sql;
                queryResult.setBeanPath(this.classNamePath);
                queryResult.setLazySQL(sql);
                // return queryResult;
            }else
            {
                // 不延迟
                System.out.println(beanPropertyName+"不需要延迟加载");
                // 打开连接
                Connection connection = this.openDBConnection();
                // 新建返回List,用于赋给queryResult
                ArrayList<HashMap<String,String>> resultHashMapList = new ArrayList<HashMap<String,String>>();
                try {
                    // 执行查询
                    PreparedStatement preparedStatement = connection.prepareStatement(sql); // 预编译
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next())
                    {
                        HashMap<String, String> queryResultMap = new HashMap<>();
                        queryResultMap.put("ID",class_ID);
                        for (HashMap<String,String> property:propertiesList)
                        {
                            // 从配置文件中遍历property的对应的column
                            System.out.println("*****");
                            String propertyColumnName = property.get("column");
                            System.out.println("    查询xml中的property名称(列名):"+propertyColumnName);
                            // 拿出对应的值
                            String resultSetString = resultSet.getString(propertyColumnName);
                            System.out.println("    其对应值(来自数据库)为:"+resultSetString);
                            queryResultMap.put(property.get("name"),resultSetString);
                            System.out.println("*****");
                        }
                        resultHashMapList.add(queryResultMap);
                    }
                    boolean closeResult = this.closeDBConnection(connection, preparedStatement, resultSet);
                    if (closeResult) System.out.println("   query()关闭成功");
                } catch (SQLException e) {
                    System.out.println("    query()方法出错:" + e);
                }
                // 设置查询结果的resultList
                queryResult.setResultList(resultHashMapList);
            }
            System.out.println("2222222222222222222222222222222222222222222222");
            return queryResult;
        } catch (Exception e) {
            System.out.println("Conversation查询反射失败"+e);
        }
        return null;
    }

    public boolean insert(Object beanObject)
    {
        // 数据库是否插入成功
        boolean insertResult = false;


        try {
            // 反射找到Bean对象
            Class beanClass = Class.forName(classNamePath);

            ArrayList<String> valueList = new ArrayList<>();
            valueList.add("'0'"); // 第一个为ID,写死为0
            // 1.获取各个getter,对于这个需要得到getUserName,getUserPass
            // 1.1.利用getter得到value(即userName的值,userPass的值)
            // 1.2.构建ArrayList(valueList) [id的值*,userName的值,userPass的值]
            for (HashMap<String,String> property:propertiesList)
            {
                System.out.println("*******");
                String propertyName = property.get("name");
                // 拼接出getter方法的完整方法名
                String propertyGetter = "get" + captureName(propertyName);
                System.out.println(property+"的getter为"+propertyGetter);
                // 反射出getter方法,并执行方法,得到属性值
                Method getterMethod = beanClass.getDeclaredMethod(propertyGetter);
                String beanProperty = (String) getterMethod.invoke(beanObject);
                System.out.println("其反射结果为:"+beanProperty);
                valueList.add("'"+beanProperty+"'");
            }

            // 2.查找到对应的column名:user_name,user_pass
            ArrayList<String> columnList = new ArrayList<>();
            columnList.add(class_ID);
            // 2.1 构建ArrayList [userID*,user_name,user_pass]
            for (HashMap<String,String> property:propertiesList)
            {
                columnList.add(property.get("column"));
            }

            // 3.根据tableName判断表是否存在
            if (!isTableExist(class_table))
            {
                // 不存在
                // 3.1.建表
                System.out.println("表格不存在,建表");
                createTable();
            }

            // 4.构建sql语句,需要tableName,两次循环,两次循环的次序不能错
            // "INSERT INTO "+tableName+"(USERNAME,USERPASS,USERID)  VALUES(?,?,"+userID+")";
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO ");
            sb.append(class_table);
            sb.append(" (");
            for (String column:columnList)
            {
                sb.append(column);
                sb.append(",");
            }
            // 删去最后一个逗号
            sb.deleteCharAt(sb.length()-1);
            sb.append(")  VALUES(");
            for (String value:valueList)
            {
                sb.append(value);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append(")");
            String sql = sb.toString();
            System.out.println("insert()插入语句为:"+sql);
            // 5.执行语句
            Connection connection = this.openDBConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int rowNum = preparedStatement.executeUpdate();
            System.out.println("        插入成功,插入行数为:"+rowNum);
            this.closeDBConnection(connection,preparedStatement,null);

            insertResult = true;
        } catch (Exception e) {
            System.out.println("insert()出错"+e);
        }

        return insertResult;
    }

    private void setJBDC()
    {
        this.driver_class = configuration.getDriver_class();
        this.url_path = configuration.getUrl_path();
        this.db_username = configuration.getDb_username();
        this.db_userpassword = configuration.getDb_userpassword();
    }

    private void setClass()
    {
        ArrayList<OrClassConfiguration> classList = configuration.getClassList();
        for(OrClassConfiguration classConfiguration:classList)
        {
            // 从xml中找出相应的class配置
            if(classConfiguration.getClassName().equals(classNameFromBean))
            {
                this.class_Name = classConfiguration.getClassName();
                this.class_table = classConfiguration.getTable();
                this.class_ID = classConfiguration.getClassId();
                this.propertiesList = classConfiguration.getPropertiesList();
            }
        }
        if (!this.class_Name.equals(classNameFromBean))
        {
            System.out.println("or_mapping中未找到名为"+classNameFromBean+"的配置文件");
        }
    }



    private boolean isTableExist(String tableName)
    {
        // 判断表是否存在
        System.out.println("开始执行表格监测方法");
        boolean flag = false;
        Connection connection = this.openDBConnection();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String type [] = {"TABLE"};
            ResultSet rs = metaData.getTables(null, null, tableName, type);
            flag = rs.next();

            this.closeDBConnection(connection,null,rs);
        } catch (SQLException e) {
            System.out.println("    *表格检测方法失败:"+e);
            // e.printStackTrace();
        }

        System.out.println("表格监测方法执行结束:"+flag);
        return flag;
    }

    private void createTable()
    {
        System.out.println("开始创建表格");
        Connection connection = this.openDBConnection();
        try {
            Statement statement = connection.createStatement();

            // sql语句拼接
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CREATE TABLE ");
            stringBuilder.append(class_table);
            stringBuilder.append(" (");
            stringBuilder.append(class_ID);
            stringBuilder.append(" VARCHAR(255) not NULL, "); // 类型写死了
            for (HashMap<String,String> property:propertiesList)
            {
                String columnName = property.get("column");
                stringBuilder.append(columnName);
                stringBuilder.append(" VARCHAR(255), "); // 写死了,其实应该按照property的type构造的
            }
            stringBuilder.append(" PRIMARY KEY ( ");
            stringBuilder.append(class_ID);
            stringBuilder.append(" ))");
            String sql = stringBuilder.toString();
            System.out.println("表格创建语句为:" + sql);

            statement.executeUpdate(sql);
            statement.close();
            this.closeDBConnection(connection,null,null);
        } catch (SQLException e) {
            System.out.println("创建表格失败"+e);
        }
    }


    private Connection openDBConnection()
    {
        Connection conn = null;
        System.out.println("创建数据库连接,加载驱动:");
        try {
            try {
                // System.out.println("000000"+url+";"+userName+";"+userPassword+"000000");
                Class.forName(driver_class);
                conn = DriverManager.getConnection(url_path,db_username,db_userpassword);
            } catch (SQLException e) {
                System.out.println("    创建链接失败:"+e);
                // e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("    加载驱动失败:" + e);
        }
        return conn;
    }

    // 关闭数据库连接
    private boolean closeDBConnection(Connection connection, PreparedStatement pstmt, ResultSet rs)
    {
        // conn数据库链接对象
        // rs数据返回结果,有可能为空
        // pstmt数据库命令执行对象
        System.out.println("关闭数据库连接");
        try {
            if (rs!=null)
            {
                rs.close();
                // rs = null;
            }
            if (pstmt!=null)
            {
                pstmt.close();
                // pstmt = null;
            }
            if (connection!=null)
            {
                connection.close();
                // connection = null;
            }
            System.out.println("    数据库连接关闭成功");
        }catch (SQLException e)
        {
            System.out.println("    数据库关闭失败,错误为:"+e);
            return false;
        }
        return true;
    }

    public ArrayList<HashMap<String,String>> queryToSQL(String sql)
    {
        System.out.println("1111111111111111111111111111111111111");
        // 如果传进来的是SQL语句
        System.out.println("查询参数是完整的SQL语句:"+sql);
        // 打开连接
        Connection connection = this.openDBConnection();
        // 返回结果
        ArrayList<HashMap<String,String>> resultHashMapList = new ArrayList<HashMap<String,String>>();

        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql); // 预编译
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("        返回的resultSet结果为:" + resultSet.toString());
            while (resultSet.next())
            {
                HashMap<String, String> queryResultMap = new HashMap<>();
                queryResultMap.put("ID",class_ID);
                for (HashMap<String,String> property:propertiesList)
                {
                    // 从配置文件中遍历property的name
                    System.out.println("*****");
                    String propertyColumnName = property.get("column");
                    System.out.println("    查询xml中的property名称(列名):"+propertyColumnName);
                    // 拿出对应的值
                    String resultSetString = resultSet.getString(propertyColumnName);
                    System.out.println("    其对应值(来自数据库)为:"+resultSetString);
                    queryResultMap.put(property.get("name"),resultSetString);
                    System.out.println("*****");
                }
                resultHashMapList.add(queryResultMap);
            }
            boolean closeResult = this.closeDBConnection(connection, preparedStatement, resultSet);
            if (closeResult) System.out.println("   query()关闭成功");
        }catch (SQLException e)
        {
            System.out.println("    queryToSQL()单语句查询方法出错"+e);
        }
        System.out.println("1111111111111111111111111111111111111111");
        return resultHashMapList;
    }

    private static String captureName(String name) {
        // 首字母大写
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }
}