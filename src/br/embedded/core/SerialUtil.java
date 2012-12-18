package br.embedded.core;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import br.embedded.entity.Vaga;
import br.embedded.exception.PortNotFoundException;
import br.embedded.service.CartaoService;
import br.embedded.service.VagaService;

/**
 * 
 * @author - Jader Assis
 *
 */
public class SerialUtil implements SerialPortEventListener {

    @SuppressWarnings("rawtypes")
	static Enumeration portList;
    static CommPortIdentifier portId;
    Integer tamanhoByte;

    static SerialPort serialPort;
    static OutputStream outputStream;
    static boolean outputBufferEmptyFlag = false;
    static boolean portFound = false;

    InputStream inputStream;
    
    private String codigoCartao;
	private CartaoService cartaoService = new CartaoService(); 
    private VagaService vagaService = new VagaService();
	
	
    public SerialUtil(String portaSerial) throws PortNotFoundException {
        boolean portFound = false;
        
        portList = CommPortIdentifier.getPortIdentifiers();
        
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();

            if (portId.getName().equals(portaSerial)) {
            	portFound = true; 
                break;
            }
        }
        
        // Inicializa porta
        if ( portFound ) {
            try {
                serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
                serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                serialPort.notifyOnDataAvailable(true);
            } catch (PortInUseException e) {
                e.printStackTrace();
            } catch (UnsupportedCommOperationException e) {
				e.printStackTrace();
			}
        } else {
        	throw new PortNotFoundException("A porta " + portaSerial + " nao foi encontrada!");
        }
    }

    public synchronized void serialEvent(SerialPortEvent oEvent) {
		
    	if ( oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE ) {
			try {
				int available = inputStream.available();
				byte chunk[] = new byte[available];
				inputStream.read(chunk, 0, available);

				// codigo do cartao
				codigoCartao = new String(chunk);
				escrever();
				
				System.out.println("Codigo do cartao " + codigoCartao);
				
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

    public void escrever () throws InterruptedException, IOException, UnsupportedCommOperationException {
    	boolean valid = cartaoService.validaCartao(codigoCartao);
        
        if ( valid ) {
        	escreverNoArduino(1);
        }else {
        	escreverNoArduino(0);
        }
        
        atualizaStatusVaga(valid);
    }
    
    private void atualizaStatusVaga(boolean valid) {
    	List<Vaga> list = vagaService.listAll();
    	for(Vaga vaga : list ) {
    		if ( valid == vaga.isOcupada() ) {
    			vagaService.atualizaStatusVaga(valid, vaga.getDescricao());
    			return;
    		}
    	}
	}

	private void escreverNoArduino(int isValid) throws InterruptedException, IOException, UnsupportedCommOperationException {
        outputStream = serialPort.getOutputStream();
        serialPort.notifyOnOutputEmpty(true);
        outputStream.write(isValid);
        
        //somente para teste
        System.out.println("Escreveu \"" + isValid + "\" na " + serialPort.getName());
        
        Thread.sleep(200); 
    }

    public void ler() throws IOException, TooManyListenersException, UnsupportedCommOperationException {
        inputStream = serialPort.getInputStream();
        serialPort.addEventListener(this);
    }
}
