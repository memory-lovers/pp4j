package jp.memorylovers.pp4j.formatter;

public interface IPP4jFormatter {

    public String fmtValue(Object value);

    public String fmtValue(String fieldName, Object value);

    public String fmtListEmpty();

    public String fmtListEmpty(String fieldName);

    public String fmtPrimitiveList(String[] elements);

    public String fmtPrimitiveList(String fieldName, String[] elements);

    public String fmtObjectList(String fieldName, String[] elements);

    public String fmtObjectList(String[] elements);

    public String fmtObject(String clsName, String[] fields);

    public String fmtObject(String fName, String cName, String[] fields);

    public String fmtField(String fieldName, String value);
}
