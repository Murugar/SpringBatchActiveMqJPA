


package com.iqmsoft.spring.batch.activemq.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.iqmsoft.spring.batch.activemq.model.enums.TicketType;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TICKETS")
@Cacheable(value = false)
public class Ticket implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private String tag;

    private Date date;

    private String content;

    private TicketType type;

    public Ticket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", date=" + date +
                ", content=" + content +
                ", type=" + type +
                '}';
    }
}
