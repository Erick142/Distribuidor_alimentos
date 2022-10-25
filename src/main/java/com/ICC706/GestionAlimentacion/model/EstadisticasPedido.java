package com.ICC706.GestionAlimentacion.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

    @Data
    @NoArgsConstructor
    @ToString(exclude={"id"})

    @Entity(name = "Estadisticas")
    public class EstadisticasPedido {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        private int racionesSolicitadas;
        private int racionesConsumidas;
        private int racionesPerdidas;
        private Date fecha;

    }


