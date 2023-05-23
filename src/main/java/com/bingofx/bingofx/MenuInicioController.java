package com.bingofx.bingofx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class MenuInicioController {

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnVerRecords;

    @FXML
    private Button btndosjugadores;

    @FXML
    private Button btnunjugador;

    @FXML
    void JugarUnJugador(ActionEvent event) {
        cerrarVentana(event);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuUnJugador.fxml"));

        try {
            Parent root = fxmlLoader.load();
            MenuUnJugadorController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("UN JUGADOR");
            stage.setScene(scene);
            stage.setResizable(false); //IMPEDIR QUE SE PUEDA MODIFICAR LA RESOLUCION DE LA VENTANA
            stage.show();

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void JugarDosJugadores(ActionEvent event) {
        cerrarVentana(event);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuDosJugadores.fxml"));

        try {
            Parent root = fxmlLoader.load();
            MenuDosJugadoresController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("DOS JUGADORES");
            stage.setScene(scene);
            stage.setResizable(false); //IMPEDIR QUE SE PUEDA MODIFICAR LA RESOLUCION DE LA VENTANA
            stage.show();

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void VerRecords(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MenuRecords.fxml"));

        try {
            Parent root = fxmlLoader.load();
            MenuRecordsController controlador = fxmlLoader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("RECORDS");
            stage.setScene(scene);
            stage.setResizable(false); //IMPEDIR QUE SE PUEDA MODIFICAR LA RESOLUCION DE LA VENTANA
            stage.show();

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Salir(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Salir del juego");
        a.setContentText("¿Quieres salir?");
        Optional<ButtonType> r = a.showAndWait();
        if(r.get() == ButtonType.OK){
            System.exit(0);
//            Platform.exit();
        }
    }

    /**
     * Metodo para cerrar la ventana donde este el boton que se ha pulsado
     * @param event
     */
    public static void cerrarVentana(ActionEvent event){
        Node source = (Node) event.getSource();     //Me devuelve el elemento al que hice click
        Stage stage = (Stage) source.getScene().getWindow();    //Me devuelve la ventana donde se encuentra el elemento
        stage.close();                          //Me cierra la ventana
    }

    /**
     * Ver la lista de records sin usar FXML ni con botones de filtrado
     */
    public static void VerRecordsSinFxml(){
        try{
            // CONEXION A LA BASE DE DATOS. SI HAY ALGUN ERROR, LO CAPTARA LA EXCEPCION
            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/devra1?serverTimezone=UTC","devra96","n6CKKs8GUz");
            // PRUEBAS EN CLASE
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bingofx?serverTimezone=UTC","root","root");
            System.out.println("Conexion al servidor establecida correctamente.");
            Statement st = con.createStatement();
            String sql = "SELECT * FROM bingofx";
            ResultSet rs = st.executeQuery(sql);

            TableView tabla = new TableView();
            TableColumn colnumpartida = new TableColumn("PARTIDA");
            TableColumn colnombrejugador = new TableColumn("NOMBRE JUGADOR");
            TableColumn coltiradaslinea = new TableColumn("TIRADAS LINEA");
            TableColumn coltiradasbingo = new TableColumn("TIRADAS BINGO");

            colnumpartida.setCellValueFactory(new PropertyValueFactory<>("numpartida"));
            colnumpartida.setPrefWidth(100);
            colnombrejugador.setCellValueFactory(new PropertyValueFactory<>("nombrejugador"));
            colnombrejugador.setPrefWidth(150);
            coltiradaslinea.setCellValueFactory(new PropertyValueFactory<>("tiradaslinea"));
            coltiradaslinea.setPrefWidth(100);
            coltiradasbingo.setCellValueFactory(new PropertyValueFactory<>("tiradasbingo"));
            coltiradasbingo.setPrefWidth(100);
//            coluid.setPrefWidth(76.0);
//            colunombre.setPrefWidth(155.0);

            tabla.getColumns().addAll(colnumpartida, colnombrejugador,coltiradaslinea,coltiradasbingo);

            ObservableList<Registro> registros = FXCollections.observableArrayList();
            tabla.setItems(registros);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(tabla);
            vBox.setPrefWidth(452.0);
            vBox.setPrefHeight(267.5);
//        vBox.setPadding(new Insets(20));
//        vBox.setSpacing(20);

            Stage stage = new Stage();
            Scene scene = new Scene(vBox);
            stage.setScene(scene);
            stage.setResizable(false); //IMPEDIR QUE SE PUEDA MODIFICAR LA RESOLUCION DE LA VENTANA
            stage.setTitle("RECORDS");
            stage.show();

            while (rs.next()){
                int numpartida = rs.getInt("numpartida");
                String nombrejugador = rs.getString("nombrejugador");
                String tiradaslinea = rs.getString("tiradaslinea");
                String tiradasbingo = rs.getString("tiradasbingo");
                Registro r = new Registro(numpartida,nombrejugador,tiradaslinea,tiradasbingo);
                registros.add(r);
            }
            con.close();
        }
        catch(SQLException e){
//            System.out.println("Error en la consulta: " + e.getErrorCode() + " - " + e.getMessage());

            Label error = new Label("HA OCURRIDO UN ERROR CON LA BASE DE DATOS\n\nCausa del error: " + e.getErrorCode() + " - " + e.getMessage());
            Insets pad = new Insets(20);
            error.setPadding(pad);
//            error.setLayoutX(37);
//            error.setLayoutY(108);

            Pane p = new Pane();
            p.getChildren().addAll(error);
            p.prefHeight(232.0);
            p.prefWidth(348.0);
            Stage stage = new Stage();
            Scene scene = new Scene(p);
            stage.setScene(scene);
            stage.setResizable(false); //IMPEDIR QUE SE PUEDA MODIFICAR LA RESOLUCION DE LA VENTANA
            stage.setTitle("ERROR");
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}


