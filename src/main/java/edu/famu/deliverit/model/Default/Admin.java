package edu.famu.deliverit.model.Default;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class Admin {
    //@DocumentId
    private String adminId;
    private String adminUser;
    private String email;
    private String password;
}
