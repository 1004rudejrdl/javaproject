package Controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Model.Modeling;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PosController implements Initializable{
	public Stage posStage;
	
	@FXML private Tab posPageOrderList;
	@FXML private Tab posPageSales;
	@FXML private Tab posPageInventory;
	@FXML private Tab posPageInventoryOrder;
	@FXML private Label posLbOrderList;
	@FXML private Label posLbSales;
	@FXML private Label posLbInventory;
	@FXML private Label posLbInventoryOrder;
	@FXML private TabPane posTabPane;
	@FXML private TextField posInventoryTxtInventoryNo;
	@FXML private TextField posInventoryTxtProduct;
	@FXML private TextField posInventoryTxtNumber;
	@FXML private TextField posTextMoney;
	@FXML private TextField posOrderListTxtTime;
	@FXML private TextField posSalesTxtTime;
	@FXML private DatePicker posInventoryDatePicker;
	@FXML private Button posInventoryBtnRegister;
	@FXML private Button posInventoryBtnModify;
	@FXML private Button posInventoryBtnRemove;
	@FXML private Button posOrderRefresh;
	@FXML private Button posInventoryRefresh; 
	@FXML private LineChart<?, ?> posLinechart;
	
	@FXML private TableView<Modeling> posOrderTableView;
	@FXML private TableView<Modeling> posSalesTableView;
	@FXML private TableView<Modeling> posInventoryTableView;
	
	private Modeling select;
	private int selectIndex;
	private int count=0;
	
	
	ObservableList<Modeling> orderList = FXCollections.observableArrayList();
	ObservableList<Modeling> inventoryList = FXCollections.observableArrayList();
	
	ArrayList<Modeling> dbArrayList;
	ArrayList<Modeling> dbInventoryArrayList;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//dbInventoryArrayList.clear();
		//���ڷ� ����
		setTextFieldInputFormat();
		//�ǹ�ư����
		posLbOrderList.setOnMouseClicked(arg0 ->{handlePosPageOrderListAction();});
		posLbSales.setOnMouseClicked(arg0 ->{handleposPosPageSalesAction();});
		posLbInventory.setOnMouseClicked(arg0 ->{handlePosPageInventoryAction();});
		//�ֹ����̺�似��
		orderTableView();
		//�ֹ�â ���ΰ�ħ
		posOrderRefresh.setOnAction((ActionEvent event)->{handlePosOrderRefreshAction();});
		//�������̺�似��
		salesTableView();
		//������̺�似��
		inventoryTableView();
		//���â ���ΰ�ħ
		posInventoryRefresh.setOnAction((ActionEvent event)->{handlePosInventoryRefreshAction();});
		//���â�� ��ư����
		posInventoryBtnRegister.setOnAction((ActionEvent event)->{handlePosInventoryBtnRegisterAction();});
		posInventoryTableView.setOnMouseClicked((e)->{handlePosInventoryBtnModifyAction(e);});
		posInventoryBtnModify.setOnAction((ActionEvent event)->{handlePosInventoryBtnModifyAction();});
		posInventoryBtnRemove.setOnAction((ActionEvent event)->{handlePosInventoryBtnRemoveAction();});
		//��Ʈ
		chart();
		//�հ�
		total();
		//�ð�
		clock();
	}


	private void setTextFieldInputFormat() {
		inputDecimalFormat(posInventoryTxtInventoryNo);
		inputDecimalFormat(posInventoryTxtNumber);
	}

	private void handlePosPageOrderListAction() {
		SingleSelectionModel<Tab> selectionModel= posTabPane.getSelectionModel();
		
		selectionModel.select(posPageOrderList);		
	}
	private void handleposPosPageSalesAction() {
		SingleSelectionModel<Tab> selectionModel= posTabPane.getSelectionModel();
		
		selectionModel.select(posPageSales);			
	}
	private void handlePosPageInventoryAction() {
		SingleSelectionModel<Tab> selectionModel= posTabPane.getSelectionModel();
		
		selectionModel.select(posPageInventory);			
	}

	
	//�������̺�
	private void orderTableView() {
		
		TableColumn tcReceiptNo=posOrderTableView.getColumns().get(0);
		tcReceiptNo.setCellValueFactory(new PropertyValueFactory<>("receiptNo"));
		tcReceiptNo.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcThatDay=posOrderTableView.getColumns().get(1);
		tcThatDay.setCellValueFactory(new PropertyValueFactory<>("thatDay"));
		tcThatDay.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcThatTime=posOrderTableView.getColumns().get(2);
		tcThatTime.setCellValueFactory(new PropertyValueFactory<>("thatTime"));
		tcThatTime.setStyle("-fx-alignment: CENTER;");

		TableColumn tcInStore=posOrderTableView.getColumns().get(3);
		tcInStore.setCellValueFactory(new PropertyValueFactory<>("inStore"));
		tcInStore.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcFood=posOrderTableView.getColumns().get(4);
		tcFood.setCellValueFactory(new PropertyValueFactory<>("food"));
		tcFood.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcPrice=posOrderTableView.getColumns().get(5);
		tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tcPrice.setStyle("-fx-alignment: CENTER;");	
		
		dbArrayList=projectDAO.getOrderDBData();
		
		for(Modeling modeling : dbArrayList) {
			Modeling order = new Modeling(
					modeling.getThatDay(),
					modeling.getThatTime(),
					modeling.getReceiptNo(),
					modeling.getInStore(),
					modeling.getFood(),
					modeling.getPrice());
			orderList.add(order);
		}
		posOrderTableView.setItems(orderList);
		
	}
	//�ֹ�â ���ΰ�ħ��ư
	private void handlePosOrderRefreshAction() {
		
		  dbArrayList.clear();
		  orderList.clear();
		  dbArrayList=projectDAO.getOrderDBData();
			
			for(Modeling modeling : dbArrayList) {
				Modeling order = new Modeling(
						modeling.getThatDay(),
						modeling.getThatTime(),
						modeling.getReceiptNo(),
						modeling.getInStore(),
						modeling.getFood(),
						modeling.getPrice());
				orderList.add(order);
			}
			posOrderTableView.setItems(orderList);
	}

	//���������̺�
	private void salesTableView() {
		
	}
	//������̺�
	private void inventoryTableView() {
			
		TableColumn tcProductNo=posInventoryTableView.getColumns().get(0);
		tcProductNo.setCellValueFactory(new PropertyValueFactory<>("productNo"));
		tcProductNo.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcProduct=posInventoryTableView.getColumns().get(1);
		tcProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
		tcProduct.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcProductNumber=posInventoryTableView.getColumns().get(2);
		tcProductNumber.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
		tcProductNumber.setStyle("-fx-alignment: CENTER;");
		
		TableColumn tcExpirationDate=posInventoryTableView.getColumns().get(3);
		tcExpirationDate.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
		tcExpirationDate.setStyle("-fx-alignment: CENTER;");
		
		dbInventoryArrayList=projectDAO.getInventoryDBData();
		
		for(Modeling modeling : dbInventoryArrayList) {
			Modeling inventory = new Modeling(
					modeling.getProductNo(),
					modeling.getProduct(),
					modeling.getProductNumber(),
					modeling.getExpirationDate());
			inventoryList.add(inventory);
		}
		posInventoryTableView.setItems(inventoryList);
	}
	//��� ���ΰ�ħ
	private void handlePosInventoryRefreshAction() {	

		dbInventoryArrayList.clear();
		inventoryList.clear();
		dbInventoryArrayList=projectDAO.getInventoryDBData();
		for(Modeling modeling : dbInventoryArrayList) {
			Modeling inventory = new Modeling(
					modeling.getProductNo(),
					modeling.getProduct(),
					modeling.getProductNumber(),
					modeling.getExpirationDate());
			inventoryList.add(inventory);
		}
		posInventoryTableView.setItems(inventoryList);
	}
	//���â�� �Է��� ��Ϲ�ư ������
	private void handlePosInventoryBtnRegisterAction() {
		try {
		Modeling modeling=new Modeling(Integer.parseInt(posInventoryTxtInventoryNo.getText()),
				posInventoryTxtProduct.getText(),
				Integer.parseInt(posInventoryTxtNumber.getText()),
				posInventoryDatePicker.getValue().toString());
		
		
		
		inventoryList.add(modeling);
		int count=projectDAO.insertInventoryData(modeling);
		if(count!=0) {
			callAlert("�Է¼��� : �����ͺ��̽��� �Է��� �����Ǿ����ϴ�");
		}
		posInventoryTxtInventoryNo.clear();
		posInventoryTxtNumber.clear();
		posInventoryTxtProduct.clear();
		posInventoryDatePicker.getEditor().clear();
		}catch(NumberFormatException e) {
			callAlert("����� �ֽ��ϴ�. : ��� �Է����ּ���");
		}catch(NullPointerException e1) {
			callAlert("����� �ֽ��ϴ�. : ��� �Է����ּ���");
			return;
		}
	}
	//���â ���̺��Ŭ���Ҷ����� ������ �ؽ�Ʈ�ʵ�� �ҷ����� �Լ�
	private void handlePosInventoryBtnModifyAction(MouseEvent e) {
		try {
		select=posInventoryTableView.getSelectionModel().getSelectedItem();
		selectIndex=posInventoryTableView.getSelectionModel().getSelectedIndex();
		
		if(e.getClickCount()==1) {
			
			posInventoryTxtInventoryNo.setText(String.valueOf(select.getProductNo()));
			posInventoryTxtProduct.setText(select.getProduct());
			posInventoryTxtNumber.setText(String.valueOf(select.getProductNumber()));
			posInventoryDatePicker.setValue(LocalDate.parse(select.getExpirationDate()));
			}
		}catch(NullPointerException e1) {}
	}
	//���â ����
	private void handlePosInventoryBtnModifyAction() {
		try {
		Modeling inventory=new Modeling(Integer.parseInt(posInventoryTxtInventoryNo.getText()),
				posInventoryTxtProduct.getText(),
				Integer.parseInt(posInventoryTxtNumber.getText()),
				posInventoryDatePicker.getValue().toString());
		
		int count=projectDAO.inventoryModify(inventory);
		if(count!=0) {
			inventoryList.remove(selectIndex);
			inventoryList.add(selectIndex, inventory);
			int arrayIndex=dbInventoryArrayList.indexOf(select);
			dbInventoryArrayList.set(arrayIndex+1, inventory);
			
			posInventoryTxtInventoryNo.clear();
			posInventoryTxtNumber.clear();
			posInventoryTxtProduct.clear();
			posInventoryDatePicker.getEditor().clear();
			callAlert("�����Ϸ� : "+select.getProduct()+"�� ������ �Ϸ�Ǿ����ϴ�");
		}else {
			return;
		}
		}catch(NumberFormatException e) {
			callAlert("����� �ֽ��ϴ�. : ��� �Է����ּ���");
		}catch(NullPointerException e1) {
			callAlert("����� �ֽ��ϴ�. : ��� �Է����ּ���");
		}
	}
	
	//���â ����
	private void handlePosInventoryBtnRemoveAction() {	
		
		int count=projectDAO.deleteInventoryData(select.getProductNo());
		if(count !=0) {
		inventoryList.remove(selectIndex);
		
		inventoryList.remove(select);

		
		posInventoryTxtInventoryNo.clear();
		posInventoryTxtNumber.clear();
		posInventoryTxtProduct.clear();
		posInventoryDatePicker.getEditor().clear();
		
		callAlert("�����Ϸ�: "+select.getProduct()+"�� ������ �Ϸ�Ǿ����ϴ�");
		}else {
			return;	
			}
	}

	private void inputDecimalFormat(TextField textField) {
		// ���ڸ� �Է�(������ �Է¹���), �Ǽ��Է¹ް������ new DecimalFormat("###.#");
		DecimalFormat format = new DecimalFormat("######");
		// ���� �Է½� ���� ���� �̺�Ʈ ó��
		textField.setTextFormatter(new TextFormatter<>(event -> {
			// �Է¹��� ������ ������ �̺�Ʈ�� ������.
			if (event.getControlNewText().isEmpty()) {
				return event;
			}
			// ������ �м��� ���� ��ġ�� ������.
			ParsePosition parsePosition = new ParsePosition(0);
			// �Է¹��� ����� �м���ġ�� �������������� format ����� ��ġ���� �м���.
			Object object = format.parse(event.getControlNewText(), parsePosition);
			// ���ϰ��� null �̰ų�, �Է��ѱ��̿� �����м���ġ���� ���������(�� �м������������� ����) �ų�, �Է��ѱ��̰� 4�̸�(3�ڸ��� �Ѿ�����
			// ����.) �̸� null ������.
			if (object == null || parsePosition.getIndex() < event.getControlNewText().length()
					|| event.getControlNewText().length() == 7) {
				return null;
			} else {
				return event;
			}
		}));
	}
	//��Ʈ
	private void chart() {
		XYChart.Series series=new XYChart.Series<>();
		
		ArrayList<Modeling> dbChartArrayList=projectDAO.getChartData();

		for(Modeling chart : dbChartArrayList) {
			String thatday=chart.getThatDay();
			int price=chart.getPrice();
			
		series.getData().add(new XYChart.Data(thatday,price));
		}
	
		posLinechart.getData().add(series);
	}

	//�հ�
	private void total() {
		int str=0;
		List<Modeling> list = projectDAO.getTotalPrice();
		for(Modeling mo : list) {
			str = mo.getPrice();
		}
		posTextMoney.setText(String.valueOf(str));
		
		
	}
	
	private void clock() {
		Task<Void> task = new Task<Void>() {

	         @Override
	         protected Void call() throws Exception {
	            try {
	               count = 0;
	               while (true) {
	                  count++;
	                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	                  String strDate = sdf.format(new Date());
	                  Platform.runLater(() -> {
	                	  posOrderListTxtTime.setText(strDate);
	                	  posSalesTxtTime.setText(strDate);
	                	  
	                  });
	                  Thread.sleep(1000);
	               }
	            } catch (InterruptedException e) {
	               Platform.runLater(() -> {
	                  callAlert("���� : ����");
	               });
	            }
	            return null;
	         }
	      };
	      Thread thread = new Thread(task);
	      thread.setDaemon(true);
	      thread.start();
	   }
	
	private void callAlert(String contentText) {		//�˸�â(�߰��� : �� �����ٰ�,,//ex."�������� :  ���� ����� �Է����ּ���)
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(contentText.substring(0,contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":")+1));
		alert.showAndWait();
	}
}
