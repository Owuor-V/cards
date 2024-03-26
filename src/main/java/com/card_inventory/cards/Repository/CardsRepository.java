package com.card_inventory.cards.Repository;

import com.card_inventory.cards.Model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository <Cards, Long> {
    Optional<Cards> getByBatchId(int cardId);

    //<T> ScopedValue<T> getByCardId(int batchId);
}
