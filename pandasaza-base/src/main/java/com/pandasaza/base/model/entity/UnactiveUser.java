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

    private String authMethods;

    private String authHistory;

    private String profileIcon;

    @ManyToOne
    private User user;

}
