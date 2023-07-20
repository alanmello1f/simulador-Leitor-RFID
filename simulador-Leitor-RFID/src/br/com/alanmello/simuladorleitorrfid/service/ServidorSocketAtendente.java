/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alanmello.simuladorleitorrfid.service;

import br.com.alanmello.simuladorleitorrfid.controller.PrincipalController;
import br.com.alanmello.simuladorleitorrfid.controller.PrincipalController;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ALAN MELLO
 */
public class ServidorSocketAtendente implements Runnable {

    private Socket requisicao;
    private String[] dadosEnviar = new String[10];
    private volatile boolean execute = true;
    private List<ServidorSocketAtendente> listaDeClientes;
    private List<String> caixaDeEnvio = new ArrayList<>();
    private boolean prioridade;
    private long tempoAckEnviado;
    private boolean aguardaEnvio;
    private PrincipalController principalController;

    public ServidorSocketAtendente(Socket requisicao) {
        dadosEnviar[0] = "0X3DAC3022";
        dadosEnviar[1] = "0X3DAC3023";
        dadosEnviar[2] = "0X3DAC3024";
        dadosEnviar[3] = "0X3DAC304A";
        dadosEnviar[4] = "0X3DAC304D";
        this.requisicao = requisicao;
    }

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void setPrioritario(boolean b) {
        this.prioridade = b;
    }

    public void addElementoCaixaDeEnvio(String dados) {
        caixaDeEnvio.add(dados);
    }

    public void setListaDeClientes(List<ServidorSocketAtendente> listaDeClientes) {
        this.listaDeClientes = listaDeClientes;
    }
    
    public void parar(){
        execute = false;
    }

    @Override
    public void run() {
        try {
            int contador = 0;
            String solicitacaoCliente;
            BufferedReader responsavelReceberDados = new BufferedReader(new InputStreamReader(requisicao.getInputStream()));
            DataOutputStream responsavelEnviarDados = new DataOutputStream(requisicao.getOutputStream());//implementa rotinas para envio de dados para o cliente atualmente conectado
//            try {
//                principalController.setJLconexoesAtivas(String.valueOf(listaDeClientes.size()));
//            } catch (NullPointerException e) {
//
//            }
            boolean eliminarRequisicao = false;
            boolean foraPadrao = true;
//            tempoAckEnviado = System.currentTimeMillis();
            while (execute) {
                try {
                    Thread.sleep(10000);
                    principalController.getView().setTextoJTA("ENVIANDO: " + dadosEnviar[contador]);
                    responsavelEnviarDados.writeBytes(dadosEnviar[contador] + '\n'); //envia dados para o cliente                  
                    contador++;
                    if(contador == 5){
                        contador = 0;
                    }
                } catch (NullPointerException e) {
                    System.out.println(e);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServidorSocketAtendente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            principalController.getView().setTextoJTA("CLIENTE ABORTOU");
            principalController.getView().setAviso("CLIENTE ABORTOU...AGUARDANDO NOVO CLIENTE!", Color.red);
            System.out.println("Cliente abortou");
        }
        
        try {
            listaDeClientes.remove(this);
//            principalController.setJLconexoesAtivas(String.valueOf(listaDeClientes.size()));
        } catch (NullPointerException e) {

        }

    }

}
