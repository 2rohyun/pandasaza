package com.pandasaza.base.model.entity;

import com.pandasaza.base.model.enumclass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"user"})
public class UnactiveUser {


    @Id
    private Long uuId;

    @Column
    private String account;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private LocalDateTime registeredAt;

    @Column
    private String nation;

    @Column
    private LocalDateTime lastLoginAt;

    @Column
    private String university;

    @Column
    private String authMethods;

    @Column
    private String authHistory;

    @Column
    private String profileIcon;

    @OneToOne(mappedBy = "unactiveUser", fetch = FetchType.LAZY)
    private User user;

}
