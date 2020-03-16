package ar.com.kike.oauth2resourseserver.repository;

import ar.com.kike.oauth2resourseserver.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRespository  extends PagingAndSortingRepository<Post, String>,
        JpaSpecificationExecutor<Post> {

    Post findById(Long id);
    List<Post> findAllByUserId(Long id);

    @Override
    List<Post> findAll(Specification<Post> specification);

    @Query("SELECT p FROM Post p WHERE p.userId IN (:userIds)")
    List<Post> findByUsersId(@Param("userIds") List<Long> userIds);

    @Query("SELECT p FROM Post p WHERE p.userId IN (:userIds)")
    Page<Post> findByUsersIdPage(@Param("userIds") List<Long> userIds, Pageable pageable);
}