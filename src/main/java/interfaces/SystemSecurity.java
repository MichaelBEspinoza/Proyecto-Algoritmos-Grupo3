package interfaces;

import domain.Role;
import domain.User;

public interface SystemSecurity {

    /**Inicio de sesion
    /*### 1. Seguridad del Sistema**/

    /**- - *Inicio de sesión:*
     login(String username, String password): boolean: Verifica las credenciales del usuario y devuelve true si son correctas, false en caso contrario.**/
    public boolean login(String username, String password);

    /** - *Encriptación de contraseñas:*
     * encryptPassword(String password): String: Aplica un algoritmo de encriptación y devuelve la contraseña encriptada.**/
    public void encryptPassword(String password);

    /**- *Gestión de roles:*
     - assignRole(User user, Role role): void: Asigna un rol a un usuario (administrador, instructor, usuario).**/
    public void assignRole(User user, Role role);
}
