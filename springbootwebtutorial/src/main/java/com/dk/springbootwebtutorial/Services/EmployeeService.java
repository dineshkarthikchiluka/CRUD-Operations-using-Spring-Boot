package com.dk.springbootwebtutorial.Services;

import com.dk.springbootwebtutorial.Configs.MapperConfig;
import com.dk.springbootwebtutorial.DTO.EmployeeDTO;
import com.dk.springbootwebtutorial.Entities.EmployeeEntity;
import com.dk.springbootwebtutorial.Exceptions.ResourceNotFoundException;
import com.dk.springbootwebtutorial.Repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelmapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.modelmapper = mapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {

        // With map, you can perform the transformation concisely in a functional style, as shown in your code.
        //  If findById(id) returns an empty Optional, the map method is skipped, and the entire method returns Optional.empty().
        return employeeRepository.findById(id)  // Step 1: Returns Optional<EmployeeEntity>
                .map(employeeEntity ->          // Step 2: Unwraps EmployeeEntity if present
                        modelmapper.map(employeeEntity, EmployeeDTO.class) // Step 3: Transforms EmployeeEntity -> EmployeeDTO
                );

        //Without map, you would need to manually check if the Optional contains a value and then transform it, like this:
        /*
        Optional<EmployeeEntity> optionalEntity = employeeRepository.findById(id);
        if (optionalEntity.isPresent()) {
            EmployeeDTO dto = modelmapper.map(optionalEntity.get(), EmployeeDTO.class);
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
         */
        //
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities
                .stream()
                .map(employeeEntity -> modelmapper.map(employeeEntity,EmployeeDTO.class))
                .collect(Collectors.toList());

    }

    public EmployeeDTO addEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity = modelmapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity); // this method accepts only entity type
        return modelmapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }

    // it will create an new employee is not present, if present it will update.
    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO employeeDTO) {
        // First, find the existing employee by the provided employeeId
        Optional<EmployeeEntity> existingEntity = employeeRepository.findById(employeeId);

        EmployeeEntity employeeEntity;

        if (existingEntity.isPresent()) {
            // If the employee exists, update its fields
            employeeEntity = existingEntity.get();
            employeeEntity.setName(employeeDTO.getName());
            employeeEntity.setAge(employeeDTO.getAge());
            employeeEntity.setEmail(employeeDTO.getEmail());
            employeeEntity.setDateOfJoining(employeeDTO.getDateOfJoining());
            employeeEntity.setIsActive(employeeDTO.getIsActive());
        } else {
            // If employee doesn't exist, create a new entity from DTO
            employeeEntity = modelmapper.map(employeeDTO, EmployeeEntity.class);
//            employeeEntity.setId(employeeId); // Set the provided ID
        }

        // Save the employee (whether updated or newly created)
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);

        // Map the saved entity back to DTO and return it
        return modelmapper.map(savedEmployeeEntity, EmployeeDTO.class);


    }

    public void isExists(Long employeeId){
        boolean exists =  employeeRepository.existsById(employeeId);
        if(!exists){
            throw new ResourceNotFoundException("Employee not found with id "+employeeId);
        }
    }

    public boolean deleteEmployeeById(Long employeeId) {
        isExists(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> updates) {
        isExists(employeeId);
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get(); // as you are already checked null or not so you no need to handle optional here instead you can use get()
        updates.forEach((field, value) ->{
            Field fieldToBeUpdated = ReflectionUtils.findField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);

        });
        return modelmapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);



    }
}
