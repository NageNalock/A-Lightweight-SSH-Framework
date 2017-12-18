/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/16
 * \* Time: 20:38
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class InvocationTest {
    public static void main(String[] args) {
        ITask task = (ITask) DynamicProxy.newInstance(new Impl());
        task.doSomething(1);
    }
}