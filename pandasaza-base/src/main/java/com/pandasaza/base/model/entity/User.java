package com.pandasaza.base.model.entity;

import com.pandasaza.base.model.enumclass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.One;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"itemList","dibList","orderDetailList","unactiveUserList"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String account;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private String nation;

    private LocalDateTime lastLoginAt;

    private String university;

    private String profileIcon;

    private String authMethods;

    private String authHistory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Item> itemList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Dib> dibList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<OrderDetail> orderDetailList;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<UnactiveUser> unactiveUserList;

}
