package com.kathesama.app.service.infrastructure.adapter.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 4431993811144438298L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "create_at")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date createAt; // Se generará automáticamente al crear la entidad

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date updatedAt; // Se actualizará automáticamente al modificar la entidad

    /**
     * En la clase que lleva la relación (user) se hacen todas las especificaciones y luego en la clase subordinada
     * (roles) solo se hace referencia a la tabla principal de cuál es el campo que se va a vincular.
     * */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name="userId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId", referencedColumnName = "id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"userId", "roleId"})}
    )
    private List<RoleEntity> roles = new ArrayList<>();

    @Column(unique = true, length = 20)
    private String username;

    @Column(columnDefinition = "boolean default false") // Valor por defecto: false
    private Boolean enabled;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(unique = true, length = 100)
    private String email;

    @Column
    private String name;

    @Column
    private String lastname;

    @PrePersist
    public void prePersist(){
        enabled = true;
    }
}
