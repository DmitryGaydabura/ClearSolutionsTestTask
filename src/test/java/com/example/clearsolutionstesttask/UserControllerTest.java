package com.example.clearsolutionstesttask;

import com.example.clearsolutionstesttask.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;


  @Test
  public void testCreateUser_ValidUser_Returns200() throws Exception {
    String userJson = "{ \"email\": \"test@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"birthDate\": \"1990-01-01\" }";
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
        .andExpect(status().isOk());
  }

  @Test
  public void testCreateUser_InvalidUser_Returns400() throws Exception {
    String userJson = "{ \"email\": \"invalid_email\", \"firstName\": \"John\", \"birthDate\": \"1990-01-01\" }";
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testUpdateUser_ValidUser_Returns200() throws Exception {
    String userJson = "{ \"email\": \"test@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"birthDate\": \"1990-01-01\" }";
    mockMvc.perform(put("/users/{email}", "test@example.com")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateUser_InvalidUser_Returns404() throws Exception {
    String userJson = "{ \"email\": \"test@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"birthDate\": \"1990-01-01\" }";
    mockMvc.perform(put("/users/{email}", "nonexistent@example.com")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteUser_ExistingUser_Returns200() throws Exception {
    mockMvc.perform(delete("/users/{email}", "test@example.com"))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteUser_NonExistingUser_Returns404() throws Exception {
    mockMvc.perform(delete("/users/{email}", "nonexistent@example.com"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testSearchUsers_ByValidDateRange_Returns200() throws Exception {
    mockMvc.perform(get("/users/search")
            .param("from", "1990-01-01")
            .param("to", "2000-01-01"))
        .andExpect(status().isOk());
  }

  @Test
  public void testSearchUsers_ByInvalidDateRange_Returns400() throws Exception {
    mockMvc.perform(get("/users/search")
            .param("from", "2000-01-01")
            .param("to", "1990-01-01"))
        .andExpect(status().isBadRequest());
  }




}
