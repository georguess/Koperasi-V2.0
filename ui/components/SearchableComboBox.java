package com.koperasi.ui.components;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ComboBox dengan fitur search/filter otomatis.
 * Mengetik di combobox akan memfilter items berdasarkan kecocokan string.
 */
public class SearchableComboBox<T> extends JComboBox<T> {
    
    private List<T> originalItems;
    private JTextField editor;
    private boolean isUpdating = false;
    
    public SearchableComboBox(T[] items) {
        this(Arrays.asList(items));
    }
    
    public SearchableComboBox(List<T> items) {
        super();
        this.originalItems = new ArrayList<>(items);
        
        // Set sebagai editable
        setEditable(true);
        
        // Setup editor
        editor = (JTextField) getEditor().getEditorComponent();
        
        // Tambahkan listener untuk dokumen
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isUpdating) filterItems();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isUpdating) filterItems();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!isUpdating) filterItems();
            }
        });
        
        // Inisial items
        updateModel(originalItems);
    }
    
    private void updateModel(List<T> items) {
        isUpdating = true;
        DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();
        for (T item : items) {
            model.addElement(item);
        }
        setModel(model);
        isUpdating = false;
    }
    
    private void filterItems() {
        String searchText = editor.getText().trim().toLowerCase();
        
        // Jika kosong, tampilkan semua
        if (searchText.isEmpty()) {
            updateModel(originalItems);
            return;
        }
        
        // Filter items berdasarkan search text
        List<T> filtered = new ArrayList<>();
        for (T item : originalItems) {
            if (item.toString().toLowerCase().contains(searchText)) {
                filtered.add(item);
            }
        }
        
        updateModel(filtered);
        
        // Tampilkan dropdown jika ada hasil
        if (getItemCount() > 0) {
            showPopup();
        }
    }
    
    /**
     * Reset semua items ke original list.
     */
    public void resetItems(List<T> items) {
        this.originalItems = new ArrayList<>(items);
        updateModel(originalItems);
    }
}
