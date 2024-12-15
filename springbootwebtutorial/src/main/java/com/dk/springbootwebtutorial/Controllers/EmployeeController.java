package com.dk.springbootwebtutorial.Controllers;

import com.dk.springbootwebtutorial.DTO.EmployeeDTO;
import com.dk.springbootwebtutorial.Entities.EmployeeEntity;
import com.dk.springbootwebtutorial.Repositories.EmployeeRepository;
import com.dk.springbootwebtutorial.Services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

//    @GetMapping("/getSecretMessage")
//    public String getMySuperSecretMessage(){
//        return "Secret Message: asdf2345kljlkkef";
//    }
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long Id){
//        return new EmployeeDTO(Id,"DK",25,"dk@gmail.com", LocalDate.of(2022,1,24),true);
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(Id);
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElse(ResponseEntity.notFound().build());
//        if(employeeDTO == null){
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false,name="inputAge") Integer age,
                                @RequestParam(required = false) String sortBy){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody @Valid EmployeeDTO inputEmployee){
//        inputEmployee.setId(100L);
        EmployeeDTO savedEmployee = employeeService.addEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody @Valid EmployeeDTO employeeDTO, @PathVariable Long employeeId){
        EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(employeeId,employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping(path = "{employeeId}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employeeId){
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "{employeeId}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Object> updates, @PathVariable Long employeeId){
         EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId,updates);
         if(employeeDTO == null){
             return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok(employeeDTO);
    }
}
