package jp.memorylovers.pp4j;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestPP4j_List {

    @Test
    public void test_List_Primitive() {
        List<String> list = Arrays.asList("a", "b", "c");
        String[] actual = PP4j.pp(list)
            .split("\n");
        String[] expected = {
            "[a, b, c]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_List_List_Primitive() {
        List<List<String>> plist = new ArrayList<>();
        plist.add(Arrays.asList("a", "b", "c"));
        plist.add(Arrays.asList("a", "b", "c"));

        String[] actual = PP4j.pp(plist)
            .split("\n");
        String[] expected = {
            "[",
            "  [a, b, c]",
            "  [a, b, c]",
            "]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_List_Object() {
        List<TestObjectNest> list = new ArrayList<>();
        list.add(new TestObjectNest());
        list.add(new TestObjectNest());

        String[] actual = PP4j.pp(list)
            .split("\n");
        String[] expected = {
            "[",
            "  TestObjectNest {",
            "    str = AAA",
            "    obj = <null>",
            "  }",
            "  TestObjectNest {",
            "    str = AAA",
            "    obj = <null>",
            "  }",
            "]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_List_Object_empty() {
        List<TestObjectNest> list = new ArrayList<>();

        String[] actual = PP4j.pp(list)
            .split("\n");
        String[] expected = {
            "[]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_List_Object_null() {
        List<TestObjectNest> list = new ArrayList<>();
        list.add(null);

        String[] actual = PP4j.pp(list)
            .split("\n");
        String[] expected = {
            "[<null>]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_List_List_Object() {
        List<List<TestObjectNest>> plist = new ArrayList<>();
        List<TestObjectNest> list = new ArrayList<>();
        list.add(new TestObjectNest());
        list.add(new TestObjectNest());

        plist.add(list);
        plist.add(list);

        String[] actual = PP4j.pp(plist)
            .split("\n");
        String[] expected = {
            "[",
            "  [",
            "    TestObjectNest {",
            "      str = AAA",
            "      obj = <null>",
            "    }",
            "    TestObjectNest {",
            "      str = AAA",
            "      obj = <null>",
            "    }",
            "  ]",
            "  [",
            "    TestObjectNest {",
            "      str = AAA",
            "      obj = <null>",
            "    }",
            "    TestObjectNest {",
            "      str = AAA",
            "      obj = <null>",
            "    }",
            "  ]",
            "]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_List_List_Object_child_empty() {
        List<List<TestObjectNest>> plist = new ArrayList<>();
        List<TestObjectNest> list = new ArrayList<>();
        plist.add(list);
        plist.add(list);

        String[] actual = PP4j.pp(plist)
            .split("\n");
        String[] expected = {
            "[",
            "  []",
            "  []",
            "]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_List_List_Object_child_null() {
        List<List<TestObjectNest>> plist = new ArrayList<>();
        plist.add(null);
        plist.add(null);

        String[] actual = PP4j.pp(plist)
            .split("\n");
        String[] expected = {
            "[<null>, <null>]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }

    @Test
    public void test_List_List_Object_child_nullMix() {
        List<List<TestObjectNest>> plist = new ArrayList<>();
        List<TestObjectNest> clist = new ArrayList<>();
        clist.add(new TestObjectNest());
        plist.add(null);
        plist.add(clist);

        String[] actual = PP4j.pp(plist)
            .split("\n");
        String[] expected = {
            "[",
            "  <null>",
            "  [",
            "    TestObjectNest {",
            "      str = AAA",
            "      obj = <null>",
            "    }",
            "  ]",
            "]"
        };

        String msg = "actual is \n" + String.join("\n", actual) + "\n";
        assertArrayEquals(msg, expected, actual);
    }
}
