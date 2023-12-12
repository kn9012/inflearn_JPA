package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
// Rollback이 포함되어 있기 때문에 insert 자체를 수행하지 않음
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("Kim");

        // when
        Long saveId = memberService.join(member);

        // then
        em.flush(); // 영속성 컨텍스트에 있는 변경이나 등록 내용을 DB에 반영해줌
        // 즉, DB에는 실제로 값이 들어가지 않지만 insert 쿼리문을 확인할 수 있음
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 줃복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("Kim");

        Member member2 = new Member();
        member2.setName("Kim");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2); // 이름이 같으므로 예외가 발생해야 한다!
        } catch (IllegalStateException e) {
            return;
        }

        // then
        assertThrows(IllegalStateException.class, () -> {
            fail("예외가 발생해야 한다.");
        });
    }
}