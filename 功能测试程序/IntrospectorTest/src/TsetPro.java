import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class TsetPro {
    public static void main(String[] args) {
        try {
            // System.out.println(Student.class);
            Class student1 = Class.forName("Student");
            // 获取bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(student1,Object.class);
            // 后面参数表示停止继承参数,表示不显示从父类继承下来的属性
            // 每个类都会从Object类继承下class属性,所以这里排除

            // 获取描述器
            PropertyDescriptor propertys[] = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property:propertys)
            {
                String name = property.getName();
                System.out.println(name);  // 是依据getter与setter来监测的,getter与setter只要有一个就行
                if (!name.equals("class"))
                {
                    // 排除继承下来的class属性
                    Method writeMethod = property.getWriteMethod();
                    String writeMethodName = writeMethod.getName();
                    // System.out.println(writeMethodName);
                }

            }
            // 获取setter

            // 测试
            Student student = new Student();
            System.out.println(propertys[0].getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}