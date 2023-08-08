package shop.mtcoding.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.ManyToAny;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// User(1) - Reply(N)
// Board(1) - Reply(N)

@Setter
@Getter
@Table(name = "reply_tb")
@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(nullable = false, length = 100)
    private String comment; // 댓글 내용

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user; // FK user_id

    @ManyToOne
    private Board board; // FK board_id

}
