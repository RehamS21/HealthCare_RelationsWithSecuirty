package com.example.healthcare_relationswithsecuirty.Controller;

import com.example.healthcare_relationswithsecuirty.Api.ApiResponse;
import com.example.healthcare_relationswithsecuirty.Model.Bill;
import com.example.healthcare_relationswithsecuirty.Model.User;
import com.example.healthcare_relationswithsecuirty.Service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bill")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping("/get")
    public ResponseEntity getAllBills(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(billService.getAllBill(user.getId()));
    }

    @PostMapping("/add/{patient_id}")
    public ResponseEntity addNewBill(@AuthenticationPrincipal User user, @PathVariable Integer patient_id, @RequestBody @Valid Bill bill){
        billService.addBill(user.getId(),patient_id,bill);
        return ResponseEntity.status(200).body(new ApiResponse("the bill added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateBill(@AuthenticationPrincipal User user,@PathVariable Integer id, @RequestBody @Valid Bill bill){
        billService.updateBill(user.getId(), id,bill);
        return ResponseEntity.status(200).body(new ApiResponse("the bill updated info successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBill(@AuthenticationPrincipal User user,@PathVariable Integer id){
        billService.deleteBill(user.getId(),id);
        return ResponseEntity.status(200).body(new ApiResponse("The bill deleted successfully"));
    }

    @PutMapping("/calc/{id}")
    public ResponseEntity clculateBills(@AuthenticationPrincipal User user,@PathVariable Integer id){
        Integer result = billService.calculateBill(user.getId(),id);
        return ResponseEntity.status(200).body(new ApiResponse("The Paitent money after the calcultation of the bill = "+result));
    }

    @PutMapping("/discount/{id}")
    public ResponseEntity dicountPaitnetBill(@AuthenticationPrincipal User user,@PathVariable Integer id){
        Integer result = billService.DiscountBill(user.getId(),id);

        return ResponseEntity.status(200).body(new ApiResponse("The bill after discount = "+result));
    }


}
