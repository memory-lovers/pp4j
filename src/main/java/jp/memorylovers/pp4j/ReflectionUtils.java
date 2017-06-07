package jp.memorylovers.pp4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {
    private static final List<Class<?>> PRIMITEVES = Arrays.asList(
        new Class<?>[] {
            byte.class,
            char.class,
            int.class,
            long.class,
            float.class,
            double.class,
            boolean.class,
            Byte.class,
            Character.class,
            String.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class,
            Boolean.class
    });

    public static boolean isList(Type obj) {
        return obj == List.class;
    }

    public static boolean isPrimitive(Type object) {
        return PRIMITEVES.contains(object);
    }

    public static boolean isPrimitiveList(Field field) {
        if (!isList(field.getType())) {
            return false;
        }

        Type gType = field.getGenericType();
        if (!(gType instanceof ParameterizedType)) {
            return false;
        }
        Type[] pType = ((ParameterizedType) gType).getActualTypeArguments();
        if (pType == null || pType.length != 1) {
            return false;
        }

        return isPrimitive(pType[0]);
    }
}
