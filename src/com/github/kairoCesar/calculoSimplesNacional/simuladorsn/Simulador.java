package com.github.kairoCesar.calculoSimplesNacional.simuladorsn;

import com.github.kairoCesar.calculoSimplesNacional.anexos.*;
import com.github.kairoCesar.calculoSimplesNacional.exceptions.*;

import java.util.*;

public abstract class Simulador {

    static private double rbt12;
    static private double faturamentoMensal;
    static private double valorGuia;
    static private int indiceAnexo;

    public static void calcularSimplesNacional() {
        ArrayList<AnexoAbstrato> anexos = criarObjetosAnexo();

        rbt12 = tratarRBT12Invalida();
        faturamentoMensal = tratarFaturamentoMensalInvalido();
        indiceAnexo = tratarAnexoInvalido();
        valorGuia = anexos.get(indiceAnexo).simularSimplesNacional(rbt12, faturamentoMensal);

        verificarSeNovaApuracao(anexos);

        System.out.printf("Valor da guia: %.2f%n", valorGuia);

    }

    private static void verificarSeNovaApuracao(ArrayList<AnexoAbstrato> anexos) {
        Scanner entrada = new Scanner(System.in);
        String escolha;

        do {
            System.out.print("Deseja fazer apuração em outro anexo? ");
            escolha = entrada.next();
            if(escolha.equalsIgnoreCase("sim")) {
                realizarNovaApuracao(anexos);
            }
        } while (escolha.equalsIgnoreCase("sim"));
    }

    public static void realizarNovaApuracao(ArrayList<AnexoAbstrato> anexos) {
        indiceAnexo = tratarAnexoInvalido();
        faturamentoMensal = tratarFaturamentoMensalInvalido();
        valorGuia += anexos.get(indiceAnexo).simularSimplesNacional(rbt12, faturamentoMensal);
    }

    private static double tratarRBT12Invalida() {
        while (true) {
            try {
                rbt12 = coletarRBT12();
                break;
            } catch (ValorInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
        return rbt12;
    }

    private static double coletarRBT12() {
        Scanner entrada2 = new Scanner(System.in);
        try {
            System.out.print("Rbt12: ");
            setRbt12(entrada2.nextDouble());
            return rbt12;
        } catch (InputMismatchException e) {
            throw new ValorInvalidoException("RBT12 inválida. Digite novamente!");
        }
    }

    private static double tratarFaturamentoMensalInvalido() {
        while (true) {
            try {
                faturamentoMensal = coletarFaturamentoMensal();
                break;
            } catch (ValorInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
        return faturamentoMensal;
    }

    private static double coletarFaturamentoMensal() {
        Scanner entrada3 = new Scanner(System.in);

        try {
            System.out.print("Faturamento mensal: ");
            setFaturamentoMensal(entrada3.nextDouble());
            return faturamentoMensal;
        } catch (InputMismatchException e) {
            throw new ValorInvalidoException("Faturamento mensal inválido. Digite novamente!");
        }
    }

    private static int tratarAnexoInvalido() {
        while (true) {
            try {
                indiceAnexo = coletarAnexo();
                break;
            } catch (ValorInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        return indiceAnexo;
    }

    private static int coletarAnexo() {
        Scanner entrada2 = new Scanner(System.in);
        try {
            System.out.print("Digite o número do anexo(1 a 5): ");
            setIndiceAnexo(entrada2.nextInt() - 1);
            return indiceAnexo;
        } catch (InputMismatchException | ValorInvalidoException e) {
            throw new ValorInvalidoException("Anexo inválido. Digite novamente!");
        }
    }

    private static ArrayList<AnexoAbstrato> criarObjetosAnexo() {
        ArrayList<AnexoAbstrato> anexos = new ArrayList<>();
        anexos.add(new Anexo1());
        anexos.add(new Anexo2());
        anexos.add(new Anexo3());
        anexos.add(new Anexo4());
        anexos.add(new Anexo5());

        return anexos;
    }

    // Getters e Setters
    public static double getRbt12() {
        return rbt12;
    }

    private static void setRbt12(double rbt12) {
        if (rbt12 < 1.0) {
            throw new ValorInvalidoException("RBT12 não pode ser menor que 0.");
        }
        Simulador.rbt12 = rbt12;
    }

    public static double getFaturamentoMensal() {
        return faturamentoMensal;
    }

    private static void setFaturamentoMensal(double faturamentoMensal) {
        if (faturamentoMensal < 1) {
            throw new ValorInvalidoException("Faturamento mensal não pode ser menor que 0");
        }
        Simulador.faturamentoMensal = faturamentoMensal;
    }

    public static double getValorGuia() {
        return valorGuia;
    }

    public static int getIndiceAnexo() {
        return indiceAnexo;
    }

    private static void setIndiceAnexo(int indiceAnexo) {

        if (indiceAnexo > 4) {
            throw new ValorInvalidoException("Anexo inválido. Digite um número de 1 a 5.");
        }

        Simulador.indiceAnexo = indiceAnexo;
    }
}
