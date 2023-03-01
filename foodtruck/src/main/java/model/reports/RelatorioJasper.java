package model.reports;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import model.dao.Banco;
import model.vo.VendaVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class RelatorioJasper {

	public void gerarRelatorioListaPedidosJasper() {
		try {
            String currentPath = "";
            try {
                currentPath = new File(".").getCanonicalPath();
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
            System.out.println(currentPath);
            JasperRunManager.runReportToPdfFile(currentPath + "/lista_vendas.jasper", currentPath + "/lista_vendas.pdf", null, Banco.getConnection());
            System.out.println("Relatorio gerado em " + currentPath + "/lista_vendas.pdf");
        } catch (JRException ex) {
            System.out.println("Não foi possivel imprimir, por favor verifique o modelo de impressão");
        }
	}
	
	public void gerarRelatorioAcompanhamentoPedidosJasper(VendaVO vendaVO) {
		try {
			String currentPath = "";
			try {
				currentPath = new File(".").getCanonicalPath();
			} catch(IOException erro) {
				System.out.println(erro.toString());
			}
			System.out.println(currentPath);
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("idVenda", vendaVO.getIdVenda());
			JasperRunManager.runReportToPdfFile(currentPath + "/acompanhamento.jasper", currentPath + "/acompanhamento.pdf", parameters, Banco.getConnection());
			System.out.println("Relatório gerado em " + currentPath + "/acompanhamento.pdf");
		} catch(JRException erro) {
			System.out.println("Não foi possivel imprimir, por favor verifique o modelo de impressão");
			System.out.println("Erro: " + erro.getMessage());
		}
	}

	
}
