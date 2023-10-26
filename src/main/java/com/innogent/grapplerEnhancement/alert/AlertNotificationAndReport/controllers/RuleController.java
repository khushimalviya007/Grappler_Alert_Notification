package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.controllers;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceAlreadyExistException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Sources;
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

@RestController
@RequestMapping("/rules")
public class RuleController {

    private static final Logger logger = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    private RuleService ruleService;

    @Operation(summary = "Retrieve List of Rules", description = "Returns the List of Rules")
    @GetMapping
    public ResponseEntity<List<RuleDto>> getAllRules() {
        try {
            logger.info("Attempting to retrieve all rules.");
            List<RuleDto> rules = ruleService.getAllRules();
            if (!rules.isEmpty()) {
                logger.info("Successfully retrieved all rules.");
                return ResponseEntity.ok(rules);
            } else {
                logger.warn("No rules found.");
                String errorMessage = "Rule data not found because no data is present.";
                return new ResponseEntity(new ApiResponse(errorMessage, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while getting all rules: " + e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get a Rule by ruleId", description = "Return the Rule if it exists")
    @GetMapping("/{ruleId}")
    public ResponseEntity<RuleDto> getSingleRule(@PathVariable("ruleId") Long ruleId) {
        try {
            logger.info("Attempting to retrieve rule with ID: " + ruleId);
            RuleDto ruleDto = this.ruleService.getRuleById(ruleId);
            logger.info("Successfully retrieved rule with ID: " + ruleId);
            return new ResponseEntity<>(ruleDto, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving rule with ID " + ruleId + ": " + e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a rule", description = "Returns the created rule")
    @PostMapping
    public ResponseEntity<RuleDto> createRule(@Valid @RequestBody RuleDto ruleDto) {
        try {
            logger.info("Attempting to create a new rule.");
            RuleDto rule = ruleService.createRule(ruleDto);
            logger.info("Successfully created a new rule with ID: " + rule.getId());
            return new ResponseEntity<>(rule, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage(), e);
            return new ResponseEntity(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        } catch (ResourceAlreadyExistException e) {
            logger.warn(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("An error occurred while creating a rule: " + e.getMessage(), e);
            return new ResponseEntity(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Soft Delete a rule", description = "Returns soft deleted status")
    @DeleteMapping("/{ruleId}")
    public ResponseEntity<Void> deleteRule(@PathVariable("ruleId") int ruleId) {
        try {
            logger.info("Attempting to delete rule with ID: " + ruleId);
            ruleService.deleteRule(ruleId);
            logger.info("Successfully deleted rule with ID: " + ruleId);
            return new ResponseEntity(new ApiResponse("Successfully deleted rule with ID: " + ruleId, true), HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            logger.warn(ex.getMessage());
            throw ex;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a rule", description = "Returns the updated rule")
    @PatchMapping("/{ruleId}")
    public ResponseEntity<RuleDto> updateRule(@PathVariable("ruleId") Long ruleId, @RequestBody RuleDto ruleDto) {
        try {
            logger.info("Attempting to update a rule.");
            RuleDto updatedRuleDto = ruleService.updateRule(ruleId, ruleDto);
            logger.info("Successfully updated rule with ID: " + updatedRuleDto.getId());
            return new ResponseEntity<>(updatedRuleDto, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while updating the rule: " + e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Rule by scope", description = "Returns rule by scope")
    @GetMapping("/{sources}/{scope}/{identity}/{trigger}/{condition}")
    public ResponseEntity getRuleByScope(@PathVariable("sources") Sources sources, @PathVariable("scope") String scope, @PathVariable("identity") String identity,
                                         @PathVariable("trigger") String trigger, @PathVariable("condition") String condition) {
        try {
            logger.info("Attempting to get by scope.");
            return ResponseEntity.ok(ruleService.getRuleByScope(sources, scope, identity, trigger, condition));
        } catch (DataIntegrityViolationException e) {
            logger.error("Database error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("An error occurred while getting by scope: " + e.getMessage());
            return new ResponseEntity(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


