package ar.com.kike.oauth2resourseserver.webtesting.dto;

public class Normal {
    private long id;
    private String name;

    public Normal() {
        super();
    }

    public Normal(final long id, final String name) {
        super();

        this.id = id;
        this.name = name;
    }

    //

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}