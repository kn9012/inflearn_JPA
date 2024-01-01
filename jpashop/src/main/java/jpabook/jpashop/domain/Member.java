package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NotEmpty가 모든 경우에 적용되기 때문에 신중하게 결정하기!
    private String name;

    @Embedded
    private Address address;

    /*
    Order와 Member는 다대일 관계
    mappedBy의 뜻은 Order에 있는 "member" 필드에 의해서 매핑 됐음을 표시
    Order 테이블이 주인이라면 주인이 아닌 테이블의 필드에 표시해준다.
    */

    @JsonIgnore // 양방향 관계일 경우 무한루프가 돌게 되어 한쪽에 @JsonIgnore를 설정해주어야 함
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
