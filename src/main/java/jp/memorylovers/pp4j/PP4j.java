package jp.memorylovers.pp4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.memorylovers.pp4j.formatter.IPP4jFormatter;
import jp.memorylovers.pp4j.formatter.SimplePP4jFormatter;

public class PP4j {
    private static final Logger LOG = LoggerFactory.getLogger(PP4j.class);

    private IPP4jFormatter formatter;
    private boolean visibleNull = true;

    private PP4j(IPP4jFormatter formatter) {
        this.formatter = formatter;
    }

    public static String pp(Object root) {
        IPP4jFormatter formatter = new SimplePP4jFormatter();
        return new PP4j(formatter).prittyPrint(root);
    }

    public String prittyPrint(Object root) {
        return prittyPrint(0, null, root);
    }

    @SuppressWarnings("unchecked")
    private String prittyPrint(int indent, String fieldName, Object obj) {

        if (obj instanceof List) {
            return ppList(indent, fieldName, (List<Object>) obj);
        } else {
            return ppObject(indent, fieldName, obj);
        }
    }

    private String ppList(int indent, String fieldName, List<Object> list) {
        if (list.isEmpty()) {
            if (fieldName == null) {
                return formatter.fmtListEmpty();
            } else {
                return formatter.fmtListEmpty(fieldName);
            }
        }

        Object elm = list.get(0);
        if (ReflectionUtils.isPrimitive(elm.getClass())) {
            String[] vals = list.stream()
                .map(Object::toString)
                .toArray(String[]::new);
            if (fieldName == null) {
                return formatter.fmtPrimitiveList(vals);
            } else {
                return formatter.fmtPrimitiveList(fieldName, vals);
            }
        } else {
            String[] contents = list.stream()
                .map(c -> prittyPrint(indent + 1, null, c))
                .toArray(String[]::new);
            if (fieldName == null) {
                return formatter.fmtObjectList(contents);
            } else {
                return formatter.fmtObjectList(fieldName, contents);
            }

        }
    }

    private String ppObject(int indent, String fieldName, Object obj) {
        if (obj == null) {
            return formatter.fmtValue(fieldName, obj);
        }

        Class<?> cls = obj.getClass();
        String clsName = cls.getName();
        if (clsName.startsWith("java")) {
            return "";
        }

        String className = cls.getSimpleName();
        // System.out.println("class name is " + className);

        String[] contents = Arrays.stream(cls.getDeclaredFields())
            .map(field -> {
                field.setAccessible(true);
                try {
                    return Optional.of(ppField(indent + 1, field, obj));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                return Optional.empty();
            })
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toArray(String[]::new);

        if (fieldName == null) {
            return formatter.fmtObject(className, contents);
        } else {
            return formatter.fmtObject(fieldName, className, contents);
        }
    }

    private String ppField(int indent, Field field, Object obj)
            throws IllegalArgumentException, IllegalAccessException {

        Object fObj = field.get(obj);
        String fName = field.getName();
        LOG.debug("field name[" + fName + "] is " + field.getType());
        if (fObj == null) {
            return formatter.fmtValue(fName, fObj);
        } else if (ReflectionUtils.isPrimitive(field.getType())) {
            LOG.debug("field name[" + fName + "] is " + field.getType());
            return formatter.fmtValue(fName, fObj);
        } else if (ReflectionUtils.isPrimitiveList(field)) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) fObj;
            String[] vals = list.stream()
                .map(Object::toString)
                .toArray(String[]::new);
            return formatter.fmtPrimitiveList(fName, vals);
        } else if (ReflectionUtils.isList(field.getType())) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) fObj;
            if (list.isEmpty()) {
                return formatter.fmtListEmpty(fName);
            } else {
                String[] contents = list.stream()
                    .map(c -> prittyPrint(indent + 1, null, c))
                    .toArray(String[]::new);
                return formatter.fmtObjectList(fName, contents);
            }
        } else {
            return prittyPrint(indent, fName, fObj);
        }
    }
}
