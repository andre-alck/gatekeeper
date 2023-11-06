package br.com.asac.gatekeeper.utils.crosscutting;

import java.io.Serializable;

public class GateKeeperException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 5209256722457416901L;

	public GateKeeperException(String message) {
		super(message);
	}

}
