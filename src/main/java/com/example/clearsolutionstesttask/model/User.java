package com.example.clearsolutionstesttask.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class User {
  @Email(message = "Invalid email format")
  @NotNull(message = "Email is required")
  private String email;

  @NotNull(message = "First name is required")
  private String firstName;

  @NotNull(message = "Last name is required")
  private String lastName;

  @NotNull(message = "Birth date is required")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Past(message = "Birth date must be in the past")
  private LocalDate birthDate;

  private String address;  // Optional

  private String phoneNumber;  // Optional


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}

