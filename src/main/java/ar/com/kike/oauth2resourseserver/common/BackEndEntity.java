package ar.com.kike.oauth2resourseserver.common;


import ar.com.kike.oauth2resourseserver.json.JSONDateTimeDeserialize;
import ar.com.kike.oauth2resourseserver.json.JSONDateTimeSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BackEndEntity extends BackEndObject {

    private static final long serialVersionUID = -5354058460706112830L;

//    @JsonProperty("id")
//    @Id
//    @Column(unique = true)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = JSONDateTimeDeserialize.class)
    @JsonSerialize(using = JSONDateTimeSerialize.class)
    @Column(name = "CREATED_DATE", updatable = false)
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
