package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.logic;

public class Descuento {

        public double aplicarDescuentoAlTotal(Integer total) {
            Double totalFinal;
            if (total >= 5000 && total < 8000) {
                Double totalCambiado = (double) total;
                totalFinal = totalCambiado * 0.95;
            } else if (total >= 8000) {
                Double totalCambiado = (double) total;
                totalFinal = totalCambiado * 0.90;
            } else {
                totalFinal = (double) total;
            }
            return totalFinal;
        }
}
