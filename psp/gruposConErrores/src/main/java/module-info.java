module org.example.demojavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static  lombok;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires io.vavr;


    opens org.example.demojavafx to javafx.fxml;
    opens org.example.demojavafx.domain.modelo to com.google.gson;
    opens fxml;

    exports org.example.demojavafx;
    exports org.example.demojavafx.ui;
    opens org.example.demojavafx.ui to javafx.fxml;
}
