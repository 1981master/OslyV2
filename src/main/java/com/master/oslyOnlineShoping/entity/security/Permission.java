
package com.master.oslyOnlineShoping.entity.security;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Permission")
public class Permission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "permission_id") // Explicit column name
  private Long id;

  private String name;

  private String description;

  @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
  private List<RolePermission> rolePermissions;

  // Getters and Setters

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<RolePermission> getRolePermissions() {
    return rolePermissions;
  }

  public void setRolePermissions(List<RolePermission> rolePermissions) {
    this.rolePermissions = rolePermissions;
  }
}
