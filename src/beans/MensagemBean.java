package beans;

public class MensagemBean {

	private String status;
	
	private String mensagem;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public MensagemBean(String status, String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}
	
}
