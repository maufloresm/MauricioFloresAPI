package com.product.api.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.product.api.domain.Usuario;

  // Localiza usuarios por su username

@Repository
public class UsuarioJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Optional<Usuario> findByUsername(String username) {
        String sql = """
            SELECT username, password, role, status
              FROM usuario
             WHERE username = ?
        """;
        
        List<Usuario> list = jdbcTemplate.query(
            sql,
            (rs, rn) -> new Usuario(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getInt("status")
            ),
            username
        );
        return list.stream().findFirst();
    }


    public Optional<Usuario> findByUsernameAndPassword(String username, String hashPass) {
        String sql = """
            SELECT username, password, role, status
              FROM usuario
             WHERE username = ?
               AND password = ?
        """;
        List<Usuario> list = jdbcTemplate.query(
            sql,
            (rs, rn) -> new Usuario(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getInt("status")
            ),
            username, hashPass
        );
        return list.stream().findFirst();
    }
}

