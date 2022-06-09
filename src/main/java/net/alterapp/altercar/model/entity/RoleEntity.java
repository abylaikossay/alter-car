package net.alterapp.altercar.model.entity;

import net.alterapp.altercar.model.enums.RoleEnum;
import lombok.*;
import net.alterapp.altercar.model.entity.audits.AuditEntityWithoutId;

import javax.persistence.*;
import java.util.*;


@Getter
@Setter
@Entity(name = "role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity extends AuditEntityWithoutId {

  @Id
  @Enumerated(value = EnumType.STRING)
  private RoleEnum id;

  @Column(name = "name")
  private String name;

  @ToString.Exclude
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "role_privilege",
      joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "privilege_id", referencedColumnName = "id")}
  )
  private Set<PrivilegeEntity> privileges = new HashSet<>();

}
