package net.es.oscars.webui.cont;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.auth.User;
import net.es.oscars.webui.RestAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
public class AdminUserController {

    @Autowired
    public AdminUserController(RestTemplate restTemplate, RestAuthProvider restAuthProvider) {
        this.restTemplate = restTemplate;
        this.restAuthProvider = restAuthProvider;
    }

    private RestTemplate restTemplate;
    private RestAuthProvider restAuthProvider;

    private final String oscarsUrl = "https://localhost:8000";


    @RequestMapping(value = "/admin/user_list", method = RequestMethod.GET)
    public String admin_user_list(Model model) {

        String restPath = oscarsUrl + "/users/";
        User[] users = restTemplate.getForObject(restPath, User[].class);

        model.addAttribute("users", users);
        return "admin_user_list";

    }

    @RequestMapping(value = "/admin/user_edit/{username}", method = RequestMethod.GET)
    public String admin_user_edit(@PathVariable String username, Model model) {


        String restPath = oscarsUrl + "/users/get/" + username;
        User user = restTemplate.getForObject(restPath, User.class);

        model.addAttribute("user", user);
        return "admin_user_edit";
    }

    @RequestMapping(value = "/admin/user_add", method = RequestMethod.GET)
    public String admin_user_add(Model model) {
        model.addAttribute("user", new User());
        return "admin_user_add";
    }


    @RequestMapping(value = "/admin/user_pwd_submit", method = RequestMethod.POST)
    public String admin_user_pwd_submit(@ModelAttribute User updatedUser) {
        String username = updatedUser.getUsername();
        String encodedPassword = restAuthProvider.passwordEncoder().encode(updatedUser.getPassword());
        log.info("changing pwd for " + username);

        String restPath = oscarsUrl + "/users/get/" + username;
        User existingUser = restTemplate.getForObject(restPath, User.class);
        existingUser.setPassword(encodedPassword);

        // only update the password
        restPath = oscarsUrl + "/users/update";
        restTemplate.postForObject(restPath, existingUser, User.class);

        return "redirect:/admin/user_edit/" + username;

    }

    @RequestMapping(value = "/admin/user_add_submit", method = RequestMethod.POST)
    public String admin_user_add_submit(@ModelAttribute User addedUser) {
        if (addedUser == null) {
            return "redirect:/admin/user_add";
        }
        String username = addedUser.getUsername();
        log.info("adding " + username);
        String encodedPassword = restAuthProvider.passwordEncoder().encode(addedUser.getPassword());
        addedUser.setPassword(encodedPassword);

        String restPath = oscarsUrl + "/users/add";
        restTemplate.postForObject(restPath, addedUser, User.class);
        log.info("added " + username);

        return "redirect:/admin/user_edit/" + username;
    }

    @RequestMapping(value = "/admin/user_del_submit", method = RequestMethod.POST)
    public String admin_user_del_submit(@ModelAttribute User userToDelete) {
        if (userToDelete == null) {
            return "redirect:/admin/user_list";
        }
        String username = userToDelete.getUsername();
        log.info("deleting " + username);

        String restPath = oscarsUrl + "/users/delete/" + username;
        restTemplate.getForObject(restPath, String.class);

        return "redirect:/admin/user_list";
    }

    @RequestMapping(value = "/admin/user_update_submit", method = RequestMethod.POST)
    public String admin_user_update_submit(@ModelAttribute User updatedUser) {
        String username = updatedUser.getUsername();
        log.info("updating " + username);

        String restPath = oscarsUrl + "/users/get/" + username;
        User existingUser = restTemplate.getForObject(restPath, User.class);

        // do NOT update the password
        String password = existingUser.getPassword();
        updatedUser.setPassword(password);

        restPath = oscarsUrl + "/users/update";
        restTemplate.postForObject(restPath, updatedUser, User.class);

        return "redirect:/admin/user_edit/" + username;
    }

}