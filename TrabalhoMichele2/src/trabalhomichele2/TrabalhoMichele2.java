package trabalhomichele2;

import java.util.HashMap;
import java.util.Map;

public class TrabalhoMichele2 {
    
    private static Map<String, String> users;

    public static void main(String[] args) {
        users = new HashMap<>();
        Inicial.main(args);
    }
    
    public static boolean usuarioExiste(String login, String senha) {
        for (Map.Entry<String, String> entry : users.entrySet()) {
            if (entry.getKey().equals(login) && entry.getValue().equals(senha)) {
                return true;
            }
        }
        return false;
    }
    
    public static void adicionaUsuario(String login, String senha) throws Exception {
        if (users.keySet().contains(login)) {
            throw new Exception();
        }
        users.put(login, senha);
    }
}
