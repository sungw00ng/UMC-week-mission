// src/main/java/week4/domain/Food.java
package week6.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import week6.domain.UserFood;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    // Food와 UserFood는 1:N 관계
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    private List<UserFood> userFoods = new ArrayList<>();
}