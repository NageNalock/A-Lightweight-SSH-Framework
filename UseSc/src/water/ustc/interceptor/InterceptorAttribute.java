package water.ustc.interceptor;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/15
 * \* Time: 17:54
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class InterceptorAttribute {
    private String name;
    private String clazz;
    private String predo;
    private String afterdo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getPredo() {
        return predo;
    }

    public void setPredo(String predo) {
        this.predo = predo;
    }

    public String getAfterdo() {
        return afterdo;
    }

    public void setAfterdo(String afterdo) {
        this.afterdo = afterdo;
    }

}