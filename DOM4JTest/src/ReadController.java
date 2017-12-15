import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/11
 * \* Time: 21:23
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class ReadController {
    public static void main(String[] args) {
        ReadController readController = new ReadController();
        readController.readController();
    }
    private void readController()
    {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/controller.xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            // 获得(0)根节点
            Element rootElement = document.getRootElement();
            System.out.println("根节点名称:"+rootElement.getName()+",其内容为:"+rootElement.getTextTrim());

            // 获得(1)层节点,<controller>
            Element controller = rootElement.element("controller");
            System.out.println("(1)层节点名称为"+controller.getName());
            System.out.println("其属性个数为:"+controller.attributeCount());

            // 获得(2)层节点<action>的List

            List<Element> actionList = controller.elements("action");
            int i = 1;
            for (Element action:actionList)
            {
                System.out.println("    (2)层的第"+i+"个节点名称为"+action.getName());
                System.out.println("    其属性个数为:"+action.attributeCount());
                System.out.println("    名为name的属性值"+action.attributeValue("name"));
                System.out.println("    名为class的属性值"+action.attributeValue("class"));
                System.out.println("    名为method的属性值"+action.attributeValue("method"));
                i++;

                List<Element> resultList = action.elements("result");
                int j = 1;
                for (Element result:resultList)
                {
                    System.out.println("        (3)层的第"+j+"个节点名称为"+result.getName());
                    System.out.println("        其属性个数为:"+result.attributeCount());
                    System.out.println("        名为name的属性值"+result.attributeValue("name"));
                    System.out.println("        名为type的属性值"+result.attributeValue("type"));
                    System.out.println("        名为value的属性值"+result.attributeValue("value"));
                    j++;
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

}