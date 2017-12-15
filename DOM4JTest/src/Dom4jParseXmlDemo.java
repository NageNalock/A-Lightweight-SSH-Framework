import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 <1>根据读取的xml路径，传递给SAXReader之后 返回一个Document文档对象；

 <2>然后操作这个Document对象，获取下面的节点以及子节点的信息；
 */
public class Dom4jParseXmlDemo {
//    public static void main(String[] args) {
//        parseXml1();
//    }

    public static void main(String[] args) {
        Dom4jParseXmlDemo demo = new Dom4jParseXmlDemo();
        // demo.parseXml1();
        // demo.parseXml2();
        // 迭代器太复杂了,还是别用了
        demo.parseXml3();
    }

    public  void parseXml1()
    {
        try {
            // 将xml转化为输入流
            InputStream inputStream = this.getClass().getResourceAsStream("/testxml.xml");
            // System.out.println(this.getClass().getName());
            // 创建SAXReader读取器
            SAXReader saxReader = new SAXReader();

            // 根据read重写方法知,既可以通过inputStream来读取,也可以通过file对象来读取
            Document document = saxReader.read(inputStream);

            // 获取 根 结点对象
            Element rootElement = document.getRootElement();
            System.out.println("根节点名称:"+rootElement.getName());
            System.out.println("根节点的属性数量:"+rootElement.attributeCount());
            System.out.println("名为\"id\"的根节点的属性值:"+rootElement.attributeValue("id"));
            // 如果元素有子节点则返回空字符串，否则返回节点内的文本
            System.out.println("根节点内文本:");
            // rootElement.getText() 之所以会换行是因为 标签与标签之间使用了tab键和换行符布局，这个也算是文本所以显示出来换行的效果。
            System.out.println("根节点内的文本(去换行):"+rootElement.getTextTrim()); // 这个方法会去掉标签与标签之间的tab键和换行符等等,不是内容前后的空格
            System.out.println("根节点子节点文本内容:" +rootElement.getStringValue()); // 返回当前节点递归所有子节点的文本信息

            // 获取modul子节点
            Element element = rootElement.element("module");
            if (element!=null)
            {
                System.out.println("子节点的文本:" + element.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("读取出错啦");
        }


    }

    private void parseXml2()
    {
        try {

            InputStream inputStream = this.getClass().getResourceAsStream("/test2.xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            Element modulesElement = document.getRootElement();
            System.out.println("根目录已获取");
            System.out.println("名为\"id\"的根节点的属性值:"+modulesElement.attributeValue("id"));
            Element element = modulesElement.element("module");
            if (element!=null)
            {
                System.out.println("子节点的文本:" + element.getText());
            }else
            {
                System.out.println("子节点为空");
            }

            // 迭代器
//            <modules id="123">
//                   <module>
//                         <name>oa</name>
//                         <value>系统基本配置</value>
//                         <descript>对系统的基本配置根目录</descript>
//                  </module>
//             </modules>
            Iterator<Element> moduleIterator = modulesElement.element("module").elementIterator(); // module标签内所有内容的迭代器
            System.out.println("迭代器已获取");
            while (moduleIterator.hasNext())
            {
                System.out.println("已进入迭代器");
                Element moduleElement = moduleIterator.next();// 获取内容
                System.out.println("内容已获取");
                // 内容标签
                Element nameElement = moduleElement.element("name");
                System.out.println(nameElement.getName() + "的内容:" + nameElement.getText());
                Element value = moduleElement.element("value");
                System.out.println(value.getName()+"的内容是"+value.getText());
                Element descript = moduleElement.element("descript");
                System.out.println(descript.getName()+"的内容是:"+descript.getText());

            }

        } catch (DocumentException e) {
            System.out.println("出错啦");
            e.printStackTrace();
        }
    }

    private void parseXml3()
    {
        // List方式实现,好理解,还可以在复杂的xml中使用

//        <?xml version="1.0" encoding="UTF-8"?>
//          <modules id="123">
//              <module>这个是module标签的文本信息</module>
//
//              <module id="">
//                  <name>oa</name>
//                  <value>系统基本配置</value>
//                  <descript>对系统的基本配置根目录</descript>
//                  <module>这个是子module标签的文本信息</module>
//              </module>
//
//              <module>
//                  <name>管理配置</name>
//                  <value>none</value>
//                  <descript>管理配置的说明</descript>
//
//                  <module id="106">
//                      <name>系统管理</name>
//                      <value>0</value>
//                      <descript>Config</descript>
//
//                      <module id="107">
//                          <name>部门编号</name>
//                          <value>20394</value>
//                          <descript>编号</descript>
//                      </module>
//                  </module>
//              </module>
//          </modules>

        try {
//            InputStream in = this.getClass().getResourceAsStream("/test3.xml");
//            SAXReader saxReader = new SAXReader();
//            Document document = saxReader.read(in);
//
//            // 根节点
//            Element rootElement = document.getRootElement();
//
//            if (rootElement.element("modules")!=null) // 第一个节点没有子节点,这里价格if跳过那个节点
//                // 否则的话会出现空指针错误
//            {
//                // 生成List
//                List<Element> elementList = rootElement.elements("module");
//                for (Element element:elementList)
//                {
//                    if (!element.getTextTrim().equals("")) // 无下层标签
//                    {
//                        System.out.println("第一个组标签的内容"+element.getTextTrim());
//                    }else
//                    {
//                        Element name = element.element("name");
//                        System.out.println("第二个标签的"+name.getName()+":"+name.getTextTrim());
//                        Element value = element.element("value");
//                        System.out.println("第二个标签的"+value.getName()+":"+value.getTextTrim());
//                        Element descript = element.element("descript");
//                        System.out.println("第二个标签的"+descript.getName()+":"+descript.getTextTrim());
//
//                        // 获得第三个标签组的List
//                        List<Element> subElementList = element.
//                    }
//                }
//            }
            //将src下面的xml转换为输入流
            InputStream inputStream = this.getClass().getResourceAsStream("/test3.xml");
            //创建SAXReader读取器，专门用于读取xml
            SAXReader saxReader = new SAXReader();
            //根据saxReader的read重写方法可知，既可以通过inputStream输入流来读取，也可以通过file对象来读取
            Document document = saxReader.read(inputStream);

            Element rootElement = document.getRootElement();
            if(rootElement.elements("module") != null ){
                //因为第一个module标签只有内容没有子节点，直接.iterator()就java.lang.NullPointerException了, 所以需要分开实现
                List<Element> elementList = rootElement.elements("module");
                for (Element element : elementList) {
                    if(!element.getTextTrim().equals("")){
                        System.out.println("【1】" + element.getTextTrim());
                    }else{
                        Element nameElement = element.element("name");
                        System.out.println("   【2】" + nameElement.getName() + ":" + nameElement.getText());
                        Element valueElement = element.element("value");
                        System.out.println("   【2】" + valueElement.getName() + ":" + valueElement.getText());
                        Element descriptElement = element.element("descript");
                        System.out.println("   【2】" + descriptElement.getName() + ":" + descriptElement.getText());

                        List<Element> subElementList = element.elements("module");
                        for (Element subElement : subElementList) {
                            if(!subElement.getTextTrim().equals("")){
                                System.out.println("      【3】" + subElement.getTextTrim());
                            }else{
                                Element subnameElement = subElement.element("name");
                                System.out.println("      【3】" + subnameElement.getName() + ":" + subnameElement.getText());
                                Element subvalueElement = subElement.element("value");
                                System.out.println("      【3】" + subvalueElement.getName() + ":" + subvalueElement.getText());
                                Element subdescriptElement = subElement.element("descript");
                                System.out.println("      【3】" + subdescriptElement.getName() + ":" + subdescriptElement.getText());
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}