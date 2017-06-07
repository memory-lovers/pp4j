package jp.memorylovers.pp4j;

import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PP4j {
    private static final Logger LOG = LoggerFactory.getLogger(PP4j.class);

    private StringBuilder sb = new StringBuilder();
    private boolean visibleNull = true;

    public static String pp(Object root) {
        return new PP4j().prittyPrint(root);
    }

    public String prittyPrint(Object root) {
        return prittyPrint(0, null, root);
    }

    @SuppressWarnings("unchecked")
    private String prittyPrint(int indent, String fieldName, Object obj) {

        if (obj instanceof List) {
            ppList(indent, fieldName, (List<Object>) obj);
        } else {
            ppObject(indent, fieldName, obj);
        }
        return sb.toString();
    }

    private void ppList(int indent, String fieldName, List<Object> list) {
        if (list.isEmpty()) {
            appendLn(indent, "[]");
            return;
        }

        Object elm = list.get(0);
        if (ReflectionUtils.isPrimitive(elm.getClass())) {
            String[] vals = list.stream()
                .map(Object::toString)
                .toArray(String[]::new);
            appendLn(indent, "[" + String.join(", ", vals) + "]");
        } else {
            appendLn(indent, "[");
            for (Object c : list) {
                prittyPrint(indent + 1, null, c);
            }
            appendLn(indent, "]");
        }
    }

    private void ppObject(int indent, String fieldName, Object obj) {
        if (obj == null) {
            appendField(indent, fieldName, obj);
            return;
        }

        Class<?> cls = obj.getClass();
        String clsName = cls.getName();
        if (clsName.startsWith("java")) {
            return;
        }

        String className = cls.getSimpleName();
        // System.out.println("class name is " + className);

        if (fieldName == null) {
            appendLn(indent, className + " {");
        } else {
            appendLn(indent, fieldName + " = " + className + " {");
        }

        indent++;
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                ppField(indent, field, obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        indent--;
        appendLn(indent, "}");
    }

    private void ppField(int indent, Field field, Object obj)
            throws IllegalArgumentException, IllegalAccessException {

        Object fObj = field.get(obj);
        String fName = field.getName();
        LOG.debug("field name[" + fName + "] is " + field.getType());
        if (fObj == null) {
            appendField(indent, fName, fObj);
        } else if (ReflectionUtils.isPrimitive(field.getType())) {
            LOG.debug("field name[" + fName + "] is " + field.getType());
            appendField(indent, fName, fObj);
        } else if (ReflectionUtils.isPrimitiveList(field)) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) fObj;
            String[] vals = list.stream()
                .map(Object::toString)
                .toArray(String[]::new);
            appendField(indent, fName, "[" + String.join(", ", vals) + "]");
        } else if (ReflectionUtils.isList(field.getType())) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) fObj;
            if (list.isEmpty()) {
                appendLn(indent, field.getName() + " = []");
            } else {
                appendLn(indent, field.getName() + " = [");
                list.stream()
                    .forEach(c -> prittyPrint(indent + 1, null, c));
                appendLn(indent, "]");
            }
        } else {
            prittyPrint(indent, fName, fObj);
        }
    }

    private void appendLn(int indent, String contents) {
        String str = "";
        for (int i = 0; i < indent; i++) {
            str += "  ";
        }

        sb.append(str + contents + "\n");
    }

    private void appendField(int indent, String key, Object obj) {
        String value;

        if (obj == null) {
            value = "<null>";
        } else {
            value = obj.toString()
                .trim();
        }

        if (visibleNull || (!visibleNull && obj != null)) {
            appendLn(indent, key + " = " + value);
        }
    }
}
