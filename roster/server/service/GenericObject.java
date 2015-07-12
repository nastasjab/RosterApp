package server.service;

public abstract class GenericObject {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GenericObject() {
    }

    GenericObject(long id) {
        this.id = id;
    }
}
