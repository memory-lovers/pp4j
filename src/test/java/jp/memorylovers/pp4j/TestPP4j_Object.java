package jp.memorylovers.pp4j;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestPP4j_Object {

    @Test
    public void test_Object_Primitive() {
        String[] actual = PP4j.pp(new TestObjectPrimitive())
            .split("\n");
        String[] expected = {
            "TestObjectPrimitive {",
            "  pubByte = 1",
            "  pubChar = a",
            "  pubInt = 11",
            "  pubLong = 22",
            "  pubFloat = 1.1",
            "  pubDouble = 2.2",
            "  pubBool = true",
            "}"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_Object_Wrapper() {
        String[] actual = PP4j.pp(new TestObjectWrapper())
            .split("\n");
        String[] expected = {
            "TestObjectWrapper {",
            "  pubByte = 1",
            "  pubStr = AAA",
            "  pubInt = 11",
            "  pubLong = 22",
            "  pubFloat = 1.1",
            "  pubDouble = 2.2",
            "  pubBool = true",
            "}"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_Object_Nest() {
        TestObjectNest parent = new TestObjectNest();
        TestObjectNest child = new TestObjectNest();
        parent.obj = child;

        String[] actual = PP4j.pp(parent)
            .split("\n");
        String[] expected = {
            "TestObjectNest {",
            "  str = AAA",
            "  obj = TestObjectNest {",
            "    str = AAA",
            "    obj = <null>",
            "  }",
            "}"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_Object_NestDouble() {
        TestObjectNest parent = new TestObjectNest();
        TestObjectNest child = new TestObjectNest();
        TestObjectNest gchild = new TestObjectNest();
        child.obj = gchild;
        parent.obj = child;

        String[] actual = PP4j.pp(parent)
            .split("\n");
        String[] expected = {
            "TestObjectNest {",
            "  str = AAA",
            "  obj = TestObjectNest {",
            "    str = AAA",
            "    obj = TestObjectNest {",
            "      str = AAA",
            "      obj = <null>",
            "    }",
            "  }",
            "}"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }
}
