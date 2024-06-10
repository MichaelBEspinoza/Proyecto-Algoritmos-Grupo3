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
    private static final int idCounter = 1; // Constante para llevar la contabilidad de cuántos usuarios han sido registrados
    CircularLinkedList list = new CircularLinkedList();

    public SecurityOperations() {
        loadUserToFile();
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
    public String encryptPassword(String password) {
        char[] encryptedPassword = password.toCharArray();

        for (int i = 0; i < encryptedPassword.length; i++) {
            char c = encryptedPassword[i];
            if (c >= 32 && c <= 126) { // Rango de caracteres ASCII visibles
                c = (char) (c + 5);
                if (c > 126) {
                    c = (char) (c - 95); // Volver al inicio del rango visible
                }
            }
            encryptedPassword[i] = c;
        }

        return String.valueOf(encryptedPassword);
    }


    public String decryptPassword(String password) { // Método que desencripta la contraseña del usuario
        char[] decryptedPassword = password.toCharArray(); // Arreglo de tipo caracter que contendrá cada letra de la contraseña

        for (int i = 0; i < decryptedPassword.length; i++) {
            char c = decryptedPassword[i];
            if (c >= 32 && c <= 126) { // Rango de caracteres ASCII visibles
                c = (char) (c - 5);
                if (c < 32) {
                    c = (char) (c + 95); // Volver al final del rango visible
                }
            }
            decryptedPassword[i] = c;
        }

        return String.valueOf(decryptedPassword); // Se retorna la contraseña desencriptada
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

    public void loadUserToFile() {
        try (BufferedReader br = getBufferedReader()) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length == 8) {
                    int id = Integer.parseInt(userDetails[0]);
                    String username = userDetails[1];
                    String password = userDetails[2];
                    String email = userDetails[3];
                    Role role = Role.valueOf(userDetails[4]);
                    String country = userDetails[5];
                    String city = userDetails[6];
                    String place = userDetails[7];

                    User user = new User(id, username, password, email, role, country, city, place);

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
