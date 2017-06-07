package jp.memorylovers.pp4j;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        TestObjectInList obj = new TestObjectInList();
        Class<?> cls = obj.getClass();

        for (Field field : cls.getFields()) {
            String fName = field.getName();
            Type type = field.getGenericType();

            ParameterizedType pType = (ParameterizedType) type;

            System.out.println(
                fName + " : " + type + " : " + pType
                    .getActualTypeArguments()[0]);
            System.out.println(fName + " : " + (field.getType() == List.class));
        }

    }
}
