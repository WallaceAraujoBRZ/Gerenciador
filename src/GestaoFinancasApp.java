import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.time.LocalDate;

public class GestaoFinancasApp extends Application {
    private Conta conta = new Conta();
    private ObservableList<Transacao> transacoesObservableList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestão de Finanças Pessoais");

        Label saldoLabel = new Label("Saldo: R$ 0.00");
        Button adicionarTransacaoButton = new Button("Adicionar Transação");

        TableView<Transacao> transacoesTable = new TableView<>();
        TableColumn<Transacao, Double> valorColumn = new TableColumn<>("Valor");
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));
        valorColumn.setCellFactory(new Callback<TableColumn<Transacao, Double>, TableCell<Transacao, Double>>() {
            @Override
            public TableCell<Transacao, Double> call(TableColumn<Transacao, Double> param) {
                return new TableCell<Transacao, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(String.format("R$ %.2f", item));
                            Transacao transacao = getTableView().getItems().get(getIndex());
                            if (transacao.getTipo().equals("receita")) {
                                setTextFill(javafx.scene.paint.Color.GREEN);
                            } else {
                                setTextFill(javafx.scene.paint.Color.RED);
                            }
                        }
                    }
                };
            }
        });

        TableColumn<Transacao, String> descricaoColumn = new TableColumn<>("Descrição");
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        TableColumn<Transacao, LocalDate> dataColumn = new TableColumn<>("Data");
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        TableColumn<Transacao, String> tipoColumn = new TableColumn<>("Tipo");
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        transacoesTable.getColumns().addAll(valorColumn, descricaoColumn, dataColumn, tipoColumn);
        transacoesTable.setItems(transacoesObservableList);

        adicionarTransacaoButton.setOnAction(e -> {
            // Janela para adicionar transação
            Stage transacaoStage = new Stage();
            transacaoStage.setTitle("Adicionar Transação");

            VBox transacaoLayout = new VBox(10);
            transacaoLayout.setAlignment(Pos.CENTER);
            TextField valorField = new TextField();
            valorField.setPromptText("Valor");
            TextField descricaoField = new TextField();
            descricaoField.setPromptText("Descrição");
            ComboBox<String> tipoComboBox = new ComboBox<>();
            tipoComboBox.getItems().addAll("receita", "despesa");
            tipoComboBox.setValue("receita");
            Button salvarButton = new Button("Salvar");

            salvarButton.setOnAction(event -> {
                try {
                    double valor = Double.parseDouble(valorField.getText());
                    String descricao = descricaoField.getText();
                    String tipo = tipoComboBox.getValue();
                    Transacao transacao = new Transacao(valor, descricao, LocalDate.now(), tipo);
                    conta.adicionarTransacao(transacao);
                    transacoesObservableList.add(transacao);
                    saldoLabel.setText("Saldo: R$ " + conta.getSaldo());
                    transacaoStage.close();
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro de Formato");
                    alert.setHeaderText("Valor Inválido");
                    alert.setContentText("Por favor, insira um valor numérico válido.");
                    alert.showAndWait();
                }
            });

            transacaoLayout.getChildren().addAll(new Label("Valor:"), valorField, new Label("Descrição:"), descricaoField, new Label("Tipo:"), tipoComboBox, salvarButton);
            Scene transacaoScene = new Scene(transacaoLayout, 300, 200);
            transacaoStage.setScene(transacaoScene);
            transacaoStage.show();
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(saldoLabel, adicionarTransacaoButton, transacoesTable);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
