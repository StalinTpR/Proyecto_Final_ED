
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Vista.Login;

public class Principal {

    public static void main(String[] args) {
        Login ventana = new Login();
        ventana.setVisible(true);
        ventana.setResizable(true);
        ventana.setLocationRelativeTo(null);
        System.out.println("Principal.Principal.main()");
    }
}
