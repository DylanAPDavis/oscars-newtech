package net.es.oscars.acct.rest;

import lombok.extern.slf4j.Slf4j;
import net.es.oscars.acct.ent.CustomerE;
import net.es.oscars.acct.svc.CustService;
import net.es.oscars.dto.acct.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
public class AcctController {
    @Autowired
    public AcctController (CustService custService) {
        this.custService = custService;
    }

    private CustService custService;

    private ModelMapper modelMapper = new ModelMapper();

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException(NoSuchElementException ex) {
        // LOG.warn("user requested a strResource which didn't exist", ex);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        // LOG.warn("user requested a strResource which didn't exist", ex);
    }

    @RequestMapping(value = "/acct/customers/", method = RequestMethod.GET)
    @ResponseBody
    public List<Customer> listCustomers() {
        log.info("listing all customers");
        List<Customer> customers = new ArrayList<>();

        for (CustomerE customerE : custService.findAll()) {
            Customer customer = convertToDto(customerE);
            customers.add(customer);
        }
        return customers;
    }

    @RequestMapping(value = "/acct/customers/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Customer custByName(@PathVariable("name") String name) {
        log.info("getting customer " + name);

        return convertToDto(custService.findByName(name).orElseThrow(NoSuchElementException::new));
    }

    @RequestMapping(value = "/acct/customers/update", method = RequestMethod.POST)
    @ResponseBody
    public Customer update(@RequestBody Customer dtoCustomer) {
        CustomerE customerE = custService.findByName(dtoCustomer.getName()).orElseThrow(NoSuchElementException::new);

        Long id = customerE.getId();
        customerE = convertToEnt(dtoCustomer);
        customerE.setId(id);

        custService.save(customerE);
        return dtoCustomer;
    }

    @RequestMapping(value = "/acct/customers/delete/{name}", method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable("name") String name) {

        custService.delete(custService.findByName(name).orElseThrow(NoSuchElementException::new));
        return "User deleted.";
    }

    private CustomerE convertToEnt(Customer dtoCustomer) {
        return modelMapper.map(dtoCustomer, CustomerE.class);
    }

    private Customer convertToDto(CustomerE customerE) {
        return modelMapper.map(customerE, Customer.class);
    }

}