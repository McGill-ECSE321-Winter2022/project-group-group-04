package ca.mcgill.ecse321.project321.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoreOwnerDTO extends UserDTO{
	private List<StoreDTO> stores;
	
	public StoreOwnerDTO() {
		super();
	}
	
	public StoreOwnerDTO(String email, String name, String password, List<StoreDTO> stores) {
		super(email, name, password);
		this.stores = new ArrayList<StoreDTO>(stores);
	}
	
	
    @SuppressWarnings("unchecked")
    public StoreOwnerDTO(String email, String name, String password) {
        this(email, name, password, Collections.EMPTY_LIST);
    }
    
    public List<StoreDTO> getStores() {
		return this.stores;
	}
    
    public void setStores(List<StoreDTO> stores) {
        this.stores = new ArrayList<StoreDTO>(stores);
    }
}
