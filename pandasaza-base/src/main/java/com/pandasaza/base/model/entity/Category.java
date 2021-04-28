package com.pandasaza.base.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private int groupCode;

    //Category 1 : N Item
    @OneToMany(mappedBy = "category")
    private List<Item> itemList = new ArrayList<>();

    //==연관 메서드==//
    public void addItem(Item item){
        item.setCategory(this);
        this.itemList.add(item);
    }

}
