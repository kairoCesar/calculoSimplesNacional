package com.github.kairoCesar.calculoSimplesNacional.tributos;

import com.github.kairoCesar.calculoSimplesNacional.exceptions.ValorInvalidoException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculadoraICMS {

    private double icms;
    private double aliquotaIcms;
    private double valorDeIcmsApagar;
    private double receitaIcmsComSt;
    private double receitaIcmsSemSt;

    public CalculadoraICMS calcularIcms(double rbt12, double faturamentoDoMes,
                                        double aliquotaEfetiva, CalculadoraICMS reparticao) {
        CalculadoraICMS icmsCalculado = new CalculadoraICMS();
        icmsCalculado.receitaIcmsComSt = tratarIcmsInvalido(rbt12, faturamentoDoMes);

            aliquotaIcms = (reparticao.icms * aliquotaEfetiva) / 100;
            icmsCalculado.aliquotaIcms = (reparticao.icms * aliquotaEfetiva) * 100;
            icmsCalculado.receitaIcmsSemSt = faturamentoDoMes - icmsCalculado.receitaIcmsComSt;
            icmsCalculado.valorDeIcmsApagar = icmsCalculado.receitaIcmsSemSt * icmsCalculado.aliquotaIcms / 100;

        return icmsCalculado;
    }

    public CalculadoraICMS calcularIcmsfaixa6(double rbt12, double faturamentoDoMes, double aliquotaFaixa5,
                                              double valorDeducaoFaixa5, double reparticaoIcmsAnexo5) {

        CalculadoraICMS icmsCalculado = new CalculadoraICMS();
        icmsCalculado.receitaIcmsComSt = tratarIcmsInvalido(rbt12, faturamentoDoMes);

        icmsCalculado.aliquotaIcms = ((((rbt12 * aliquotaFaixa5) - valorDeducaoFaixa5) / rbt12) *
                reparticaoIcmsAnexo5) * 100;
        icmsCalculado.receitaIcmsSemSt = faturamentoDoMes - icmsCalculado.receitaIcmsComSt;
        icmsCalculado.valorDeIcmsApagar = icmsCalculado.receitaIcmsSemSt * (icmsCalculado.aliquotaIcms / 100);

        return icmsCalculado;
    }

    private double tratarIcmsInvalido(double rbt12, double faturamentoDoMes) {
        while (true) {
            try {
                receitaIcmsComSt = verificarIcmsComSt(rbt12, faturamentoDoMes);
                break;
            } catch (ValorInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        return receitaIcmsComSt;
    }

    private double verificarIcmsComSt(double rbt12, double faturamentoDoMes) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("A empresa vendeu mercadorias sujeitas à ICMS ST? ");
        String icmsComSt = entrada.next();
        try {
            if (icmsComSt.equalsIgnoreCase("sim")) {
                System.out.print("Digite o valor da receita sujeita à ICMS ST: ");
                double receitaComIcmsSt = entrada.nextDouble();
                while (receitaComIcmsSt > faturamentoDoMes) {
                    System.out.printf("A receita sujeita ao ICMS ST não pode ser superior ao " +
                            "faturamento mensal de %.2f%n" + "Digite novamente o valor da " +
                            "receita sujeita à ICMS ST: ", faturamentoDoMes);
                    receitaComIcmsSt = entrada.nextDouble();
                }
                return receitaComIcmsSt;
            } else {
                return 0.00;
            }
        } catch (InputMismatchException e) {
            throw new ValorInvalidoException("Digite apenas valores numéricos!");
        }
    }

    public double getIcms() {
        return icms;
    }

    public void setIcms(double icms) {
        this.icms = icms;
    }

    public double getAliquotaIcms() {
        return aliquotaIcms;
    }

    public void setAliquotaIcms(double aliquotaIcms) {
        this.aliquotaIcms = aliquotaIcms;
    }

    public double getValorDeIcmsApagar() {
        return valorDeIcmsApagar;
    }

    public void setValorDeIcmsApagar(double valorDeIcmsApagar) {
        this.valorDeIcmsApagar = valorDeIcmsApagar;
    }

    public double getReceitaIcmsComSt() {
        return receitaIcmsComSt;
    }

    public void setReceitaIcmsComSt(double receitaIcmsComSt) {
        this.receitaIcmsComSt = receitaIcmsComSt;
    }

    public double getReceitaIcmsSemSt() {
        return receitaIcmsSemSt;
    }

    public void setReceitaIcmsSemSt(double receitaIcmsSemSt) {
        this.receitaIcmsSemSt = receitaIcmsSemSt;
    }
}
