package ar.com.kike.oauth2resourseserver.webtesting.controller;

import ar.com.kike.oauth2resourseserver.webtesting.dto.Super;
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
public class SuperController {

    public SuperController() {
        super();
    }

    // API - read
    @PreAuthorize("#oauth2.hasScope('super') and #oauth2.hasScope('read')")
    @ApiOperation(value = "Find One", notes = "Just a test endpoint to evaluate oaut", response = Super.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    @RequestMapping(method = RequestMethod.GET, value = "/super/{id}")
    @ResponseBody
    public Super findById(@PathVariable final long id) {
        return new Super(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
    }

    // API - write
    @PreAuthorize("#oauth2.hasScope('super') and #oauth2.hasScope('write')")
    @ApiOperation(value = "Allows to create one", notes = "Just a test endpoint to evaluate oaut", response = Super.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error interno del sistema => Ver code y message lanzados"),
            @ApiResponse(code = 401, message = "Usuario no autenticado"),
            @ApiResponse(code = 403, message = "Usuario no autorizado")})
    @RequestMapping(method = RequestMethod.POST, value = "/super")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Super create(@RequestBody final Super superS) {
        superS.setId(Long.parseLong(randomNumeric(2)));
        return superS;
    }

}
