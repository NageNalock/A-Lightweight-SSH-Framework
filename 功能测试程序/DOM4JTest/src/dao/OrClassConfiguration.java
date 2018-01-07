package dao;

import java.util.ArrayList;
import java.util.HashMap;

public class OrClassConfiguration {

    private String name;
    private String table;
    private String id;

    ArrayList<HashMap> propertiesList = new ArrayList<>();

    public void setPropertiy(String p_name,String p_column,String p_type,String p_lazy)
    {
        HashMap<String, String> tempProperty = new HashMap<>();
        tempProperty.put("name",p_name);
        tempProperty.put("column",p_column);
        tempProperty.put("type",p_type);
        tempProperty.put("lazy",p_lazy);
        // 直接加到List中
        propertiesList.add(tempProperty);
    }

    public ArrayList<HashMap> getPropertiesList() {
        return propertiesList;
    }
    public String getClassName() {
        return name;
    }

    public void setClassName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}