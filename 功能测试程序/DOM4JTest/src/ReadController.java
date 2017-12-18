import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
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

                    String value = result.attributeValue("value");
                    if (value.contains("_view.xml"))
                    {
                        System.out.println("        result调用文件是视图文件:");
                        System.out.println("            视图文件xml文件名是:"+value+",,,,,,读取xml文件");
                        doResultView(value);
                        // 生成html文件
                        System.out.println();
                    }else System.out.println("          result调用文件是普通文件");
                    j++;
                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    private String doResultView(String resultValue)
    {
        String newResultValue = resultValue.split("_")[0];
        System.out.println(newResultValue);
        try {
            System.out.println("*********************ViewXML************************");

            // 创建HTML文件与Builder
            StringBuilder sb = new StringBuilder();
            PrintStream printStream = null ;
            try {
                printStream= new PrintStream(new FileOutputStream("src"+newResultValue+".html"));//路径默认在项目根目录下
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            InputStream inputStream = this.getClass().getResourceAsStream(resultValue);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            // 获得(0)根节点,<view>
            Element rootElement = document.getRootElement();
            System.out.println("根节点名称:"+rootElement.getName()+",其内容为:"+rootElement.getTextTrim());

            /* HTML标题部分,无列表 */
            // 获得(1)层节点,<header>
            Element header = rootElement.element("header");
            System.out.println("    标题部分(1)层节点名称为"+header.getName());
            // System.out.println("    其属性个数为:"+header.attributeCount());
            // 获得(2)层节点,<title>
            Element title = header.element("title");
            String titleTextTrim = title.getTextTrim();
            System.out.println("        标题部分(2)层节点名称为"+title.getName());
            System.out.println("        其文本内容为"+titleTextTrim); // 标题实际内容
            // 这里可以输出HTML的标题部分
            sb.append("<!DOCTYPE html>\n");
            sb.append("<html lang=\"en\">\n");
            sb.append("<head>\n");
            sb.append("    <meta charset=\"UTF-8\">\n");
            sb.append("<title>"+titleTextTrim+"</title>\n");
            sb.append("</head>\n");
            sb.append("<body>\n");

            /* HTML内容部分,其子节点会有列表 */
            // 获得(1)层节点,<body>
            Element body = rootElement.element("body");
            System.out.println("    内容部分(1)层节点名称为"+body.getName()); // 这层不需要列表
            List<Element> bodyChildElementsList = body.elements(); // HTML页面内容有多种可能
            for (Element bodyChild:bodyChildElementsList)
            {
                   switch (bodyChild.getName())
                   {
                       case "form":
                       {
                           // 获得(2)层节点,<form>
                           Element form = body.element("form");
                           System.out.println("        内容部分(2)层节点名称为"+form.getName()); // 这层也无列表,但是下一层有
                           // 获得(3)层节点,完全遍历
                           List<Element> formAttributesAndViewsList = form.elements();

                           // <form name="logoutForm" action="logout.action" method="post">
                           String formName = form.element("name").getTextTrim();
                           String formAction = form.element("action").getTextTrim();
                           String formMethod = form.element("method").getTextTrim();
                           // System.out.println("<form name=\""+formName+"\" action=\""+formAction+"\" method=\""+formMethod+"\">");
                           sb.append("<form name=\""+formName+"\" action=\""+formAction+"\" method=\""+formMethod+"\">\n");

                           for(Element formAV:formAttributesAndViewsList)
                           {
                               // 这里可以输出
                               System.out.println("            内容部分(3)层节点名称"+formAV.getName());
                               switch (formAV.getName())
                               {
                                   case "textView":
                                   {
                                       String avValue = formAV.element("value").getTextTrim();
                                       String avName = formAV.element("name").getTextTrim();
                                       String avLabel = formAV.element("label").getTextTrim();
                                       // System.out.println(avValue);
                                       sb.append("  <input type=\"text\" name=\""+avName+"\" value=\""+avValue+"\" aria-label=\""+avLabel+"\">\n");
                                       System.out.println("写入内容为:");
                                       System.out.println("  <input type=\"text\" name=\""+avName+" value="+avValue+" aria-label="+avLabel+"\">\n");
                                       break;
                                   }
                                   case "buttonView":
                                   {
                                       String avName = formAV.element("name").getTextTrim();
                                       String avLabel = formAV.element("label").getTextTrim();
                                       sb.append("  <input type=\"button\" name=\""+avName+"\" aria-label=\""+avLabel+"\">\n") ;
                                       System.out.println("写入内容为:");
                                       System.out.println("  <input type=\"button\" name=\""+avName+"\" aria-label=\""+avLabel+"\">\n");
                                       break;
                                   }
                               }

                           }
                           sb.append("</form>\n");
                           break;
                       }
                   }
            }

            sb.append("</body>\n");
            sb.append("</html>\n");
            printStream.println(sb.toString());
            System.out.println("*********************ViewXML************************");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return newResultValue;
    }

}