package project.app.Controllers;

import project.app.Entities.User;
import project.app.JpaRepository.UserRepository;
import project.app.Vo.RegisterVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class RegistrationController {
    UserRepository userRepository;

    //  Register API
    @ResponseBody
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterVo regUser){
        System.out.println(regUser);

        User user = userRepository.findByUsername(regUser.getUsername());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Map<String, Object> map = new HashMap<>(3);

        if (user == null)
        {
            user = new User();
            user.setUsername(regUser.getUsername());
            user.setPassword(regUser.getUsername());
            user.setType(regUser.getType());
            user.setFirstname(regUser.getFirstname());
            user.setSurname(regUser.getSurname());
            user.setPhone(regUser.getPhone());
            user.setAddress(regUser.getAddress());

            user.setCreatedAt(now);
            user.setUpdatedAt(now);
            user.setLastLoginAt(now);
            userRepository.save(user);
            User saveSuccess = userRepository.findByUsername(user.getUsername());
            if (saveSuccess == null)
            {
                map.put("status", "fail");
                map.put("msg", "User registration failed.");
            } else {
                map.put("status", "success");
                map.put("data", saveSuccess);
            }
        } else {
            map.put("status", "fail");
            map.put("msg", "The username has been registered.");
        }

        return map;
    }
}
