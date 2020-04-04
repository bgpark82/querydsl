package study.querydsl.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Commit // 다른 테스트 할 때 데이터가 남아있어서 문제가 될 수 있음
class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 초기화
        em.flush();     // 영속석 context에 있는 Object들을 실제 쿼리를 만들어서 DB에 보냄
        em.clear();     // 영속성 context를 완전 초기화해서 cache 날림


        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        for(Member member : members){
            System.out.println("member = " + member);
            System.out.println("team = " + member.getTeam());
        }

    }
}