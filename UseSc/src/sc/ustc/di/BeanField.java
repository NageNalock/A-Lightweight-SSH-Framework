package sc.ustc.di;

public class BeanField {
    private String name;
    private String bean_ref;

    public String getBeanClass() {
        return beanClass;
    }

    private String beanClass;

    public BeanField(String name, String bean_ref, String beanClass)
    {
        this.name = name;
        this.bean_ref = bean_ref;
        this.beanClass = beanClass;
    }

    public String getBean_ref() {
        return bean_ref;
    }

    public String getName() {
        return name;
    }
}