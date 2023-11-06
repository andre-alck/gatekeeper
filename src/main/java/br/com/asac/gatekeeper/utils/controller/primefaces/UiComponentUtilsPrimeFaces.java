package br.com.asac.gatekeeper.utils.controller.primefaces;

import java.io.Serializable;

import org.primefaces.PrimeFaces;

import br.com.asac.gatekeeper.utils.controller.UIComponentUtils;

public class UiComponentUtilsPrimeFaces implements UIComponentUtils, Serializable {

	private static final long serialVersionUID = -9098320772959284175L;

	@Override
	public void showDialog(String dialogVariableName) {
		StringBuilder statement = new StringBuilder();
		statement.append("PF('");
		statement.append(dialogVariableName);
		statement.append("').show();");
		PrimeFaces.current().executeScript(statement.toString());
	}
}
