package com.pandasaza.base.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"user","category","dibList","orderDetailList","thumbnailItemList"})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column
    private String status;

    @Column
    private String name;

    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private BigDecimal price;

    @Column
    private LocalDateTime registeredAt;


    @Column
    private Integer cntLike;

    @Column
    private Integer cntShow;

    //Item N : 1 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //Item N : 1 Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<Dib> dibList = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    //Item 1 : N ItemImage
    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImageList = new ArrayList<>();

    // 연관 설정
    public void addDib(Dib dib){
        dib.setItem(this);
        this.dibList.add(dib);
    }
    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setItem(this);
        this.orderDetailList.add(orderDetail);
    }
    public void addItemImage(ItemImage itemImage){
        itemImage.setItem(this);
        this.itemImageList.add(itemImage);
    }

}
