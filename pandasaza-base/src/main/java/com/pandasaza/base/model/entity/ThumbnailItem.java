package com.pandasaza.base.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Builder
@Accessors(chain = true)
@ToString(exclude = {"item"})
public class ThumbnailItem {

    @Id
    private Long tiId;

    @ManyToOne
    private Item item;

    @Column
    private String status;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    private LocalDateTime registeredAt;

    @Column
    private String thumbnailImageUrl;

    @Column
    private Integer cntLike;

    @Column
    private Integer cntShow;

}
