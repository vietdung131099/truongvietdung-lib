package com.truongvietdung.internal.dto.request;

import java.util.Date;
import lombok.Data;

@Data
public class UserRequest {
  private String fullName;
  private String email;
  private String phone;
  private String image;
  private String username;
  private String password;
  private Date dob;
  private String status;
  private String role;
}
