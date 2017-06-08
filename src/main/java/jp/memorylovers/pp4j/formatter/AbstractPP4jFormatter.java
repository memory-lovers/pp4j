package jp.memorylovers.pp4j.formatter;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractPP4jFormatter implements IPP4jFormatter {

    protected static final String INDENT_DLM = "  ";

    protected String appendLn(int indent, String contents) {
        return indent(indent) + contents + "\n";
    }

    protected String indent(int indent) {
        return StringUtils.repeat(INDENT_DLM, indent);
    }
}
