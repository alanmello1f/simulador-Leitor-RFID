/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.alanmello.simuladorleitorrfid.controller;

import br.com.alanmello.simuladorleitorrfid.service.ServidorSocket;
import br.com.alanmello.simuladorleitorrfid.constante.Constantes;
import br.com.alanmello.simuladorleitorrfid.view.PrincipalView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author ALAN MELLO
 */
public class PrincipalController {

    private PrincipalView view;
    private PrincipalController controller;
    private ServidorSocket servidor;

    public PrincipalController() {
        controller = this;
        this.view = new PrincipalView(new ActionListenerView());
    }

    private class ActionListenerView implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equalsIgnoreCase(Constantes.JBiniciar)) {
                servidor = new ServidorSocket();
                servidor.setPrincipalController(controller);
                servidor.iniciaConexao(3513);
                getView().setAviso("Servidor pronto! Realize uma conexão via cliente socket...", Color.GREEN);
            }
            if (e.getActionCommand().equalsIgnoreCase(Constantes.JBlimpar)) {
                getView().limparJTA();
            }
            if (e.getActionCommand().equalsIgnoreCase(Constantes.JBparar)) {
                getView().setAviso("Servidor parado...", Color.RED);
            }
        }
    }

    public PrincipalView getView() {
        return view;
    }

}
