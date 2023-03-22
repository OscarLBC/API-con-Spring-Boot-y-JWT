package com.apisrpingboot.apispringjwt.controllers;

import com.apisrpingboot.apispringjwt.dao.UsuarioDao;
import com.apisrpingboot.apispringjwt.models.Usuario;
import com.apisrpingboot.apispringjwt.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;
    @PostMapping("/login")
    public String loginUusario(@RequestBody Usuario usuario){

        Usuario usuarioLogueado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);
        if(usuarioLogueado != null){
            /**
             * jwtUtil.create() creamos el token con id del usuario y el email
             */
           String tokenJwt =jwtUtil.create(String.valueOf(usuarioLogueado.getId()),usuarioLogueado.getEmail());
            return tokenJwt;
        }
        return "Error al iniciar sesion";

    }
}
