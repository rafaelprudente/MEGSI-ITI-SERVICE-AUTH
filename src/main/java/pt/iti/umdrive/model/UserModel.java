package pt.iti.umdrive.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private UUID id;
    private String username;
    private String password;
    private List<RoleModel> roles = new ArrayList<>();
}
