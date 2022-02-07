package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.FabricaConexao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Cadastro {
	
	// Adicionar cadastro de departamentos
	// Mais infos sobre os funcionários
	// Classe Date
	// Problema na classe CREATE com next/nextLine
	
	static final Scanner entrada = new Scanner(System.in);
	static final List<PessoaCrud> lista = new ArrayList<>();
	static final Random aleatorio = new Random();
	static final Connection conexao = FabricaConexao.getConexao();
	public static void main(String[] args) throws SQLException {
	
		int opcao = -1;
		
		do {
			System.out.println("Bem-vindo ao cadastro de funcionários!");
			System.out.println("Digite a opção que você deseja: ");
			System.out.println("1 - Adicionar");
			System.out.println("2 - Listar");
			System.out.println("3 - Alterar");
			System.out.println("4 - Remover");
			System.out.println("9 - Sair");
			opcao = entrada.nextInt();
			switch (opcao) {
				case 1:
					create();	
					break;
				case 2:
					getAll();
					break;
				case 3:
					update();
					break;
				case 4:
					delete();
					break;
				case 9:
					System.out.println("Saindo...");
					break;
				default:
					System.out.println("Opção inválida!");
			}
			
		} while (opcao != 9);

		entrada.close();
	}
	
	public static void create() throws SQLException{
		System.out.println("Informe o departamento do funcionário:");
		String departamento = entrada.next();
		System.out.println("Informe o nome do funcionário:");
		String nome = entrada.next();
		int codigo = aleatorio.nextInt(10000);
		System.out.println("Código gerado: " + codigo);
		
		String insertSQL = "INSERT INTO funcionarios (codigo, nome,"
				+ "departamento) VALUES (?, ?, ?)";
		
		PreparedStatement stmt = conexao.prepareStatement(insertSQL);
		stmt.setInt(1, codigo);
		stmt.setString(2, nome);
		stmt.setString(3, departamento);
		stmt.execute();
		
		System.out.println("Pessoa incluída com sucesso!");
	}
	
	public static void getAll() throws SQLException {
		System.out.print("1 - Por departamento OU 2 - Toda a lista: ");
		int opcao = entrada.nextInt();
		
		if(opcao == 1) {
			System.out.print("Digite o departamento: ");
			String dep = entrada.next();
			String selectSQL = "SELECT * FROM funcionarios WHERE departamento like ?";
			
			PreparedStatement stmt = conexao.prepareStatement(selectSQL);
			stmt.setString(1, "%" + dep + "%");
			ResultSet resultado = stmt.executeQuery();
			
			List<PessoaCrud> funcs = new ArrayList<>();
		
			while(resultado.next()) {
				int codigo = resultado.getInt("codigo");
				String nome = resultado.getString("nome");
				String departamento = resultado.getString("departamento");
				funcs.add(new PessoaCrud(codigo, nome, departamento));
			}
			
			for(PessoaCrud func: funcs) {
				System.out.println(func.getCodigo() + " - " + func.getNome()
					+ " - " + func.getDepartamento());
			}
		}else if (opcao == 2) {
			String selectSQL = "SELECT * FROM funcionarios";
			Statement stmt = conexao.createStatement();
			ResultSet resultado = stmt.executeQuery(selectSQL);
			
			List<PessoaCrud> funcs = new ArrayList<>();
			
			while(resultado.next()) {
				int codigo = resultado.getInt("codigo");
				String nome = resultado.getString("nome");
				String departamento = resultado.getString("departamento");
				funcs.add(new PessoaCrud(codigo, nome, departamento));
			}
			
			for(PessoaCrud func: funcs) {
				System.out.println(func.getCodigo() + " - " + func.getNome()
					+ " - " + func.getDepartamento());
			}
		}
	}
	
	public static void update() throws SQLException {
		System.out.print("Digite o código do funcionário: ");
		int codigo = entrada.nextInt();
		String selectSQL = "SELECT * FROM funcionarios WHERE codigo = ?";
		String updateSQL = "UPDATE funcionarios SET departamento = ? "
				+ "WHERE codigo = ?";
		PreparedStatement stmt = conexao.prepareStatement(selectSQL);
		stmt.setInt(1, codigo);
		ResultSet r = stmt.executeQuery();
		
		if(r.next()) {
			System.out.print("Digite o novo departamento do funcionário:");
			String novoDp = entrada.next();
			stmt = conexao.prepareStatement(updateSQL);
			stmt.setString(1, novoDp);
			stmt.setInt(2, codigo);
			stmt.execute();
			
			System.out.println("Departamento atualizado com sucesso!");
			getAll();
		}else {
			System.out.println("Nada feito!");	
		}
	}
	
	public static void delete() throws SQLException {
		System.out.print("Informe o código do funcionário: ");
		int codigo = entrada.nextInt();
		String deleteSQL = "DELETE FROM funcionarios WHERE codigo = ?";
		PreparedStatement stmt = conexao.prepareStatement(deleteSQL);
		stmt.setInt(1, codigo);
		int contador = stmt.executeUpdate();
		
		if(contador > 0) {
			System.out.println("Funcionário excluído com sucesso!");
		}else {
			System.out.println("Nada feito!");	
		}
	}
}

	/*public static int getIndexByCodigo() throws Exception {
		System.out.println("Informe o código do funcionário: ");
		int codigo = entrada.nextInt();
		int index = -1;
		
		for(int i = 0; i < lista.size(); i++) {
			Pessoa p = lista.get(i);
			if(p.getCodigo() == codigo) {
				index = i;		
			}
		}
		
		if(index == -1) {
			System.out.println("Código inexistente!!!");
			throw new Exception();
		}
		return index;
	}*/
