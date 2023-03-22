package com.apisrpingboot.apispringjwt.dao;

import com.apisrpingboot.apispringjwt.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {

    private String query="";
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Usuario> getUsuarios() {
        query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void deleteUsuario(Long id) {
       Usuario usuario = entityManager.find(Usuario.class,id);
       entityManager.remove(usuario);
    }

    @Override
    public void registreUsuario(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        /**
         * hacemos una consulta sql donde buscamos el usuario por email
         * y lo guardamos en una list<Usuario>
         */
        query = "FROM Usuario WHERE email= :email";
       List<Usuario> lista =  entityManager.createQuery(query)
                .setParameter("email",usuario.getEmail())
                 .getResultList();

        /**
         * verificamos si las list esta vacia
         */

        if(lista.isEmpty()){
           return null;
       }

        /**
         * hacemos las comparacion de contraseña con hash y normal
         */
       String passwordHash = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(passwordHash,usuario.getPassword())){
            return lista.get(0);
        }

        return null;
    }
}
