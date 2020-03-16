package ar.com.kike.oauth2resourseserver.rest;


import ar.com.kike.oauth2resourseserver.common.PageRequest;
import ar.com.kike.oauth2resourseserver.common.PageResponse;
import ar.com.kike.oauth2resourseserver.model.Post;
import ar.com.kike.oauth2resourseserver.service.PostService;
import ar.com.kike.oauth2resourseserver.utils.AppConstants;
import ar.com.kike.oauth2resourseserver.webtesting.dto.Normal;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/api")
@Produces(MediaType.APPLICATION_JSON)
public class PostRestController {

    @Autowired
    PostService postService;

    @GetMapping("/postsbyuser/{id}")
    @ApiOperation(value = "getPostByUSer", notes = "Allows to get all the posts from a given user", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getPostByUSer(
            @ApiParam(value = "User id to obtain Posts", example = "1")@PathVariable Long id){
        if(id == null) return ResponseEntity.badRequest().build();
        List<Post> postList = postService.getPostListByUser(id);
        if (postList.size() > 0) return new ResponseEntity<>(postList, getHeadersOk(), HttpStatus.OK);
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/posts/paged")
    @ApiOperation(value = "getPostPagedByUser", notes = "Allows to get all the posts from a given list of Users in a Paged way", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getPostPagedByUser(
            @ApiParam(value = "nro de pagina a devolver") @RequestParam(value = "page-number", required = false, defaultValue = "0") String pageNumber,
            @ApiParam(value = "cantidad de resultados a devolver") @RequestParam(value = "page-size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) String pageSize,
            @RequestBody List<Long> listaPost){

        if (listaPost== null) return ResponseEntity.badRequest().build();
        PageResponse<Post> page = postService.getPostPagedListByUser(new PageRequest(Integer.valueOf(pageNumber), Integer.valueOf(pageSize)),listaPost);
        return new ResponseEntity<>(page, getHeadersOk(), HttpStatus.OK);
        //return ResponseEntity.notFound().build();
    }

    private HttpHeaders getHeadersOk() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("status", "ok");
        responseHeaders.set("message", "succesfully");
        return responseHeaders;
    }
}
