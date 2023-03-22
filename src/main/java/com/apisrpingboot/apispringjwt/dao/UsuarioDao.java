package com.apisrpingboot.apispringjwt.dao;

import com.apisrpingboot.apispringjwt.models.Usuario;

import java.util.List;

public interface UsuarioDao {

    List<Usuario> getUsuarios();
    void deleteUsuario(Long id);

    void registreUsuario(Usuario usuario);

    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}
