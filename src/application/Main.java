package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextBoundsType;

public class Main extends Application {

	private TextArea ta;

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
		grid1.add(k1p3, 0, 2);
		grid1.add(k1p2, 1, 2);
		grid1.add(k1p1, 2, 2);

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
		grid2.add(k2p3, 0, 2);
		grid2.add(k2p2, 1, 2);
		grid2.add(k2p1, 2, 2);

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
		grid3.add(k3p3, 0, 2);
		grid3.add(k3p2, 1, 2);
		grid3.add(k3p1, 2, 2);

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

		ta = new TextArea();
		ta.setPrefHeight(600);
		ta.textProperty().addListener(new ChangeListener<Object>() {
		    @Override
		    public void changed(ObservableValue<?> observable, Object oldValue,
		            Object newValue) {
		        ta.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
		        //use Double.MIN_VALUE to scroll to the top
		    }
		});
		
		
		right.getChildren().add(ta);

		vote.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				Greska("-------------------------------");
				int y1, y2, y3, k1, k2, k3, f1, f2, f3;
				int id;
				try {
					y1 = Integer.parseInt(k1p1.getText());
					y2 = Integer.parseInt(k1p2.getText());
					y3 = Integer.parseInt(k1p3.getText());

					k1 = Integer.parseInt(k2p1.getText());
					k2 = Integer.parseInt(k2p2.getText());
					k3 = Integer.parseInt(k2p3.getText());

					f1 = Integer.parseInt(k3p1.getText());
					f2 = Integer.parseInt(k3p2.getText());
					f3 = Integer.parseInt(k3p3.getText());

					id = Integer.parseInt(ID.getText());

				} catch (Exception e) {
					e.printStackTrace();
					Greska("Greska prilikom obrade glasova!\nProverite da li su svi brojevi uneti");
					return;
				}
				
				if(y1==y2 ||y2==y3 || y1==y3 || k1==k2 ||k2==k3 || k1==y3 || f1==f2 ||f2==f3 || f1==f3){
					Greska("Glasanje za isti auto dva puta nije dozvoleno!");
					return;
				}
				if(y1==id || y2==id || y3==id ||k1==id || k2==id || k3==id ||f1==id || f2==id || f3==id){
					Greska("Glasanje za samog sebe nije dozvoljeno!");
					return;
				}
				
				if (y1 > 100 || y2 > 100 || y3 > 100 || y1<0 || y2<0 || y3<0) {
					Greska("Yugo glasovi neispravni");
					return;
				}

				if (k1 > 150 || k2 > 150 || k3 > 150 || k1<100 || k2<100 || k3<100) {
					Greska("101/128 glasovi neispravni");
					return;
				}
				if (f1 < 150 || f2 < 150 || f3 < 150) {
					Greska("Fica glasovi neispravni");
					return;
				}
				if(InsertUBazu(3, y3) && InsertUBazu(2, y2) &&InsertUBazu(1, y1)){
					Greska("Ubaceni podaci za Yugo klasu");
				}
				else{
					Greska("Podaci NISU ubaceni za Yugo klasu");
				}

				if(	InsertUBazu(3, k3) && InsertUBazu(2, k2) && InsertUBazu(1, k1)){
					Greska("Ubaceni podaci za 101/128 klasu");
				}
				else{
					Greska("Podaci NISU ubaceni za Yugo klasu");
				}

				if(InsertUBazu(3, f3) && InsertUBazu(2, f2) && InsertUBazu(1, f1)){
					Greska("Ubaceni podaci za Fica klasu");
				}
				else{
					Greska("Podaci NISU ubaceni za Yugo klasu");
				}
				Greska("Korisnik sa ID-jem "+id+ " je uspesno glasao!");
				
				k1p1.setText("");
				k1p2.setText("");
				k1p3.setText("");
				k2p1.setText("");
				k2p2.setText("");
				k2p3.setText("");
				k3p1.setText("");
				k3p2.setText("");
				k3p3.setText("");
				
				ID.setText("");
				PrikaziRezultate(1);
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
			Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");

			Statement stmt = c
					.createStatement();/*
										 * String sql =
										 * "DROP TABLE GLASANJE; CREATE TABLE GLASANJE "
										 * + "(ID INT PRIMARY KEY     NOT NULL,"
										 * + " BROJ_AUTA      INT    NOT NULL, "
										 * +
										 * " KLASA          INT     NOT NULL, "
										 * + " BROJ_BODOVA    INT )";
										 * stmt.executeUpdate(sql);
										 */
			// stmt.close();
			// c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");

	}

	protected void PrikaziRezultate(int i) {
		// TODO Auto-generated method stub
		
		if(i>3)
			return;
		
		try {
		      Class.forName("org.sqlite.JDBC");
		      Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      Statement stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery( "SELECT * FROM GLASANJE where klasa="+i+";" );
		      int j=0;
		      if(i==1)
		    	  Greska("--------Yugo--------");	
		      else if(i==2)
		    	  Greska("-------101/128------");	
		      else
		    	  Greska("--------Fica--------");	

		      Greska("Auto | Bodovi");
		      while ( rs.next() ) {
		    	 if(j>2)
		    		 break;
		         int  broj_auta = rs.getInt("BROJ_AUTA");
		         int   broj_bodova = rs.getInt("BROJ_BODOVA");
		         
		         Greska(broj_auta+" | "+broj_bodova);
		         ++j;
		      }
		      rs.close();
		      stmt.close();
		      c.close();
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		
		PrikaziRezultate(i+1);
		
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

	private boolean InsertUBazu(int bodovi, int broj_auta) {
		// Ovde ide sql
		try {
			Class.forName("org.sqlite.JDBC");

			Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);

			// prvo select dal postoji, ako postoji update, ako ne onda insert

			Statement stmt = null;
			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM GLASANJE where BROJ_AUTA=" + broj_auta);

			if (rs.next()) {
				System.out.println("Postoji");
				rs.close();
				String sql = "UPDATE GLASANJE set BROJ_BODOVA = BROJ_BODOVA+" + bodovi + " WHERE BROJ_AUTA=" + broj_auta
						+ ";";
				stmt.executeUpdate(sql);
				// c.commit();
			} else {
				rs.close();
				System.out.println("Ne postoji");
				int klasa = 0;
				if (broj_auta < 100)
					klasa = 1;
				else if (broj_auta < 150)
					klasa = 2;
				else if (broj_auta > 150)
					klasa = 3;

				String sql = "INSERT INTO GLASANJE (ID,BROJ_AUTA,KLASA,BROJ_BODOVA) " + "VALUES (" + broj_auta + ", "
						+ broj_auta + "," + klasa + ", " + bodovi + ");";
				stmt.executeUpdate(sql);
				// c.commit();
			}

			stmt.close();
			c.commit();
			c.close();
			return true;
			

		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			Greska("Unos neuspesan: "+e);
			return false;
		}
		

	}

	void Greska(String msg) {
		ta.appendText(msg+"\n");
	}

}
