package com.github.kairoCesar.calculoSimplesNacional.anexos;

public interface AnexoAbstrato {

    double simularSimplesNacional(double rbt12, double faturamentoDoMes);

     default int verificarFaixa(double rbt12) {
        int indiceFaixa;

        if (rbt12 < 180000.00) {
            indiceFaixa = 0;
        } else if (rbt12 < 360000.01) {
            indiceFaixa = 1;
        } else if (rbt12 < 720000.01) {
            indiceFaixa = 2;
        } else if (rbt12 < 1800000.01) {
            indiceFaixa = 3;
        } else if (rbt12 < 3600000.01) {
            indiceFaixa = 4;
        } else if (rbt12 < 4800000.00) {
            indiceFaixa = 5;
        } else {
            indiceFaixa = -1;
        }

        return indiceFaixa;
    }
}
