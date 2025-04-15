
package com.master.oslyOnlineShoping.entity.security;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id") // Explicit column name
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
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

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }

  public List<RolePermission> getRolePermissions() {
    return rolePermissions;
  }

  public void setRolePermissions(List<RolePermission> rolePermissions) {
    this.rolePermissions = rolePermissions;
  }
}
