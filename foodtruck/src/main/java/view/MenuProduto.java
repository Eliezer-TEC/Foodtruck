package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.ProdutoController;
import model.vo.ProdutoVO;
import model.vo.TipoProdutoVO;


public class MenuProduto {
	
	private static final int OPCAO_MENU_CADASTRAR_PRODUTO = 1;
	private static final int OPCAO_MENU_CONSULTAR_PRODUTO = 2;
	private static final int OPCAO_MENU_ATUALIZAR_PRODUTO = 3;
	private static final int OPCAO_MENU_EXCLUIR_PRODUTO = 4;
	private static final int OPCAO_MENU_PRODUTO_VOLTAR = 9;

	private static final int OPCAO_MENU_CONSULTAR_TODOS_PRODUTO = 1;
	private static final int OPCAO_MENU_CONSULTAR_UM_PRODUTO = 2;
	private static final int OPCAO_MENU_CONSULTAR_PRODUTO_VOLTAR = 9;
		
	Scanner teclado = new Scanner(System.in);
	
	public void apresentarMenuProduto() {
		int opcao = this.apresentarOpcoesMenu();
		while (opcao != OPCAO_MENU_PRODUTO_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_CADASTRAR_PRODUTO: {
				ProdutoVO produtoVO = new ProdutoVO();
				this.cadastrarProduto(produtoVO);
				break;
			}
			case OPCAO_MENU_CONSULTAR_PRODUTO: {
				this.consultarProduto();
				break;
			}
			case OPCAO_MENU_ATUALIZAR_PRODUTO: {
				this.atualizarProduto();
				break;
			}
			case OPCAO_MENU_EXCLUIR_PRODUTO: {
				this.excluirProduto();
				break;
			}
			default: {
				System.out.println("\nOpção Invalida");
			}
			}
			opcao = this.apresentarOpcoesMenu();
		}
	}

	private int apresentarOpcoesMenu() {
		System.out.println("\n---------- Sistema FoodTruck ----------");
		System.out.println("---------- Menu Produto ----------");
		System.out.println("\nOpções: ");
		System.out.println(OPCAO_MENU_CADASTRAR_PRODUTO + " - Cadastrar produto");
		System.out.println(OPCAO_MENU_CONSULTAR_PRODUTO + " - Consultar produto");
		System.out.println(OPCAO_MENU_ATUALIZAR_PRODUTO + " - Atualizar produto");
		System.out.println(OPCAO_MENU_EXCLUIR_PRODUTO + " - Excluir produto");
		System.out.println(OPCAO_MENU_PRODUTO_VOLTAR + " - Voltar");
		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}
	

	private void cadastrarProduto(ProdutoVO produtoVO) {
		if (produtoVO.getTipoProduto() == null) {
			do {
				produtoVO.setTipoProduto(TipoProdutoVO.getTipoProdutoVOporValor(this.apresentaOpcoesTipoProduto()));
			} while (produtoVO.getTipoProduto() == null);
		}
		System.out.println("\nDigite o nome: ");
		produtoVO.setNome(teclado.nextLine());
		System.out.println("Digite o preço: ");
		produtoVO.setPreco(Double.parseDouble(teclado.nextLine()));
		System.out.println("Data de Cadastro: ");
		produtoVO.setDataCadastro(LocalDateTime.now());
		
		if (this.validarCamposCadastro(produtoVO)) {
			ProdutoController produtoController = new ProdutoController();
			produtoVO = produtoController.cadastrarProdutoController(produtoVO);
			if (produtoVO.getIdProduto() != 0) {
				System.out.println("Produto cadastrado com sucesso!");
			} else {
				System.out.println("Não foi possivel cadastrar o Produto!");
			}
		}		
	}
	private int apresentaOpcoesTipoProduto() {
		ProdutoController produtoController = new ProdutoController();
		ArrayList<TipoProdutoVO> listaTipoProdutoVO = produtoController.consultarTipoProduto();
		System.out.println("\n ---- Tipo de Produtos ----");
		System.out.println("Opções");
		for (int i = 0; i < listaTipoProdutoVO.size(); i++) {
			System.out.println(listaTipoProdutoVO.get(i).getValor() + " ' " + listaTipoProdutoVO.get(i));
		}
		System.out.println("\nDigite uma opção");
		return Integer.parseInt(teclado.nextLine());
	}
	
	private boolean validarCamposCadastro(ProdutoVO produtoVO) {
		boolean resultado = true;
		System.out.println();
		if (produtoVO.getNome() == null || produtoVO.getNome().isEmpty()) {
			System.out.println("O Campo nome é obrigatório: ");
			resultado = false;
		}
		if (Double.isNaN(produtoVO.getPreco())) {
			System.out.println("O Campo Preço é obrigatório: ");
			resultado = false;
		}
		if (produtoVO.getDataCadastro() == null) {
			System.out.println("O Campo Data de cadastro é obrigatório: ");
			resultado = false;
		}
		return resultado;
	}

	

	private void consultarProduto() {
		int opcao = this.apresentarOpcoesConsulta();
		ProdutoController produtoController = new ProdutoController();
		while (opcao != OPCAO_MENU_CONSULTAR_PRODUTO_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_CONSULTAR_TODOS_PRODUTO: {
				opcao = OPCAO_MENU_CONSULTAR_PRODUTO_VOLTAR;
				ArrayList<ProdutoVO> listaProdutoVO = produtoController.consultarTodosProdutosController();
				System.out.print("\n---------- RESULTADO DA CONSULTA ----------");
				System.out.printf("\n%3s  %-13s  %-20s  %5s  %-24s  %-24s  ", "ID", "TIPO PRODUTO",
                        "NOME", "PREÇO", "DATA CADASTRO", "DATA EXPIRAÇÃO");
				for (int i = 0; i < listaProdutoVO.size(); i++) {
					listaProdutoVO.get(i).imprimir();
				}
				System.out.println();
				break;

			}

			case OPCAO_MENU_CONSULTAR_UM_PRODUTO: {
				opcao = OPCAO_MENU_CONSULTAR_PRODUTO_VOLTAR;
				ProdutoVO produtoVO = new ProdutoVO();
				System.out.println("\nInforme o código do produto: ");
				produtoVO.setIdProduto(Integer.parseInt(teclado.nextLine()));
				if (produtoVO.getIdProduto() != 0) {
					ProdutoVO produto = produtoController.consultarProdutoController(produtoVO);
					System.out.print("\n---------- RESULTADO DA CONSULTA ----------");
					System.out.printf("\n%3s  %-13s  %-20s  %5s  %-24s  %-24s  ", "ID", "TIPO PRODUTO",
                            "NOME", "PREÇO", "DATA CADASTRO", "DATA EXPIRAÇÃO");
					produto.imprimir();
					System.out.println();
				} else {
					System.out.println("O campo código do produto é obrigátorio");
				}
				break;
			}
			default: {
				System.out.println("\nOpção invalida!");
				opcao = this.apresentarOpcoesConsulta();
			}
			}
		}		
	}

	private int apresentarOpcoesConsulta() {
		System.out.println("\nInforme o tipo de consulta  a ser realizada: ");
		System.out.println(OPCAO_MENU_CONSULTAR_TODOS_PRODUTO + " - Consultar todos os produto: ");
		System.out.println(OPCAO_MENU_CONSULTAR_UM_PRODUTO + " - Consultar um produto: ");
		System.out.println(OPCAO_MENU_CONSULTAR_PRODUTO_VOLTAR + " - Voltar");
		System.out.print("\nDigite uma opção: ");
		return Integer.parseInt(teclado.nextLine());
	}

	private void atualizarProduto() {
		ProdutoVO produtoVO = new ProdutoVO();
		System.out.print("\nInforme o código do produto: ");
		produtoVO.setIdProduto(Integer.parseInt(teclado.nextLine()));
		if (produtoVO.getTipoProduto() == null) {
			do {
				produtoVO.setTipoProduto(TipoProdutoVO.getTipoProdutoVOporValor(this.apresentaOpcoesTipoProduto()));
			} while (produtoVO.getTipoProduto() == null);
		}
		System.out.println("\nDigite o nome: ");
		produtoVO.setNome(teclado.nextLine());
		System.out.println("Digite o preço: ");
		produtoVO.setPreco(Double.parseDouble(teclado.nextLine()));
		System.out.println("Data de Cadastro: ");
		produtoVO.setDataCadastro(LocalDateTime.now());
		
		if (this.validarCamposCadastro(produtoVO)) {
			ProdutoController produtoController = new ProdutoController();
			boolean resultado = produtoController.atualizarProdutoController(produtoVO);
			if (resultado) {
				System.out.println("Produto atualizado com sucesso!");
			} else {
				System.out.println("Não foi possivel atualizar o Produto!");
			}
		}	
	}

	private void excluirProduto() {
		ProdutoVO produtoVO = new ProdutoVO();
		System.out.print("\nInforme o código do produto: ");
		produtoVO.setIdProduto(Integer.parseInt(teclado.nextLine()));
		System.out.print("Digite a data de exclusão no formato dd/MM/yyyy HH:mm:ss ");
		produtoVO.setDataExclusao(
				LocalDateTime.parse(teclado.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

		if (produtoVO.getIdProduto() == 0 || produtoVO.getDataExclusao() == null) {
			System.out.println("Os campos código do usuário e data de expiração são obrigatórios: ");
		} else {
			ProdutoController produtoController = new ProdutoController();
			boolean resultado = produtoController.excluirProdutoController(produtoVO);
			if (resultado) {
				System.out.println("\nProduto excluido com sucesso!");
			} else {
				System.out.println("\nNão foi possivel excluir o Produto!!");
			}
		}
	}

	


}
