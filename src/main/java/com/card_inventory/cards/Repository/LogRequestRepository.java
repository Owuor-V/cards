package com.card_inventory.cards.Repository;

import com.card_inventory.cards.Model.LogRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRequestRepository extends JpaRepository <LogRequest, Long> {
}
