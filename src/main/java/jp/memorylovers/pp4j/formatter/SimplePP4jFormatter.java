package jp.memorylovers.pp4j.formatter;

import java.util.Arrays;

public class SimplePP4jFormatter extends AbstractPP4jFormatter {

    @Override
    public String fmtValue(Object obj) {
        if (obj == null) {
            return "<null>";
        } else {
            return obj.toString()
                .trim();
        }
    }

    @Override
    public String fmtListEmpty() {
        return "[]";
    }

    @Override
    public String fmtListEmpty(String key) {
        return fmtField(key, fmtListEmpty());
    }

    @Override
    public String fmtValue(String fName, Object obj) {
        return fmtField(fName, fmtValue(obj));
    }

    @Override
    public String fmtObject(String fName, String cName, String[] fields) {
        return fmtField(fName, fmtObject(cName, fields));
    }

    @Override
    public String fmtPrimitiveList(String[] elems) {
        return "[" + String.join(", ", elems) + "]";
    }

    @Override
    public String fmtPrimitiveList(String fName, String[] elems) {
        return fmtField(fName, fmtPrimitiveList(elems));
    }

    @Override
    public String fmtObjectList(String[] elements) {
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");
        Arrays.stream(elements)
            .map(elm -> elm.split("\n"))
            .flatMap(c -> Arrays.stream(c))
            .forEach(elm -> {
                sb.append(indent(1) + elm + "\n");
            });
        sb.append("]");

        return sb.toString();
    }

    @Override
    public String fmtObjectList(String fName, String[] elements) {
        return fmtField(fName, fmtObjectList(elements));
    }

    @Override
    public String fmtObject(String className, String[] fields) {
        StringBuilder sb = new StringBuilder();

        sb.append(className + " {\n");
        Arrays.stream(fields)
            .map(field -> field.split("\n"))
            .flatMap(c -> Arrays.stream(c))
            .forEach(field -> {
                sb.append(indent(1) + field + "\n");
            });
        sb.append("}");
        return sb.toString();
    }

    @Override
    public String fmtField(String key, String value) {
        return key + " = " + value;
    }
}
