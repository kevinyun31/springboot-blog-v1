package shop.mtcoding.blog.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessionUser {
    
    private Integer id;
    private String username;
    private String password;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
