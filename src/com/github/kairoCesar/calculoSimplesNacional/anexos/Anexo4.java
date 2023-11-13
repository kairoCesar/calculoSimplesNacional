package com.github.kairoCesar.calculoSimplesNacional.anexos;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraISS;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraTributosFederais;

public class Anexo4 implements AnexoAbstrato{
    CalculadoraTributosFederais tributosFederaisCalculados = new CalculadoraTributosFederais();
    CalculadoraISS issCalculado = new CalculadoraISS();

    @Override
    public double simularSimplesNacional(double rbt12, double faturamentoDoMes) {
        tributosFederaisCalculados = calcularTributosFederais(rbt12, faturamentoDoMes);
        issCalculado = calcularIss(rbt12, faturamentoDoMes);

       return tributosFederaisCalculados.getValorDeTributosFederaisApagar() +
               issCalculado.getValorDeIssApagar();
    }

    private CalculadoraISS calcularIss(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);
        int indiceFaixa = verificarFaixa(rbt12);

        if (indiceFaixa < 5) {
            issCalculado = issCalculado.calcularIss(rbt12, faturamentoDoMes, aliquotaEfetiva, reparticaoDeIssAnexo4(rbt12));
        } else {
            double aliquotaFaixa5 = 0.22;
            double valorDeducaoFaixa5 = 183_780.00;
            double reparticaoIcmsFaixa5 = 0.40;
            issCalculado = issCalculado.calcularIssFaixa6(rbt12, faturamentoDoMes, aliquotaFaixa5, valorDeducaoFaixa5, reparticaoIcmsFaixa5);
        }

        return issCalculado;
    }

    private CalculadoraISS reparticaoDeIssAnexo4(double rbt12) {
        CalculadoraISS reparticaoDeIssAnexo4 = new CalculadoraISS();

        double[] reparticaoIss = {0.4450, 0.40, 0.40, 0.40, 0.40, 0.00};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoDeIssAnexo4.setIss(reparticaoIss[indiceFaixa]);

        return reparticaoDeIssAnexo4;
    }

    private CalculadoraTributosFederais calcularTributosFederais(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);

        tributosFederaisCalculados = tributosFederaisCalculados.calcularTributosFederaisSemMonofasico(rbt12, faturamentoDoMes,
                aliquotaEfetiva, reparticaoDeTributosFederaisAnexo1(rbt12));

        return  tributosFederaisCalculados;
    }

    private CalculadoraTributosFederais reparticaoDeTributosFederaisAnexo1(double rbt12) {
        CalculadoraTributosFederais reparticaoTributosFederaisAnexo1 = new CalculadoraTributosFederais();

        double[] reparticoesCsll = {0.1520, 0.1520, 0.1520, 0.1920, 0.1920, 0.2150};
        double[] reparticoesIrpj = {0.1880, 0.1980, 0.2080, 0.1780, 0.1880, 0.5350};
        double[] reparticoesPis = {0.0383, 0.0445, 0.0427, 0.0410, 0.0392, 0.0445};
        double[] reparticoesCofins = {0.1767, 0.2055, 0.1973, 0.1890, 0.1808, 0.2055};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoTributosFederaisAnexo1.setCsll(reparticoesCsll[indiceFaixa]);
        reparticaoTributosFederaisAnexo1.setIrpj(reparticoesIrpj[indiceFaixa]);
        reparticaoTributosFederaisAnexo1.setCofins(reparticoesCofins[indiceFaixa]);
        reparticaoTributosFederaisAnexo1.setPisPasep(reparticoesPis[indiceFaixa]);

        return reparticaoTributosFederaisAnexo1;
    }

    private double encontrarAliquotaEfetiva(double rbt12, double faturamentoDoMes) {

        double[] aliquotasFixas = {0.0450, 0.09, 0.1020, 0.14, 0.22, 0.33};
        double[] valoresDeducao = {0.00, 8100.00, 12420.00, 39780.00, 183780.00, 828000.00};

        int indiceFaixa = verificarFaixa(rbt12);

        double aliquotaEfetiva = ((rbt12 * aliquotasFixas[indiceFaixa]) - valoresDeducao[indiceFaixa]) / rbt12;

        return aliquotaEfetiva;
    }

}
