package ar.com.kike.oauth2resourseserver.webtesting.controller;

import ar.com.kike.oauth2resourseserver.webtesting.dto.Normal;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

@Controller
public class NormalController {

    public NormalController() {
        super();
    }

    // API - read
    @PreAuthorize("#oauth2.hasScope('normal') and #oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/normal/{id}")
    @ApiOperation(value = "Find One", notes = "Just a test endpoint to evaluate oaut", response = Normal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    @ResponseBody
    public Normal findById(@PathVariable final long id) {
        return new Normal(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
    }

    // API - write
    @PreAuthorize("#oauth2.hasScope('normal') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Allows to Create one", notes = "Just a test endpoint to evaluate oaut", response = Normal.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    @RequestMapping(method = RequestMethod.POST, value = "/normal")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Normal create(@RequestBody final Normal normal) {
        normal.setId(Long.parseLong(randomNumeric(2)));
        return normal;
    }

}
