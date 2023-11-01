package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceAlreadyExistException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Trigger;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.ApiResponse;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.RuleDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.RuleRepositary;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services.RuleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rules")
public class RuleController {
    private static final Logger logger = LoggerFactory.getLogger(RuleController.class);
    @Autowired
    private RuleService ruleService;
    @Autowired
    RuleRepositary ruleRepositary;
        @Operation(summary = "Retrieve List of Rules", description = "Returns the List of Rules")
    @GetMapping
        public ResponseEntity getAllRules() {
        try {
            logger.info("Attempting to retrieve all rules.");
            List<RuleDto> rules = ruleService.getAllRules();
            if (!rules.isEmpty()) {
                logger.info("Successfully retrieved all rules.");
                return  new ResponseEntity<>(new ApiResponse(rules,"Successfully retrieved all rules.",true), HttpStatus.OK);

            } else {
                logger.warn("No rules found.");
                String errorMessage = "Rule data not found because no data is present.";
                return new ResponseEntity(new ApiResponse(null,errorMessage, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting all rules: " + e.getMessage());
            return new ResponseEntity(new ApiResponse(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        @Operation(summary = "Get a Rule by ruleId", description = "Return the Rule if it exists")
    @GetMapping("/{ruleId}")
    public ResponseEntity getSingleRule(@PathVariable("ruleId") Long ruleId) {
        try {
            logger.info("Attempting to retrieve rule with ID: " + ruleId);
            RuleDto ruleDto = this.ruleService.getRuleById(ruleId);
            logger.info("Successfully retrieved rule with ID: " + ruleId);
            return  new ResponseEntity<>(new ApiResponse(ruleDto,"Successfully retrieved rule with ID: " + ruleId,true), HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving rule with ID " + ruleId + ": " + e.getMessage());
            return new ResponseEntity(new ApiResponse(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a rule", description = "Returns the created rule")
    @PostMapping
    public ResponseEntity createRule(@Valid @RequestBody RuleDto ruleDto) {
        try {
            logger.info("Attempting to create a new rule.");
            RuleDto rule = ruleService.createRule(ruleDto);
            logger.info("Successfully created a new rule with ID: " + rule.getId());
            return  new ResponseEntity<>(new ApiResponse(rule,"Successfully created a new rule with ID: " + rule.getId(),true), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage(), e);
            return new ResponseEntity(new ApiResponse(null,e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (ResourceAlreadyExistException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while creating a rule: " + e.getMessage(), e);
            return new ResponseEntity(new ApiResponse(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @Operation(summary = "Soft Delete a rule", description = "Returns soft deleted status")
    @DeleteMapping("/{ruleId}")
    public ResponseEntity deleteRule(@PathVariable("ruleId") int ruleId) {
        try {
            logger.info("Attempting to delete rule with ID: " + ruleId);
            ruleService.deleteRule(ruleId);
            logger.info("Successfully deleted rule with ID: " + ruleId);
            return new ResponseEntity(new ApiResponse("Successfully deleted rule with ID: " + ruleId,"Successfully deleted rule with ID: " + ruleId, true), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            logger.warn(ex.getMessage());
            throw ex;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponse(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @Operation(summary = "Update a rule", description = "Returns the updated rule")
    @PatchMapping("/{ruleId}")
    public ResponseEntity updateRule(@PathVariable("ruleId") Long ruleId, @RequestBody RuleDto ruleDto) {
        try {
            logger.info("Attempting to update a rule.");
            RuleDto updatedRuleDto = ruleService.updateRule(ruleId, ruleDto);
            logger.info("Successfully updated rule with ID: " + updatedRuleDto.getId());
            return new ResponseEntity(new ApiResponse(updatedRuleDto,"Successfully updated rule with ID: " + updatedRuleDto.getId(), true), HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity(new ApiResponse(null,"Database error: " + e.getMessage(), false), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            logger.error("An error occurred while updating the rule: " + e.getMessage());
            return new ResponseEntity(new ApiResponse(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{ruleId}/disable")
    public ResponseEntity<Rule> updateRule(@PathVariable("ruleId") Long ruleId) {
        Optional<Rule> rule = ruleRepositary.findById(ruleId);

        if (rule.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Rule rulee = rule.get();
        rulee.setIsDeleted(true);
        ruleRepositary.save(rulee);
        return ResponseEntity.ok(rulee);
    }

    @Operation(summary = "Get Rule by scope", description = "Returns rule by scope")
    @GetMapping("/{sources}/{scope}/{identity}/{trigger}/{condition}")
    public ResponseEntity getRuleByScope(@PathVariable("sources") Trigger sources, @PathVariable("scope") String scope, @PathVariable("identity") String identity,
                                         @PathVariable("trigger") String trigger, @PathVariable("condition") String condition) {
        try {
            logger.info("Attempting to get by scope.");
            return new ResponseEntity(new ApiResponse(ruleService.getRuleByScope(sources, scope, identity, trigger, condition),"Rule Found", true), HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity(new ApiResponse(null,"Database error: " + e.getMessage(), false), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            logger.error("An error occurred while getting by scope: " + e.getMessage());
            return new ResponseEntity(new ApiResponse(null,e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
