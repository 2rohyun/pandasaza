package com.pandasaza.base.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.mariadb.jdbc.internal.com.read.resultset.ColumnDefinition;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @OneToOne
    @JoinColumn(name = "user")
    private User user;


    @Length(min = 10, max = 11)
    private String phone;

    @CreatedDate
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;

}
