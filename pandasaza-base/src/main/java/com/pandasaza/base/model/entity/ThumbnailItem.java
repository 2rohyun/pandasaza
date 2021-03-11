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

    private String status;

    private String name;

    private BigDecimal price;

    private LocalDateTime registeredAt;

    private String thumbnailImageUrl;

    private Integer cntLike;

    private Integer cntShow;

}
