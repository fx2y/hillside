package cyou.ithan.orchestration.profilesvc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class Profile {
    private Integer id;
    private String username, password;
}
