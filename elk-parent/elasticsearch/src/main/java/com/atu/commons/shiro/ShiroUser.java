/**
 *
 */
package com.atu.commons.shiro;

import java.io.Serializable;
import java.util.Set;

/**
 * @description：自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 */
public class ShiroUser implements Serializable {
    private static final long serialVersionUID = -1373760761780840081L;
    
    private String id;
    private final String username;
    private String name;
    private Set<String> urlSet;
    private Set<String> roles;
  

    public ShiroUser(String username) {
        this.username = username;
    }

    public ShiroUser(String id, String username, String name, Set<String> urlSet) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.urlSet = urlSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getUrlSet() {
        return urlSet;
    }

    public void setUrlSet(Set<String> urlSet) {
        this.urlSet = urlSet;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }
    

	/**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return name;
    }
}