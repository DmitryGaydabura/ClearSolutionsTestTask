package com.example.clearsolutionstesttask.controllers;

import com.example.clearsolutionstesttask.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

  private List<User> users = new ArrayList<>();
  @Value("${user.minimum-age}")
  private int minimumAge;

  @PostMapping
  public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
    LocalDate today = LocalDate.now();
    Period age = Period.between(user.getBirthDate(), today);
    if (age.getYears() < minimumAge) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("User must be at least " + minimumAge + " years old.");
    }
    users.add(user);
    return ResponseEntity.ok("User created successfully.");
  }


  @PatchMapping("/{email}")
  public ResponseEntity<?> updateUserFields(@PathVariable String email,
      @RequestBody Map<String, Object> updates) {
    User user = findUserByEmail(email);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    // Apply updates to user
    patchUser(user, updates);
    return ResponseEntity.ok("User updated successfully.");
  }

  @PutMapping("/{email}")
  public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody User updatedUser) {
    User user = findUserByEmail(email);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    // Update user fields
    user.setEmail(updatedUser.getEmail());
    user.setFirstName(updatedUser.getFirstName());
    user.setLastName(updatedUser.getLastName());
    user.setBirthDate(updatedUser.getBirthDate());
    user.setAddress(updatedUser.getAddress());
    user.setPhoneNumber(updatedUser.getPhoneNumber());
    return ResponseEntity.ok("User updated successfully.");
  }

  @DeleteMapping("/{email}")
  public ResponseEntity<?> deleteUser(@PathVariable String email) {
    User user = findUserByEmail(email);
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    users.remove(user);
    return ResponseEntity.ok("User deleted successfully.");
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchUsers(@RequestParam LocalDate from, @RequestParam LocalDate to) {
    if (from.isAfter(to)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("'From' date must be before 'To' date.");
    }
    List<User> result = users.stream()
        .filter(user -> !user.getBirthDate().isBefore(from) && !user.getBirthDate().isAfter(to))
        .collect(Collectors.toList());
    return ResponseEntity.ok(result);
  }

  private void patchUser(User user, Map<String, Object> updates) {

    ObjectMapper mapper = new ObjectMapper();
    for (Map.Entry<String, Object> entry : updates.entrySet()) {
      try {
        Field field = User.class.getDeclaredField(entry.getKey());
        field.setAccessible(true); // Bypass the private access modifier
        field.set(user, mapper.convertValue(entry.getValue(), field.getType()));
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException("Failed to update field", e);
      }
    }
  }

  private User findUserByEmail(String email) {
    return users.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
  }
}

