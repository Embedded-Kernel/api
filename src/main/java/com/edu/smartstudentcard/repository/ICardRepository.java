package com.edu.smartstudentcard.repository;

import com.edu.smartstudentcard.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICardRepository extends JpaRepository<Card, String> {
}
