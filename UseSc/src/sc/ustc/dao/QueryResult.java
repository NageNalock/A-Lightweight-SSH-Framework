package sc.ustc.dao;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryResult {

    // 懒加载标志
    private boolean lazyFlag;
    // 返回结果,如果是懒加载则为空,因为有时可能会返回多个查询结果,所以用List
    private ArrayList<HashMap<String,String>> resultList = null;
    // 存放对应的bean名称
    private String beanPath;


    // 存放sql信息,在实际需要调用result时进行数据库查询,其实beanName好像也应该加进去
    private String sql;
    private String driver_class;
    private String url_path;
    private String db_username;
    private String db_userpassword;

    public QueryResult(boolean lazyFlag)
    {
        this.lazyFlag = lazyFlag;
    }

    public void setLazySQL(String sql,String driver_class,String url_path,String db_username,String db_userpassword)
    {
        System.out.println("是懒加载,存放queryResult的SQL信息");
        this.sql = sql;
        this.driver_class = driver_class;
        this.url_path = url_path;
        this.db_username = db_username;
        this.db_userpassword = db_userpassword;
    }

    public ArrayList<HashMap<String,String>> notLazy()
    {
        System.out.println("对懒加载结果开始查询数据库");
        // 实际需要获取结果了,查询数据库

        // 1. 新建Conversation对象,将bean名称传到构造函数中,剩下的会自动搞定
        Conversation conversation = new Conversation(beanPath);
        // 2. 调用Conversation的单句查询方法
        ArrayList<HashMap<String, String>> lazyResult = conversation.queryToSQL(sql);
        return lazyResult;
    }


    public ArrayList<HashMap<String,String>> getResultList() {
        return resultList;
    }

    public void setResultList(ArrayList<HashMap<String,String>> resultList) {
        // 这个setter将要在Conversation中调用
        this.resultList = resultList;
    }

    public String getBeanPath() {
        return beanPath;
    }

    public void setBeanPath(String beanPath) {
        this.beanPath = beanPath;
    }

    public boolean isLazy()
    {
        return lazyFlag;
    }
}