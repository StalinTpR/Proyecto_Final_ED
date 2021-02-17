/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista.Administrador;

import Controlador.Dao.CuentaDao;
import Controlador.Dao.DepartamentoDao;
import Controlador.Dao.PersonaDao;
import Controlador.Dao.RolDao;
import Controlador.ListaSimple;
import Controlador.Servicio.PersonaServicio;
import Controlador.Utilies;
import Modelo.CitaMedica;
import Modelo.Consulta;
import Modelo.Cuenta;
import Modelo.Departamento;
import Modelo.Especialidad;
import Modelo.HistoriaClinica;
import Modelo.Persona;
import Modelo.Rol;
import Vista.Login;
import Vista.Sesion;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author uwu
 */
public class VistaAdministrador extends javax.swing.JFrame {

    public static RolDao rol = new RolDao();
    private CuentaDao cuenta = new CuentaDao();
    private PersonaDao per = new PersonaDao();
    private ListaSimple s = rol.ordenar();
    private TablaPersona modelPersona = new TablaPersona();
    private DepartamentoDao dep = new DepartamentoDao();
    private TablaEspecialidad modelEspecialidad = new TablaEspecialidad();
    private Persona PerEnc;

    /**
     * Creates new form VistaAdministrador
     */
    Color NoActive = new Color(245, 245, 245);
    Color Active = new Color(46, 134, 193);

    CardLayout cardLayout;

    public VistaAdministrador() {
        initComponents();

        cardLayout = (CardLayout) (contenedor.getLayout());
    }

    public void colorNav(Color color1, Color color2, Color color3, Color color4, Color color5) {
        PNavD.setBackground(color1);
        PNavE.setBackground(color2);
        PNavDoc.setBackground(color3);
        PNavP.setBackground(color4);
        PNavC.setBackground(color5);
    }

    public void cargar() {
        cbxTipo.removeAllItems();
        int cont = s.tamano() - 1;
        for (int i = 0; i < s.tamano(); i++) {
            Rol s1 = (Rol) rol.Dato(cont);
            cbxTipo.addItem(s1.getNombre());
            cont--;
        }
        CargarCbxDep(ComboBoxDep);
        cargarTablaPersona();

    }

    public void cargarTablaPersona() {
        modelPersona.setS(per.listar());
        jTable1.setModel(modelPersona);
        jTable1.updateUI();
    }

    public void CargarCbxDep(JComboBox cbxa) {
        cbxa.removeAllItems();
        ListaSimple listaDep = dep.listar();
        for (int i = 0; i < listaDep.tamano(); i++) {
            Departamento departa = (Departamento) listaDep.obtenerPorPosicion(i);
            cbxa.addItem(departa.getNombre());
        }
    }

    public void CargarVDep() {
        jList1.removeAll();
        ListaSimple listaDep = dep.listar();
        String[] NombresDep = new String[listaDep.tamano()];
        for (int i = 0; i < listaDep.tamano(); i++) {
            Departamento Deplis = (Departamento) listaDep.obtenerPorPosicion(i);
            NombresDep[i] = Deplis.getNombre();
        }
        jList1.setListData(NombresDep);
    }

    public void CargarVEsp() {
        cbxDepEs.removeAllItems();
        ListaSimple listaDep = dep.listar();
        for (int i = 0; i < listaDep.tamano(); i++) {
            Departamento departa = (Departamento) listaDep.obtenerPorPosicion(i);
            cbxDepEs.addItem(departa.getNombre());
        }

    }

    public void CargarTablaEspecialidad() {
        ListaSimple listaDep = dep.listar();
        Departamento dep = (Departamento) listaDep.obtenerPorPosicion(cbxDepEs.getSelectedIndex());
        modelEspecialidad.setEsp(dep.getEsp());
        jTableEspecialidad.setModel(modelEspecialidad);
        jTableEspecialidad.updateUI();
    }

    public Persona buscarPaciente() {
        ListaSimple lista = per.listar();
        return (Persona) lista.obtenerPorPosicion(lista.buscarindice(PerEnc));
    }

    public void CargarVCita() {
        CargarCbxDep(cbxDepCita);
    }

    public Persona SacarDoctor(int pos) {
        ListaSimple personas = per.listar();
        Persona doctor = new Persona();
        int cont = 0;
        int cont1 = 0;
        for (int i = 0; i < personas.tamano(); i++) {
            Persona persona = (Persona) personas.obtenerPorPosicion(i);
            if (persona.getId_rol() == 2 && persona.getE().getNombre().equals(cbxEspCita.getSelectedItem())) {
                cont++;
            }
        }
        Persona[] listaPersonas = new Persona[cont];
        for (int i = 0; i < personas.tamano(); i++) {
            Persona persona = (Persona) personas.obtenerPorPosicion(i);
            if (persona.getId_rol() == 2 && persona.getE().getNombre().equals(cbxEspCita.getSelectedItem())) {
                listaPersonas[cont1] = persona;
                cont1++;
            }
        }
        return doctor = listaPersonas[pos];
    }

    public void ModificarPersonasCitas(Object c) {
        CitaMedica nuevacita = new CitaMedica();
        nuevacita.setDoc(SacarDoctor(CBoxMed.getSelectedIndex()));
        nuevacita.setPaciente(PerEnc);
        nuevacita.setFecha(DateCita.getDate());
        nuevacita.setTipoCita(txtNombreCita.getText());
        nuevacita.setEstado(false);
        ListaSimple listaPersonas = per.listar();
        Persona doctor = (Persona) c;
        CitaMedica[] cita = doctor.getCitas();
        CitaMedica[] temCitas = new CitaMedica[doctor.getCitas().length + 1];
        int cont = 0;
        for (int i = 0; i < temCitas.length; i++) {
            if (cont == i && i + 1 < temCitas.length) {
                if (cita.length == 0) {
                    temCitas[i] = nuevacita;
                    break;
                }
                temCitas[i] = cita[i];
                cont++;
            } else {
                temCitas[i] = nuevacita;
            }
        }
        doctor.setCitas(temCitas);
        try {
            per.modificar(s, listaPersonas.buscarindice(doctor));
        } catch (Exception ex) {
            Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnD = new javax.swing.JButton();
        btnE = new javax.swing.JButton();
        btnC = new javax.swing.JButton();
        btnDoc = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        PNav = new javax.swing.JPanel();
        PNavD = new javax.swing.JPanel();
        PNavE = new javax.swing.JPanel();
        PNavDoc = new javax.swing.JPanel();
        PNavP = new javax.swing.JPanel();
        PNavC = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        contenedor = new javax.swing.JPanel();
        panelE = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtEspecialidadES = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        btnModificarEsp = new javax.swing.JButton();
        btnEliminarEsp = new javax.swing.JButton();
        cbxDepEs = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableEspecialidad = new javax.swing.JTable();
        panelUsr = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtEspecialidad = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        cbxTipo = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtDepartamento = new javax.swing.JLabel();
        ComboBoxDep = new javax.swing.JComboBox<>();
        ComboBoxEsp = new javax.swing.JComboBox<>();
        panelC = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        DateCita = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        cbxDepCita = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        CBoxMed = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtNombreCita = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cbxEspCita = new javax.swing.JComboBox<>();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        CedBusqueda = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        RtaNombre = new javax.swing.JLabel();
        RtaApellido = new javax.swing.JLabel();
        RtaDir = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        panelD = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtDep = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(254, 254, 254));

        btnD.setBackground(new java.awt.Color(254, 254, 254));
        btnD.setText("Departamento");
        btnD.setBorder(null);
        btnD.setBorderPainted(false);
        btnD.setFocusPainted(false);
        btnD.setSelected(true);
        btnD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDMousePressed(evt);
            }
        });
        btnD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDActionPerformed(evt);
            }
        });

        btnE.setBackground(new java.awt.Color(254, 254, 254));
        btnE.setText("Especialidad");
        btnE.setBorder(null);
        btnE.setBorderPainted(false);
        btnE.setFocusPainted(false);
        btnE.setSelected(true);
        btnE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnEMousePressed(evt);
            }
        });
        btnE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEActionPerformed(evt);
            }
        });

        btnC.setBackground(new java.awt.Color(254, 254, 254));
        btnC.setText("Citas Medicas");
        btnC.setBorder(null);
        btnC.setBorderPainted(false);
        btnC.setFocusPainted(false);
        btnC.setSelected(true);
        btnC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCMousePressed(evt);
            }
        });
        btnC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCActionPerformed(evt);
            }
        });

        btnDoc.setBackground(new java.awt.Color(254, 254, 254));
        btnDoc.setText("Usuarios");
        btnDoc.setBorder(null);
        btnDoc.setBorderPainted(false);
        btnDoc.setFocusPainted(false);
        btnDoc.setSelected(true);
        btnDoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDocMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDocMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDocMousePressed(evt);
            }
        });
        btnDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocActionPerformed(evt);
            }
        });

        jLabel1.setText("Lgo X");

        PNav.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout PNavLayout = new javax.swing.GroupLayout(PNav);
        PNav.setLayout(PNavLayout);
        PNavLayout.setHorizontalGroup(
            PNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 357, Short.MAX_VALUE)
        );
        PNavLayout.setVerticalGroup(
            PNavLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PNavD.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout PNavDLayout = new javax.swing.GroupLayout(PNavD);
        PNavD.setLayout(PNavDLayout);
        PNavDLayout.setHorizontalGroup(
            PNavDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );
        PNavDLayout.setVerticalGroup(
            PNavDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        PNavE.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout PNavELayout = new javax.swing.GroupLayout(PNavE);
        PNavE.setLayout(PNavELayout);
        PNavELayout.setHorizontalGroup(
            PNavELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );
        PNavELayout.setVerticalGroup(
            PNavELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PNavDoc.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout PNavDocLayout = new javax.swing.GroupLayout(PNavDoc);
        PNavDoc.setLayout(PNavDocLayout);
        PNavDocLayout.setHorizontalGroup(
            PNavDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );
        PNavDocLayout.setVerticalGroup(
            PNavDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PNavP.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout PNavPLayout = new javax.swing.GroupLayout(PNavP);
        PNavP.setLayout(PNavPLayout);
        PNavPLayout.setHorizontalGroup(
            PNavPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );
        PNavPLayout.setVerticalGroup(
            PNavPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PNavC.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout PNavCLayout = new javax.swing.GroupLayout(PNavC);
        PNavC.setLayout(PNavCLayout);
        PNavCLayout.setHorizontalGroup(
            PNavCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );
        PNavCLayout.setVerticalGroup(
            PNavCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel10.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnD, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnE, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(PNavD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(PNavE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(PNavDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(PNavP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PNav, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(612, 612, 612)
                .addComponent(PNavC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PNavC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PNav, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnD, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnE, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnC, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PNavD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PNavE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PNavDoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PNavP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );

        contenedor.setLayout(new java.awt.CardLayout());

        panelE.setName("panelE"); // NOI18N

        jLabel19.setText("Nom. Especialidad:");

        txtEspecialidadES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEspecialidadESActionPerformed(evt);
            }
        });

        jButton13.setText("Agregar");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        btnModificarEsp.setText("Modificar");
        btnModificarEsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarEspActionPerformed(evt);
            }
        });

        btnEliminarEsp.setText("Eliminar");
        btnEliminarEsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEspActionPerformed(evt);
            }
        });

        cbxDepEs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxDepEs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDepEsActionPerformed(evt);
            }
        });

        jLabel24.setText("Departamento:");

        jTableEspecialidad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTableEspecialidad);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(btnModificarEsp)
                        .addGap(106, 106, 106))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnEliminarEsp)
                        .addGap(94, 207, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel24))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxDepEs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEspecialidadES, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(cbxDepEs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtEspecialidadES, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(btnModificarEsp))
                .addGap(18, 18, 18)
                .addComponent(btnEliminarEsp)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 220, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelELayout = new javax.swing.GroupLayout(panelE);
        panelE.setLayout(panelELayout);
        panelELayout.setHorizontalGroup(
            panelELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelELayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5305, Short.MAX_VALUE))
        );
        panelELayout.setVerticalGroup(
            panelELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelELayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contenedor.add(panelE, "panelE");
        panelE.getAccessibleContext().setAccessibleName("panelE");

        panelUsr.setBackground(new java.awt.Color(245, 245, 245));

        jLabel10.setText("Nombre");

        jLabel11.setText("Apellidos");

        jLabel12.setText("Cedula");

        jLabel13.setText("Direccion");

        txtEspecialidad.setText("Especialidad:");

        jButton5.setText("Agregar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Modificar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Eliminar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTipoActionPerformed(evt);
            }
        });

        jLabel15.setText("Tipo:");

        jLabel16.setText("Usuario;");

        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        jLabel17.setText("Clave:");

        jButton8.setText("Seleccionar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        txtDepartamento.setText("Departamento:");

        ComboBoxDep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ComboBoxDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxDepActionPerformed(evt);
            }
        });

        ComboBoxEsp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13)
                            .addComponent(txtEspecialidad)
                            .addComponent(txtDepartamento))))
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre)
                            .addComponent(txtApellido)
                            .addComponent(txtCedula)
                            .addComponent(txtDireccion)
                            .addComponent(txtUsuario)
                            .addComponent(txtClave)
                            .addComponent(cbxTipo, 0, 125, Short.MAX_VALUE))
                        .addGap(74, 74, 74)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ComboBoxEsp, javax.swing.GroupLayout.Alignment.LEADING, 0, 193, Short.MAX_VALUE)
                            .addComponent(ComboBoxDep, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addGap(2, 2, 2))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton6)
                                .addGap(18, 18, 18)
                                .addComponent(jButton7)
                                .addGap(18, 18, 18)
                                .addComponent(jButton8)
                                .addGap(27, 27, 27)))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDepartamento)
                                    .addComponent(ComboBoxDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtEspecialidad)
                                    .addComponent(ComboBoxEsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(164, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelUsrLayout = new javax.swing.GroupLayout(panelUsr);
        panelUsr.setLayout(panelUsrLayout);
        panelUsrLayout.setHorizontalGroup(
            panelUsrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsrLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5309, Short.MAX_VALUE))
        );
        panelUsrLayout.setVerticalGroup(
            panelUsrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsrLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 125, Short.MAX_VALUE))
        );

        contenedor.add(panelUsr, "panelDoc");
        panelUsr.getAccessibleContext().setAccessibleName("panelDoc");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos iniciales"));

        jLabel2.setText("Fecha de la consulta:");

        jLabel8.setText("Departamento:");

        cbxDepCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxDepCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDepCitaActionPerformed(evt);
            }
        });

        jLabel9.setText("Medico de la consulta:");

        CBoxMed.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setText("Nombre de la cita:");

        jLabel20.setText("Especialidad:");

        cbxEspCita.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxEspCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxEspCitaMouseClicked(evt);
            }
        });
        cbxEspCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEspCitaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtNombreCita, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxEspCita, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DateCita, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(CBoxMed, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(37, 37, 37)
                                .addComponent(cbxDepCita, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(325, 325, 325)))
                .addGap(140, 140, 140))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNombreCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(cbxDepCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(DateCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CBoxMed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxEspCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del paciente"));

        jLabel6.setText("C.I:");

        jButton2.setText("BUSCAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Nombres:");

        jLabel5.setText("Apellidos:");

        jLabel7.setText("Dirección:");

        RtaNombre.setText(" ");

        RtaApellido.setText(" ");

        RtaDir.setText(" ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(RtaApellido))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(RtaDir))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(RtaNombre))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(CedBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(CedBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(RtaNombre))
                .addGap(7, 7, 7)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(RtaApellido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(RtaDir))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jButton4.setText("INGRESAR CITA MEDICA");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCLayout = new javax.swing.GroupLayout(panelC);
        panelC.setLayout(panelCLayout);
        panelCLayout.setHorizontalGroup(
            panelCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(5343, Short.MAX_VALUE))
        );
        panelCLayout.setVerticalGroup(
            panelCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(235, Short.MAX_VALUE))
        );

        contenedor.add(panelC, "panelC");
        panelC.getAccessibleContext().setAccessibleName("panelC");

        panelD.setName("panelD"); // NOI18N

        jButton9.setText("Agregar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Modificar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Eliminar");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Seleccionar");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(331, 331, 331)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9)
                    .addComponent(jButton11)
                    .addComponent(jButton10))
                .addGap(35, 35, 35)
                .addComponent(jButton12)
                .addContainerGap(238, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton12)
                        .addGap(4, 4, 4)))
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de departamentos"));

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jButton17.setText("Eliminar");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setText("Modificar");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton18, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Ingreso de departamentos"));

        jLabel18.setText("Nombre del departamento:");

        txtDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDepActionPerformed(evt);
            }
        });

        jButton3.setText("Añadir Departamento");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18)
                    .addComponent(txtDep)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelDLayout = new javax.swing.GroupLayout(panelD);
        panelD.setLayout(panelDLayout);
        panelDLayout.setHorizontalGroup(
            panelDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4526, Short.MAX_VALUE))
        );
        panelDLayout.setVerticalGroup(
            panelDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDLayout.createSequentialGroup()
                .addGroup(panelDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelDLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(panelDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(236, Short.MAX_VALUE))
        );

        contenedor.add(panelD, "panelD");
        panelD.getAccessibleContext().setAccessibleName("panelD");
        panelD.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(898, 761));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDMouseEntered
        btnD.setBackground(new Color(247, 247, 247));
        //[254,254,254]
    }//GEN-LAST:event_btnDMouseEntered

    private void btnDMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDMouseExited
        btnD.setBackground(new Color(254, 254, 254));
        //[89,92,110]
    }//GEN-LAST:event_btnDMouseExited

    private void btnEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEMouseEntered
        btnE.setBackground(new Color(247, 247, 247));
    }//GEN-LAST:event_btnEMouseEntered

    private void btnEMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEMouseExited
        btnE.setBackground(new Color(254, 254, 254));
    }//GEN-LAST:event_btnEMouseExited

    private void btnDocMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDocMouseEntered
        btnDoc.setBackground(new Color(247, 247, 247));
    }//GEN-LAST:event_btnDocMouseEntered

    private void btnDocMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDocMouseExited
        btnDoc.setBackground(new Color(254, 254, 254));
    }//GEN-LAST:event_btnDocMouseExited

    private void btnCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMouseEntered
        btnC.setBackground(new Color(247, 247, 247));
    }//GEN-LAST:event_btnCMouseEntered

    private void btnCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMouseExited
        btnC.setBackground(new Color(254, 254, 254));
    }//GEN-LAST:event_btnCMouseExited

    private void btnDMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDMousePressed
        colorNav(Active, NoActive, NoActive, NoActive, NoActive);
    }//GEN-LAST:event_btnDMousePressed

    private void btnEMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEMousePressed
        colorNav(NoActive, Active, NoActive, NoActive, NoActive);
    }//GEN-LAST:event_btnEMousePressed

    private void btnDocMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDocMousePressed
        colorNav(NoActive, NoActive, Active, NoActive, NoActive);
    }//GEN-LAST:event_btnDocMousePressed

    private void btnCMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCMousePressed
        colorNav(NoActive, NoActive, NoActive, NoActive, Active);
    }//GEN-LAST:event_btnCMousePressed

    private void btnDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDActionPerformed
        cardLayout.show(contenedor, "panelD");
        CargarVDep();
    }//GEN-LAST:event_btnDActionPerformed

    private void btnEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEActionPerformed
        cardLayout.show(contenedor, "panelE");
        CargarVEsp();
    }//GEN-LAST:event_btnEActionPerformed

    private void btnDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocActionPerformed
        cardLayout.show(contenedor, "panelDoc");
        cargar();
    }//GEN-LAST:event_btnDocActionPerformed

    private void btnCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCActionPerformed
        cardLayout.show(contenedor, "panelC");
        CargarVCita();
    }//GEN-LAST:event_btnCActionPerformed
    public void limpiarUsuario() {
        txtApellido.setText("");
        txtCedula.setText("");
        txtClave.setText("");
        txtDireccion.setText("");
        txtUsuario.setText("");
        txtNombre.setText("");
    }
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (txtNombre.getText().isEmpty() || txtUsuario.getText().isEmpty() || txtClave.getText().isEmpty() || txtApellido.getText().isEmpty() || txtCedula.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Llene todo los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (txtNombre.getText().length() > 0 && txtCedula.getText().length() > 0 && txtDireccion.getText().length() > 0 && txtApellido.getText().length() > 0) {
                Persona people = new Persona();
                people.setNombres(txtNombre.getText());
                people.setDireccion(txtDireccion.getText());
                people.setApellidos(txtApellido.getText());
                people.setCedula(txtCedula.getText());
                Rol roles = (Rol) s.obtenerPorPosicion(cbxTipo.getSelectedIndex());
                people.setId_rol(roles.getId());
                people.setExternal_id(UUID.randomUUID().toString());
                Especialidad especialidad = new Especialidad();
                System.out.println(cbxTipo.getSelectedIndex());
                if (cbxTipo.getSelectedIndex() == 1) {
                    especialidad.setNombre((String) ComboBoxEsp.getSelectedItem());
                    people.setE(especialidad);
                }
                if (cbxTipo.getSelectedIndex() == 1 || cbxTipo.getSelectedIndex() == 2) {
                    CitaMedica[] citas = new CitaMedica[0];
                    people.setCitas(citas);
                }
                if (cbxTipo.getSelectedIndex() == 2) {
                    HistoriaClinica historia = new HistoriaClinica();
                    Consulta[] consulta = new Consulta[0];
                    people.setHistoria(historia);
                    people.getHistoria().setS(consulta);
                }
                per.setPersona(people);
                per.guardar();
                Cuenta ncuenta = new Cuenta();
                ncuenta.setUsuario(txtUsuario.getText());
                ncuenta.setClave(txtClave.getText());
                ncuenta.setEstado(true);
                ncuenta.setId_persona(per.getPersona().getId());
                ncuenta.setExternal_id(UUID.randomUUID().toString());
                cuenta.setCuenta(ncuenta);
                cuenta.guardar();
                limpiarUsuario();
                cuenta.setCuenta(null);
                per.setPersona(null);
            } else {
                JOptionPane.showMessageDialog(null, "Llenar los campos");
            }
            cargarTablaPersona();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (txtNombre.getText().isEmpty() || txtUsuario.getText().isEmpty() || txtClave.getText().isEmpty() || txtApellido.getText().isEmpty() || txtCedula.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Llene todo los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Persona st = (Persona) per.Dato(jTable1.getSelectedRow());
            Cuenta ct = (Cuenta) cuenta.Dato(jTable1.getSelectedRow());
            st.setNombres(txtNombre.getText());
            st.setDireccion(txtDireccion.getText());
            st.setApellidos(txtApellido.getText());
            st.setCedula(txtCedula.getText());
            ct.setUsuario(txtUsuario.getText());
            ct.setClave(txtClave.getText());
            try {
                per.modificar(st, jTable1.getSelectedRow());
            } catch (Exception ex) {
                Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                cuenta.modificar(ct, jTable1.getSelectedRow());
                limpiarUsuario();
            } catch (Exception ex) {
                Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
            cargarTablaPersona();
        }


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            per.eliminar(jTable1.getSelectedRow());
            cuenta.eliminar(jTable1.getSelectedRow());
            cargarTablaPersona();
        } catch (Exception ex) {
            Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton7ActionPerformed

    private void cbxTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTipoActionPerformed

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            Persona s = (Persona) per.Dato(jTable1.getSelectedRow());
            Cuenta c = (Cuenta) cuenta.Dato(jTable1.getSelectedRow());
            txtNombre.setText(s.getNombres());
            txtApellido.setText(s.getApellidos());
            txtCedula.setText(s.getCedula());
            txtDireccion.setText(s.getDireccion());
            txtUsuario.setText(c.getUsuario());
            txtClave.setText(c.getClave());
        } catch (Exception ex) {
            Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if (txtEspecialidadES.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Escriba el nombre de la especialidad", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Departamento departa = (Departamento) dep.Dato(cbxDepEs.getSelectedIndex());
            Especialidad especialidad = new Especialidad();
            System.out.println(departa.getEsp().length);
            if (departa.getEsp().length == 0) {
                especialidad.setId(1);
                especialidad.setNombre(txtEspecialidadES.getText());
            } else {
                especialidad.setId(departa.getEsp().length + 1);
                especialidad.setNombre(txtEspecialidadES.getText());
            }
            Especialidad[] esp = departa.getEsp();
            Especialidad[] tem = new Especialidad[departa.getEsp().length + 1];
            int cont = 0;
            for (int i = 0; i < tem.length; i++) {
                if (cont == i && i + 1 < tem.length) {
                    if (esp.length == 0) {
                        tem[i] = especialidad;
                        break;
                    }
                    tem[i] = esp[i];
                    cont++;
                } else {
                    tem[i] = especialidad;
                }
            }
            departa.setEsp(tem);
            try {
                dep.modificar(departa, cbxDepEs.getSelectedIndex());
                CargarTablaEspecialidad();
            } catch (Exception ex) {
                Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void btnModificarEspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarEspActionPerformed
        if (txtEspecialidadES.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Escriba el nombre de la especialidad", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Departamento departamento = (Departamento) dep.Dato(cbxDepEs.getSelectedIndex());
            for (int i = 0; i < departamento.getEsp().length; i++) {
                if (i == jTableEspecialidad.getSelectedRow()) {
                    departamento.getEsp()[i].setNombre(txtEspecialidadES.getText());
                }
            }
            try {
                dep.modificar(departamento, cbxDepEs.getSelectedIndex());
                CargarTablaEspecialidad();
            } catch (Exception ex) {
                Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnModificarEspActionPerformed

    private void btnEliminarEspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEspActionPerformed
        if (jTableEspecialidad.getSelectedRow() != -1) {
            Departamento departamento = (Departamento) dep.Dato(cbxDepEs.getSelectedIndex());
            Especialidad[] actual = departamento.getEsp();
            Especialidad[] temporal = new Especialidad[departamento.getEsp().length - 1];
            int cont = 0;
            for (int i = 0; i < actual.length; i++) {
                if (i != jTableEspecialidad.getSelectedRow()) {
                    temporal[cont] = actual[i];
                    cont++;
                }
            }
            departamento.setEsp(temporal);
            try {
                dep.modificar(departamento, cbxDepEs.getSelectedIndex());
                CargarTablaEspecialidad();
            } catch (Exception ex) {
                Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un elemento en la tabla");
        }


    }//GEN-LAST:event_btnEliminarEspActionPerformed

    private void cbxDepEsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDepEsActionPerformed
        ListaSimple lista = dep.listar();
        if (!lista.estaVacio()) {
            CargarTablaEspecialidad();
        }
    }//GEN-LAST:event_cbxDepEsActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ListaSimple lista = per.listar();
        ListaSimple listaPersonas = Utilies.busquedaSecuencial(per.listar(), per.Dato(lista.buscarString(CedBusqueda.getText())), "cedula");
        PerEnc = (Persona) listaPersonas.obtenerPorPosicion(0);
        if (PerEnc != null) {
            if (PerEnc.getId_rol() == 3) {
                System.out.println("-----------Encontrado--------");
                RtaNombre.setText(PerEnc.getNombres());
                RtaApellido.setText(PerEnc.getApellidos());
                RtaDir.setText(PerEnc.getDireccion());
            }
        } else {
            System.out.println("----------No------------");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (txtNombreCita.getText().length() > 0) {
            if (PerEnc != null) {
                CitaMedica nuevacita = new CitaMedica();
                Persona PacienteC = PerEnc;
                Persona doc = SacarDoctor(CBoxMed.getSelectedIndex());
                doc.setCitas(null);
                nuevacita.setDoc(doc);
                PacienteC.setCitas(null);
                nuevacita.setPaciente(PacienteC);
                nuevacita.setFecha(DateCita.getDate());
                nuevacita.setTipoCita(txtNombreCita.getText());
                nuevacita.setEstado(false);
                ListaSimple listaPersonas = per.listar();
                Persona doctor = (Persona) SacarDoctor(CBoxMed.getSelectedIndex());
                CitaMedica[] cita = doctor.getCitas();
                CitaMedica[] temCitas = new CitaMedica[doctor.getCitas().length + 1];
                int cont = 0;
                for (int i = 0; i < temCitas.length; i++) {
                    if (cont == i && i + 1 < temCitas.length) {
                        if (cita.length == 0) {
                            temCitas[i] = nuevacita;
                            break;
                        }
                        temCitas[i] = cita[i];
                        cont++;
                    } else {
                        temCitas[i] = nuevacita;
                    }
                }
                doctor.setCitas(temCitas);
                try {
                    per.modificar(doctor, listaPersonas.buscarindice(doctor));
                } catch (Exception ex) {
                    Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                }
                Persona Paciente = (Persona) buscarPaciente();
                CitaMedica[] cita1 = Paciente.getCitas();
                CitaMedica[] temCitas1 = new CitaMedica[Paciente.getCitas().length + 1];
                int cont1 = 0;
                for (int i = 0; i < temCitas1.length; i++) {
                    if (cont1 == i && i + 1 < temCitas1.length) {
                        if (cita1.length == 0) {
                            temCitas1[i] = nuevacita;
                            break;
                        }
                        temCitas1[i] = cita1[i];
                        cont++;
                    } else {
                        temCitas1[i] = nuevacita;
                    }
                }
                Paciente.setCitas(temCitas1);
                try {
                    per.modificar(Paciente, listaPersonas.buscarindice(Paciente));
                } catch (Exception ex) {
                    Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "No hay Paciente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Compruebe que Fecha y nombre de la Cita");
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (txtDep.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Llene todo los campos", "Error", JOptionPane.ERROR_MESSAGE);            
        } else {
            Departamento departamento = new Departamento();
            departamento.setNombre(txtDep.getText());
            departamento.setEsp(new Especialidad[0]);
            dep.setDepartamento(departamento);
            dep.guardar();
            txtDep.setText("");
            dep.setDepartamento(null);
            CargarVDep();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        try {
            dep.eliminar(jList1.getSelectedIndex());
            CargarVDep();
        } catch (Exception ex) {
            Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton17ActionPerformed

    private void cbxEspCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxEspCitaMouseClicked

    }//GEN-LAST:event_cbxEspCitaMouseClicked

    private void cbxEspCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEspCitaActionPerformed
        ListaSimple personas = per.listar();
        int cont = 0;
        int cont1 = 0;
        for (int i = 0; i < personas.tamano(); i++) {
            Persona persona = (Persona) personas.obtenerPorPosicion(i);
            if (persona.getId_rol() == 2 && persona.getE().getNombre().equals(cbxEspCita.getSelectedItem())) {
                cont++;
            }
        }
        Persona[] listaPersonas = new Persona[cont];
        for (int i = 0; i < personas.tamano(); i++) {
            Persona persona = (Persona) personas.obtenerPorPosicion(i);
            if (persona.getId_rol() == 2 && persona.getE().getNombre().equals(cbxEspCita.getSelectedItem())) {
                listaPersonas[cont1] = persona;
                cont1++;
            }
        }
        CBoxMed.removeAllItems();
        for (int i = 0; i < listaPersonas.length; i++) {
            CBoxMed.addItem(listaPersonas[i].getNombres());
        }
    }//GEN-LAST:event_cbxEspCitaActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        if (txtDep.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Llene todo los campos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            ListaSimple lista = dep.listar();
            Departamento departamento = (Departamento) dep.Dato(jList1.getSelectedIndex());
            departamento.setNombre(txtDep.getText());
            try {
                dep.modificar(departamento, jList1.getSelectedIndex());
                CargarVDep();
                txtDep.setText("");
            } catch (Exception ex) {
                Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton18ActionPerformed

    private void ComboBoxDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxDepActionPerformed
        ComboBoxEsp.removeAllItems();
        ListaSimple listaDep = dep.listar();
        Departamento depEsp = (Departamento) listaDep.obtenerPorPosicion(ComboBoxDep.getSelectedIndex());
        for (int i = 0; i < depEsp.getEsp().length; i++) {
            ComboBoxEsp.addItem(depEsp.getEsp()[i].getNombre());
        }
    }//GEN-LAST:event_ComboBoxDepActionPerformed

    private void cbxDepCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDepCitaActionPerformed

        cbxEspCita.removeAllItems();
        ListaSimple listaDep = dep.listar();
        Departamento depEsp = (Departamento) listaDep.obtenerPorPosicion(cbxDepCita.getSelectedIndex());
        for (int i = 0; i < depEsp.getEsp().length; i++) {
            cbxEspCita.addItem(depEsp.getEsp()[i].getNombre());
        }
    }//GEN-LAST:event_cbxDepCitaActionPerformed

    private void txtDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDepActionPerformed

    private void txtEspecialidadESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEspecialidadESActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEspecialidadESActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaAdministrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CBoxMed;
    private javax.swing.JTextField CedBusqueda;
    private javax.swing.JComboBox<String> ComboBoxDep;
    private javax.swing.JComboBox<String> ComboBoxEsp;
    private com.toedter.calendar.JDateChooser DateCita;
    private javax.swing.JPanel PNav;
    private javax.swing.JPanel PNavC;
    private javax.swing.JPanel PNavD;
    private javax.swing.JPanel PNavDoc;
    private javax.swing.JPanel PNavE;
    private javax.swing.JPanel PNavP;
    private javax.swing.JLabel RtaApellido;
    private javax.swing.JLabel RtaDir;
    private javax.swing.JLabel RtaNombre;
    private javax.swing.JButton btnC;
    private javax.swing.JButton btnD;
    private javax.swing.JButton btnDoc;
    private javax.swing.JButton btnE;
    private javax.swing.JButton btnEliminarEsp;
    private javax.swing.JButton btnModificarEsp;
    private javax.swing.JComboBox<String> cbxDepCita;
    private javax.swing.JComboBox<String> cbxDepEs;
    private javax.swing.JComboBox<String> cbxEspCita;
    private javax.swing.JComboBox<String> cbxTipo;
    private javax.swing.JPanel contenedor;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTableEspecialidad;
    private javax.swing.JPanel panelC;
    private javax.swing.JPanel panelD;
    private javax.swing.JPanel panelE;
    private javax.swing.JPanel panelUsr;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtDep;
    private javax.swing.JLabel txtDepartamento;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JLabel txtEspecialidad;
    private javax.swing.JTextField txtEspecialidadES;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCita;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

}
