import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/16
 * \* Time: 20:43
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class AnotherDynamicProxy implements InvocationHandler{
    private Object object;

    private AnotherDynamicProxy(Object obj)
    {
        // 构造函数
        this.object = obj;
    }

    public static Object newInstance(Object obj)
    {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),new AnotherDynamicProxy(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            System.out.println("---before method"+method.getName());
            result = method.invoke(object,args);
        } catch (InvocationTargetException e)
        {
            throw e.getTargetException();
        } catch (Exception e)
        {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        }
        finally {
            System.out.println("---after method"+method.getName());
        }
        return result;
    }
}