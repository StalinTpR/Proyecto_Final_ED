/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Vista.Login;

/**
 *
 * @author Stalin Jimenez
 */
public class Principal {
    public static void main(String[] args) {        
        Vista.Login ventana = new Vista.Login();
        ventana.setVisible(true);
        ventana.setResizable(true);
        ventana.setLocationRelativeTo(null);
    }
}
