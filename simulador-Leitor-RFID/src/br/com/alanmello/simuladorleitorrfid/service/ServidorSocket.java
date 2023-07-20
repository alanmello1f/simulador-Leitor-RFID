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
    private List<ServidorSocketAtendente> listaDeClientes = new ArrayList<>();
    private int clientes = 0;

    public void iniciaConexao(int porta) {
        this.porta = porta;
        Thread thread = new Thread(this);
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }

    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    public void pararServidor() {
        for(ServidorSocketAtendente atendente: listaDeClientes){
            atendente.parar();
        }
        this.execute = false;
    }

    @Override
    public void run() {
//        JOptionPane.showMessageDialog(null, "A");
        criaServidor();
        int clientes = 0;
        while (execute) {
            try {
                //laço infinito
//                System.out.println("Esperando conexões...");
                Socket conexaoAtual = servidorSocket.accept();//gerencia um cliente que conectou ao servidor (aceita a conexão)
                System.out.println("TERMINAL CONECTADO...");
                principalController.getView().setAviso("TERMINAL CONECTADO...", Color.GREEN);
                principalController.getView().setTextoJTA("TERMINAL CONECTADO...");
                ServidorSocketAtendente atendente = new ServidorSocketAtendente(conexaoAtual);
                atendente.setPrincipalController(principalController);
                new Thread(atendente).start();
                listaDeClientes.add(atendente);
                atendente.setListaDeClientes(listaDeClientes);
                clientes++;
//                GlobaisControleAcesso.clientesConectados = clientes;
//                principalController.setJLconexoes(String.valueOf(clientes));
            } catch (IOException ex) {
//                principalController.setJLstatusServidor("Reconectando...");
//                principalController.setJLstatusServidor(Color.RED);
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
//                JOptionPane.showMessageDialog(null, "B");
//                JOptionPane.showMessageDialog(null, porta);
                servidorSocket = new ServerSocket(porta); //deixa disponivel o socket na porta escolhida
            }
//            principalController.setJLstatusServidor("Em Funcionamento");
//            principalController.setJLstatusServidor(Color.GREEN);
        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//            principalController.setJLstatusServidor("Porta em uso!");
//            principalController.setJLstatusServidor(Color.red);
//            execute = false;
        }
    }
}
