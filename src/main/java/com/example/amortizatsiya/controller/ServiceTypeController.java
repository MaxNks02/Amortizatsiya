package com.example.amortizatsiya.controller;

import com.example.amortizatsiya.dto.ServiceTypeDto;
import com.example.amortizatsiya.service.ServiceTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/serviceType")
public class ServiceTypeController {
    private final ServiceTypeService service;

    public ServiceTypeController(ServiceTypeService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return service.getAll();
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getByName(@RequestParam(name = "name") String name){
        return service.getByName(name);
    }

    @GetMapping("/getByCode")
    public ResponseEntity<?> getByCode(@RequestParam(name = "code") String code){
        return service.getByCode(code);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ServiceTypeDto dto){
        return service.save(dto);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody ServiceTypeDto dto){
        return service.edit(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return service.delete(id);
    }
}
