package water.ustc.interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/16
 * \* Time: 22:22
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class InterDynProxy implements InvocationHandler
{
    private Object obj;
    private static InterceptorAttribute interceptor;
    private static String returnResult;

    private InterDynProxy(Object object)
    {
        obj = object;
    }

    public static Object newInstance(Object obj,InterceptorAttribute inter,String result) {
        interceptor = inter; // 拦截器属性信息赋值
        returnResult = result; // 将要执行的Result方法

        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),new InterDynProxy(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;


        // 创建拦截器类
        Object interObject = null;
        try {
            //可以将所有方法相同的一些操作放在这里处理
            System.out.println("--before method " + method.getName());
            // preAction内容
            Date currDate = Calendar.getInstance().getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = sdf.format(currDate);

            System.out.println("现在执行拦截器preDo方法");
            System.out.println("此时时间为"+dateTime);
            // System.out.println(interceptorName+"匹配成功");
            // System.out.println("假装执行拦截器~~~~~~~~~~~~");

            // 对拦截器进行反射
            Class interClass = Class.forName(interceptor.getClazz());
            interObject = interClass.newInstance();
            Method preDoMethod = interClass.getDeclaredMethod(interceptor.getPredo(), String.class, String.class);
            preDoMethod.invoke(interObject,interceptor.getName(),dateTime);

            result = method.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        } finally {
            System.out.println("--after method " + method.getName());
            // afterAction内容
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
        return result;
    }
}