package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor // 모든 필드로 생성자를 만들어줌
@RequiredArgsConstructor // final이 붙은 필드로만 생성자를 만들어줌
public class MemberService {
    /*
        1. Field Injection
        테스트 코드를 작성하거나 할때 값 변경이 불가능하므로 주입해주기가 까다로움
    */
//    @Autowired
    // MemberRepository의 값은 변경될일 없기 때문에 final 설정해주어야 함
    private final MemberRepository memberRepository;

    /*
        2. Setter Injection
        테스트 코드 작성 시 mock을 직접 주입해줄 수 있음
        but, spring 작동 후에 값이 변경되는 것은 불필요
     */
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /*
        3. 생성자 Injection
        spring 작동 시 생성하면서 Injection이 같이 주입됨
        테스트 케이스 작성 시 의존관계를 명확히 알 수 있는 장점이 있음
        생성자가 하나만 있는 경우 자동으로 @Autowired 설정됨
     */
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    // 데이터 변경은 Transaction이 있어야 함
    // 보통 읽기 전용이므로 클래스 단에 readOnly = true해주고 읽기 전용이 아닌 경우에만 @Transactional 붙여주기
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 아이디로 회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
