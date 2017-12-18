/**
 * \* Created with IntelliJ IDEA.
 * \* User: hasee
 * \* Date: 2017/12/18
 * \* Time: 21:21
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class FormViewAttributes {
    private String viewType;



    private String name;
    private String label;
    private String value;

    public FormViewAttributes(String type)
    {
        this.viewType = type;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}