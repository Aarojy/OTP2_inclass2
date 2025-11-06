package org.example.otp2_inclass2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class BmiController {

    @FXML private ComboBox<String> languageComboBox;
    @FXML private volatile Button convertButton;

    @FXML private Label heightLabel;
    @FXML private Label weightLabel;
    @FXML private Label bmiLabel;
    @FXML private Label errorLabel;
    @FXML private Label langErrorLabel;
    @FXML private Label timeLabel;

    @FXML private TextField heightField;
    @FXML private TextField weightField;
    @FXML private TextField bmiField;

    private Locale setLocale;

    @FXML
    private void initialize() {
        setLanguage("en", "US");
    }

    private void setLanguage(String language, String Country) {
        langErrorLabel.setVisible(false);
        langErrorLabel.setManaged(false);

        setLocale = new Locale(language, Country);
        Map<String, String> localizedStrings = LocalizationService.getLocalizedStrings(setLocale);

        try {
            timeLabel.setText(displayLocalTime(setLocale));

            languageComboBox.getItems().set(0, localizedStrings.getOrDefault("choiceEnglish", "English"));
            languageComboBox.getItems().set(1, localizedStrings.getOrDefault("choiceUrdu", "Urdu"));
            languageComboBox.getItems().set(2, localizedStrings.getOrDefault("choiceFrench", "French"));
            languageComboBox.getItems().set(3, localizedStrings.getOrDefault("choiceVietnamese", "Vietnamese"));

            convertButton.setText(localizedStrings.getOrDefault("buttonConvert", "Convert"));

            heightLabel.setText(localizedStrings.getOrDefault("labelHeight", "Height"));
            weightLabel.setText(localizedStrings.getOrDefault("labelWeight", "Weight"));
            bmiLabel.setText(localizedStrings.getOrDefault("labelBMI", "Body Mass Index (BMI):"));
            errorLabel.setText(localizedStrings.getOrDefault("labelError", "Please enter valid numeric values for height and weight."));
            langErrorLabel.setText(localizedStrings.getOrDefault("labelLangError", "Language selection error."));

            heightField.setPromptText(localizedStrings.getOrDefault("promptHeight", "Height in meters"));
            weightField.setPromptText(localizedStrings.getOrDefault("promptWeight", "Weight in kilograms"));
            bmiField.setPromptText(localizedStrings.getOrDefault("promptBMI", "Your BMI will be displayed here"));

            Platform.runLater(() -> {
                Stage stage = (Stage) convertButton.getScene().getWindow();
                stage.setTitle(localizedStrings.getOrDefault("appTitle", "Aaro's BMI Calculator"));
            });

        } catch (Exception e) {
            langErrorLabel.setVisible(true);
            langErrorLabel.setManaged(true);
        }
    }

    @FXML
    protected void onLanguageSelected() {
        int language = languageComboBox.getSelectionModel().getSelectedIndex();

        switch (language) {
            case 0 -> setLanguage("en", "US");
            case 1 -> setLanguage("ur", "PK");
            case 2 -> setLanguage("fr", "FR");
            case 3 -> setLanguage("vi", "VN");
        }
    }

    @FXML
    protected void onConvertClick() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            double bmi = calculateBmi(height, weight);
            bmiField.setText(String.format("%.2f", bmi));

            String language = setLocale.getLanguage();
            BMIResultService.saveResult(weight, height, bmi, language);

        } catch (NumberFormatException e) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    public static double calculateBmi(Double height, Double weight) {
        return weight / (height * height);
    }

    public String displayLocalTime(Locale locale) {
        String zoneId;

        switch (locale.getCountry()) {
            case "US":
                zoneId = "America/New_York";
                break;
            case "FR":
                zoneId = "Europe/Paris";
                break;
            case "PK":
                zoneId = "Asia/Karachi";
                break;
            case "VN":
                zoneId = "Asia/Ho_Chi_Minh";
                break;
            default:
                zoneId = "UTC";
        }
        ZonedDateTime zoneDate = ZonedDateTime.now(ZoneId.of(zoneId));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");

        return zoneDate.format(formatter);
    }
}
