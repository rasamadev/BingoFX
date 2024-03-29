package com.bingofx.bingofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MenuDosJugadoresController {

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnJugar;

    @FXML
    private TextField txtNombreUno;

    @FXML
    private TextField txtNombreDos;

    //VARIABLES ESTATICAS QUE GUARDAN LOS NOMBRES DEL JUGADOR
    static String nombrejugador1;
    static String nombrejugador2;

    //OBJETO PARA LLAMAR A METODOS PARA CAMBIAR DE PANTALLA
    private Pantalla pantalla = new Pantalla();

    /**
     * Comprueba si se han escrito los nombres correctamente y lleva
     * a la pantalla de juego
     * @param event
     */
    @FXML
    void JugarPartida(ActionEvent event) {
        if(txtNombreUno.getText().equals("") || txtNombreDos.getText().equals("")){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Ha ocurrido un error.");
            a.setContentText("Ambos jugadores debeis escribir vuestro nombre para continuar.");
            a.showAndWait();
        }
        else if(txtNombreUno.getLength() > 20 || txtNombreDos.getLength() > 20){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("ERROR");
            a.setHeaderText("Ha ocurrido un error.");
            a.setContentText("Ambos nombres no deben superar los 20 caracteres.");
            a.showAndWait();
        }
        else{
            nombrejugador1 = txtNombreUno.getText();
            nombrejugador2 = txtNombreDos.getText();
            pantalla.CerrarVentanaActual();
            pantalla.IrJuegoDosJugadores();
        }
    }

    /**
     * Cancelar y volver al menu principal
     * @param event
     */
    @FXML
    void Cancelar(ActionEvent event) {
        pantalla.CerrarVentanaActual();
        pantalla.IrMenuInicio();
    }

}