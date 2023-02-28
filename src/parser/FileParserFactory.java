package parser;

import java.util.HashMap;
import java.util.Map;

public class FileParserFactory {

    private static final Map<String, FileParser> parsers;

    private FileParserFactory() {
    }

    static {
        parsers = new HashMap();
        parsers.put(".edg", new ConvertFileParser());
        parsers.put(".sav", new SaveFileParser());
        parsers.put(".xml", new XmlFileParser());
    }

    public static FileParser get(String type) {
        return parsers.get(type);
    }

}