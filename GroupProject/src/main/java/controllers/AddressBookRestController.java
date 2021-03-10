package controllers;
import models.*;
import repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8082")

public class AddressBookRestController {
	
    @Autowired
    private AddressBookModelRepo addressBookModelRepo;
    
    @GetMapping("/api/getAddressBooks")
    public Iterable <AddressBookModel> getAddressBooks() {
    	
        Iterable <AddressBookModel> addressBooks = addressBookModelRepo.findAll();
        
        
        return addressBooks;
    }
    
    @PostMapping("/api/newAddressBook")
    
    public AddressBookModel addressBook() {
    	
    	
        AddressBookModel addressBook = new AddressBookModel();
        
        addressBookModelRepo.save(addressBook);
        
        return addressBook;
    }
    
    @PostMapping("/api/removeBuddy")
    public AddressBookModel removeBuddy(
    		@RequestParam(value="id") long id,
    		@RequestParam(value="name") String name,
    		@RequestParam(value="phonenumber") String phonenumber) {
    	
        AddressBookModel addressBook = addressBookModelRepo.findById(id);
        addressBook.removeBuddy(new BuddyInfoModel(name, phonenumber));
        addressBookModelRepo.save(addressBook);
        return addressBook;
    }
}