package com.pbru.it.backend.Controllers;

import com.pbru.it.backend.DTO.response.BranchMiniResponse;
import com.pbru.it.backend.DTO.request.BranchRequest;
import com.pbru.it.backend.DTO.response.BranchResponse;
import com.pbru.it.backend.Models.Branch;
import com.pbru.it.backend.Services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchResponse>> findAll() {
        return ResponseEntity.ok(branchService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> findById(@PathVariable Long id) {
        BranchResponse branch = branchService.findById(id);
        return ResponseEntity.ok(branch);
    }

    @GetMapping("/mini")
    public ResponseEntity<List<BranchMiniResponse>> getMiniResponse(
        @RequestParam(required = false) Boolean status
        ) {
        return ResponseEntity.ok(branchService.filterMiniBranch(status));
    }
    

    @PostMapping
    public ResponseEntity<Branch> save(@ModelAttribute BranchRequest branchRequest) {
        Branch branch = branchService.save(branchRequest);
        URI location = URI.create("/branch/" + branch.getId());
        return ResponseEntity.created(location).body(branch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateById(@PathVariable Long id, @ModelAttribute BranchRequest branchRequest) {
        return ResponseEntity.ok(branchService.updateById(id, branchRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> setStatusBranch(@PathVariable Long id, @RequestParam Boolean status){
        branchService.setStatusBranch(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        branchService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
