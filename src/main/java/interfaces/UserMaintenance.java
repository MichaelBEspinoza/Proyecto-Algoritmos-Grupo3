package interfaces;

import domain.User;

import java.util.List;

public interface UserMaintenance {
    /***
    * ### 2. Mantenimiento de Usuarios**/

    /**
    - *CRUD de usuarios:*
    - createUser(User user): boolean: Agrega un nuevo usuario a la lista doblemente enlazada circular y devuelve true si se crea con éxito.**/
    public boolean createUser(User user);

    /**
    - readUser(int userId): User: Busca y devuelve un usuario por su ID.**/
    public User readUser(int UserID);

    /**
    - updateUser(User user): boolean: Actualiza la información de un usuario existente y devuelve true si se actualiza con éxito.**/
    public boolean updateUser(User user);

    /**
    - deleteUser(int userId): boolean: Elimina un usuario por su ID y devuelve true si se elimina con éxito.**/
    public boolean deleteUser(int userId);

    /**
    - listUsers(): List<User>: Devuelve una lista de todos los usuarios.**/
    public List<User> listUsers();

    /**
    - *Notificaciones por correo electrónico:*
    - sendEmailNotification(User user, String message): void: Envía un correo electrónico al usuario.**/
    public void sendEmailNotification(User user, String message);

    /**
    - *Gestión de perfil:*
    - updateProfile(User user): boolean: Permite al usuario actualizar su perfil y devuelve true si se actualiza con éxito.**/
    public boolean updateProfile(User user);

    /**
    - changePassword(int userId, String newPassword): boolean: Permite al usuario cambiar su contraseña y devuelve true si se cambia con éxito.
    */
    public boolean changePassword(int userId, String newPassword);
}
