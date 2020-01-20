public enum Header {
    MINIMAL("minimal"),
    CONCISE("concise"),
    ALL("all");

    private String name;

    Header(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
