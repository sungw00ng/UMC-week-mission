// src/main/java/week4/domain/Term.java
package week6.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import week6.domain.Enums.TermName;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "term")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "term_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", columnDefinition = "enum('AGE','SERVICE','PRIVACY','LOCATION','MARKETING')")
    private TermName name;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<UserTerm> userTerms = new ArrayList<>();
}