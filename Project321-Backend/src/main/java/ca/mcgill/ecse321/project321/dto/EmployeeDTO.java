package ca.mcgill.ecse321.project321.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class EmployeeDTO extends UserDTO {
	
	public enum EmployeeStatusDTO { 
		Sick,
		Inactive,
		Active 
	}
	private EmployeeStatusDTO status;
	private List<ShiftDTO> shifts;
	
	public EmployeeDTO() {
		super();
	}
	
	public EmployeeDTO(String email, String name, String password, EmployeeStatusDTO status, List<ShiftDTO> shifts) {
		super(email, name, password);
		this.status = status;
		this.shifts = new ArrayList<ShiftDTO>(shifts);
	}
	
    @SuppressWarnings("unchecked")
    public EmployeeDTO(String email, String name, String password, EmployeeStatusDTO status) {
        this(email, name, password, status, Collections.EMPTY_LIST);
    }
    
    public EmployeeStatusDTO getStatus() {
		return this.status;
	}
    
    public List<ShiftDTO> getShifts() {
		return this.shifts;
	}
    
    public void setShifts(List<ShiftDTO> shifts) {
        this.shifts = new ArrayList<ShiftDTO>(shifts);
    }
}
