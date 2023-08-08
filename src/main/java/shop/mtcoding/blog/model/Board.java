package shop.mtcoding.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.agent.builder.AgentBuilder.PoolStrategy.Eager;

@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create일때 자동으로 DB가 만들어진다.
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 현재 나의 DB전략에 맞춰서 ID값을 증가시킨다.
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, length = 10000)
    private String content;
    private Timestamp createdAt;

    @ManyToOne
    private User user;
    // private Integer userId; 이렇게 해도 됨.

    @JsonIgnoreProperties({ "board" })
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<Reply> replys;

}
