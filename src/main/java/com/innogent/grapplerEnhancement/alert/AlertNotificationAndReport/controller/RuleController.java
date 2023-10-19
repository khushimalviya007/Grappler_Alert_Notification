package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controller;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entity.Rule;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RuleController {

    @Operation(summary = "Get list of rule", description = "Returns list of rule")
    @GetMapping("/rule")
    public ResponseEntity<List<Rule>> getRule(){

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @Operation(summary = "Get list of rule by scope", description = "Returns list of rule by scope")
    @GetMapping("/rule/{scope}/{identity}/{trigger}/{condition}/{flag}")

    public ResponseEntity<List<Rule>> getRuleByScope(@PathVariable("scope")String scope,@PathVariable("identity")String identity,@PathVariable("trigger")String trigger,@PathVariable("scope")String condition,@PathVariable("flag") boolean flag){

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @Operation(summary = "create a rule", description = "Returns created rule")
    @PostMapping("/rule")
  public ResponseEntity<Rule> createRule(@RequestBody Rule rule){
        return ResponseEntity.ok(new Rule());
    }

    @Operation(summary = "Soft Delete a rule", description = "Returns soft deleted status")
    @DeleteMapping("/rule/{ruleId}")
    public ResponseEntity<Rule> deleteRule(@PathVariable("ruleId")Long ruleId){
        return ResponseEntity.ok(new Rule());
    }

    @Operation(summary = "update rule", description = "Returns the updated rule")
    @PutMapping("/rule/{ruleId}")
    public ResponseEntity<Rule> updateRule(@PathVariable("ruleId")Long ruleId){
        return ResponseEntity.ok(new Rule());
    }



}
