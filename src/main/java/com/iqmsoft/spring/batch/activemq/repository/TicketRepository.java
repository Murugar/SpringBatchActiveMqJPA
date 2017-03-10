
package com.iqmsoft.spring.batch.activemq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iqmsoft.spring.batch.activemq.model.Ticket;


public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Ticket findByTag(final String name);
}
