package com.edu.smartstudentcard.repository;

import com.edu.smartstudentcard.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event, Long> {
}
