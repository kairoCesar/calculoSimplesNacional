package com.github.kairoCesar.calculoSimplesNacional.anexos;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraISS;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraTributosFederais;

public class Anexo3 implements AnexoAbstrato{
    CalculadoraTributosFederais tributosFederaisCalculados = new CalculadoraTributosFederais();
    CalculadoraISS issCalculado = new CalculadoraISS();

    @Override
    public double simularSimplesNacional(double rbt12, double faturamentoDoMes) {
        tributosFederaisCalculados = calcularTributosFederais(rbt12, faturamentoDoMes);
        issCalculado = calcularIss(rbt12, faturamentoDoMes);

        return tributosFederaisCalculados.getValorDeTributosFederaisApagar() +
                issCalculado.getValorDeIssApagar();
    }

    public CalculadoraISS calcularIss(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);
        int indiceFaixa = verificarFaixa(rbt12);

        if (indiceFaixa < 5) {
            issCalculado = issCalculado.calcularIss(rbt12, faturamentoDoMes, aliquotaEfetiva, reparticaoDeIssAnexo3(rbt12));
        } else {
            double aliquotaFaixa5 = 0.21;
            double valorDeducaoFaixa5 = 125_640.00;
            double reparticaoIcmsFaixa5 = 0.3350;
            issCalculado = issCalculado.calcularIssFaixa6(rbt12, faturamentoDoMes, aliquotaFaixa5, valorDeducaoFaixa5, reparticaoIcmsFaixa5);
        }

        return issCalculado;
    }

    private CalculadoraISS reparticaoDeIssAnexo3(double rbt12) {
        CalculadoraISS reparticaoDeIssAnexo3 = new CalculadoraISS();

        double[] reparticaoIss = {0.3350, 0.3200, 0.3250, 0.3250, 0.3350, 0.00};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoDeIssAnexo3.setIss(reparticaoIss[indiceFaixa]);

        return reparticaoDeIssAnexo3;
    }

    public CalculadoraTributosFederais calcularTributosFederais(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);

        tributosFederaisCalculados = tributosFederaisCalculados.calcularTributosFederaisSemMonofasico(rbt12, faturamentoDoMes,
                aliquotaEfetiva, reparticaoDeTributosFederaisAnexo3(rbt12));

        return  tributosFederaisCalculados;
    }

    private CalculadoraTributosFederais reparticaoDeTributosFederaisAnexo3(double rbt12) {
        CalculadoraTributosFederais reparticaoTributosFederaisAnexo3 = new CalculadoraTributosFederais();

        double[] reparticoesCpp = {0.4340, 0.4340, 0.4340, 0.4340, 0.4340, 0.3050};
        double[] reparticoesCsll = {0.0350, 0.0350, 0.0350, 0.0350, 0.0350, 0.15};
        double[] reparticoesIrpj = {0.04, 0.04, 0.04, 0.04, 0.04, 0.35};
        double[] reparticoesPis = {0.0278, 0.0305, 0.0296, 0.0296, 0.0278, 0.0347};
        double[] reparticoesCofins = {0.1282, 0.1405, 0.1364, 0.1364, 0.1282, 0.1603};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoTributosFederaisAnexo3.setCpp(reparticoesCpp[indiceFaixa]);
        reparticaoTributosFederaisAnexo3.setCsll(reparticoesCsll[indiceFaixa]);
        reparticaoTributosFederaisAnexo3.setIrpj(reparticoesIrpj[indiceFaixa]);
        reparticaoTributosFederaisAnexo3.setCofins(reparticoesCofins[indiceFaixa]);
        reparticaoTributosFederaisAnexo3.setPisPasep(reparticoesPis[indiceFaixa]);

        return reparticaoTributosFederaisAnexo3;
    }

    private double encontrarAliquotaEfetiva(double rbt12, double faturamentoDoMes) {

        double[] aliquotasFixas = {0.06, 0.1120, 0.1350, 0.16, 0.21, 0.33};
        double[] valoresDeducao = {0.00, 9360.00, 17640.00, 35640.00, 125640.00, 648000.00};

        int indiceFaixa = verificarFaixa(rbt12);

        double aliquotaEfetiva = ((rbt12 * aliquotasFixas[indiceFaixa]) - valoresDeducao[indiceFaixa]) / rbt12;

        return aliquotaEfetiva;
    }

}
