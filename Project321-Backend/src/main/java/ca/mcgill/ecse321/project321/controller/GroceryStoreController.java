package ca.mcgill.ecse321.project321.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.project321.dto.AddressDTO;
import ca.mcgill.ecse321.project321.dto.CustomerDTO;
import ca.mcgill.ecse321.project321.model.Address;
import ca.mcgill.ecse321.project321.model.Customer;
import ca.mcgill.ecse321.project321.service.GroceryStoreService;

@CrossOrigin(origins = "*")
@RestController
public class GroceryStoreController {
    
    @Autowired
    private GroceryStoreService service;

    @GetMapping(value = {"/customers", "/customers/"})
    public List<CustomerDTO> getAllCustomers() throws IllegalArgumentException{
        return convertListToDTO(service.getAllCustomers());
    }

    @GetMapping(value = {"/customers/{email}", "/customers/{email}/"})
    public CustomerDTO getCustomer(@PathVariable("email") String email) throws IllegalArgumentException{
        return convertToDTO(service.getCustomer(email));
    }

    @PostMapping(value = {"/customers", "/customers/"})
    public CustomerDTO createCustomer(@RequestParam(name = "email")     String email, 
                                      @RequestParam(name = "name")      String name, 
                                      @RequestParam(name = "password")  String password, 
                                      @RequestParam(name = "phone")     String phone, 
                                      @RequestParam(name = "address")   AddressDTO address) 
    throws IllegalArgumentException {
        Customer c = service.createCustomer(email, name, password, phone, convertToDomainObject(address));
        return convertToDTO(c);
    }

    /* Helper methods ---------------------------------------------------------------------------------------------------- */
    private List<CustomerDTO> convertListToDTO(List<Customer> customers) throws IllegalArgumentException{
        List<CustomerDTO> list = new ArrayList<CustomerDTO>();
        for(Customer c : customers) {
            list.add(convertToDTO(c));
        }
        return null;
    }

    private CustomerDTO convertToDTO(Customer customer) {
        if(customer == null) throw new IllegalArgumentException("Customer does not exist");
        AddressDTO address = convertToDTO(customer.getAddress());
        CustomerDTO c = new CustomerDTO(customer.getEmail(), customer.getName(), customer.getPassword(), 
                                        customer.getPhone(), address);
        return c;
    }

    private AddressDTO convertToDTO(Address address) {
        if(address == null) throw new IllegalArgumentException("Address does not exist");
        AddressDTO a = new AddressDTO(address.getTown(), address.getStreet(), address.getPostalCode(), address.getUnit());
        return a;
    }

    private Address convertToDomainObject(AddressDTO address) {
        List<Address> allAddresses = service.getAllAddresses();
        for( Address a : allAddresses ) {
            if( (address.getPostalCode().equals(a.getPostalCode())) && (address.getStreet().equals(a.getStreet())) && 
                (address.getTown().equals(a.getTown())) && (address.getUnit() == a.getUnit())) {
                    return a;
            }
        }
        return null;
    }
}
