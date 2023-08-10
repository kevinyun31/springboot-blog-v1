package shop.mtcoding.blog;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptTest {
	public static void main(String[] args) {

		String password = "1234";
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("encPassword : " + encPassword);
        System.out.println(encPassword.length());

        String newEncPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("newEncPassword : " + newEncPassword);
        System.out.println(newEncPassword.length());

        // boolean isValid = BCrypt.checkpw("12345", encPassword);
        // System.out.println(isValid);
    }
}
 