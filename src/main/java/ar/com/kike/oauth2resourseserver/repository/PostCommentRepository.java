package ar.com.kike.oauth2resourseserver.repository;

import ar.com.kike.oauth2resourseserver.model.PostComment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends PagingAndSortingRepository<PostComment, String>,
        JpaSpecificationExecutor<PostComment> {

    PostComment findById(Long id);
}