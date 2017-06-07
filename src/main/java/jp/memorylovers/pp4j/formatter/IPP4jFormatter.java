package jp.memorylovers.pp4j.formatter;

import java.util.List;

public interface IPP4jFormatter {

    public String fmtValue(int indent, Object obj);

    public String fmtObject(int indent, Object obj, String contents);

    public String fmtList(int indent, List<Object> obj, String contents);
}
