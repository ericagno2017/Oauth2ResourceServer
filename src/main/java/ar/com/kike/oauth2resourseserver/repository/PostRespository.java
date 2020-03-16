package ar.com.kike.oauth2resourseserver.repository;

import ar.com.kike.oauth2resourseserver.model.Post;
import ar.com.kike.oauth2resourseserver.model.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRespository  extends PagingAndSortingRepository<Post, String>,
        JpaSpecificationExecutor<Post> {

   // List<Post> findAllByUserId(Long id);

    @Query("SELECT new ar.com.kike.oauth2resourseserver.model.PostDto(p.id, p.title, p.userId) " +
            "FROM Post p WHERE p.userId = :id")
    List<PostDto> findAllByUserId(Long id);

    @Query("SELECT p FROM Post p WHERE p.id IN (:ids)")
    Page<Post> findByPostIdPage(@Param("ids") List<Long> ids, Pageable pageable);

}