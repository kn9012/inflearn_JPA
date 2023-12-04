package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // Order와 Member는 다대일 관계
    /*
    Member와의 관계에서 Order가 주인이 됨
    : Order에 FK가 있기 때문?
    */
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // 주로 Order를 보고 Delivery를 찾으므로 (주문 정보를 보고 배송 정보를 찾지 배송 정보를 보고 주문 정보를 찾지 않기 때문)
    // Order에 FK를 둔다.
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]
}
