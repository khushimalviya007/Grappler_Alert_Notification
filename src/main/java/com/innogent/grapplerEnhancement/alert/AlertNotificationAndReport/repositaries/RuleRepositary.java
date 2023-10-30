package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Trigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepositary extends JpaRepository <Rule,Long> {
    public List<Rule> findByTriggerAndEntityAndFieldAndConditionAndIsDeletedAndIsEnabled(Trigger trigger, String entity, String field, String condition, boolean isDeleted, boolean isEnabled);
    public List<Rule> findByIsDeletedAndIsEnabled(boolean isDeleted,boolean isEnabled);
    public Rule findByIdAndIsDeletedAndIsEnabled(long id,boolean isDeleted,boolean isEnabled);
}
