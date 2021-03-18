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
    private Long adminId;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String status;

    @Column
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
