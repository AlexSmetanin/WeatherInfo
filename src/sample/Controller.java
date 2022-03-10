package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text pressure;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String userCity = city.getText().trim(); // отримуємо назву міста з форми введення
            if (!userCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + userCity + "&APPID=5d0788ab0be4ac1f063b81f9be16785c&units=metric");
                if (!output.isEmpty()) {
                    JSONObject object = new JSONObject(output);
                    temp_info.setText("Температура: " + object.getJSONObject("main").getDouble("temp"));
                    temp_feels.setText("По-вiдчуттям: " + object.getJSONObject("main").getDouble("feels_like"));
                    temp_max.setText("Максимум: " + object.getJSONObject("main").getDouble("temp_max"));
                    temp_min.setText("Мiнiмум: " + object.getJSONObject("main").getDouble("temp_min"));
                    pressure.setText("Тиск: " + object.getJSONObject("main").getDouble("pressure"));
                }
            }
        });
    }

    private static String getUrlContent(String urlAddress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Таке місто не було знайдено!"); // TODO замінити на вікно-месседж
        }
        return content.toString();
    }
}
