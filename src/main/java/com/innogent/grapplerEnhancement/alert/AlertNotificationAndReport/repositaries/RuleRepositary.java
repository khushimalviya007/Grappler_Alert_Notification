package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Trigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepositary extends JpaRepository <Rule,Long> {
    public List<Rule> findByTriggerAndEntityAndFieldAndConditionAndIsDeleted(Trigger trigger, String entity, String field, String condition, boolean isDeleted);
    public List<Rule> findByIsDeleted(boolean isDeleted);
    public Rule findByIdAndIsDeleted(long id,boolean isDeleted);
}