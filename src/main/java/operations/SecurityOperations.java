package operations;

import domain.Role;
import domain.User;
import interfaces.SystemSecurity;
import structures.lists.CircularLinkedList;
import structures.lists.ListException;
import structures.lists.Node;

import java.io.*;

public class SecurityOperations implements SystemSecurity {

    File file = new File("users.txt"); // Change file reference to users.txt
    private static int idCounter = 1; // Constante para llevar la contabilidad de cuántos usuarios han sido registrados
    CircularLinkedList list = new CircularLinkedList();

    public SecurityOperations() {
        loadUsertoFile();
    }


    @Override
    public boolean login(String username, String password) {
        System.out.println("Intentando iniciar sesión con el usuario: " + username);

        if (list.isEmpty()) {
            System.out.println("La lista de usuarios está vacía.");
            return false;
        }

        try {
            Node aux = list.getNode(1);  // Obtener el primer nodo
            if (aux == null) {
                System.out.println("El nodo inicial es null.");
                return false;
            }

            Node firstNode = aux;  // Guardar referencia al primer nodo
            int index = 1;

            do {
                User user = (User) aux.data;
                if (user == null) {
                    System.out.println("El usuario es null en la iteración " + index);
                    return false;
                }

                System.out.println("Verificando usuario en índice " + index + ": " + user.getName());

                if (user.getName().equals(username)) {
                    //String passwordDes = desencryptedPassword(user.getPassword());
                    System.out.println("Contraseña para encriptar: " + user.getPassword());

                    if (user.getPassword().equals(password)) {
                        System.out.println("Inicio de sesión exitoso para el usuario: " + username);
                        return true;
                    }
                }

                aux = aux.next;  // Ir al siguiente nodo
                index++;
            } while (aux != firstNode);  // Continuar hasta volver al primer nodo
        } catch (ListException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("Nombre de usuario o contraseña incorrectos para el usuario: " + username);
        return false;
    }


    @Override
    public String encryptPassword(String password) { // Método que encrypta la contraseña del usuario al registrarse
        char[] encryptedPassword = password.toCharArray(); // Arreglo de tipo caracter que contendrá cada letra de la contraseña

        // Se recorre el arreglo para ir encriptando la contraseña de forma que, por cada letra encontrada, se le agregan 5 letras después del abecedario
        // para que no sea reconocida
        for (int i = 0; i < encryptedPassword.length; i++) encryptedPassword[i] = (char) (encryptedPassword[i] + (char) 5);

        return String.valueOf(encryptedPassword); // Se retorna la contraseña encriptada
    }

    public String desencryptedPassword(String password) { // Método que desencripta la contraseña del usuario
        char[] desencryptedPassword = password.toCharArray(); // Arreglo de tipo caracter que contendrá cada letra de la contraseña

        // Se recorre el arreglo para ir desencriptando la contraseña de forma que, por cada letra encontrada, se le restan 5 letras después del abecedario
        // para que no sea reconocida
        for (int i = 0; i < desencryptedPassword.length; i++) desencryptedPassword[i] = (char) (desencryptedPassword[i] - (char) 5);

        return String.valueOf(desencryptedPassword); // Se retorna la contraseña desencriptada
    }


    public User getUserByUsername(String username) throws ListException {
        if (list.isEmpty()) {
            return null;
        }

        Node aux = list.getNode(1); // Comenzar desde el primer nodo
        if (aux == null) {
            System.out.println("El primer nodo es null.");
            return null;
        }

        Node firstNode = aux;
        int index = 1;

        do {
            User user = (User) aux.data;
            if (user == null) {
                System.out.println("Usuario es null en el índice " + index);
                return null;
            }

            if (user.getName().equals(username)) {
                return user;
            }

            aux = aux.next;
            index++;
        } while (aux != firstNode); // Continuar hasta volver al primer nodo

        return null;
    }


    public BufferedReader getBufferedReader() {
        BufferedReader br = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

        } catch (FileNotFoundException e) {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        return br;
    }

    public void loadUsertoFile() {

        try (BufferedReader br = getBufferedReader()) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length == 5) {
                    int id = Integer.parseInt(userDetails[0]);
                    String username = userDetails[1];
                    String password = userDetails[2];
                    String email = userDetails[3];
                    Role role = Role.valueOf(userDetails[4]);

                    User user = new User(id, username, password, email, role);

                    list.add(user);  // Agregar el usuario a la lista
                    System.out.println("Usuario añadido: " + line);
                } else {
                    System.out.println("Formato de línea incorrecto: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

}
