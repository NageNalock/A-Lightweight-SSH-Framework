package water.ustc.interceptor;

import org.dom4j.Element;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface ITask {
    void doSomething(List<Element> resultList, String xmlActionName, String returnResult, HttpServletRequest request, HttpServletResponse response);
}

//class IResult implements ITask{
//    @Override
//    public void doSomething(List<Element> resultList,String xmlActionName,String returnResult,HttpServletRequest request, HttpServletResponse response)
//    {
//        try {
//            boolean resultNotInXML = true; // 是否有在xml中找到result
//            for (Element result:resultList)
//            {
//                String resultName = result.attributeValue("name");
//                System.out.println("XML中"+xmlActionName+"Action的结果有"+resultName);
//                if (resultName.equals(returnResult))
//                {
//                    // 有找到result
//                    resultNotInXML = false;
//                    String resultType = result.attributeValue("type");
//                    System.out.println("有找到Result!!!!!!!xml中result的type为:"+resultType);
//                    String resultValue = result.attributeValue("value");
//                    System.out.println("其value为:"+resultValue);
//                    if (resultType.equals("forward"))
//                    {
//                        // 转发
//                        request.getRequestDispatcher(resultValue).forward(request,response);
//                    }
//                    else if(resultType.equals("redirect"))
//                    {
//                        // 重定向
//                        response.sendRedirect(request.getContextPath()+resultValue);
//                    }
//                    else
//                    {
//                        System.out.println("未查到相关的result类型");
//                    }
//                }else
//                {
//                    System.out.println("未在xml中找到对应的result结果,继续查询result");
//                    System.out.println("-------继续查询Result--------");
//                    // response.sendError(501,"没有请求的资源");
//                }
//            }
//            if (resultNotInXML) response.sendError(501,"没有请求的资源");
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}




