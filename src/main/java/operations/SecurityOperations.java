package operations;

import domain.Role;
import domain.User;
import interfaces.SystemSecurity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityOperations implements SystemSecurity  {

    File file = new File("Registro.txt");
    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&+=*'?¿}{,.]).+$";


    public SecurityOperations()  {
    }

    @Override
    public boolean login(String username, String password) {

        return false;
    }

    @Override
    public String encryptPassword(String password) {

        char []encryptedPassword = password.toCharArray();

        for (int i = 0; i < encryptedPassword.length; i++) encryptedPassword[i] = (char) (encryptedPassword[i] + (char) 5);

        String encryptedFields = String.valueOf(encryptedPassword);

        return encryptedFields;
    }

    @Override
    public void assignRole(User user, String rolePassword) {
        user.setRole(Role.USER);

    }

    @Override
    public boolean register(String username, String password, String email, String rolePassword) {

        Pattern rangePattern = Pattern.compile(regex);
        Matcher regexMatcher = rangePattern.matcher(password);

        boolean register = false;

        if (!username.isEmpty() && !password.isEmpty() && regexMatcher.find()){
            PrintWriter printer = null;
            try {
                printer = new PrintWriter(file);

                String saveUser = username;
                String savePassword = password;
                String saveEmail = email;
                printer.println(saveUser);
                printer.println(savePassword);
                printer.println(saveEmail);

                register = true;

                if(rolePassword.equals("Admin")){
                    User usuario = new User(util.Utility.getRandom(20),username, password, email);

                    assignRole(usuario, rolePassword);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }


        return register;
    }
}
