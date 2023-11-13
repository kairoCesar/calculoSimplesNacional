package com.github.kairoCesar.calculoSimplesNacional.tributos;

import com.github.kairoCesar.calculoSimplesNacional.exceptions.ValorInvalidoException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculadoraISS {

    private double iss;
    private double aliquotaIss;
    private double valorDeIssApagar;
    private double receitaIssRetido;
    private double receitaIssProprio;

    public CalculadoraISS calcularIss(double rbt12, double faturamentoDoMes,
                                      double aliquotaEfetiva, CalculadoraISS reparticao) {

        CalculadoraISS issCalculado = new CalculadoraISS();
        issCalculado.receitaIssRetido = tratarIssInvalido(rbt12, faturamentoDoMes);

        aliquotaIss = (reparticao.iss * aliquotaEfetiva) / 100;
        issCalculado.aliquotaIss = (reparticao.iss * aliquotaEfetiva) * 100;
        issCalculado.receitaIssProprio = faturamentoDoMes - issCalculado.receitaIssRetido;
        issCalculado.valorDeIssApagar = issCalculado.receitaIssProprio * (issCalculado.aliquotaIss / 100);

        return issCalculado;
    }

    public CalculadoraISS calcularIssFaixa6(double rbt12, double faturamentoDoMes, double aliquotaFaixa5,
                                            double valorDeducaoFaixa5, double reparticaoIssAnexo5) {

        CalculadoraISS issCalculado = new CalculadoraISS();
        issCalculado.receitaIssRetido = tratarIssInvalido(rbt12, faturamentoDoMes);

        issCalculado.aliquotaIss = ((((rbt12 * aliquotaFaixa5) - valorDeducaoFaixa5) / rbt12)
                * reparticaoIssAnexo5) * 100;
        issCalculado.receitaIssProprio = faturamentoDoMes - issCalculado.receitaIssRetido;
        issCalculado.valorDeIssApagar = issCalculado.receitaIssProprio * (issCalculado.aliquotaIss / 100);

        return issCalculado;
    }

    private double tratarIssInvalido(double rbt12, double faturamentoDoMes) {
        while (true) {
            try {
                receitaIssRetido = verificarIssRetido(rbt12, faturamentoDoMes);
                break;
            } catch (ValorInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
        return receitaIssRetido;
    }

    private double verificarIssRetido(double rbt12, double faturamentoDoMes) {
        Scanner entrada = new Scanner(System.in);
        System.out.print("A empresa emitiu notas com retenção de ISS? ");
        String icmsComSt = entrada.next();

        try {
            if (icmsComSt.equalsIgnoreCase("sim")) {
                System.out.print("Digite o valor total das notas com retenção de ISS: ");
                double receitaIssRetido = entrada.nextDouble();
                while (receitaIssRetido > faturamentoDoMes) {
                    System.out.printf("O valor das notas não pode ser superior ao faturamento mensal de %.2f%n" +
                            "Digite novamente o valor total das notas com retenção de ISS: ", faturamentoDoMes);
                    receitaIssRetido = entrada.nextDouble();
                }
                return receitaIssRetido;
            } else {
                return 0.00;
            }
        } catch (InputMismatchException e) {
            throw new ValorInvalidoException("Digite apenas valores numéricos!");
        }
    }

    public double getIss() {
        return iss;
    }

    public void setIss(double iss) {
        this.iss = iss;
    }

    public double getAliquotaIss() {
        return aliquotaIss;
    }

    public void setAliquotaIss(double aliquotaIss) {
        this.aliquotaIss = aliquotaIss;
    }

    public double getValorDeIssApagar() {
        return valorDeIssApagar;
    }

    public void setValorDeIssApagar(double valorDeIssApagar) {
        this.valorDeIssApagar = valorDeIssApagar;
    }

    public double getReceitaIssRetido() {
        return receitaIssRetido;
    }

    public void setReceitaIssRetido(double receitaIssRetido) {
        this.receitaIssRetido = receitaIssRetido;
    }

    public double getReceitaIssProprio() {
        return receitaIssProprio;
    }

    public void setReceitaIssProprio(double receitaIssProprio) {
        this.receitaIssProprio = receitaIssProprio;
    }
}

