package com.edu.smartstudentcard.controller;

import com.edu.smartstudentcard.dto.CreateStudentDto;
import com.edu.smartstudentcard.dto.TransactDto;
import com.edu.smartstudentcard.model.Card;
import com.edu.smartstudentcard.model.Student;
import com.edu.smartstudentcard.model.TransactionHistory;
import com.edu.smartstudentcard.model.User;
import com.edu.smartstudentcard.repository.ICardRepository;
import com.edu.smartstudentcard.repository.ITransactionHistoryRepository;
import com.edu.smartstudentcard.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/transact")
public class TransactionController {

    @Autowired
    private ICardRepository cardRepository;

    @Autowired
    private ITransactionHistoryRepository transactionHistoryRepository;

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@Valid @RequestBody TransactDto transactDto){
        //Search for the card
        Optional<Card> CardData = cardRepository.findById(transactDto.getCardId());

        if(!CardData.isPresent()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse("Card not found", false));
        }

        //If card exists update amount from card
        Card updatedCard = CardData.get();
        Double previousAmount = updatedCard.getAmount();
        updatedCard.withdraw(transactDto.getAmount());
        cardRepository.save(updatedCard);

        //Record transaction in Transaction History

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setCard(updatedCard);
        transactionHistory.setDateOfTransaction(transactDto.getDateOfTransaction());
        transactionHistory.setPreviousAmount(previousAmount);
        transactionHistory.setAmountRemained(updatedCard.getAmount());

//        return ResponseEntity.created(location).body("History recorded");
        return new ResponseEntity<>(transactionHistoryRepository.save(transactionHistory),HttpStatus.OK);

    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@Valid @RequestBody TransactDto transactDto){
        //Search for the card
        Optional<Card> CardData = cardRepository.findById(transactDto.getCardId());

        if(!CardData.isPresent()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse("Card not found", false));
        }

        //If card exists update amount from card
        Card updatedCard = CardData.get();
        Double previousAmount = updatedCard.getAmount();
        updatedCard.deposit(transactDto.getAmount());
        cardRepository.save(updatedCard);

        //Record transaction in Transaction History

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setCard(updatedCard);
        transactionHistory.setDateOfTransaction(transactDto.getDateOfTransaction());
        transactionHistory.setPreviousAmount(previousAmount);
        transactionHistory.setAmountRemained(updatedCard.getAmount());

//        return ResponseEntity.created(location).body("History recorded");
        return new ResponseEntity<>(transactionHistoryRepository.save(transactionHistory),HttpStatus.OK);

    }

}
