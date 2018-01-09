package sc.ustc.dao;

import java.util.ArrayList;
import java.util.HashMap;

class OrClassConfiguration {

    private String name;
    private String table;
    private String id;

    private ArrayList<HashMap> propertiesList = new ArrayList<>();

    // 包私有
    void setPropertiy(String p_name,String p_column,String p_type,String p_lazy)
    {
        HashMap<String, String> tempProperty = new HashMap<>();
        tempProperty.put("name",p_name);
        tempProperty.put("column",p_column);
        tempProperty.put("type",p_type);
        tempProperty.put("lazy",p_lazy);
        // 直接加到List中
        propertiesList.add(tempProperty);
    }

    ArrayList<HashMap> getPropertiesList() {
        return propertiesList;
    }
    String getClassName() {
        return name;
    }

    void setClassName(String name) {
        this.name = name;
    }

    String getTable() {
        return table;
    }

    void setTable(String table) {
        this.table = table;
    }

    String getClassId() {
        return id;
    }

    void setClassId(String id) {
        this.id = id;
    }

}