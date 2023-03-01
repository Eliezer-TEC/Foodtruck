package model.bo;

import java.util.ArrayList;

import model.dao.RelatorioDAO;
import model.dto.VendasCanceladasDTO;
import model.reports.RelatorioJasper;
import model.vo.VendaVO;

public class RelatorioBO {

	public ArrayList<VendasCanceladasDTO> gerarRelatorioVendasCanceladasBO() {
		RelatorioDAO relatorioDAO = new RelatorioDAO();
		return relatorioDAO.gerarRelatorioVendasCanceladasDAO();
	}

	public void gerarRelatorioListaPedidosBO() {
		RelatorioJasper relatorioJasper = new RelatorioJasper();
		relatorioJasper.gerarRelatorioListaPedidosJasper();
	}

	public void gerarRelatorioAcompanhamentoPedidosBO(VendaVO vendaVO) {
		RelatorioJasper relatorioJasper = new RelatorioJasper();
		relatorioJasper.gerarRelatorioAcompanhamentoPedidosJasper(vendaVO);
	}

	
	

}
