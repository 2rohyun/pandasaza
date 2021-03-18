package com.pandasaza.base.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"item","user","chattingList","reviewList"})
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column
    private String status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Item item;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderDetail")
    private List<Chatting> chattingList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderDetail")
    private List<Review> reviewList;

}
