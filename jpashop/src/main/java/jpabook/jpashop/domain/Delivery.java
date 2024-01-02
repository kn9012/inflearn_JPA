package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    // EnumType.ORDINAL을 쓰게 된다면 순서만큼 int형으로 생기게 되는데
    // 새로운 EnumType이 들어오게 되면 오류가 생기므로 절대 사용 x
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP
}
