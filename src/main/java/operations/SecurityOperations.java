package operations;

import domain.Role;
import domain.User;
import interfaces.SystemSecurity;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityOperations implements SystemSecurity  {

    File file = new File("Registro.txt"); //Archivo de texto en donde se guardarán los registros de los usuarios
    private static int idCounter = 1; //Constante para llevar la contabilidad de cuántos usuarios han sido registrados

    @Override
    public boolean login(String username, String password) { //Método que se encarga de verificar si el usuario puede logearse

        BufferedReader br = getBufferedReader(); //Se declara un objeto de tipo BufferedReader para realizar la lectura del archivo

        try{ //Try catch para atrapar excepciones

            String line = ""; //Objeto tipo string para que contenga la información del archivo
            while (line != null){ //Se realiza el recorrido en el archivo

                line = br.readLine(); //Se leen las líneas del archivo
                String []userData = line.split(" "); //Se le quitan los espacios al archivo
                String arrayUsername = userData[1]; //La posicion 1 del arreglo corresponde al nombre del usuario
                String arrayPassword = userData[2]; //La posicion 2 del arreglo corresponde a la contraseña guardada

                if (arrayUsername.equals(username)){ //Si el nombre que se obtuvo del arreglo, es el mismo del que pasa como parámetro

                    String passwordDes = desencryptedPassword(arrayPassword); //Se desencripta la contraseña guardada

                    if (passwordDes.equals(password)) return true; //Si estos dos datos corresponden, retorna que el usuario sí se ha encontrado
                }

            }
        } catch (IOException e) { //Excepciones si existe algún error
            throw new RuntimeException(e);
        }

        return false; //Sino se encontró al usuario, se retorna un false.
    }

    @Override
    public String encryptPassword(String password) {

        char []encryptedPassword = password.toCharArray();

        for (int i = 0; i < encryptedPassword.length; i++) encryptedPassword[i] = (char) (encryptedPassword[i] + (char)5);

        return String.valueOf(encryptedPassword);
    }

    public String desencryptedPassword(String password){
        char []desencryptedPassword = password.toCharArray();

        for (int i = 0; i < desencryptedPassword.length; i++) desencryptedPassword[i] = (char) (desencryptedPassword[i] - (char) 5);

        return String.valueOf(desencryptedPassword);
    }

    @Override
    public void assignRole(User user, Role role) { user.setRole(role); }

    @Override
    public boolean register(String username, String password, String email, Role userRole) {

        boolean register; //Variable booleana que verifica si un usuario ya se ha registrado
        StringBuilder userRegistrarion = new StringBuilder(); //StringBuilder para concatenar la informacion del nuevo usuario registrado

        PrintWriter writer; //Declaracion de un objeto PrinterWriter para escribir la informacion de nuevo usuario en el archivo
        try {

            writer = new PrintWriter(file); //Se ingresa el nombre del archivo en donde se va a escribir el registro del usuario
            //Concatenacion de la informacion del usuario
            int id = idCounter++; //Variable que corresponde al incremento de una constante para generar automaticamente el id.
            String encryptedPassword = encryptPassword(password);

            User user = new User(id, username, encryptedPassword, email);

            userRegistrarion.append(id + " ").append(username + " ").append(encryptedPassword + " ").append(email + " ").append(userRole + " ");
            writer.println(userRegistrarion); //Se escribe la informacion del usuario en el archivo

            register = true; //Variable register pasa a ser true, indicando que el usuario ha sido registrado

        } catch (FileNotFoundException e) { throw new RuntimeException(e);}

        return register; //Se retorna el estado indicativo de la variable.
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

}
