package jp.memorylovers.pp4j.formatter;

public abstract class AbstractPP4jFormatter implements IPP4jFormatter {

    protected String appendLn(int indent, String contents) {
        String str = "";
        for (int i = 0; i < indent; i++) {
            str += "  ";
        }

        return str + contents + "\n";
    }
}
