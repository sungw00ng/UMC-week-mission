// src/main/java/week4/domain/UserTerm.java
package week7.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import week7.domain.Term;
import week7.domain.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_term")
public class UserTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_term_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "term_id", nullable = false)
    private Term term;
}