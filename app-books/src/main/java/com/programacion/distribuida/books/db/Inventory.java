package com.programacion.distribuida.books.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "inventory")
public class Inventory {

    @Id
    @OneToOne
    @JoinColumn(name = "isbn")
    private Book book;
    private Integer sold;
    private Integer supplied;
}
