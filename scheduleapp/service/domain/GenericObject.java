package service.domain;

public abstract class GenericObject {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    GenericObject() {
    }

    GenericObject(long id) {
        this.id = id;
    }
}
