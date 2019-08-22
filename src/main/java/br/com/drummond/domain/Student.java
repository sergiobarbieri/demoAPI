package br.com.drummond.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Student {

 @Id
 private Integer ra;
 private String name;
 private String email;

 public Integer getRa() {
 return ra;
 }
 public void setRa(Integer ra) {
 this.ra = ra;
 }
 public String getName() {
 return name;
 }
 public void setName(String name) {
 this.name = name;
 }
 public String getEmail() {
 return email;
 }
 public void setEmail(String email) {
 this.email = email;
 }
} 
