package com.pandasaza.base.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false, length = 20)
    private String account;

    @Column(nullable = false, length = 1000)
    private String password;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String role;

    @Column
    private LocalDateTime lastLoginAt;

    @Column
    private LocalDateTime passwordUpdatedAt;

    @Column
    private int loginFailCount;

    @Column
    private LocalDateTime registeredAt;

}
