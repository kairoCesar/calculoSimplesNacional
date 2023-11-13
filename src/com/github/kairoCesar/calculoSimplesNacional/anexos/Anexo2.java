package com.github.kairoCesar.calculoSimplesNacional.anexos;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraICMS;
import com.github.kairoCesar.calculoSimplesNacional.tributos.CalculadoraTributosFederais;

public class Anexo2 implements AnexoAbstrato{
    CalculadoraICMS icmsCalculado = new CalculadoraICMS();
    CalculadoraTributosFederais tributosFederaisCalculados = new CalculadoraTributosFederais();

    @Override
    public double simularSimplesNacional(double rbt12, double faturamentoDoMes) {
        icmsCalculado = calcularIcms(rbt12, faturamentoDoMes);
        tributosFederaisCalculados = calcularTributosFederais(rbt12, faturamentoDoMes);

        return tributosFederaisCalculados.getValorDeTributosFederaisApagar() +
                icmsCalculado.getValorDeIcmsApagar();
    }

    public CalculadoraICMS calcularIcms(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);
        int indiceFaixa = verificarFaixa(rbt12);

        if (indiceFaixa < 5) {
            icmsCalculado = icmsCalculado.calcularIcms(rbt12, faturamentoDoMes, aliquotaEfetiva, reparticaoDeIcmsAnexo2(rbt12));
        } else {
            double aliquotaFaixa5 = 0.1470;
            double valorDeducaoFaixa5 = 85500.00;
            double reparticaoIcmsFaixa5 = 0.32;
            icmsCalculado = icmsCalculado.calcularIcmsfaixa6(rbt12, faturamentoDoMes, aliquotaFaixa5, valorDeducaoFaixa5, reparticaoIcmsFaixa5);
        }

        return icmsCalculado;
    }

    private CalculadoraICMS reparticaoDeIcmsAnexo2(double rbt12) {
        CalculadoraICMS reparticaoDeIcmsAnexo2 = new CalculadoraICMS();

        double[] reparticoesIcms = {0.32, 0.32, 0.32, 0.32, 0.32, 0.00};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoDeIcmsAnexo2.setIcms(reparticoesIcms[indiceFaixa]);

        return reparticaoDeIcmsAnexo2;
    }

    public CalculadoraTributosFederais calcularTributosFederais(double rbt12, double faturamentoDoMes) {
        double aliquotaEfetiva = encontrarAliquotaEfetiva(rbt12, faturamentoDoMes);

        tributosFederaisCalculados = tributosFederaisCalculados.calcularTributosFederaisComMonofasico(rbt12,
                faturamentoDoMes, aliquotaEfetiva, reparticaoDeTributosFederaisAnexo2(rbt12));

        return tributosFederaisCalculados;
    }

    private CalculadoraTributosFederais reparticaoDeTributosFederaisAnexo2(double rbt12) {
        CalculadoraTributosFederais reparticaoTributosFederaisAnexo2 = new CalculadoraTributosFederais();

        double[] reparticoesCpp = {0.3750, 0.3750, 0.3750, 0.3750, 0.3750, 0.2350};
        double[] reparticoesIpi = {0.0750, 0.0750, 0.0750, 0.0750, 0.0750, 0.35};
        double[] reparticoesCsll = {0.0350, 0.0350, 0.0350, 0.0350, 0.0350, 0.0750};
        double[] reparticoesIrpj = {0.0550, 0.0550, 0.0550, 0.0550, 0.0550, 0.0850};
        double[] reparticoesPis = {0.0249, 0.0249, 0.0249, 0.0249, 0.0249, 0.0454};
        double[] reparticoesCofins = {0.1151, 0.1151, 0.1151, 0.1151, 0.1151, 0.2096};

        int indiceFaixa = verificarFaixa(rbt12);

        reparticaoTributosFederaisAnexo2.setCpp(reparticoesCpp[indiceFaixa]);
        reparticaoTributosFederaisAnexo2.setIpi(reparticoesIpi[indiceFaixa]);
        reparticaoTributosFederaisAnexo2.setCsll(reparticoesCsll[indiceFaixa]);
        reparticaoTributosFederaisAnexo2.setIrpj(reparticoesIrpj[indiceFaixa]);
        reparticaoTributosFederaisAnexo2.setCofins(reparticoesCofins[indiceFaixa]);
        reparticaoTributosFederaisAnexo2.setPisPasep(reparticoesPis[indiceFaixa]);

        return reparticaoTributosFederaisAnexo2;
    }

    private double encontrarAliquotaEfetiva(double rbt12, double faturamentoDoMes) {

        double[] valoresDeducao = {0.00, 5940.00, 13860.00, 22500.00, 85500.00, 720000.00};
        double[] aliquotasFixas = {0.045, 0.078, 0.10, 0.112, 0.147, 0.30};

        int indiceFaixa = verificarFaixa(rbt12);

        double aliquotaEfetiva = ((rbt12 * aliquotasFixas[indiceFaixa]) - valoresDeducao[indiceFaixa]) / rbt12;

        return aliquotaEfetiva;
    }

}
