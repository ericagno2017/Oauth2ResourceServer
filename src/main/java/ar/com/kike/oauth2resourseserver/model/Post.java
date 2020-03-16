package ar.com.kike.oauth2resourseserver.model;


import ar.com.kike.oauth2resourseserver.common.BackEndEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "Post")
@Table(name = "post")
public class Post extends BackEndEntity {

    @JsonProperty("postId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long userId;

    @OneToMany(
            mappedBy = "post",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JsonProperty("comentarios")
    private List<PostComment> comments = new ArrayList<>();

    //Constructors, getters and setters removed for brevity

    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}
