module GregFastCheck {
	exports Test.GregFastCheck;
	opens Test.GregFastCheck to javafx.fxml;

	requires java.sql;
	requires org.jsoup;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
}