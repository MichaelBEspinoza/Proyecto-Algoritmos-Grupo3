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
        // Verificar si la lista está vacía
        if (list.isEmpty()) {
            System.out.println("La lista de usuarios está vacía.");
            return false;
        }

        try {
            Node aux = list.getNode(0);  // Obtener el primer nodo

            for (int i = 0; i < list.size(); i++) {
                if (aux == null) {
                    System.out.println("El nodo es null en la iteración " + i);
                    return false;
                }

                User user = (User) aux.data;

                if (user == null) {
                    System.out.println("El usuario es null en la iteración " + i);
                    return false;
                }

                if (user.getName().equals(username)) {
                    String passwordDes = desencryptedPassword(user.getPassword());

                    if (passwordDes.equals(password))
                        return true;
                }
                aux = aux.next;  // Ir al siguiente nodo
            }
        } catch (ListException e) {
            e.printStackTrace();
            return false;
        }

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

    @Override
    public void assignRole(User user, Role role) {
        user.setRole(role);
        saveUserInformationinFile();
    } // Método que asigna los roles al usuario

    @Override
    public boolean register(String username, String password, String email, Role userRole) {
        String encryptedPassword = encryptPassword(password);

        User user = new User(idCounter++, username, encryptedPassword, email);
        user.setRole(userRole);

        list.add(user);
        saveUserInformationinFile();

        return true;
    }

    public User getUserByUsername(String username) throws ListException {
        Node aux = list.getNode(0);
        for (int i = 0; i < list.size(); i++) {
            User user = (User) aux.data;
            if (user.getName().equals(username)) {
                return user;
            }
            aux = aux.next;
        }
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
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Suponiendo que el formato de cada línea es "id,username,password,email,role"
                String[] userDetails = line.split(",");
                if (userDetails.length == 5) {
                    int id = Integer.parseInt(userDetails[0]);
                    String username = userDetails[1];
                    String password = userDetails[2];
                    String email = userDetails[3];
                    Role role = Role.valueOf(userDetails[4]);

                    User user = new User(id, username, password, email);
                    user.setRole(role);
                    list.add(user);  // Agregar el usuario a la lista
                } else {
                    System.out.println("Formato de línea incorrecto: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void saveUserInformationinFile() {
        PrintWriter writer;

        try {
            writer = new PrintWriter(file);
            Node currentNode = list.getNode(0);

            for (int i = 0; i < list.size(); i++) {
                User user = (User) currentNode.data;
                writer.println(user.getId() + "," + user.getName() + "," + user.getPassword() + "," + user.getEmail() + "," + user.getRole());
                currentNode = currentNode.next;
            }
            writer.close(); // Cerrar el writer para asegurarse de que los datos se guarden correctamente
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }
}
