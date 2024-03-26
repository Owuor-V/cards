package com.card_inventory.cards.Service;

import com.card_inventory.cards.DTOs.AddCardsRequest;
import com.card_inventory.cards.Model.Cards;
import com.card_inventory.cards.Repository.CardsRepository;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class CardsService {

    @Autowired
    private CardsRepository cardsRepository;
    public Cards addCardToInventory(AddCardsRequest addRequest) {
        Cards cards = new Cards();
        cards.setCardId(addRequest.getCardId());
        cards.setBatchId(addRequest.getBatchId());
        cards.setCardRange(addRequest.getCardRange());
        cards.setQuantityReceived(addRequest.getQuantityReceived());
        cards.setReceivedDate(LocalDateTime.now());
        cards.setExpiryDate(addRequest.getExpiryDate());
        cards.setVendorName(addRequest.getVendorName());
        cards.setStockStatus(addRequest.getStockStatus());
        cards.setCardType(addRequest.getCardType());
        return cardsRepository.save(cards);
    }
    @Observed(name = "getAllCards")
    public List<Cards> getAllCards() {
        return cardsRepository.findAll();
    }
    @Observed(name = "getByBatchId")
    public Cards getByBatchId(int batchId) throws ChangeSetPersister.NotFoundException {
            return cardsRepository.getByBatchId(batchId)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }
    @Observed(name = "deleteCardsFromInventory")
    public List<Cards> deleteCardsFromInventory(int batchId) throws ChangeSetPersister.NotFoundException {
        Cards deletedCard = cardsRepository.getByBatchId(batchId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        cardsRepository.delete(deletedCard);

        return Collections.singletonList(deletedCard);
    }
    @Observed(name = "updateInventory")
    public List<Cards> updateInventory(int batchId) throws ChangeSetPersister.NotFoundException {
        Cards existingCard = cardsRepository.getByBatchId(batchId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        Cards updatedCard = cardsRepository.save(existingCard);

        return Collections.singletonList(updatedCard);

    }
    @Observed(name = "saveAll")
    public void saveAll(List<Cards> cardList) {

        cardsRepository.saveAll(cardList);
    }
}
