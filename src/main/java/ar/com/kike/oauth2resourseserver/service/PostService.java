package ar.com.kike.oauth2resourseserver.service;

import ar.com.kike.oauth2resourseserver.common.PageRequest;
import ar.com.kike.oauth2resourseserver.common.PageResponse;
import ar.com.kike.oauth2resourseserver.model.Post;
import ar.com.kike.oauth2resourseserver.model.PostDto;
import ar.com.kike.oauth2resourseserver.repository.PostCommentRepository;
import ar.com.kike.oauth2resourseserver.repository.PostRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PostService {

    @Autowired
    PostRespository postRespository;

    @Autowired
    PostCommentRepository postCommentRepository;

    public List<PostDto> getPostListByUser(Long id) {
        if(id!= null){
            return  postRespository.findAllByUserId(id);
        }

        return null;
    }

    public PageResponse<Post> getPostPagedListByPost(PageRequest pageRequest, List<Long> listaPost) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Instant start = Instant.now();
        // time passes
        Page<Post> page =  postRespository.findByPostIdPage(listaPost,pageRequest.convert(sort));
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        log.info("TIME ELAPSED ON PAGED QUERY " + timeElapsed + " FINDING "+ page.getTotalElements() +" ELEMENTS " );

        List<Post> postList = page.getContent();
        return PageResponse.fromList(postList, pageRequest.getPageNumber(), pageRequest.getPageSize(), page.getTotalElements());
    }
}
