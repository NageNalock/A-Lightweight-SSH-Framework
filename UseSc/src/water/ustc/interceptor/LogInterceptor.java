package water.ustc.interceptor;

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
 * \* Time: 21:58
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class LogInterceptor {
    private String name;
    private String s_time;
    private String e_time;
    private String result;

    public void preAction(String input_name,String input_time)
    {
        // Action执行前执行,记录name和time
        // 此时的时间为访问开始时间
        name = input_name;
        s_time = input_time;
    }

    public void afterAction(String input_result,String input_time)
    {
        // Action执行后执行,记录time和result
        // 此时的时间为访问结束时间
        // 同时要写入log
        result = input_result;
        e_time = input_time;
        writeLog();
    }

    private void writeLog()
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