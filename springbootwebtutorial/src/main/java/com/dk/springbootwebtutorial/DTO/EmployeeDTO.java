package com.dk.springbootwebtutorial.DTO;

import com.dk.springbootwebtutorial.Annotations.EmployeeValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name of an employee can not be blank")
    @Size(min = 3,max = 10,message = "Number of characters in the name should be in the range: range[3,10]")
    private String name;


    @NotNull(message = "age of an employee can not be null")
    @Max(value = 80,message = "age of employee can not be greater than 80")
    @Min(value = 18, message = "age of employee can not be less than 18 ")
    private Integer age;

    @NotBlank(message = "email of an employee can not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Role of an employee can not be blank")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of an employee can be ADMIN OR USER")
    @EmployeeValidation
    private String role; //ADMIN, USER

    @PastOrPresent(message = "Joining date of an employee should not be in future")
    private LocalDate dateOfJoining;

    @NotNull(message = "salary of an employee can not be null")
    @Positive(message = "salary of an employee can not be negative")
    @Digits(integer = 6,fraction = 2,message = "The salary can be in the form xxxxxx.yy")
    @DecimalMin(value = "100.50")
    @DecimalMax(value = "100000.99")
    private Double salary;

    @JsonProperty("isActive") // Explicitly name the JSON field
    @AssertTrue(message = "Employee should be active")
    private Boolean isActive; // you can different name also but that belongs to object only, in postman you have to give the name which you have mentioned in JsonProperty

//    public EmployeeDTO(){
//
//    }
//
//    public EmployeeDTO(Long id, String name, Integer age, String email, LocalDate dateOfJoining, Boolean isActive) {
//        this.id = id;
//        this.name = name;
//        this.age = age;
//        this.email = email;
//        this.dateOfJoining = dateOfJoining;
//        this.isActive = isActive;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public LocalDate getDateOfJoining() {
//        return dateOfJoining;
//    }
//
//    public void setDateOfJoining(LocalDate dateOfJoining) {
//        this.dateOfJoining = dateOfJoining;
//    }
//
//    public Boolean getActive() {
//        return isActive;
//    }
//
//    public void setActive(Boolean active) {
//        isActive = active;
//    }
}
