package com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.repositaries;

import com.innogent.grapplerEnhancement.alert.AlertNotificationAndReport.entities.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepositary extends JpaRepository <Rule,Long> {
}
