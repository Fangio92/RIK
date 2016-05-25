package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Main extends Application {

	Connection c = null;
	Statement stmt = null;

	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();
		HBox top = new HBox();
		top.setPadding(new Insets(20, 20, 20, 20));
		top.setSpacing(10);
		TextField ID = new TextField();
		top.getChildren().addAll(new Label("Vas ID:"), ID);

		VBox center = new VBox();
		center.setPadding(new Insets(20, 20, 20, 20));
		center.setSpacing(60);

		Label naslov1 = new Label("Yugo:");
		GridPane grid1 = new GridPane();
		TextField k1p1 = new TextField();
		TextField k1p2 = new TextField();
		TextField k1p3 = new TextField();
		naslov1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid1.setHgap(10);
		grid1.setVgap(10);
		grid1.setPadding(new Insets(0, 10, 0, 10));
		grid1.add(naslov1, 0, 0);
		grid1.add(new Label("Tri poena:"), 0, 1);
		grid1.add(new Label("Dva poena:"), 1, 1);
		grid1.add(new Label("Jedan poen:"), 2, 1);
		grid1.add(k1p1, 0, 2);
		grid1.add(k1p2, 1, 2);
		grid1.add(k1p3, 2, 2);

		Label naslov2 = new Label("101/128:");
		GridPane grid2 = new GridPane();
		TextField k2p1 = new TextField();
		TextField k2p2 = new TextField();
		TextField k2p3 = new TextField();
		naslov2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid2.setHgap(10);
		grid2.setVgap(10);
		grid2.setPadding(new Insets(0, 10, 0, 10));
		grid2.add(naslov2, 0, 0);
		grid2.add(new Label("Tri poena:"), 0, 1);
		grid2.add(new Label("Dva poena:"), 1, 1);
		grid2.add(new Label("Jedan poen:"), 2, 1);
		grid2.add(k2p1, 0, 2);
		grid2.add(k2p2, 1, 2);
		grid2.add(k2p3, 2, 2);

		Label naslov3 = new Label("Fica:");
		GridPane grid3 = new GridPane();
		TextField k3p1 = new TextField();
		TextField k3p2 = new TextField();
		TextField k3p3 = new TextField();
		naslov3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid3.setHgap(10);
		grid3.setVgap(10);
		grid3.setPadding(new Insets(0, 10, 0, 10));
		grid3.add(naslov3, 0, 0);
		grid3.add(new Label("Tri poena:"), 0, 1);
		grid3.add(new Label("Dva poena:"), 1, 1);
		grid3.add(new Label("Jedan poen:"), 2, 1);
		grid3.add(k3p1, 0, 2);
		grid3.add(k3p2, 1, 2);
		grid3.add(k3p3, 2, 2);

		Button vote = new Button("Glasaj");
		vote.setPrefSize(60, 40);
		center.getChildren().addAll(grid1, grid2, grid3, vote);

		VBox right = new VBox();

		right.setPrefWidth(300);
		right.setSpacing(20);
		right.setAlignment(Pos.TOP_LEFT);
		final Separator separator = new Separator();
		separator.setStyle(" -fx-background-color: #e79423;-fx-background-radius: 2;");

		right.getChildren().addAll(AddDeveloper("Slobodan", "Zivancevic", "zivancevic.slobodan@gmail.com"), separator,
				AddDeveloper("Damir", "Dizdarevic", "dizdarevic@ymail.com"));

		vote.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				// Ovde ide sql
				try {
					Class.forName("org.sqlite.JDBC");

					c = DriverManager.getConnection("jdbc:sqlite:test.db");
				      c.setAutoCommit(false);

					//prvo select dal postoji, ako postoji update, ako ne onda insert
					
					 ResultSet rs = stmt.executeQuery( "SELECT * FROM GLASANJE where BROJ_AUTA=4");
					 stmt = c.createStatement();
					 
					 if(rs.next()){
						 System.out.println("Postoji");
						 rs.close();
					      String sql = "UPDATE GLASANJE set BROJ_BODOVA = BROJ_BODOVA+3 WHERE BROJ_AUTA=4;";
					      stmt.executeUpdate(sql);
					      //c.commit();						 
					 }
					 else{
						 rs.close();
						 System.out.println("Ne postoji");
						 String sql = "INSERT INTO GLASANJE (ID,BROJ_AUTA,KLASA,BROJ_BODOVA) " +
				                   "VALUES (3,4,1,12);"; 
						 stmt.executeUpdate(sql);
						 //c.commit();
					 }				      
					
					stmt.close();
					c.commit();
					c.close();
					
					
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				

			}
		});

		root.setTop(top);
		root.setCenter(center);
		root.setRight(right);

		Scene scene = new Scene(root, 600, 600);
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");

			stmt = c.createStatement();/*
			String sql = "DROP TABLE GLASANJE; CREATE TABLE GLASANJE " + "(ID INT PRIMARY KEY     NOT NULL,"
					+ " BROJ_AUTA      INT    NOT NULL, " + " KLASA          INT     NOT NULL, "
					+ " BROJ_BODOVA    INT )";
			stmt.executeUpdate(sql);*/
			//stmt.close();
			//c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");

	}

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * public GridPane AddCategory(String naslov) { Label a = new Label(naslov);
	 * GridPane grid = new GridPane(); TextField p1 = new TextField(); TextField
	 * p2 = new TextField(); TextField p3 = new TextField();
	 * a.setFont(Font.font("Arial", FontWeight.BOLD, 20)); grid.setHgap(10);
	 * grid.setVgap(10); grid.setPadding(new Insets(0, 10, 0, 10)); grid.add(a,
	 * 0, 0); grid.add(new Label("Jedan poen:"), 0, 1); grid.add(new Label(
	 * "Dva poena:"), 1, 1); grid.add(new Label("Tri poena:"), 2, 1);
	 * grid.add(p1, 0, 2); grid.add(p2, 1, 2); grid.add(p3, 2, 2); return grid;
	 * }
	 */

	public GridPane AddDeveloper(String ime, String prezime, String email) {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));
		String style = new String("-fx-font-weight: bold;");
		Label a = new Label("Ime:");
		a.setStyle(style);
		Label b = new Label("Prezime:");
		b.setStyle(style);
		Label c = new Label("e-mail:");
		c.setStyle(style);

		grid.add(a, 0, 0);
		grid.add(new Label(ime), 1, 0);
		grid.add(b, 0, 1);
		grid.add(new Label(prezime), 1, 1);
		grid.add(c, 0, 2);
		grid.add(new Label(email), 1, 2);

		return grid;
	}

}
