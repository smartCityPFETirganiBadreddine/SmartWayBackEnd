    package com.example.backendsmartcities.entity;

    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import lombok.*;
    import org.hibernate.annotations.Loader;
    import org.hibernate.annotations.SQLDelete;
    import org.hibernate.annotations.Where;
    import org.springframework.format.annotation.DateTimeFormat;

    import javax.persistence.*;
    import javax.validation.constraints.Email;
    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.Size;
    import java.util.Date;
    import java.util.List;
    /**
     * Author: Badreddine TIRGANI
     */
    @Entity(name = "User")
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @JsonIdentityInfo(scope = User.class, generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @Table(name = "users")

    @SQLDelete(sql =
            "update User " +
                    "SET deleted = true " +
                    "where id = ?")
    @Loader(namedQuery = "findUserById")
    @NamedQuery(name = "findUserById", query =
            "select u " +
                    "from User u " +
                    "where " +
                    "    u.id = ?1 and " +
                    "    u.deleted = false")
    @Where(clause = "deleted = false")

    public class User extends BaseEntity{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank
        @Size(max = 60)
        private String userName;

        @NotBlank
        @Size(max = 100)
        @Email
        @Column(unique = true)
        private String email;

        private String cin;

        private boolean enabled;

        private String password;

        private String matricule;

        private boolean chargeDeTravaux;

        @DateTimeFormat(pattern = "dd-MM-yyyy")
        private Date lastLogin;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
        private List<Role> roles ;

        @Size(max = 60)
        private String firstName;

        @Size(max= 60)
        private String lastName;

        private boolean locked;

        private String phone;

        @ManyToOne

        private Branch branch;

        @ManyToOne

        private Team team;

        public User(String userName, String email, String password) {
            this.userName = userName;
            this.email = email;
            this.password = password;
        }
    }
