package edu.famu.deliverit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


public class Admin {
    //@DocumentId
    private String adminId;
    private String adminUser;
    private String email;
    private String password;
}
