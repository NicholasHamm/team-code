package project.app.Controllers;
import project.app.Entities.User;
import project.app.JpaRepository.AuthorityRepository;
import project.app.JpaRepository.UserRepository;
import project.app.Vo.LoginForm;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class LoginController {

    UserRepository userRepository;
    AuthorityRepository authorityRepository;

    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginForm login) {
        User user = userRepository.findByUsername(login.getUsername());
        Map<String, Object> map = new HashMap<>(3);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (user == null) {
            map.put("status", "fail");
            map.put("msg", "Account does not exist.");
        } else if (!user.getPassword().equals(login.getPassword())) {
            map.put("status", "fail");
            map.put("msg", "Wrong username or password.");
        } else {
            user.setLastLoginAt(now);
            userRepository.save(user);
            user = userRepository.findByUsername(login.getUsername());
            map.put("status", "success");
            map.put("data", user);
        }
        return map;
    }
}

