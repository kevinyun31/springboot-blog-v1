package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "user_tb")
@Entity // ddl-auto가 crdate일때 자동으로 DB가 만들어진다.
public class User {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)  // 현재 나의 DB전략에 맞춰서 ID값을 증가시킨다.
 private Integer id;
 
 @Column(nullable = false, length = 20, unique = true)
 private String username;

 @Column(nullable = false, length = 20)
  private String password;

 @Column( nullable = false, length = 20)
 private String email;
  
    
}
