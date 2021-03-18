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
@ToString(exclude = {"itemList"})
public class Category {

    @Id
    private Long categoryId;

    @Column
    private String type;

    @Column
    private int groupCode;

    //Category 1 : N Item
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Item> itemList;

}
