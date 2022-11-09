package org.safari.houseservicebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Profile {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "lastname", nullable = false)
    private String lastname;
    @Column(name = "company")
    private String company;

    // relation
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    private User user;
}
