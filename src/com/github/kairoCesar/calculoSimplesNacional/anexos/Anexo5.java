package com.github.kairoCesar.calculoSimplesNacional.anexos;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraISS;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraTributosFederais;

import java.util.Scanner;

public class Anexo5 implements AnexoAbstrato{
    Scanner entrada = new Scanner(System.in);
    CalculadoraTributosFederais tributosFederaisCalculados = new CalculadoraTributosFederais();
    CalculadoraISS issCalculado = new CalculadoraISS();

    @Override
    public double simularSimplesNacional(double rbt12, double faturamentoDoMes) {
        boolean fatorR = verificarFatorR(rbt12);

        if (fatorR) {
            Anexo3 anexo3 = new Anexo3();
            tributosFederaisCalculados = anexo3.calcularTributosFederais(rbt12, faturamentoDoMes);
            issCalculado = anexo3.calcularIss(rbt12, faturamentoDoMes);
        } else {
            tributosFederaisCalculados = calcularTributosFederais(rbt12, faturamentoDoMes);
            issCalculado = calcularIss(rbt12, faturamentoDoMes);
        }

        return tributosFederaisCalculados.getValorDeTributosFederaisApagar() +
                issCalculado.getValorDeIssApagar();
    }

    private boolean verificarFatorR(double rbt12) {
        System.out.print("Informe o somatório da folha de pagamento dos últimos 12 meses: ");
        double valorFolhaDeSalarios = entrada.nextDouble();

        if ((valorFolhaDeSalarios / rbt12) >= 0.28) {
            return true;
        } else {
            return false;
        }
    }

    private CalculadoraISS calcularIss(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);
        int indiceFaixa = verificarFaixa(rbt12);

        if (indiceFaixa < 5) {
            issCalculado = issCalculado.calcularIss(rbt12, faturamentoDoMes, aliquotaEfetiva, reparticaoDeIssAnexo5(rbt12));
        } else {
            double aliquotaFaixa5 = 0.2350;
            double valorDeducaoFaixa5 = 62_100.00;
            double reparticaoIcmsFaixa5 = 0.23;
            issCalculado = issCalculado.calcularIssFaixa6(rbt12, faturamentoDoMes, aliquotaFaixa5, valorDeducaoFaixa5, reparticaoIcmsFaixa5);
        }

        return issCalculado;
    }

    private CalculadoraISS reparticaoDeIssAnexo5(double rbt12) {
        CalculadoraISS reparticaoDeIssAnexo5 = new CalculadoraISS();

        double[] reparticaoIss = {0.14, 0.17, 0.19, 0.21, 0.2350, 0.00};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoDeIssAnexo5.setIss(reparticaoIss[indiceFaixa]);

        return reparticaoDeIssAnexo5;
    }

    private CalculadoraTributosFederais calcularTributosFederais(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);

        tributosFederaisCalculados = tributosFederaisCalculados.calcularTributosFederaisSemMonofasico(rbt12, faturamentoDoMes,
                aliquotaEfetiva, reparticaoDeTributosFederaisAnexo5(rbt12));

        return tributosFederaisCalculados;
    }

    private CalculadoraTributosFederais reparticaoDeTributosFederaisAnexo5(double rbt12) {
        CalculadoraTributosFederais reparticaoTributosFederaisAnexo5 = new CalculadoraTributosFederais();

        double[] reparticoesCpp = {0.2885, 0.2785, 0.2385, 0.2385, 0.2385, 0.2950};
        double[] reparticoesCsll = {0.15, 0.15, 0.15, 0.15, 0.1250, 0.1550};
        double[] reparticoesIrpj = {0.25, 0.23, 0.24, 0.21, 0.23, 0.35};
        double[] reparticoesPis = {0.0305, 0.0305, 0.0323, 0.0341, 0.0305, 0.0356};
        double[] reparticoesCofins = {0.1410, 0.1410, 0.1492, 0.1574, 0.1410, 0.1644};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoTributosFederaisAnexo5.setCpp(reparticoesCpp[indiceFaixa]);
        reparticaoTributosFederaisAnexo5.setCsll(reparticoesCsll[indiceFaixa]);
        reparticaoTributosFederaisAnexo5.setIrpj(reparticoesIrpj[indiceFaixa]);
        reparticaoTributosFederaisAnexo5.setCofins(reparticoesCofins[indiceFaixa]);
        reparticaoTributosFederaisAnexo5.setPisPasep(reparticoesPis[indiceFaixa]);

        return reparticaoTributosFederaisAnexo5;
    }

    private double encontrarAliquotaEfetiva(double rbt12, double faturamentoDoMes) {

        double[] aliquotasFixas = {0.1550, 0.18, 0.1950, 0.2050, 0.23, 0.3050};
        double[] valoresDeducao = {0.00, 4500.00, 9900.00, 17100.00, 62100.00, 540000.00};

        int indiceFaixa = verificarFaixa(rbt12);

        double aliquotaEfetiva = ((rbt12 * aliquotasFixas[indiceFaixa]) - valoresDeducao[indiceFaixa]) / rbt12;

        return aliquotaEfetiva;
    }

}
