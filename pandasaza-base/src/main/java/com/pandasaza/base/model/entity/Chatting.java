package com.pandasaza.base.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"orderDetail"})

//TODO ( after implementing chatting server )
public class Chatting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chattingId;

    @ManyToOne
    private OrderDetail orderDetail;

    @Column(columnDefinition="TEXT")
    private String chatContent;

}
