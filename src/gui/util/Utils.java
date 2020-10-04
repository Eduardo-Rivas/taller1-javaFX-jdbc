package gui.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class Utils {

	// --Método Statico para tomar la Pantalla Padre--//
	public static Stage actualStage(ActionEvent evento) {
		return (Stage) ((Node) evento.getSource()).getScene().getWindow();
	}

	// --Método para Convertir a Entero--//
	public static Integer convertirEntero(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}

	}
 
	// --Método para Fromatear la Date en TableView--//
	public static <T> void formatTableColumnDate(TableColumn<T, Date> tableColumn, String format) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(format);
				
				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} 
					else {
						setText(sdf.format(item));
					} 
				}
				
			};
			return cell;
		});
		
	}//--Fin--//

	// --Método para Fromatear Double en TableView--//
	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} 
					else {
						Locale.setDefault(Locale.US);
						setText(String.format("%."+decimalPlaces+"f", item));
					}
				}
				
			};
			return cell;
		});	
	}//--Fin--//
	

}
