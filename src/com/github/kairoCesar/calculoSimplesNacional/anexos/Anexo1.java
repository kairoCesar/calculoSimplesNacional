package com.github.kairoCesar.calculoSimplesNacional.anexos;

import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraICMS;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraTributosFederais;

public class Anexo1 implements AnexoAbstrato{
    CalculadoraICMS icmsCalculado = new CalculadoraICMS();
    CalculadoraTributosFederais tributosFederaisCalculados = new CalculadoraTributosFederais();

    @Override
    public double simularSimplesNacional(double rbt12, double faturamentoDoMes) {
        icmsCalculado = calcularIcms(rbt12, faturamentoDoMes);
        tributosFederaisCalculados = calcularTributosFederais(rbt12, faturamentoDoMes);

        return tributosFederaisCalculados.getValorDeTributosFederaisApagar() +
                icmsCalculado.getValorDeIcmsApagar();
    }

    private CalculadoraICMS calcularIcms(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);
        int indiceFaixa = verificarFaixa(rbt12);

        if (indiceFaixa < 5) {
            icmsCalculado = icmsCalculado.calcularIcms(rbt12, faturamentoDoMes, aliquotaEfetiva,
                    reparticaoDeIcmsAnexo1(rbt12));
        } else {
            double aliquotaFaixa5 = 0.1430;
            double valorDeducaoFaixa5 = 87300.00;
            double reparticaoIcmsFaixa5 = 0.3350;
            icmsCalculado = icmsCalculado.calcularIcmsfaixa6(rbt12, faturamentoDoMes, aliquotaFaixa5,
                    valorDeducaoFaixa5, reparticaoIcmsFaixa5);
        }

        return icmsCalculado;
    }

    private CalculadoraICMS reparticaoDeIcmsAnexo1(double rbt12) {
        CalculadoraICMS reparticaoDeIcmsAnexo1 = new CalculadoraICMS();

        double[] reparticoesIcms = {0.34, 0.34, 0.3350, 0.3350, 0.3350, 0.00};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoDeIcmsAnexo1.setIcms(reparticoesIcms[indiceFaixa]);

        return reparticaoDeIcmsAnexo1;
    }

    private CalculadoraTributosFederais calcularTributosFederais(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);

        tributosFederaisCalculados = tributosFederaisCalculados.calcularTributosFederaisComMonofasico(rbt12,
                faturamentoDoMes, aliquotaEfetiva, reparticaoDeTributosFederaisAnexo1(rbt12));

        return  tributosFederaisCalculados;
    }

    private CalculadoraTributosFederais reparticaoDeTributosFederaisAnexo1(double rbt12) {
        CalculadoraTributosFederais reparticaoTributosFederaisAnexo1 = new CalculadoraTributosFederais();

        double[] reparticoesCpp = {0.4150, 0.4150, 0.42, 0.42, 0.42, 0.4210};
        double[] reparticoesCsll = {0.0350, 0.0350, 0.0350, 0.0350, 0.0350, 0.10};
        double[] reparticoesIrpj = {0.0550, 0.0550, 0.0550, 0.0550, 0.0550, 0.1350};
        double[] reparticoesPis = {0.0276, 0.0276, 0.0276, 0.0276, 0.0276, 0.0613};
        double[] reparticoesCofins = {0.1274, 0.1274, 0.1274, 0.1274, 0.1274, 0.2827};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoTributosFederaisAnexo1.setCpp(reparticoesCpp[indiceFaixa]);
        reparticaoTributosFederaisAnexo1.setCsll(reparticoesCsll[indiceFaixa]);
        reparticaoTributosFederaisAnexo1.setIrpj(reparticoesIrpj[indiceFaixa]);
        reparticaoTributosFederaisAnexo1.setCofins(reparticoesCofins[indiceFaixa]);
        reparticaoTributosFederaisAnexo1.setPisPasep(reparticoesPis[indiceFaixa]);

        return reparticaoTributosFederaisAnexo1;
    }

    private double encontrarAliquotaEfetiva(double rbt12, double faturamentoDoMes) {
        double[] aliquotasFixas = {0.04, 0.073, 0.095, 0.107, 0.143, 0.19};
        double[] valoresDeducao = {0.00, 5940.00, 13860.00, 22500.00, 87300.00, 378000.00};

        int indiceFaixa = verificarFaixa(rbt12);

        double aliquotaEfetiva = ((rbt12 * aliquotasFixas[indiceFaixa]) - valoresDeducao[indiceFaixa]) / rbt12;

        return aliquotaEfetiva;
    }

}
