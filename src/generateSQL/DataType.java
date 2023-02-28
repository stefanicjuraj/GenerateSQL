package generateSQL;

public enum DataType {

    STRING(0), BOOLEAN(1), INT(2), DOUBLE(3);

    private final int value;

    DataType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}