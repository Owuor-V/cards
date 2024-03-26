package com.card_inventory.cards.Controllers;

import com.card_inventory.cards.DTOs.AddCardsRequest;
import com.card_inventory.cards.Model.Cards;
import com.card_inventory.cards.Service.CardsService;
import jakarta.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardsController {
    @Autowired
    private CardsService cardsService;

    @PostMapping("/add")
    public ResponseEntity<?> addCardToInventory(@Valid @RequestBody AddCardsRequest addRequest){
        Cards savedCard = cardsService.addCardToInventory(addRequest);
        if(savedCard != null){
            return new ResponseEntity<>("Successful posted.", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Failed to add card to inventory.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cards>> getAllCards() {

        return ResponseEntity.ok(cardsService.getAllCards());
    }

    @GetMapping(path = "{batchId}")
    public ResponseEntity<Cards> getByBatchId(@PathVariable ("batchId") int batchId) throws ChangeSetPersister.NotFoundException {
        Cards card = cardsService.getByBatchId(batchId);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }

    @DeleteMapping(path = "{batchId}")
    public ResponseEntity<List<Cards>> deleteCardsFromInventory(@PathVariable int batchId) {
        try {
            List<Cards> deletedCards = cardsService.deleteCardsFromInventory(batchId);
            return new ResponseEntity<>(deletedCards, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "{batchId}")
    public ResponseEntity<List<Cards>> updateInventory(@PathVariable int batchId) {
        try {
            List<Cards> updatedCards = cardsService.updateInventory(batchId);
            return new ResponseEntity<>(updatedCards, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/import/excel")
    public ResponseEntity<List<Cards>> importExcelFile(@RequestParam("file") MultipartFile excelFile) throws IOException, ParseException, ParseException, IOException {
        HttpStatus status = HttpStatus.OK;
        List<Cards> cardList = new ArrayList<>();

        try {
            Workbook workbook = new XSSFWorkbook(excelFile.getInputStream());
            Sheet worksheet = workbook.getSheetAt(0);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
                if (index > 0) {
                    Row row = worksheet.getRow(index);

                    // Assuming your Card entity has appropriate fields
                    Cards card = new Cards();
                    card.setId((long) row.getCell(0).getNumericCellValue());
                    card.setCardId((long) row.getCell(1).getNumericCellValue());
                    card.setBatchId((int) row.getCell(2).getNumericCellValue());
                    card.setCardRange(row.getCell(3).getStringCellValue());
                    card.setQuantityReceived((int) row.getCell(4).getNumericCellValue());

                    // Parse Date from String, adjust the date column index accordingly
                    Date expiryDate = dateFormat.parse(row.getCell(5).getStringCellValue());
                    card.setExpiryDate(expiryDate);

                    card.setVendorName(row.getCell(6).getStringCellValue());

                    // Parse Date from String, adjust the date column index accordingly
//                Date receivedDate = dateFormat.parse(row.getCell(7).getStringCellValue());
//                card.setReceivedDate(receivedDate);

                    card.setStockStatus(row.getCell(8).getStringCellValue());
                    card.setCardType(row.getCell(9).getStringCellValue());

                    cardList.add(card);
                }
            }

            // Save the cards to the database using a service or repository
            cardsService.saveAll(cardList);
        } catch (IOException | ParseException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            e.printStackTrace();

        }
        return new ResponseEntity<>(cardList, status);
    }

}
