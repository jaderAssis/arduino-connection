package br.embedded.main;

import br.embedded.core.SerialUtil;

public class Start {
	
	public static void main(String args[]) {
		
		try {
			SerialUtil serial = new SerialUtil("/dev/ttyUSB0");
			serial.ler();
//			serial.escrever();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}