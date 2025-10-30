package org.example.otp2_inclass2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class BmiController {

    @FXML private ComboBox<String> languageComboBox;
    @FXML private volatile Button convertButton;

    @FXML private Label heightLabel;
    @FXML private Label weightLabel;
    @FXML private Label bmiLabel;
    @FXML private Label errorLabel;
    @FXML private Label langErrorLabel;

    @FXML private TextField heightField;
    @FXML private TextField weightField;
    @FXML private TextField bmiField;

    @FXML
    private void initialize() {
        setLanguage("en", "US");
    }

    private void setLanguage(String language, String Country) {
        langErrorLabel.setVisible(false);
        langErrorLabel.setManaged(false);

        try {
            Locale locale = new Locale(language, Country);
            ResourceBundle rb = ResourceBundle.getBundle("MessagesBundle", locale);

            languageComboBox.getItems().set(0, rb.getString("choiceEnglish.text"));
            languageComboBox.getItems().set(1, rb.getString("choiceUrdu.text"));
            languageComboBox.getItems().set(2, rb.getString("choiceFrench.text"));
            languageComboBox.getItems().set(3, rb.getString("choiceVietnamese.text"));

            convertButton.setText(rb.getString("buttonConvert.text"));

            heightLabel.setText(rb.getString("labelHeight.text"));
            weightLabel.setText(rb.getString("labelWeight.text"));
            bmiLabel.setText(rb.getString("labelBMI.text"));
            errorLabel.setText(rb.getString("labelError.text"));
            langErrorLabel.setText(rb.getString("labelLangError.text"));

            heightField.setPromptText(rb.getString("promptHeight.text"));
            weightField.setPromptText(rb.getString("promptWeight.text"));
            bmiField.setPromptText(rb.getString("promptBMI.text"));

            Platform.runLater(() -> {
                Stage stage = (Stage) convertButton.getScene().getWindow();
                stage.setTitle(rb.getString("appTitle.text"));
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

        } catch (NumberFormatException e) {
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    public static double calculateBmi(Double height, Double weight) {
        return weight / (height * height);
    }
}
