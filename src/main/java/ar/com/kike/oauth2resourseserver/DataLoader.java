package ar.com.kike.oauth2resourseserver;

import ar.com.kike.oauth2resourseserver.model.Post;
import ar.com.kike.oauth2resourseserver.model.PostComment;
import ar.com.kike.oauth2resourseserver.model.User;
import ar.com.kike.oauth2resourseserver.repository.PostCommentRepository;
import ar.com.kike.oauth2resourseserver.repository.PostRespository;
import ar.com.kike.oauth2resourseserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader {

    //Using user repository only to load data to DB . In production this is not needed
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRespository postRespository;

    @Autowired
    PostCommentRepository postCommentRepository;

    private static final Random generator = new Random();


    @PostConstruct
    public void loadInitialData(){
        List<User> userList = userRepository.findAll();
        Iterable<Post> postIterable = postRespository.findAll();
        if (!userList.isEmpty() && !postIterable.iterator().hasNext()){ //si hay que inicializar la DB
            for(User usuario :userList){
                //por cada usuario crea un valor entre 500 y 1000 post!
                Integer postQty = getRandomInRange(500,1000);
                for(Integer postNumber = 1; postNumber <= postQty; postNumber++ ){
                    Post post = new Post();
                   // post.setId(Long.valueOf(postNumber));
                    post.setTitle("Post Title Number " + postNumber);
                    post.setUserId(usuario.getId());
                    postRespository.save(post);
                    createPostComment(post);
                }
            }
        }
    }

    private void createPostComment(Post post){
        //por cada post crear entre 4 y 8 comentarios
        Integer postCommentqty = getRandomInRange(4,8);
        List<PostComment> postCommentList = new ArrayList<>();
        for (Integer postCommentNumber = 1;postCommentNumber<=postCommentqty; postCommentNumber++){
            PostComment postComment = new PostComment();
            postComment.setReview("Comment Number " + postCommentNumber + " for Post Number " + post.getId());
//            postComment.setUserId(idUsuario);
            postComment.setPost(post);
            postCommentList.add(postComment);
        }
        postCommentRepository.saveAll(postCommentList);
    }

    private static int getRandomInRange(int start, int end) { return start + generator.nextInt(end - start + 1); }

}
