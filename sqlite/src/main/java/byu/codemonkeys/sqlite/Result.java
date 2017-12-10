package byu.codemonkeys.sqlite;

class Result {
    private String id;
    private String data;

    public Result(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String id() {
        return id;
    }

    public String data() {
        return data;
    }
}
