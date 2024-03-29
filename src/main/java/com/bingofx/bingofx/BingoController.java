package com.bingofx.bingofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class BingoController implements Initializable {

    @FXML
    private Button btnJugar1;

    @FXML
    private Button btnJugar2;

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnGuardarComentario;

    @FXML
    private Label txtBingo;

    @FXML
    private Label txtJugador;

    @FXML
    private Label txtLinea;

    @FXML
    private Label txtRegistro;

    @FXML
    private Label txtTestComentario;

    @FXML
    private TextArea txtComentario;

    //VARIABLES QUE GUARDAN LOS REGISTROS DEL JUGADOR QUE LUEGO SE USARAN
    //PARA INSERTARLOS EN LA BBDD
    private String nombrejugador;
    private String hizolinea;
    private String hizobingo;
    private int tiradaslinea;
    private int tiradasbingo;

    //OBJETO PARA LLAMAR A METODOS PARA CAMBIAR DE PANTALLA
    private Pantalla pantalla = new Pantalla();

    /**
     * Ir a la pantalla de escribir nombre para el modo 1 jugador
     * @param event
     */
    @FXML
    void JugarUnJugador(ActionEvent event) {
        pantalla.CerrarVentanaActual();
        pantalla.IrMenuUnJugador();
    }

    /**
     * Ir a la pantalla de escribir nombres para el modo 2 jugadores
     * @param event
     */
    @FXML
    void JugarDosJugadores(ActionEvent event) {
        pantalla.CerrarVentanaActual();
        pantalla.IrMenuDosJugadores();
    }

    /**
     * Volver a la pantalla de inicio
     * @param event
     */
    @FXML
    void VolverInicio(ActionEvent event) {
        pantalla.CerrarVentanaActual();
        pantalla.IrMenuInicio();
    }

    /**
     * Dependiendo de que modo de juego se realizo y quien canto bingo,
     * se guardan los registros de la partida, se muestran los textos
     * y se ponen las variables estaticas de las otras clases a null
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //UN JUGADOR
        if(MenuUnJugadorController.nombrejugador != null){
            nombrejugador = MenuUnJugadorController.nombrejugador;
            hizolinea = JuegoUnJugadorController.hizolinea;
            hizobingo = JuegoUnJugadorController.hizobingo;
            tiradaslinea = JuegoUnJugadorController.contadorlinea;
            tiradasbingo = JuegoUnJugadorController.contadorbingo;
            PonerTextos();
        }
        //DOS JUGADORES
        else{
            //SI GANA EL JUGADOR 1
            if(JuegoDosJugadoresController.nombreganador.equals(MenuDosJugadoresController.nombrejugador1)){
                nombrejugador = JuegoDosJugadoresController.nombreganador;
                hizolinea = JuegoDosJugadoresController.hizolineajugador1;
                tiradaslinea = JuegoDosJugadoresController.contadorlineajugador1;
                hizobingo = JuegoDosJugadoresController.hizobingojugador1;
                tiradasbingo = JuegoDosJugadoresController.contadorbingojugador1;
                PonerTextos();
            }
            //SI GANA EL JUGADOR 2
            else{
                nombrejugador = JuegoDosJugadoresController.nombreganador;
                hizolinea = JuegoDosJugadoresController.hizolineajugador2;
                tiradaslinea = JuegoDosJugadoresController.contadorlineajugador2;
                hizobingo = JuegoDosJugadoresController.hizobingojugador2;
                tiradasbingo = JuegoDosJugadoresController.contadorbingojugador2;
                PonerTextos();
            }
        }

        //PRUEBAS CONSOLA
        System.out.println("Jugador: " + nombrejugador);
        System.out.println("¿Hizo linea?: " +  hizolinea);
        System.out.println("Tiradas linea: " + tiradaslinea);
        System.out.println("¿Hizo bingo?: " + hizobingo);
        System.out.println("Tiradas bingo: " + tiradasbingo);
        RestaurarRegistros();
    }

    /**
     * Pone todas las variables estaticas de las otras clases a null
     */
    public static void RestaurarRegistros(){
        MenuUnJugadorController.nombrejugador = null;
        JuegoUnJugadorController.hizolinea = null;
        JuegoUnJugadorController.contadorlinea = 0;
        JuegoUnJugadorController.hizobingo = null;
        JuegoUnJugadorController.contadorbingo = 0;

        MenuDosJugadoresController.nombrejugador1 = null;
        MenuDosJugadoresController.nombrejugador2 = null;
        JuegoDosJugadoresController.hizolineajugador1 = null;
        JuegoDosJugadoresController.contadorlineajugador1 = 0;
        JuegoDosJugadoresController.hizolineajugador2 = null;
        JuegoDosJugadoresController.contadorlineajugador2 = 0;
        JuegoDosJugadoresController.hizobingojugador1 = null;
        JuegoDosJugadoresController.contadorbingojugador1 = 0;
        JuegoDosJugadoresController.hizobingojugador2 = null;
        JuegoDosJugadoresController.contadorbingojugador2 = 0;
    }

    /**
     * Muestra X textos en la pantalla de Bingo segun quien haya cantado
     * bingo
     */
    public void PonerTextos(){

        //UN JUGADOR
        if(MenuUnJugadorController.nombrejugador != null){
            //TEXTO JUGADOR
            if(hizobingo.equals("Si")){
                txtJugador.setText("¡HAS GANADO!");
            }
            else{
                txtJugador.setText("Has perdido...");
            }
            //TEXTO LINEA
            if(hizolinea.equals("Si")){
                txtLinea.setText("Has hecho linea en " + tiradaslinea + " tiradas.");
            }
            else{
                txtLinea.setText("No has hecho linea...");
            }
            //TEXTO BINGO
            if(hizobingo.equals("Si")){
                txtBingo.setText("Has hecho bingo en " + tiradasbingo + " tiradas.");
            }
            else{
                txtBingo.setText("No has hecho bingo...");
            }
            //TEXTO REGISTRO
            if(insertRegistro(nombrejugador,hizolinea,tiradaslinea,hizobingo,tiradasbingo)){
                txtRegistro.setTextFill(Color.BLUE);
                txtRegistro.setText("¡Se han registrado tus resultados!");
            }
            else{
                txtRegistro.setTextFill(Color.RED);
                txtRegistro.setText("¡No se han podido guardar los resultados!");
                txtComentario.setDisable(true);
                btnGuardarComentario.setDisable(true);
            }
        }
        //DOS JUGADORES
        else{
            //TEXTO JUGADOR
            txtJugador.setText("Ganador: " + nombrejugador);
            //TEXTO LINEA
            if(hizolinea.equals("Si")){
                txtLinea.setText("Has hecho linea en " + tiradaslinea + " tiradas.");
            }
            else{
                txtLinea.setText("No has hecho linea...");
            }
            //TEXTO BINGO
            txtBingo.setText("Has hecho bingo en " + tiradasbingo + " tiradas.");
            //TEXTO REGISTRO
            if(insertRegistro(nombrejugador,hizolinea,tiradaslinea,hizobingo,tiradasbingo)){
                txtRegistro.setTextFill(Color.BLUE);
                txtRegistro.setText("¡Se han registrado tus resultados!");
            }
            else{
                txtRegistro.setTextFill(Color.RED);
                txtRegistro.setText("¡No se han podido guardar los resultados!");
                txtComentario.setDisable(true);
                btnGuardarComentario.setDisable(true);
            }
        }
    }

    /**
     * Si la conexion a la base de datos tiene exito, inserta un registro
     * de los resultados de la partida
     * @param nombrejugador Nombre del jugador
     * @param hizolinea Si hizo linea o no
     * @param tiradaslinea Numero de tiradas hasta que ha hecho linea
     * @param hizobingo Si hizo bingo o no
     * @param tiradasbingo Numero de tiradas hasta que ha hecho bingo
     * @return true o false dependiendo de si hay algun problema o no con la BBDD
     */
    public boolean insertRegistro(String nombrejugador,String hizolinea,int tiradaslinea,String hizobingo,int tiradasbingo){
        try{
            // CONEXION A LA BASE DE DATOS. SI HAY ALGUN ERROR, LO CAPTARA LA EXCEPCION
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/devra1?serverTimezone=UTC","devra96","n6CKKs8GUz");
            // PRUEBAS EN CLASE
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bingofx?serverTimezone=UTC","root","root");
            System.out.println("Conexion al servidor establecida correctamente.");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM bingofx";
            ResultSet rs = st.executeQuery(sql);

            rs.moveToInsertRow();
            rs.updateString(2,nombrejugador);
            rs.updateString(3,hizolinea);
            rs.updateInt(4,tiradaslinea);
            rs.updateString(5,hizobingo);
            rs.updateInt(6,tiradasbingo);
            rs.insertRow();

            con.close();
            return true;
        }
        catch(SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("HA OCURRIDO UN ERROR CON LA BASE DE DATOS");
            a.setContentText("Causa del error: " + e.getErrorCode() + " - " + e.getMessage());
            a.showAndWait();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Guarda un comentario escrito en el ultimo registro insertado, en la columna
     * "comentario"
     * @return true o false dependiendo de si hay algun problema o no con la BBDD
     */
    public boolean GuardarComentario(){
        try{
            // CONEXION A LA BASE DE DATOS. SI HAY ALGUN ERROR, LO CAPTARA LA EXCEPCION
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/devra1?serverTimezone=UTC","devra96","n6CKKs8GUz");
            // PRUEBAS EN CLASE
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bingofx?serverTimezone=UTC","root","root");
            System.out.println("Conexion al servidor establecida correctamente.");
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM bingofx";
            ResultSet rs = st.executeQuery(sql);

            rs.last();
            rs.updateString("comentario",txtComentario.getText());
            rs.updateRow();

            con.close();
            return true;
        }
        catch(SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("HA OCURRIDO UN ERROR CON LA BASE DE DATOS");
            a.setContentText("Causa del error: " + e.getErrorCode() + " - " + e.getMessage());
            a.showAndWait();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Boton para enviar el comentario a la BBDD
     * @param event
     */
    @FXML
    void guardarComentario(ActionEvent event) {
        if(txtComentario.getLength() > 200){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Ha ocurrido un error.");
            a.setContentText("El comentario no debe superar los 200 caracteres.");
            a.showAndWait();
        }
        else{
            if(GuardarComentario()){
                txtTestComentario.setText("¡OK!");
                txtComentario.setDisable(true);
                btnGuardarComentario.setDisable(true);
            }
            else{
                txtTestComentario.setTextFill(Color.RED);
                txtTestComentario.setText("¡ERROR!");
            }
        }
    }
}
