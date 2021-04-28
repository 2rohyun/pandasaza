package com.pandasaza.base.model.entity;

import com.pandasaza.base.model.enumclass.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@ToString(exclude = {"itemList","dibList","orderDetailList","unactiveUserList"})
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    //@Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false, length = 1000)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false)
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private LocalDateTime registeredAt;

    @Column(nullable = false)
    private String nation;

    @Column
    private LocalDateTime lastLoginAt;

    @Column
    private String university;

    @Column
    private String profileIcon;

    @Column
    private String authMethods;

    @Column
    private String authHistory;

    @Column
    private String auth;

    @OneToMany(mappedBy = "user")
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Dib> dibList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uu_id")
    private UnactiveUser unactiveUser;

    // 연관 설정
    public void addItem(Item item){
        item.setUser(this);
        this.itemList.add(item);
    }

    public void addDib(Dib dib){
        dib.setUser(this);
        this.dibList.add(dib);
    }

    public void addOrderDetail(OrderDetail orderDetail){
        orderDetail.setUser(this);
        this.orderDetailList.add(orderDetail);
    }

    public void setUnactiveUser(UnactiveUser unactiveUser) {
        this.unactiveUser = unactiveUser;
        unactiveUser.setUser(this);
    }







    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return account;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
