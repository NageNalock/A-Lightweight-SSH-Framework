package sc.ustc.dao;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Configuration {


    private String driver_class;
    private String url_path;
    private String db_username;
    private String db_userpassword;


    // 保存class信息
    private ArrayList<OrClassConfiguration> classList = new ArrayList<>();

    Configuration()
    {
        this.readMapping();
    }


    private void readMapping()
    {
        try {
            InputStream inputStream = this.getClass().getResourceAsStream("/or_mapping.xml");
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(inputStream);

            // 获取(0)根节点
            Element rootElement = document.getRootElement();
            System.out.println("根节点名称:"+rootElement.getName());

            // 获得(1)层节点,可能有多种
            List<Element> baseElementsList = rootElement.elements();
            for(Element baseElement:baseElementsList)
            {
                switch (baseElement.getName())
                {
                    case "jdbc":
                    {
                        // 读取配置信息
                        isJDBC(baseElement);
                        break;
                    }
                    case "class":
                    {
                        // 读取映射信息
                        isClass(baseElement);
                        break;
                    }
                }
            }

        } catch (DocumentException e) {
            System.out.println("Configuration文件读取失败"+e);
        }
    }

    private void isJDBC(Element jdbcElement)
    {
        List<Element> propertiesList = jdbcElement.elements();
        for(Element property:propertiesList)
        {
            // System.out.println(property.getName());
            Element nameElement = property.element("name");
            String nameText = nameElement.getTextTrim();
            Element valueElement = property.element("value");
            String valueText = valueElement.getTextTrim();

            System.out.println(nameText);
            switch (nameText)
            {
                case "driver_class":
                {
                    driver_class = valueText;
                    break;
                }
                case "url_path":
                {
                    url_path = valueText;
                    break;
                }
                case "db_username":
                {
                    db_username = valueText;
                    break;
                }
                case "db_userpassword":
                {
                    db_userpassword = valueText;
                    break;
                }
            }
        }
        // 检查用
        // System.out.println(driver_class+";"+url_path+";"+db_username+";"+db_userpassword);
    }

    private void isClass(Element classElement)
    {
        OrClassConfiguration orClassConfigurationTemp = new OrClassConfiguration();
        Element name = classElement.element("name");
//        Element table = classElement.element("table");
//        Element id_name = classElement.element("id").element("name");
        List<Element> classFeaturesList = classElement.elements();
        for(Element classFeatureElement:classFeaturesList)
        {
            switch (classFeatureElement.getName())
            {
                case "name":
                {
                    // orClassConfigurationTemp.getClassName(classFeatureElement.getTextTrim());
                    String textTrim = classFeatureElement.getTextTrim();
                    orClassConfigurationTemp.setClassName(textTrim);
                    break;
                }
                case "table":
                {
                    String textTrim = classFeatureElement.getTextTrim();
                    orClassConfigurationTemp.setTable(textTrim);
                    break;
                }
                case "id":
                {
                    Element id_name = classFeatureElement.element("name");
                    String textTrim = id_name.getTextTrim();
                    orClassConfigurationTemp.setClassId(textTrim);
                    break;
                }
                case "property":
                {
                    Element p_name = classFeatureElement.element("name");
                    Element p_column = classFeatureElement.element("column");
                    Element p_type = classFeatureElement.element("type");
                    Element p_lazy = classFeatureElement.element("lazy");

                    orClassConfigurationTemp.setPropertiy(p_name.getTextTrim(),p_column.getTextTrim(),p_type.getTextTrim(),p_lazy.getTextTrim());
                }
            }
        }
        classList.add(orClassConfigurationTemp);
    }

    public ArrayList<OrClassConfiguration> getClassList() {
        return classList;
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.readMapping();
        ArrayList<OrClassConfiguration> classList = configuration.getClassList();
        System.out.println("classList长度为:"+classList.size());
        for (OrClassConfiguration configClass:classList)
        {
            System.out.println("配置中的class的name为:"+configClass.getClassName());
            System.out.println("配置中的class的table为:"+configClass.getTable());
            System.out.println("配置中的class的id为:"+configClass.getTable());
            ArrayList<HashMap> propertiesList = configClass.getPropertiesList();
            for (HashMap<String,String> property:propertiesList)
            {
                System.out.println("---------------");
                System.out.println("    property的name:"+property.get("name"));
                System.out.println("    property的column:"+property.get("column"));
                System.out.println("    property的type:"+property.get("type"));
                System.out.println("    property的lazy:"+property.get("lazy"));
            }
        }
        System.out.println();
    }
    public String getDriver_class() {
        return driver_class;
    }

    public String getUrl_path() {
        return url_path;
    }

    public String getDb_username() {
        return db_username;
    }

    public String getDb_userpassword() {
        return db_userpassword;
    }

}