import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface ITask {
    void doSomething(int i);
}




class Impl implements ITask {




    @Override
    public void doSomething(int i) {
        System.out.println("xxxxxxxxxxxxx"+i);
    }
}




class DynamicProxy implements InvocationHandler {
    private Object obj;




    private DynamicProxy(Object object) {
        obj = object;
    }




    public static Object newInstance(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(),
                new DynamicProxy(obj));
    }




    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            //可以将所有方法相同的一些操作放在这里处理
            System.out.println("--before method " + method.getName());
            result = method.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
        } finally {
            System.out.println("--after method " + method.getName());
        }
        return result;
    }




}
