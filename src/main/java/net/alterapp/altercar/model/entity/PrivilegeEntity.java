package net.alterapp.altercar.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;
import net.alterapp.altercar.model.enums.PrivilegeEnum;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;


@Data
@Builder
@Entity
@Table(name = "privilege")
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Account session table")
public class PrivilegeEntity {

  @Id
  @Enumerated(value = EnumType.STRING)
  private PrivilegeEnum id;

  @Column(name = "name")
  private String name;

  @ToString.Exclude
  @ManyToMany(mappedBy = "privileges")
  private Collection<RoleEntity> roles;

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrivilegeEntity privilege = (PrivilegeEntity) o;
    return id == privilege.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
