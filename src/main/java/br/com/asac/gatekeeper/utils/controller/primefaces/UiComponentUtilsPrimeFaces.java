package br.com.asac.gatekeeper.utils.controller.primefaces;

import org.primefaces.PrimeFaces;

import br.com.asac.gatekeeper.utils.controller.UIComponentUtils;

public class UiComponentUtilsPrimeFaces implements UIComponentUtils {
	
	private String deleteme;

	@Override
	public void showDialog(String dialogVariableName) {
		StringBuilder statement = new StringBuilder();
		statement.append("PF('");
		statement.append(dialogVariableName);
		statement.append("').show();");
		PrimeFaces.current().executeScript(statement.toString());
	}

	public String getDeleteme() {
		return deleteme;
	}

	public void setDeleteme(String deleteme) {
		this.deleteme = deleteme;
	}
}
