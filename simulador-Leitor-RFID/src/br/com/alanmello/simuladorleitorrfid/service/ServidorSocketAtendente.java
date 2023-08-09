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

    public void parar() {
        execute = false;
    }

    @Override
    public void run() {
        try {
            int contador = 0;
            DataOutputStream responsavelEnviarDados = new DataOutputStream(requisicao.getOutputStream());//implementa rotinas para envio de dados para o cliente atualmente conectado
            while (execute) {
                try {
                    Thread.sleep(10000);
                    principalController.getView().setTextoJTA("ENVIANDO: " + dadosEnviar[contador]);
                    responsavelEnviarDados.writeBytes(dadosEnviar[contador] + '\n'); //envia dados para o cliente                  
                    contador++;
                    if (contador == 5) {
                        contador = 0;
                    }
                } catch (NullPointerException | InterruptedException ex) {
                    Logger.getLogger(ServidorSocketAtendente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            principalController.getView().setTextoJTA("CLIENTE ABORTOU");
            principalController.getView().setAviso("CLIENTE ABORTOU...AGUARDANDO NOVO CLIENTE!", Color.red);
        }

        try {
            listaDeClientes.remove(this);
        } catch (NullPointerException e) {

        }
    }

}
