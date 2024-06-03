package operations;

import domain.Role;
import domain.User;
import interfaces.SystemSecurity;
import structures.lists.CircularLinkedList;
import structures.lists.ListException;
import structures.lists.Node;

import java.io.*;

public class SecurityOperations implements SystemSecurity  {

    File file = new File("Registro.txt"); //Archivo de texto en donde se guardarán los registros de los usuarios
    private static int idCounter = 1; //Constante para llevar la contabilidad de cuántos usuarios han sido registrados
    CircularLinkedList list = new CircularLinkedList();

    public SecurityOperations(){
        loadUsertoFile();
    }

    @Override
    public boolean login(String username, String password) { //Método que se encarga de verificar si el usuario puede logearse

        if(list.isEmpty()) return false;

        try{ //Try catch para atrapar excepciones

            Node aux = list.getNode(0);

            for (int i = 0; i < list.size(); i++) {

                User user = (User) aux.data;

                if (user.getName().equals(username)) { //Si el nombre que se obtuvo del arreglo, es el mismo del que pasa como parámetro

                    String passwordDes = desencryptedPassword(user.getPassword()); //Se desencripta la contraseña guardada

                    if (passwordDes.equals(password))
                        return true; //Si estos dos datos corresponden, retorna que el usuario sí se ha encontrado
                }

            }
        } catch (ListException e) {
            throw new RuntimeException(e);
        }

        return false; //Sino se encontró al usuario, se retorna un false.
    }

    @Override
    public String encryptPassword(String password) { //Método que encrypta la contraseña del usuario al registrarse

        char []encryptedPassword = password.toCharArray(); //Arreglo de tipo caracter que contendrá cada letra de la contraseña

        //Se recorre el arreglo para ir encriptando la contraseña de forma que, por cada letra encontrada, se le agregan 5 letras después del abecedario
        //para que no sea reconocida
        for (int i = 0; i < encryptedPassword.length; i++) encryptedPassword[i] = (char) (encryptedPassword[i] + (char)5);

        return String.valueOf(encryptedPassword); //Se retorna la contraseña encriptada
    }

    public String desencryptedPassword(String password){ //Método que desencripta la contraseña del usuario

        char []desencryptedPassword = password.toCharArray(); //Arreglo de tipo caracter que contendrá cada letra de la contraseña

        //Se recorre el arreglo para ir desencriptando la contraseña de forma que, por cada letra encontrada, se le restan 5 letras después del abecedario
        //para que no sea reconocida
        for (int i = 0; i < desencryptedPassword.length; i++) desencryptedPassword[i] = (char) (desencryptedPassword[i] - (char) 5);

        return String.valueOf(desencryptedPassword); //Se retorna la contraseña encriptada
    }

    @Override
    public void assignRole(User user, Role role) { user.setRole(role); saveUserInformationinFile();} //Método que asigna los roles al usuario

    @Override
    public boolean register(String username, String password, String email, Role userRole) {

        String encryptedPassword = encryptPassword(password);

        User user = new User(idCounter++, username, encryptedPassword, email);
        user.setRole(userRole);

        list.add(user);
        saveUserInformationinFile();

        return true;
    }

    public BufferedReader getBufferedReader(){

        BufferedReader br = null;

        try{

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

        }catch (FileNotFoundException e){
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        return br;
    }

    public void loadUsertoFile(){

        String line;

        BufferedReader br = getBufferedReader();

        try {
            line = br.readLine();

            while (line != null){
                String []userData = line.split(" ");
                int id = Integer.parseInt(userData[0]);
                String username = userData[1];
                String password = userData[2];
                String email = userData[3];
                Role role = Role.valueOf(userData[4]);

                User user = new User(id, username, password, email);
                user.setRole(role);

                list.add(user);
                if (id >= idCounter) idCounter = id + 1; // Mantener idCounter actualizado

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserInformationinFile(){

        PrintWriter writer;

        try {
            writer = new PrintWriter(file);

            Node currentNode = list.getNode(1);

            for (int i = 1; i <= list.size(); i++) {
                User user = (User) currentNode.data;
                writer.println(user.getId() + " " + user.getName() + " " + user.getPassword() + " " + user.getEmail() + " " + user.getRole());
                currentNode = currentNode.next;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

}
