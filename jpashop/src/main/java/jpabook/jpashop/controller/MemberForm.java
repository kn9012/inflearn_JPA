package jpabook.jpashop.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    // MemberForm data를 따로 만든 이유는 기존 엔티티에 validation을 넣는다면 코드가 복잡해지기 때문에
    // 간단한 어플리케이션이 아니라면 따로 만드는 것이 좋다
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;
}
