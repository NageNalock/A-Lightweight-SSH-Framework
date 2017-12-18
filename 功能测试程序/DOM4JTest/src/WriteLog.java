import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.Writer;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/14
 * \* Time: 21:40
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class WriteLog {

    public static void main(String[] args) {
        WriteLog demo = new WriteLog();
        demo.writeLog("login","2013-12-04 14:20:56","2013-12-04 14:20:59","success");
    }
    public void writeLog(String name, String s_time, String e_time, String result)
    {
//        <log>---(0)根节点
//            <action>-----(1)一级节点
//                <name>login</name>------(2)二级节点
//                <s-time>*****</s-time>------(2)
//                <e-time>#####</e-time>------(2)
//                <result>success</result>-------(2)
//            </action>
//        </log>
        // 不用加属性
        try {

            Document document = DocumentHelper.createDocument();

            // 根节点<log>,啥都不用加 空的
            Element logElement = document.addElement("log");

            // <action>节点,依然是空的
            Element actionElement = logElement.addElement("action");

            // 添加四个节点,name stime etime result,里面有内容(就是加个名字)
            Element nameElement = actionElement.addElement("name");
            nameElement.setText(name);
            Element s_timeElement = actionElement.addElement("s_time");
            s_timeElement.setText(s_time);
            Element e_timeElement = actionElement.addElement("e_time");
            e_timeElement.setText(e_time);
            Element resultElement = actionElement.addElement("result");
            resultElement.setText(result);

            // 格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");

            Writer fileWriter = new FileWriter("f:\\\\log.xml");
            // 写入文件的对象XMLWriter
            // XMLWriter xmlWriter = new XMLWriter(fileWriter);
            XMLWriter xmlWriter = new XMLWriter(fileWriter,format);
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
            System.out.println("xml文档添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}