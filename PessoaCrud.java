package crud;

public class PessoaCrud {
	
	private String nome;
	private int codigo;
	private String departamento;
	
	public PessoaCrud() {
	}

	public PessoaCrud(int codigo, String nome, String departamento) {
		this.codigo = codigo;
		this.nome = nome;
		this.departamento = departamento;
	}
	
	public String getDepartamento() {
		return departamento;
	}
	
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
