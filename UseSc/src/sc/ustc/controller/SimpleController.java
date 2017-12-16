package sc.ustc.controller;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import water.ustc.interceptor.InterceptorAttribute;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

// @WebServlet(name = "SimpleController")
public class SimpleController extends HttpServlet {

    private static Object interObject; // 拦截器全局变量

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

        doAction(request,response,trueReqActionName,username,pwd);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    private  void doAction(HttpServletRequest request, HttpServletResponse response,String trueReqActionName,String username,String pwd)
    {
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

            List<Element> interceptorList = rootElement.elements("interceptor");
            // System.out.println("**********************");
            List<InterceptorAttribute> interceptorAttributeList = new ArrayList<InterceptorAttribute>(); // 存放过滤器信息
            System.out.println("    (1)层某单一节点名称为:interceptor");
            System.out.println("    其总数为"+interceptorAttributeList.size());
            for (Element interceptor:interceptorList)
            {
                System.out.println("***");
                InterceptorAttribute interceptorAttribute_temp = new InterceptorAttribute();
                interceptorAttribute_temp.setName(interceptor.attributeValue("name"));
                // System.out.println("存放过滤器信息:<"+interceptor.attributeValue("name")+"><name>属性完毕");
                interceptorAttribute_temp.setClazz(interceptor.attributeValue("class"));
                // System.out.println("存放过滤器信息:<"+interceptor.attributeValue("name")+"><class>属性完毕");
                interceptorAttribute_temp.setPredo(interceptor.attributeValue("predo"));
                // System.out.println("存放过滤器信息:<"+interceptor.attributeValue("name")+"><predo>属性完毕");
                interceptorAttribute_temp.setAfterdo(interceptor.attributeValue("afterdo"));
                // System.out.println("存放过滤器信息:<"+interceptor.attributeValue("name")+"><afterdo>属性完毕");

                interceptorAttributeList.add(interceptorAttribute_temp);
                System.out.println("    存放过滤器信息:<"+interceptor.attributeValue("name")+">完毕");
                System.out.println("    其属性个数为:"+interceptor.attributeCount());
                System.out.println("***");
            }
//            System.out.println("过滤器信息列表如下:");
//            for (InterceptorAttribute interceptor:interceptorAttributeList)
//            {
//                System.out.println("    "+interceptor.getName()+"#  "+interceptor.getClazz()+"# "+interceptor.getPredo()+"# "+interceptor.getAfterdo());
//            }
//            System.out.println("**********************");


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


                    // 虽然已经返回了Action结果,但是还不执行,这里开始执行拦截器进入pre方法
                    List<String> interceptorNameList = hasInterceptor(action);
                    if (!interceptorNameList.isEmpty())
                    {
                        // 在result前执行拦截器
                        for (String interceptorName : interceptorNameList) {
                            // 从之前的过滤器信息List中比对找出该Action下的拦截器信息
                            // System.out.println("@@@@@@暂存列表中的拦截器名称"+interceptorName);
                            for (InterceptorAttribute interceptor : interceptorAttributeList) {
                                if (interceptorName.equals(interceptor.getName()))
                                {
                                    // 获取当前时间并转化成字符串
                                    Date currDate = Calendar.getInstance().getTime();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String dateTime = sdf.format(currDate);

                                    System.out.println("现在执行拦截器preDo方法");
                                    System.out.println("此时时间为"+dateTime);
                                    System.out.println(interceptorName+"匹配成功");
                                    // System.out.println("假装执行拦截器~~~~~~~~~~~~");

                                    // 对拦截器进行反射
                                    Class interClass = Class.forName(interceptor.getClazz());
                                    interObject = interClass.newInstance(); // 使用全局变量
                                    Method preDoMethod = interClass.getDeclaredMethod(interceptor.getPredo(), String.class, String.class);
                                    preDoMethod.invoke(interObject,interceptor.getName(),dateTime);

                                    // System.out.println("这里做反射,找到拦截器的class" + interceptor.getClazz());
                                    // System.out.println("这里先执行predo方法"+interceptor.getPredo());
                                }
                            }
                        }
                        System.out.println("~~~~~~~~~~~~~~下面是result~~~~~~~~~~~~~~~~~");
                    }


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

                    // result执行完毕,逆序执行过滤器
                    if (!interceptorNameList.isEmpty())
                    {
                        // 在result后执行拦截器
                        // System.out.println("qqqqqqqqqqq可执行过滤器列表长度为"+interceptorNameList.size());
                        for (int interNum=(interceptorNameList.size()-1);interNum<interceptorNameList.size()&&interNum>=0;interNum--) {
                            // 从之前的过滤器信息List中比对找出该Action下的拦截器信息
                            // System.out.println(interNum);
                            String interName_temp = interceptorNameList.get(interNum);
                            System.out.println("action退出时的拦截器为:"+interName_temp);
                            for (InterceptorAttribute interceptor : interceptorAttributeList) {
                                if (interName_temp.equals(interceptor.getName()))
                                {
                                    Date currDate = Calendar.getInstance().getTime();
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String dateTime = sdf.format(currDate);
                                    System.out.println("现在执行拦截器afterDo方法");
                                    System.out.println("此时时间为"+dateTime);
                                    // 对拦截器进行反射
                                    Class interClass = Class.forName(interceptor.getClazz());
                                    // Object interObject = interClass.newInstance();
                                    Method preDoMethod = interClass.getDeclaredMethod(interceptor.getAfterdo(), String.class, String.class);
                                    preDoMethod.invoke(interObject,returnResult,dateTime);
                                    System.out.println("~~~~~~~~~~~~拦截器执行完毕~~~~~~~~~~~");
                                }
                            }
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

    private List<String> hasInterceptor(Element action)
    {
        // 判断该action下是否有有拦截器,并返回拦截器名称List
        List<String> returnList = new ArrayList<String>();
        if (action.element("interceptro-ref")!= null)
        {
            System.out.println("!!!!!!!!!!!!!");
            System.out.println(action.attributeValue("name")+"下具有拦截器:");
            List<Element> inter_refList = action.elements("interceptro-ref");
            for (Element inter_ref:inter_refList)
            {
                System.out.println("其名称为:"+inter_ref.attributeValue("name"));
                String inter_refName = inter_ref.attributeValue("name");
                returnList.add(inter_refName);
            }
        }else
        {
            System.out.println(action.attributeValue("name")+"下没有拦截器");
        }
        // System.out.println(action.attributeValue("name")+"!!!!!!!!判断拦截器函数返回结果为:"+returnList);
        // System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("!!!!!!!!!!!!!");
        return returnList;
    }
}
