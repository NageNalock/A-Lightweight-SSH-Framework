// import jdk.internal.util.xml.impl.XMLWriter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
//import org.dom4j.DocumentHelper;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/14
 * \* Time: 21:07
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class WriteTest {
    public static void main(String[] args) {
        WriteTest demo1 = new WriteTest();
        demo1.buildXML();
    }
    private void buildXML()
    {
        try {
            Document document = DocumentHelper.createDocument();
            // 根节点信息
            Element rootElement = document.addElement("modules");
            rootElement.setText("这里好像是根节点<modules>的文本信息");

            // 注意这里
            Element element = rootElement.addElement("module");

            // 添加三个子节点
            Element nameElement = element.addElement("name");
            Element valueElement = element.addElement("value");
            Element descriptionElement = element.addElement("description");

            nameElement.setText("名称");
            // 为节点添加属性
            nameElement.addAttribute("language","java");

            valueElement.setText("值");
            valueElement.addAttribute("language","c#");

            descriptionElement.setText("描述");
            descriptionElement.addAttribute("language","描述节点的值");

            // 将document文档对象直接转换成字符串输出
            System.out.println(document.asXML());

            // 格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");

            Writer fileWriter = new FileWriter("f:\\\\lalalalalamodule.xml");
            // 写入文件的对象XMLWriter
            // XMLWriter xmlWriter = new XMLWriter(fileWriter);
            XMLWriter xmlWriter = new XMLWriter(fileWriter,format);
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
            System.out.println("xml文档添加成功！");
        } catch (IOException e) {
            System.out.println("文件写入失败啦");
            e.printStackTrace();
        }
    }


}