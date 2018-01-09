package di;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadDI {

    private HashMap<String,ArrayList<BeanField>> readDI()
    {
        HashMap<String, ArrayList<BeanField>> returnHashMap = new HashMap<>();

        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/di/di.xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            // 获取根节点
            Element rootElement = document.getRootElement();
            // System.out.println(rootElement.getName());

            // 获取bean节点
            List<Element> beansElementList = rootElement.elements("bean");
            for(Element beanElement:beansElementList)
            {
                String beanId = beanElement.attributeValue("id");
                String beanClass = beanElement.attributeValue("class");
                // 存储field节点信息,因为可能有多个field,所以用List存储
                ArrayList<BeanField> beanFieldsList = new ArrayList<>();
                List<Element> fieldElementList = beanElement.elements("field");
                for (Element fieldElement:fieldElementList)
                {
                    String fieldName = fieldElement.attributeValue("name");
                    String fieldBean_ref = fieldElement.attributeValue("bean-ref");
                    BeanField beanField = new BeanField(fieldName, fieldBean_ref,beanClass);
                    // System.out.println(fieldName+";"+fieldBean_ref);
                    beanFieldsList.add(beanField);
                }
                returnHashMap.put(beanId,beanFieldsList);
            }

        } catch (DocumentException e) {
            System.out.println("di.xml读取失败"+e);
        }

        return returnHashMap;
    }

    public static void main(String[] args) {
        ReadDI readDI = new ReadDI();
        HashMap<String, ArrayList<BeanField>> stringArrayListHashMap = readDI.readDI();
        // System.out.println(stringArrayListHashMap.get("11"));
        stringArrayListHashMap.get("11");
        if (stringArrayListHashMap.get("11")==null)
        {
            System.out.println("11未查询到");
        }
        // System.out.println(stringArrayListHashMap.get("loginAction.sc"));
        ArrayList<BeanField> beanFields = stringArrayListHashMap.get("user");
        for (BeanField beanField:beanFields)
        {
            System.out.println(beanField.getBeanClass());
            System.out.println(beanField.getName());
            System.out.println(beanField.getBean_ref());
        }
    }
}