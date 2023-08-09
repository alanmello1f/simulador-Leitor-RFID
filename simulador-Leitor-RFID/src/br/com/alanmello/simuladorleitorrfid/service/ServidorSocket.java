/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alanmello.simuladorleitorrfid.service;

import br.com.alanmello.simuladorleitorrfid.controller.PrincipalController;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ALAN MELLO
 */
public class ServidorSocket implements Runnable{

    private ServerSocket servidorSocket;
    private volatile boolean execute = true;
    public String dadosEnviar = null;
    private int porta;
    private PrincipalController principalController;

    public void iniciaConexao(int porta) {
        this.porta = porta;
        Thread thread = new Thread(this);
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void run() {
        criaServidor();
        while (execute) {
            try {
                Socket conexaoAtual = servidorSocket.accept();//gerencia um cliente que conectou ao servidor (aceita a conexão)
                principalController.getView().setAviso("TERMINAL CONECTADO...", Color.GREEN);
                principalController.getView().setTextoJTA("TERMINAL CONECTADO...");
                ServidorSocketAtendente atendente = new ServidorSocketAtendente(conexaoAtual);
                atendente.setPrincipalController(principalController);
                new Thread(atendente).start();
            } catch (IOException ex) {
                criaServidor();
                System.err.println("Servidor socket não está associado à porta");
            } catch (NullPointerException e) {

            }
        }
    }

    public void criaServidor() {
        try {
            try {
                servidorSocket.close();
                servidorSocket = new ServerSocket(porta); //deixa disponivel o socket na porta escolhida
            } catch (NullPointerException e) {
                servidorSocket = new ServerSocket(porta); //deixa disponivel o socket na porta escolhida
            }
        } catch (IOException ex) {
            
        }
    }
}
