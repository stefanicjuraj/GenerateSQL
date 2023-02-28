package ddl;

import java.util.HashMap;
import java.util.Map;

public class DDLGeneratorFactory {

    private static final Map<String, CreateDDL> ddl;

    private DDLGeneratorFactory() {
    }

    static {
        ddl = new HashMap();
        ddl.put("0", new DDLMySQL());
        ddl.put("1", new DDLOracle());
    }

    public static CreateDDL get(String type) {
        return ddl.get(type);
    }

}