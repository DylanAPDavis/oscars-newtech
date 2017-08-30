package net.es.oscars.webui.cont;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.dto.acct.Customer;
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
public class AdminAcctController {

    @Autowired
    private RestTemplate restTemplate;

    private final String oscarsUrl = "https://localhost:8000";

    @RequestMapping(value = "/admin/cust_list", method = RequestMethod.GET)
    public String admin_comp_list(Model model) {

        String restPath = oscarsUrl + "/acct/customers/";
        Customer[] customers = restTemplate.getForObject(restPath, Customer[].class);

        model.addAttribute("customers", customers);
        return "admin_cust_list";
    }

    @RequestMapping(value = "/admin/cust_edit/{name}", method = RequestMethod.GET)
    public String admin_cust_edit(@PathVariable String name, Model model) {


        String restPath = oscarsUrl + "/acct/customers/" + name;
        Customer customer = restTemplate.getForObject(restPath, Customer.class);
        log.info(customer.toString());


        model.addAttribute("customer", customer);
        return "admin_cust_edit";
    }

    @RequestMapping(value = "/admin/cust_update_submit", method = RequestMethod.POST)
    public String admin_user_update_submit(@ModelAttribute Customer updatedCustomer) {
        String name = updatedCustomer.getName();
        log.info(updatedCustomer.toString());

        return "redirect:/admin/cust_edit/" + name;
    }
}