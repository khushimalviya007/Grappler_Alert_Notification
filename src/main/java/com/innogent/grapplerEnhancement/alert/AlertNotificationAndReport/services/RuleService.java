package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceAlreadyExistException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.customExceptions.ResourceNotFoundException;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Sources;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.payloads.RuleDto;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.RuleRepositary;
import jakarta.persistence.Column;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleService {
    @Autowired
    private RuleRepositary ruleRepositary;

    @Autowired
    ModelMapper modelMapper;

    final Logger logger = LoggerFactory.getLogger(RuleService.class);

    public List<RuleDto> getAllRules() {
        logger.info("Fetching all rules.");
        List<Rule> rules = ruleRepositary.findByIsDeletedAndIsEnabled(false, true);
        if (rules.isEmpty()) {
            throw new ResourceNotFoundException("Rule List", "and size of list is ",0);
        }
        List<RuleDto> ruleDtos = rules.stream().map(rule -> this.ruleToDto(rule)).collect(Collectors.toList());
        logger.info("Fetched {} rules.", ruleDtos.size());
        return ruleDtos;
    }

    public RuleDto getRuleById(Long ruleId) {
        logger.info("Fetching rule by ID: {}", ruleId);
        Rule rule = this.ruleRepositary.findByIdAndIsDeletedAndIsEnabled(ruleId, false, true);

        if (rule == null) {
            throw new ResourceNotFoundException("Rule", "ID", ruleId);
        }
        logger.info("Fetched rule by ID: {}", ruleId);
        return this.ruleToDto(rule);
    }

    public RuleDto createRule(RuleDto ruleDto) {
        logger.info("Creating a new rule.");
        List<Rule> ruleExist = ruleRepositary.findBySourcesAndIdentityAndTriggerAndConditionAndIsDeletedAndIsEnabled(ruleDto.getSources(), ruleDto.getIdentity(), ruleDto.getTrigger(), ruleDto.getCondition(), false, true);

        for(Rule r:ruleExist)
        {
            if(ruleDto.getScope().equalsIgnoreCase(r.getScope()))
                throw new ResourceAlreadyExistException("Rule", "id",r.getId());
        }
        Rule rule = this.modelMapper.map(ruleDto, Rule.class);
        rule.setCreationDate(new Date().toString());
        Rule savedRule = ruleRepositary.save(rule);
        logger.info("Created a new rule with ID: {}", savedRule.getId());

        return this.modelMapper.map(savedRule, RuleDto.class);
    }

    public void deleteRule(int ruleId) {
        logger.info("Deleting rule by ID: {}", ruleId);
        Rule rule = this.ruleRepositary.findByIdAndIsDeletedAndIsEnabled(ruleId, false, true);
        if (rule == null) {
            throw new ResourceNotFoundException("Rule", "ID", ruleId);
        }
        rule.setIsDeleted(true);
        ruleRepositary.save(rule);
        logger.info("Deleted rule by ID: {}", ruleId);
    }

    public RuleDto updateRule(Long ruleId, RuleDto ruleDto) {
        logger.info("Updating rule with ID: {}", ruleId);
        Rule nonUpdatedRule = ruleRepositary.findByIdAndIsDeletedAndIsEnabled(ruleId,false,true);
        if(nonUpdatedRule==null)
            throw  new ResourceNotFoundException("Rule", "id", ruleId);

        if(ruleDto.getSources()!=null)
            nonUpdatedRule.setSources(ruleDto.getSources());

        if(ruleDto.getScope()!=null)
            nonUpdatedRule.setScope(ruleDto.getScope());

        if(ruleDto.getIdentity()!=null)
            nonUpdatedRule.setIdentity(ruleDto.getIdentity());

        if(ruleDto.getTrigger()!=null)
            nonUpdatedRule.setTrigger(ruleDto.getTrigger());

        if(ruleDto.getCondition()!=null)
            nonUpdatedRule.setCondition(ruleDto.getCondition());

        if(ruleDto.getAction()!=null)
            nonUpdatedRule.setAction(ruleDto.getAction());

        if(ruleDto.getDescription()!=null)
            nonUpdatedRule.setDescription(ruleDto.getDescription());

        if(ruleDto.getSeverity()!=null)
            nonUpdatedRule.setSeverity(ruleDto.getSeverity());

        if(ruleDto.getRecepientDescription()!=null)
            nonUpdatedRule.setRecepientDescription(ruleDto.getRecepientDescription());

        if(ruleDto.getChannel()!=null)
            nonUpdatedRule.setChannel(ruleDto.getChannel());

        if(ruleDto.getIsEnabled()!=null)
            nonUpdatedRule.setIsEnabled(ruleDto.getIsEnabled());


        Rule savedRule = ruleRepositary.save(nonUpdatedRule);
        logger.info("Updated rule with ID: {}", ruleId);

        return this.modelMapper.map(savedRule, RuleDto.class);
    }

    private Rule dtoToRule(RuleDto ruleDto) {
        Rule rule = this.modelMapper.map(ruleDto, Rule.class);
        return rule;
    }

    private RuleDto ruleToDto(Rule rule) {
        RuleDto ruleDto = this.modelMapper.map(rule, RuleDto.class);
        return ruleDto;
    }

    public List<Rule> getRuleByScope(Sources sources,String scope, String identity, String trigger, String condition) {
        List<Rule> rule = ruleRepositary.findBySourcesAndIdentityAndTriggerAndConditionAndIsDeletedAndIsEnabled(sources, identity, trigger, condition, false, true);
        for(Rule r:rule)
        {
            if(!scope.equalsIgnoreCase(r.getScope())){
                if(!(r.getScope().equalsIgnoreCase("global"))){
                    rule.remove(r);
                }
            }
        }
        return rule;
    }
}