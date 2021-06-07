package com.edu.smartstudentcard.controller;

import com.edu.smartstudentcard.dto.CreateCardDto;
import com.edu.smartstudentcard.dto.UpdateCardDto;
import com.edu.smartstudentcard.enums.ERoleName;
import com.edu.smartstudentcard.model.Card;
import com.edu.smartstudentcard.model.Student;
import com.edu.smartstudentcard.model.User;
import com.edu.smartstudentcard.model.Card;
import com.edu.smartstudentcard.repository.ICardRepository;
import com.edu.smartstudentcard.repository.IStudentRepository;
import com.edu.smartstudentcard.repository.IUserRepository;
import com.edu.smartstudentcard.repository.ICardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    @Autowired
    private ICardRepository cardRepository;

    @Autowired
    private IStudentRepository studentRepository;

    @Autowired
    private IUserRepository userRepository;

    //Add card
    @PostMapping("")
    public Card addCard(@RequestBody CreateCardDto cardDto){

        //Get user from unique UserName
        Optional<User> user = userRepository.findByUsername(cardDto.getStudentUserName());

        //Check if User is a student
        if(user.get().getRoles().toString() == ERoleName.STUDENT.toString()) {

            //Check if student exists
            Optional<Student> newStudent = studentRepository.findByUser(user.get());

            //Check if card exists
            Optional<Card> newCard = cardRepository.findById(cardDto.getId());

            if (!newCard.isPresent()) {
                Card cardToSave = newCard.get();
                cardToSave.setId(cardDto.getId());
                cardToSave.setAutoAmount();
                cardToSave.setAutoStatus();
                cardToSave.setStudent(newStudent.get());

                return cardRepository.save(cardToSave);
            }
        }
        return null;
    }

    //Get card by Id
    @GetMapping("/{id}")
    public Card getCardById(@PathVariable String id){
        return cardRepository.findById(id)
                .orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    //Get all cards
    @GetMapping("")
    public List<Card> getALlCards(){
        return cardRepository.findAll();
    }

    //Update card by Id
    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCardById(@PathVariable String id, @RequestBody UpdateCardDto cardDto){
        Optional<Student> student = studentRepository.findById(cardDto.getStudentId());
        Student studentToSave = student.get();

        Optional<Card> cardData = cardRepository.findById(id);

        if(cardData.isPresent()){
            Card _card = cardData.get();
            _card.setId(cardDto.getId());
            _card.setStudent(studentToSave);
            _card.setStatus(cardDto.getCardStatus());

            return new ResponseEntity<>(cardRepository.save(_card),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete card by ID
    @DeleteMapping("/{id}")
    public void deleteCardById(@PathVariable String id){
        cardRepository.deleteById(id);
    }

}
