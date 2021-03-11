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
@ToString(exclude = {"user","item"})
public class Dib {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dibId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Item item;

}
