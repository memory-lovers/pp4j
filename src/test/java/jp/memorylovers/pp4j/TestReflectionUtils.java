package jp.memorylovers.pp4j;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestReflectionUtils {

    @Test
    public void test_isPrimitive() {
        Class<?>[] primitives = {
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
        };

        for (Class<?> cls : primitives) {
            String msg = cls.getName() + " is " + cls;
            assertTrue(msg, ReflectionUtils.isPrimitive(cls));
        }
    }

    @Test
    public void test_isPrimitiveList() {
        List<String> list = new ArrayList<>();
        list.add(null);

        Class<?> cls = list.getClass();

        System.out.println("class is " + cls);
        Type type = cls.getGenericSuperclass();
        System.out.println("type is " + type);

        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            System.out.println("paramType is " + paramType);

            Type[] actTypes = paramType.getActualTypeArguments();
            Arrays.stream(actTypes)
                .forEach(tv -> {
                    System.out.println("actual Type variable is " + tv);
                });
        }

        System.out.println(cls.getName());
        Arrays.stream(cls.getGenericInterfaces())
            .forEach(ty -> System.out.println("Type is " + ty));
    }
}
