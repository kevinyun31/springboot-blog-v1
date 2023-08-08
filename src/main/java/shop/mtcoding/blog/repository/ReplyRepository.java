package shop.mtcoding.blog.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import shop.mtcoding.blog.dto.ReplyWriteDTO;
import shop.mtcoding.blog.dto.WriteDTO;

@Repository
public class ReplyRepository {
    
    @Autowired
    private EntityManager em;


    // userId로 로그인을 해야지 댓글을 쓸수 있고 = 댓글을 쓴 ID
	// boardId로 몇번째 글에 댓글을 달 수 있다 = 댓글의 번호 ID
	@Transactional
   public void save(ReplyWriteDTO replyWriteDTO, Integer userId) {

		Query query = em.createNativeQuery(
				"insert into reply_tb(comment, board_id, user_id) values(:comment, :boardId, :userId)");

		query.setParameter("comment", replyWriteDTO.getComment());
		query.setParameter("boardId", replyWriteDTO.getBoardId());  // 게시글의 ID
		query.setParameter("userId", userId);  // 댓글 쓰는 사람의 ID
		query.executeUpdate();
	}

}
