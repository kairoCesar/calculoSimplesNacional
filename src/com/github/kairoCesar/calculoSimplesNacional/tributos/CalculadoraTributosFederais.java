package com.github.kairoCesar.calculoSimplesNacional.tributos;

import java.util.Scanner;

public class CalculadoraTributosFederais {
    Scanner entrada = new Scanner(System.in);

    private double cpp;
    private double ipi;
    private double csll;
    private double irpj;
    private double cofins;
    private double pisPasep;
    private double aliquotaTributosFederais;
    private double receitaComTributacaoMonofasica;
    private double receitaSemTributacaoMonofasica;
    private double valorDeTributosFederaisApagar;

    // Método utilizado pelas classes "Anexo3", "Anexo4" e "Anexo5"
    public CalculadoraTributosFederais calcularTributosFederaisSemMonofasico
    (double rbt12, double faturamentoDoMes, double aliquotaEfetiva, CalculadoraTributosFederais reparticao) {
        CalculadoraTributosFederais tributosFederaisCalculados = new CalculadoraTributosFederais();

        tributosFederaisCalculados.setCpp((reparticao.getCpp() * aliquotaEfetiva) * 100);
        tributosFederaisCalculados.setIpi((reparticao.getIpi() * aliquotaEfetiva) * 100);
        tributosFederaisCalculados.setCsll((reparticao.getCsll() * aliquotaEfetiva) * 100);
        tributosFederaisCalculados.setIrpj((reparticao.getIrpj() * aliquotaEfetiva) * 100);
        tributosFederaisCalculados.setPisPasep((reparticao.getPisPasep() * aliquotaEfetiva) * 100);
        tributosFederaisCalculados.setCofins((reparticao.getCofins() * aliquotaEfetiva) * 100);

        tributosFederaisCalculados.setAliquotaTributosFederais((tributosFederaisCalculados.getCpp() + tributosFederaisCalculados.getIpi() +
                tributosFederaisCalculados.getCsll() + tributosFederaisCalculados.getIrpj() + tributosFederaisCalculados.getPisPasep()
                + tributosFederaisCalculados.getCofins()));


        tributosFederaisCalculados.setReceitaSemTributacaoMonofasica(faturamentoDoMes);

        tributosFederaisCalculados.setValorDeTributosFederaisApagar((tributosFederaisCalculados.getReceitaSemTributacaoMonofasica() *
                (tributosFederaisCalculados.getAliquotaTributosFederais() / 100)));

        return tributosFederaisCalculados;
    }

    // Método utilizado pelas classes "Anexo1" e "Anexo2"
    public CalculadoraTributosFederais calcularTributosFederaisComMonofasico
            (double rbt12, double faturamentoDoMes, double aliquotaEfetiva, CalculadoraTributosFederais reparticao) {

        // Cálculo dos tributos federais considerando a tributação monofásica de PIS & Cofins

        CalculadoraTributosFederais tributosFederaisCalculadosComMonofasico = new CalculadoraTributosFederais();
        tributosFederaisCalculadosComMonofasico.setReceitaComTributacaoMonofasica(verificarTributacaoMonofasicaDePisCofins(faturamentoDoMes));

        tributosFederaisCalculadosComMonofasico.setCpp((reparticao.getCpp() * aliquotaEfetiva) * 100);
        tributosFederaisCalculadosComMonofasico.setIpi((reparticao.getIpi() * aliquotaEfetiva) * 100);
        tributosFederaisCalculadosComMonofasico.setCsll((reparticao.getCsll() * aliquotaEfetiva) * 100);
        tributosFederaisCalculadosComMonofasico.setIrpj((reparticao.getIrpj() * aliquotaEfetiva) * 100);

        tributosFederaisCalculadosComMonofasico.setAliquotaTributosFederais((tributosFederaisCalculadosComMonofasico.getCpp() +
                tributosFederaisCalculadosComMonofasico.getIpi() + tributosFederaisCalculadosComMonofasico.getCsll() +
                tributosFederaisCalculadosComMonofasico.getIrpj()) / 100);

        tributosFederaisCalculadosComMonofasico.setValorDeTributosFederaisApagar(tributosFederaisCalculadosComMonofasico.getReceitaComTributacaoMonofasica() *
                tributosFederaisCalculadosComMonofasico.getAliquotaTributosFederais());

        // Cálculo dos tributos federais desconsiderando a tributação monofásica de PIS & Cofins

        double receitaSemTributacaoMonofasica =  faturamentoDoMes -
                tributosFederaisCalculadosComMonofasico.getReceitaComTributacaoMonofasica();

        CalculadoraTributosFederais tributosFederaisCalculadosSemMonofasico = calcularTributosFederaisSemMonofasico(rbt12,
                receitaSemTributacaoMonofasica, aliquotaEfetiva, reparticao);

        tributosFederaisCalculadosComMonofasico.setValorDeTributosFederaisApagar(tributosFederaisCalculadosComMonofasico.getValorDeTributosFederaisApagar() + tributosFederaisCalculadosSemMonofasico.getValorDeTributosFederaisApagar());

        return tributosFederaisCalculadosComMonofasico;
    }

    private double verificarTributacaoMonofasicaDePisCofins(double faturamentoDoMes) {
        System.out.print("A empresa vendeu mercadorias sujeitas à tributação monofásica de Pis/Cofins? ");
        String tributacaoMonofasica = entrada.next();

        if (tributacaoMonofasica.equalsIgnoreCase("sim")) {
            System.out.print("Digite o valor da receita sujeita à tributação monofásica de Pis/Cofins: ");
            double receitaComTributacaoMonofasica = entrada.nextDouble();
            while (receitaComTributacaoMonofasica > faturamentoDoMes) {
                System.out.printf("A receita sujeita à tributação monofásica não pode ser superior ao faturamento mensal de %.2f%n" +
                        "Digite novamente o valor da receita sujeita à tributação monofásica: ", faturamentoDoMes);
                receitaComTributacaoMonofasica = entrada.nextDouble();
            }
            return receitaComTributacaoMonofasica;
        } else {
            return 0.00;
        }
    }

    public double getCpp() {
        return cpp;
    }

    public void setCpp(double cpp) {
        this.cpp = cpp;
    }

    public double getIpi() {
        return ipi;
    }

    public void setIpi(double ipi) {
        this.ipi = ipi;
    }

    public double getCsll() {
        return csll;
    }

    public void setCsll(double csll) {
        this.csll = csll;
    }

    public double getIrpj() {
        return irpj;
    }

    public void setIrpj(double irpj) {
        this.irpj = irpj;
    }

    public double getCofins() {
        return cofins;
    }

    public void setCofins(double cofins) {
        this.cofins = cofins;
    }

    public double getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(double pisPasep) {
        this.pisPasep = pisPasep;
    }

    public double getAliquotaTributosFederais() {
        return aliquotaTributosFederais;
    }

    public void setAliquotaTributosFederais(double aliquotaTributosFederais) {
        this.aliquotaTributosFederais = aliquotaTributosFederais;
    }

    public double getReceitaComTributacaoMonofasica() {
        return receitaComTributacaoMonofasica;
    }

    public void setReceitaComTributacaoMonofasica(double receitaComTributacaoMonofasica) {
        this.receitaComTributacaoMonofasica = receitaComTributacaoMonofasica;
    }

    public double getReceitaSemTributacaoMonofasica() {
        return receitaSemTributacaoMonofasica;
    }

    public void setReceitaSemTributacaoMonofasica(double receitaSemTributacaoMonofasica) {
        this.receitaSemTributacaoMonofasica = receitaSemTributacaoMonofasica;
    }

    public double getValorDeTributosFederaisApagar() {
        return valorDeTributosFederaisApagar;
    }

    public void setValorDeTributosFederaisApagar(double valorDeTributosFederaisApagar) {
        this.valorDeTributosFederaisApagar = valorDeTributosFederaisApagar;
    }
}
