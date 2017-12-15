package sc.ustc.controller;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

// @WebServlet(name = "SimpleController")
public class SimpleController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // req.getRequestDispatcher("/HelloJ2EE.html").forward(req,resp);
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 请求action名称
        String reqActionName = request.getServletPath();
        String trueReqActionName = reqActionName.substring(1,reqActionName.length());
        System.out.println("请求中的actionName为"+trueReqActionName);

        // 获取网页表单中的账号密码,作为action的方法的参数
        String username = request.getParameter("username");
        String pwd = request.getParameter("password");
        System.out.println(username+":"+ pwd);

        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/controller.xml");
            // System.out.println("获取inputStream:"+inputStream);
            SAXReader saxReader = new SAXReader();
            // System.out.println("获取saxReader");
            Document document = saxReader.read(inputStream);
            // System.out.println("获取Document");

            // 生成(0)根节点<sc-configuration>
            Element rootElement = document.getRootElement();
            // System.out.println("根节点名称:"+rootElement.getName()+",其内容为:"+rootElement.getTextTrim());

            // 获得(1)层节点,<controller>
            Element controller = rootElement.element("controller");
//            System.out.println("(1)层节点名称为"+controller.getName());
//            System.out.println("其属性个数为:"+controller.attributeCount());

            // 获得(2)层节点<action>的List
            List<Element> actionList = controller.elements("action");
            boolean actionNotInXml = true; // 是否有在xml中找到对应action
            for (Element action:actionList)
            {
                String xmlActionName = action.attributeValue("name");
                if (xmlActionName.equals(trueReqActionName))
                {
                    // 请求中的action的name能在xml中找到
                    actionNotInXml = false;
                    String xmlActionClass = action.attributeValue("class");
                    System.out.println(xmlActionName+"的Class为"+xmlActionClass);
                    String xmlActionMethod = action.attributeValue("method");
                    System.out.println(xmlActionName+"的Method为"+xmlActionMethod);
                    // 反射
                    Class actionClass = Class.forName(xmlActionClass); // 构建类
                    Object actionObeject = actionClass.newInstance(); // 实现类(无参)
                    Method actionMethod = actionClass.getDeclaredMethod(xmlActionMethod, String.class, String.class); // 获取方法

                    // 反射后,获取执行对应类的对应方法的结果
                    String returnResult = (String) actionMethod.invoke(actionObeject, username, pwd);
                    System.out.println("反射后,获取执行对应类的对应方法的结果为"+returnResult);

                    // 获得(3)层节点<result>的List
                    List<Element> resultList = action.elements("result");
                    boolean resultNotInXML = true; // 是否有在xml中找到result
                    for (Element result:resultList)
                    {
                        String resultName = result.attributeValue("name");
                        System.out.println("XML中"+xmlActionName+"Action的结果有"+resultName);
                        if (resultName.equals(returnResult))
                        {
                            // 有找到result
                            resultNotInXML = false;
                            String resultType = result.attributeValue("type");
                            System.out.println("有找到Result!!!!!!!xml中result的type为:"+resultType);
                            String resultValue = result.attributeValue("value");
                            System.out.println("其value为:"+resultValue);
                            if (resultType.equals("forward"))
                            {
                                // 转发
                                request.getRequestDispatcher(resultValue).forward(request,response);
                            }
                            else if(resultType.equals("redirect"))
                            {
                                // 重定向
                                response.sendRedirect(request.getContextPath()+resultValue);
                            }
                            else
                            {
                                System.out.println("未查到相关的result类型");
                            }
                        }else
                        {
                            System.out.println("未在xml中找到对应的result结果,继续查询result");
                            System.out.println("-------继续查询Result--------");
                            // response.sendError(501,"没有请求的资源");
                        }
                    }
                    if (resultNotInXML) response.sendError(501,"没有请求的资源");
                }else
                {
                    // 本次循环中未找到Action
                    System.out.println("未在xml中找到对应的Action结果,继续查询action");
                    System.out.println("-------继续查询Action--------");
                }
            }
            if (actionNotInXml) response.sendError(500,"不可识别的action请求");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }


}
