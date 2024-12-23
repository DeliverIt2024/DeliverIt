package edu.famu.deliverit.controller;

import com.google.cloud.firestore.DocumentReference;
import edu.famu.deliverit.model.Default.Users;
import edu.famu.deliverit.model.Default.Vendors;
import edu.famu.deliverit.model.Rest.RestItems;
import edu.famu.deliverit.model.Rest.RestVendors;
import edu.famu.deliverit.service.VendorsService;
import edu.famu.deliverit.util.ApiResponse;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

//http:localhost:8080/api/vendor
@RestController
@RequestMapping("/api/vendor")
public class VendorsController {
    private final VendorsService service;


    public VendorsController(VendorsService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Vendors>>> getAllVendors() {
        try{
            List<Vendors> vendors = service.getAllVendors();
            if(vendors != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Vendor List", vendors, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No vendors", null, null));
        }catch (ParseException | ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<ApiResponse<Vendors>> getVendorById(@PathVariable(name="vendorId") String vendorId){
        try {
            Vendors vendor = service.getVendorDetails(vendorId);

            if(vendor != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Vendor", vendor, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "User not found", null, null));

        } catch (ParseException | ExecutionException | java.text.ParseException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @PutMapping("/{vendorId}")
    public ResponseEntity<ApiResponse<String>> updateVendorInfo(@PathVariable String vendorId, @RequestBody RestVendors updatedVendor) {
        try {
            String updateTime = service.updateVendorInfo(vendorId, updatedVendor);
            return ResponseEntity.ok(new ApiResponse<>(true, "Vendor info updated", updateTime, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @PostMapping("/{vendorId}/item")
    public ResponseEntity<ApiResponse<String>> addItemToVendorMenu(@PathVariable String vendorId, @RequestBody RestItems newItem) {
        try {
            String updateTime = service.addItemToVendorMenu(vendorId, newItem);
            return ResponseEntity.ok(new ApiResponse<>(true, "Item added to vendor menu", updateTime, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addVendor( @RequestBody Vendors vendor) {
        try{
            Vendors vendors = new Vendors();
            vendors.setVendorId(service.addVendor(vendor));
            vendors.setEmail(vendors.getEmail());
            vendors.setName(vendors.getName());
            vendors.setAddress(vendors.getAddress());
            vendors.setMenu(vendors.getMenu());
            vendors.setAverageRating(vendors.getAverageRating());
            vendors.setPhone(vendors.getPhone());
            vendors.setImageUrl(vendors.getImageUrl());
            String id = service.addVendor(vendor);
            return ResponseEntity.status(201).body(new ApiResponse<>(true,"User created",id,null));
        } catch (ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"Internal server error", null,e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false,"Unable to reach firebase", null,e));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteVendor(@RequestParam String vendorId) {
        boolean deleted = service.deleteVendor(vendorId);

        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null, null));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Failed to delete user", null, null));
        }
    }
}
