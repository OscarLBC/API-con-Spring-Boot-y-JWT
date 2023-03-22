package com.apisrpingboot.apispringjwt.controllers;

import com.apisrpingboot.apispringjwt.dao.UsuarioDao;
import com.apisrpingboot.apispringjwt.models.Usuario;
import com.apisrpingboot.apispringjwt.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/usuarios")
    public void registreUsuario(@RequestBody Usuario usuario){

        /**
         * con argon2 encriptamos las contraseña y la guardamos en la base de datos
         */
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registreUsuario(usuario);

    }
    @DeleteMapping("/usuarios/{id}")
    public void deleteUsuario(@RequestHeader(value = "Authorization") String token,@PathVariable Long id){
        if(!validarToken(token)){
            return;
        }
    usuarioDao.deleteUsuario(id);
    }

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token){

        if(!validarToken(token)){
            return null;
        }
        return usuarioDao.getUsuarios();
    }

    public boolean validarToken(String token){
        String[] datos = token.split(" ");
        String usuarioId = jwtUtil.getKey(datos[1]);
        return usuarioId != null;
    }
}
