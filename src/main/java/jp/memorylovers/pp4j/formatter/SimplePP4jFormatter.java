package jp.memorylovers.pp4j.formatter;

import java.util.List;

public class SimplePP4jFormatter extends AbstractPP4jFormatter {

    @Override
    public String fmtValue(Object obj) {
        if (obj == null) {
            return "<null>";
        } else {
            return obj.toString().trim();
        }
    }

    @Override
    public String fmtFieldValue(int indent, String key, Object obj) {
        String value = fmtValue(obj);

        return appendLn(indent, key + " = " + value);
    }

    @Override
    public String fmtObject(int indent, Object obj, String contents) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    @Override
    public String fmtList(int indent, List<Object> obj, String contents) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

}
