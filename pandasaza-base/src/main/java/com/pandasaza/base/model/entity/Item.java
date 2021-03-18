package com.pandasaza.base.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private String itemImagesUrl;

    @Column
    private Integer cntLike;

    @Column
    private Integer cntShow;

    //Item N : 1 User
    @ManyToOne
    private User user;

    //Item N : 1 Category
    @ManyToOne
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<Dib> dibList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<OrderDetail> orderDetailList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<ThumbnailItem> thumbnailItemList;

}
