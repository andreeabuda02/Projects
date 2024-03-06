package start;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

public class Reflection<T> {
    /**
     * Această metodă completează tabela specificată cu datele din lista furnizată.
     *
     * @param tabela Tabela în care vor fi afișate datele.
     * @param lista  Lista de obiecte care conține datele.
     */
    public void completeazaTabel(JTable tabela, List<T> lista) {
        if(lista.size()>0){
            DefaultTableModel tabelaModel = new DefaultTableModel();
            for (Field field : lista.get(0).getClass().getDeclaredFields()) {
                String fieldName = field.getName();
                tabelaModel.addColumn(fieldName);
            }
            for (T element:lista) {
                Vector vector=new Vector<>();
                for (Field field : element.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        vector.add(field.get(element));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                tabelaModel.addRow(vector);
            }
            tabela.setModel(tabelaModel);
        }

    }

    /**
     * Această metodă populează un ComboBox cu identificatorii din lista furnizată.
     *
     * @param comboBox ComboBox-ul în care vor fi afișați identificatorii.
     * @param lista    Lista de obiecte care conține identificatorii.
     */
    public void idTabele(JComboBox<String> comboBox, List<T> lista){
        comboBox.removeAllItems();
        comboBox.addItem("Select id:");
        for (T element:lista) {
            for (Field field : element.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    comboBox.addItem(String.valueOf(field.get(element)));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
        comboBox.setSelectedIndex(0);
    }
}
