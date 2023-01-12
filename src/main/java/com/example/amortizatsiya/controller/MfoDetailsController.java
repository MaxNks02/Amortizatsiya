package com.example.amortizatsiya.controller;


import com.example.amortizatsiya.dto.AmortizationMfoDto;
import com.example.amortizatsiya.service.MfoDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mfoDetails")
public class MfoDetailsController extends BaseController<MfoDetailsService>{

    public MfoDetailsController(MfoDetailsService service) {
        super(service);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return service.get();
    }

    @GetMapping("/get")
    public ResponseEntity<?> getById(@RequestParam Long id){
        return service.getById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody AmortizationMfoDto dto){
        return service.save(dto);
    }

    @PostMapping("/editCode")
    public ResponseEntity<?> editCode(@RequestParam(name = "code") String code, @RequestParam(name = "id") Long id){
        return service.editCode(code, id);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody AmortizationMfoDto dto, @RequestParam Long id){
        return  service.edit(dto, id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        return  service.delete(id);
    }



}
