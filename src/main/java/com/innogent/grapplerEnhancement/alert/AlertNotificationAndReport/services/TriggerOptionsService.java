package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.services;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.TriggerOptions;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries.TriggerOptionsRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TriggerOptionsService {
    @Autowired
    private TriggerOptionsRepository triggerOptionsRepository;
    public List<TriggerOptions> getAllTriggers(){
        return this.triggerOptionsRepository.findAll();
    }
}
