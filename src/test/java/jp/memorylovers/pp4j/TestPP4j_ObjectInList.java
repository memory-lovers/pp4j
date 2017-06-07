package jp.memorylovers.pp4j;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class TestPP4j_ObjectInList {

    @Test
    public void test_ObjectInList() {
        TestObjectInList obj = new TestObjectInList();
        obj.str = Arrays.asList("a", "b", "c");

        String[] actual = PP4j.pp(obj)
            .split("\n");
        String[] expected = {
            "TestObjectInList {",
            "  str = [a, b, c]",
            "  obj = <null>",
            "}"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }
}
