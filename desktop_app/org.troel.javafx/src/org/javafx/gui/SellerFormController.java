package org.javafx.gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.javafx.gui.listeners.DataChangeListener;
import org.javafx.gui.utils.Alerts;
import org.javafx.gui.utils.Constraints;
import org.javafx.gui.utils.Utils;
import org.javafx.model.exceptions.ValidationException;
import org.jdbc.db.DBExeption;
import org.jdbc.model.entities.Department;
import org.jdbc.model.entities.Seller;
import org.jdbc.services.DepartmentService;
import org.jdbc.services.SellerService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class SellerFormController implements Initializable {

	private Seller entity;
	
	private SellerService service;
	
	private DepartmentService deptService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	private ObservableList<Department> obsList;
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtEmail;
	
	@FXML
	private DatePicker dpBirthDate;
	
	@FXML
	private TextField txtBaseSalary;
	
	@FXML
	private ComboBox<Department> comboBoxDepartment;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Label labelErrorEmail;
	
	@FXML
	private Label labelErrorBirthDate;
	
	@FXML
	private Label labelErrorBaseSalary;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch(DBExeption e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		} catch(ValidationException e) {
			setErrorsMsg(e.getErrors());
		}
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	public void setServices(SellerService service, DepartmentService deptService) {
		this.service = service;
		this.deptService = deptService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 70);
		Constraints.setTextFieldDouble(txtBaseSalary);
		Constraints.setTextFieldMaxLength(txtEmail, 60);
		Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
		initializeComboBoxDepartment();
	}
	
	public void updateFormData() {
		if(entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtEmail.setText(entity.getEmail());
		Locale.setDefault(Locale.US);
		txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
		if(entity.getBirthDate() != null) {
			dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
		}
		if(entity.getDepartment() == null) {
			comboBoxDepartment.getSelectionModel().selectFirst();
		} else {
			comboBoxDepartment.setValue(entity.getDepartment());
		}
	}
	
	private Seller getFormData() {
		Seller seller = new Seller();
		ValidationException e = new ValidationException("Validation Error");
		
		seller.setId(Utils.tryParseToInt(txtId.getText()));
		
		if(txtName.getText() == null || txtName.getText().isEmpty()) {
			e.addError("Name", "Field can't be empty!");
		}
		seller.setName(txtName.getText());
		
		if(txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
			e.addError("Email", "Field can't be empty!");
		}
		seller.setEmail(txtEmail.getText());
		
		if(dpBirthDate.getValue() == null) {
			e.addError("BirthDate", "Field can't be empty!");
		}else {
			Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
			seller.setBirthDate(Date.from(instant));
		}
		
		if(txtBaseSalary.getText() == null || txtBaseSalary.getText().isEmpty()) {
			e.addError("BaseSalary", "Field can't be empty!");
		}
		seller.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));
		
		seller.setDepartment(comboBoxDepartment.getValue());
		
		if(e.getErrors().size() > 0) {
			throw e;
		}
		
		return seller;
	}
	
	private void notifyDataChangeListeners() {
		dataChangeListeners.forEach(x -> x.onDataChanged());
	}
	
	private void setErrorsMsg(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		labelErrorName.setText(fields.contains("Name") ? errors.get("Name") : "");
		labelErrorEmail.setText(fields.contains("Email") ? errors.get("Email") : "");
		labelErrorBaseSalary.setText(fields.contains("BaseSalary") ? errors.get("BaseSalary") : "");
		labelErrorBirthDate.setText(fields.contains("BirthDate") ? errors.get("BirthDate") : "");
	}
	
	public void loadAssociatedObjects() {
		if(deptService == null) {
			throw new IllegalStateException("Department Service was null");
		}
		List<Department> depts = deptService.findAll();
		obsList = FXCollections.observableArrayList(depts);
		comboBoxDepartment.setItems(obsList);
	}
	
	private void initializeComboBoxDepartment() {
		Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
			@Override
			protected void updateItem(Department item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : item.getName());
			}
		};
		comboBoxDepartment.setCellFactory(factory);
		comboBoxDepartment.setButtonCell(factory.call(null));
	}
}
