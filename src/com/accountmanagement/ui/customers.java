/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountmanagement.ui;

import com.accountmanagement.models.AccountMovement;
import com.accountmanagement.models.Currency;
import com.accountmanagement.models.Customer;
import com.accountmanagement.models.CustomerBalance;
import com.accountmanagement.models.CustomerBuilder;
import com.accountmanagement.models.IncomingDocument;
import com.accountmanagement.models.OutgoingDocument;
import com.accountmanagement.repositories.accountmovement.AccountMovementSqliteRepository;
import com.accountmanagement.repositories.currency.CurrencySqliteRepository;
import com.accountmanagement.repositories.customer.CustomerSqliteRepository;
import com.accountmanagement.repositories.incomingdocument.IncomingDocumentSqliteRepository;
import com.accountmanagement.repositories.outgoingdocument.OutgoingDocumentSqliteRepository;
import com.accountmanagement.utils.Constants;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author asdf
 */
public class customers extends javax.swing.JPanel {
    
    
    CustomerSqliteRepository customerRepo = new CustomerSqliteRepository();
    CurrencySqliteRepository currencyRepo = new CurrencySqliteRepository();
    IncomingDocumentSqliteRepository incomingDocumentRepo = new IncomingDocumentSqliteRepository();
    OutgoingDocumentSqliteRepository outgoingDocumentRepo = new OutgoingDocumentSqliteRepository();
    AccountMovementSqliteRepository accountMovementRepo = new AccountMovementSqliteRepository();
    
    
    
    DecimalFormat numberFormater =  new DecimalFormat("#,###,###.##");
    String reportsPath = Constants.REPORTS_PATH;
    
    
    /**
     * Creates new form customers
     */
    public customers() {
        initComponents();
       // Set Tabed pane RTL 
       jTabbedPane1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       tbCustomers.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       tbCurrency.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       tbIncomingDocuments.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       tbOutgoingDocuments.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       
       
        // set customer table width
        tbCustomers.getColumnModel().getColumn(0).setMaxWidth(150);
        
        // set Currency table col width
        tbCurrency.getColumnModel().getColumn(0).setMaxWidth(150);
        
        // set IncomingDocument table col width
        tbIncomingDocuments.getColumnModel().getColumn(0).setMaxWidth(150);
        
        // set Outgoing table col width
        tbOutgoingDocuments.getColumnModel().getColumn(0).setMaxWidth(150);
        
       // set tables headers center
        DefaultTableCellRenderer customerTableCellRenderer = (DefaultTableCellRenderer) tbCustomers.getTableHeader().getDefaultRenderer();
        customerTableCellRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer centerStringRenderer = (DefaultTableCellRenderer) tbCustomers.getDefaultRenderer(String.class);
        centerStringRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer currencyTableHeaderRenderer = (DefaultTableCellRenderer) tbCurrency.getTableHeader().getDefaultRenderer();
        currencyTableHeaderRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer currencyTableRenderer = (DefaultTableCellRenderer) tbCurrency.getDefaultRenderer(String.class);
        currencyTableRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer incomingDocumentTableHeaderRenderer = (DefaultTableCellRenderer) tbIncomingDocuments.getTableHeader().getDefaultRenderer();
        incomingDocumentTableHeaderRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer incomingDocumentTableRenderer = (DefaultTableCellRenderer) tbIncomingDocuments.getDefaultRenderer(String.class);
        incomingDocumentTableRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer outgoingDocumentsTableRenderer = (DefaultTableCellRenderer) tbOutgoingDocuments.getDefaultRenderer(String.class);
        outgoingDocumentsTableRenderer.setHorizontalAlignment(0);

        
       // set tables data 
       setCustomerTableData();
       setCurrencyTableData();
       setIncomingDocumentTableData();
       setOutgoingDocumentTableData();
       
      // customer table selection listener
      
      tbCustomers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(tbCustomers.getSelectedRow() > -1) {
                    setSelectedCustomerDataToTextFields();
                }
                
            }
        });  
      
      // currency table selection listener
      
      tbCurrency.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(tbCurrency.getSelectedRow() > -1) {
                    setSelectedCurrencyDataToTextFields();
                }
            }

            private void setSelectedCurrencyDataToTextFields() {
                try {
                    int row = tbCurrency.getSelectedRow();
                    
                    txtCurrencyId.setText(String.valueOf(tbCurrency.getValueAt(row, 0)));
                    txtCurrencyName.setText(String.valueOf(tbCurrency.getValueAt(row, 1)));
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, e, "Error", 0);
                }
            }
        });
      
      // incoming document table selection listener
      
      tbIncomingDocuments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(tbIncomingDocuments.getSelectedRow() > -1) {
                    setSelectedIncomigDocumentDatatoTextFields();
                }
                
                
            }
        });
      
      // outgoing document table selection listener
      
      tbOutgoingDocuments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(tbOutgoingDocuments.getSelectedRow() > -1) {
                    setSelectedOutgoingDocumentDatatoTextFields();
                }
                
                
            }

            private void setSelectedOutgoingDocumentDatatoTextFields() {
                try {
                    int row = tbOutgoingDocuments.getSelectedRow();
                    
                    txtIdOutgoingDocument.setText(tbOutgoingDocuments.getValueAt(row, 0).toString());
                    txtDateOutgoingDocument.setDate(new Date(tbOutgoingDocuments.getValueAt(row, 1).toString()));
                    cbCutomerNameOutgoingDocument.setSelectedItem(tbOutgoingDocuments.getValueAt(row, 2).toString());
                    cbCurrenyNameOutgoingDocument.setSelectedItem(tbOutgoingDocuments.getValueAt(row, 3).toString());
                    txtValueOutgoingDocument.setText(tbOutgoingDocuments.getValueAt(row, 4).toString());
                    txtCommentOutgoingDocument.setText(tbOutgoingDocuments.getValueAt(row, 5).toString());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, e, "Error", 0);
                }
            }
        });
      
      // set tbIncomingDocuments sort by id 
      
      tbIncomingDocuments.setAutoCreateRowSorter(true);
      
      // set IncomingDocument customer name combobox items
      
      setIncomingDocumentCustomerNameCbItems();
      
      // set IncomingDocument currency name combobox items
      
      setIncomingDocumentCurrencyNameCbItems();
      
      // set tbIncomingDocuments sort by id 
      
      tbOutgoingDocuments.setAutoCreateRowSorter(true);
      
      // set IncomingDocument customer name combobox items
      
      setOutgoingDocumentCustomerNameCbItems();
      
      // set IncomingDocument currency name combobox items
      
      setOutgoingDocumentCurrencyNameCbItems();
      
      // set reports customer cb data
      
      setCbCustomerNameReportsUIData();
      
      // set reports currency cb data
      
      setCbCurrencyNameReportsUIData();
      
      // autocompelete IncomingDocument customer name combobox
      
        AutoCompleteDecorator.decorate(cbCutomerNameIncomingDocument);
        
      // autocompelete IncomingDocument currency name combobox
        
        AutoCompleteDecorator.decorate(cbCurrenyNameIncomingDocument);
        
      // autocompelete OutgoingDocument customer name combobox
      
        AutoCompleteDecorator.decorate(cbCutomerNameOutgoingDocument);
        
      // autocompelete OutgoingDocument currency name combobox
        
        AutoCompleteDecorator.decorate(cbCurrenyNameOutgoingDocument);
        
      // autocompelete Reports customer name combobox
      
        AutoCompleteDecorator.decorate(cbCustomerNameReportsUI);
        
      // autocompelete Reports currency name combobox
      
        AutoCompleteDecorator.decorate(cbCurrencyNameReportsUI);
        
      // set incoming document date and text fields
      clearIncomingDocumentTextFields();
      
      // set incoming document date and text fields
      clearOutgoingDocumentTextFields();
      
    }
    
    private void setCbCustomerNameReportsUIData() {
        try {
            ArrayList<Customer> list = customerRepo.findAll();
            
            cbCustomerNameReportsUI.removeAllItems();
            
            for (Customer customer : list) {
                cbCustomerNameReportsUI.addItem(customer.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }
    
    private void setCbCurrencyNameReportsUIData() {
        try {
            ArrayList<Currency> list = currencyRepo.findAll();
            
            cbCurrencyNameReportsUI.removeAllItems();
            
            for (Currency currency : list) {
                cbCurrencyNameReportsUI.addItem(currency.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }
   
    void setSelectedIncomigDocumentDatatoTextFields() {
        int row = tbIncomingDocuments.getSelectedRow();
        
        txtIdIncomingDocument.setText(tbIncomingDocuments.getValueAt(row, 0).toString());
        txtDateIncomingDocument.setDate(new Date((String) tbIncomingDocuments.getValueAt(row, 1)));
        cbCutomerNameIncomingDocument.setSelectedItem(tbIncomingDocuments.getValueAt(row, 2));
        cbCurrenyNameIncomingDocument.setSelectedItem(tbIncomingDocuments.getValueAt(row, 3));
        txtValueIncomingDocument.setText(tbIncomingDocuments.getValueAt(row, 4).toString());
        txtCommentIncomingDocument.setText(tbIncomingDocuments.getValueAt(row, 5).toString());
    }
    
    private void setSelectedCustomerDataToTextFields() {
        try {
            int row = tbCustomers.getSelectedRow();

            Customer customer = new CustomerBuilder()
            .id((int) tbCustomers.getValueAt(row, 0))
            .name( (String) tbCustomers.getValueAt(row, 1))
            .phone((String) tbCustomers.getValueAt(row, 2))
                            .build();
            txtCustomerId.setText(String.valueOf(customer.getId()));
            txtCustomerName.setText(customer.getName());
            txtCustomerPhone.setText(customer.getPhone());
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }
            
    
    private void setCustomerTableData() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbCustomers.getModel();
            model.setRowCount(0);

            ArrayList<Customer> list = customerRepo.findAll();
            
            
            for(Customer customer : list) {
                Vector vector = new Vector();
                vector.add(customer.getId());
                vector.add(customer.getName());
                vector.add(customer.getPhone());
                
//                System.out.println(customer.getName());
                model.addRow(vector);
            }
            
            tbCustomers.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "خطأ", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setIncomingDocumentTableData() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbIncomingDocuments.getModel();
            model.setRowCount(0);
            
            ArrayList<IncomingDocument> list = incomingDocumentRepo.findAllDesc();
            
            
            for (IncomingDocument incomingDocument : list) {
                Vector vector = new Vector();
                vector.add(incomingDocument.getId());
                vector.add(incomingDocument.getDate());
                Currency currency = currencyRepo.findById(incomingDocument.getCurrencyId());
                Customer customer = customerRepo.findById(incomingDocument.getCustomerId());
                vector.add(customer.getName());
                vector.add(currency.getName());
                vector.add(numberFormater.format(incomingDocument.getValue()));
                vector.add(incomingDocument.getComment());
                
                model.addRow(vector);
            }
            tbIncomingDocuments.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "خطأ", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void clearCustomerTextFields(){
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerPhone.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCustomerPhone = new javax.swing.JTextField();
        btnCustomerSave = new javax.swing.JButton();
        btnCustomerEdit = new javax.swing.JButton();
        btnCustomerDelete = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbCustomersStatus = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCustomerId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCustomerNameSearch = new javax.swing.JTextField();
        btnClearCutomerSelection = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCustomers = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCurrencyName = new javax.swing.JTextField();
        btnSaveCurrency = new javax.swing.JButton();
        btnEditCurrency = new javax.swing.JButton();
        btnDeleteCurrency = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCurrency = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lbCurrencyStatus = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCurrencyId = new javax.swing.JTextField();
        btnClearCurrencySelection = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtDateIncomingDocument = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        cbCutomerNameIncomingDocument = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cbCurrenyNameIncomingDocument = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txtValueIncomingDocument = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCommentIncomingDocument = new javax.swing.JTextField();
        btnSaveIncomingDocument = new javax.swing.JButton();
        btnEditIncomingDocument = new javax.swing.JButton();
        btnDeleteIncomingDocument = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtIdIncomingDocument = new javax.swing.JTextField();
        btnClearIncomingDocumentTextFields = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txtSearchIdIncomingDocument = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        lbIncomingDocumentStatus = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbIncomingDocuments = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtIdOutgoingDocument = new javax.swing.JTextField();
        btnClearOutgoingDocumentTextFields = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtSearchIdOutgoingDocument = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtDateOutgoingDocument = new com.toedter.calendar.JDateChooser();
        jLabel18 = new javax.swing.JLabel();
        cbCutomerNameOutgoingDocument = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        cbCurrenyNameOutgoingDocument = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        txtValueOutgoingDocument = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtCommentOutgoingDocument = new javax.swing.JTextField();
        btnSaveOutgoingDocument = new javax.swing.JButton();
        btnEditOutgoingDocument = new javax.swing.JButton();
        btnDeleteOutgoingDocument = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        lbOutgoingDocumentStatus = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbOutgoingDocuments = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        btnPrintCustomerBalanceReport = new javax.swing.JButton();
        btnPrintTotalCustomerBalanceReport = new javax.swing.JButton();
        cbCustomerNameReportsUI = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        cbCurrencyNameReportsUI = new javax.swing.JComboBox<>();
        panelReport = new javax.swing.JPanel();

        jTabbedPane1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel2.setText("اسم العميل : ");

        txtCustomerName.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtCustomerName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCustomerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomerNameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel3.setText("رقم الهاتف :");

        txtCustomerPhone.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtCustomerPhone.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCustomerPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomerPhoneActionPerformed(evt);
            }
        });

        btnCustomerSave.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnCustomerSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/save.png"))); // NOI18N
        btnCustomerSave.setText("حفظ");
        btnCustomerSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerSaveActionPerformed(evt);
            }
        });

        btnCustomerEdit.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnCustomerEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/edit.png"))); // NOI18N
        btnCustomerEdit.setText("تعديل");
        btnCustomerEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerEditActionPerformed(evt);
            }
        });

        btnCustomerDelete.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnCustomerDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/delete.png"))); // NOI18N
        btnCustomerDelete.setText("حذف");
        btnCustomerDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomerDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnCustomerSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCustomerPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(btnCustomerEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCustomerDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCustomerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCustomerSave, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCustomerEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCustomerDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("شريط الحالة :");

        lbCustomersStatus.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbCustomersStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbCustomersStatus))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel5.setText("رقم العميل :");

        txtCustomerId.setEditable(false);
        txtCustomerId.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtCustomerId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCustomerId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomerIdActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel6.setText("اسم العميل :");

        txtCustomerNameSearch.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtCustomerNameSearch.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCustomerNameSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustomerNameSearchActionPerformed(evt);
            }
        });
        txtCustomerNameSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCustomerNameSearchKeyReleased(evt);
            }
        });

        btnClearCutomerSelection.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnClearCutomerSelection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/clear.png"))); // NOI18N
        btnClearCutomerSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearCutomerSelectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(btnClearCutomerSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCustomerNameSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnClearCutomerSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCustomerNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbCustomers.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tbCustomers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم", "اسم العميل", "رقم الهاتف"
            }
        ));
        tbCustomers.setRowHeight(30);
        jScrollPane1.setViewportView(tbCustomers);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("العملاء", jPanel1);

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel4.setText("العملات : ");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel7.setText("اسم العملة :");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCurrencyName.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtCurrencyName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnSaveCurrency.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnSaveCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/save.png"))); // NOI18N
        btnSaveCurrency.setText("حفظ");
        btnSaveCurrency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveCurrencyActionPerformed(evt);
            }
        });

        btnEditCurrency.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnEditCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/edit.png"))); // NOI18N
        btnEditCurrency.setText("تعديل");
        btnEditCurrency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditCurrencyActionPerformed(evt);
            }
        });

        btnDeleteCurrency.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnDeleteCurrency.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/delete.png"))); // NOI18N
        btnDeleteCurrency.setText("حذف");
        btnDeleteCurrency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCurrencyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDeleteCurrency, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditCurrency, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(btnSaveCurrency, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCurrencyName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCurrencyName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteCurrency, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbCurrency.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tbCurrency.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم", "اسم العملة"
            }
        ));
        tbCurrency.setRowHeight(30);
        jScrollPane2.setViewportView(tbCurrency);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel8.setText("شريط الحالة :");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lbCurrencyStatus.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbCurrencyStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbCurrencyStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbCurrencyStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbCurrencyStatus))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel9.setText("رقم العملة :");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        txtCurrencyId.setEditable(false);
        txtCurrencyId.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtCurrencyId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCurrencyId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurrencyIdActionPerformed(evt);
            }
        });

        btnClearCurrencySelection.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnClearCurrencySelection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/clear.png"))); // NOI18N
        btnClearCurrencySelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearCurrencySelectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClearCurrencySelection, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCurrencyId, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCurrencyId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearCurrencySelection, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("العملات", jPanel4);

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel12.setForeground(new java.awt.Color(0, 204, 0));

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 0));
        jLabel10.setText("سندات القبض :");

        txtDateIncomingDocument.setDateFormatString("dd-MMMM-yyyy");

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("العملة :");

        cbCutomerNameIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("اسم العميل :");

        cbCurrenyNameIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("المبلغ :");

        txtValueIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtValueIncomingDocument.setForeground(new java.awt.Color(0, 204, 51));
        txtValueIncomingDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValueIncomingDocument.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValueIncomingDocumentKeyReleased(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("البيان :");

        txtCommentIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtCommentIncomingDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnSaveIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnSaveIncomingDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/save.png"))); // NOI18N
        btnSaveIncomingDocument.setText("حفظ");
        btnSaveIncomingDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveIncomingDocumentActionPerformed(evt);
            }
        });

        btnEditIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnEditIncomingDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/edit.png"))); // NOI18N
        btnEditIncomingDocument.setText("تعديل");
        btnEditIncomingDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditIncomingDocumentActionPerformed(evt);
            }
        });

        btnDeleteIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnDeleteIncomingDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/delete.png"))); // NOI18N
        btnDeleteIncomingDocument.setText("حذف");
        btnDeleteIncomingDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteIncomingDocumentActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("التاريخ :");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCommentIncomingDocument)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(cbCurrenyNameIncomingDocument, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(txtValueIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDateIncomingDocument, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbCutomerNameIncomingDocument, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDateIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCutomerNameIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCurrenyNameIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValueIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCommentIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("رقم السند :");

        txtIdIncomingDocument.setEditable(false);
        txtIdIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtIdIncomingDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnClearIncomingDocumentTextFields.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnClearIncomingDocumentTextFields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/clear.png"))); // NOI18N
        btnClearIncomingDocumentTextFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearIncomingDocumentTextFieldsActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("رقم السند :");

        txtSearchIdIncomingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtSearchIdIncomingDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSearchIdIncomingDocument.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchIdIncomingDocumentKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(btnClearIncomingDocumentTextFields, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                        .addComponent(txtSearchIdIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearIncomingDocumentTextFields, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchIdIncomingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("شريط الحالة :");

        lbIncomingDocumentStatus.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbIncomingDocumentStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbIncomingDocumentStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(lbIncomingDocumentStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbIncomingDocuments.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tbIncomingDocuments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم", "التاريخ", "اسم العميل", "العملة", "القيمة", "البيان"
            }
        ));
        tbIncomingDocuments.setRowHeight(30);
        jScrollPane3.setViewportView(tbIncomingDocuments);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("سند قبض", jPanel2);

        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel22.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("رقم السند :");

        txtIdOutgoingDocument.setEditable(false);
        txtIdOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtIdOutgoingDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnClearOutgoingDocumentTextFields.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnClearOutgoingDocumentTextFields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/clear.png"))); // NOI18N
        btnClearOutgoingDocumentTextFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearOutgoingDocumentTextFieldsActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("رقم السند :");

        txtSearchIdOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtSearchIdOutgoingDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSearchIdOutgoingDocument.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchIdOutgoingDocumentKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(btnClearOutgoingDocumentTextFields, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIdOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addComponent(txtSearchIdOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClearOutgoingDocumentTextFields, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearchIdOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel18.setForeground(new java.awt.Color(0, 204, 0));

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 51, 0));
        jLabel11.setText("سندات الصرف :");

        txtDateOutgoingDocument.setDateFormatString("dd-MMMM-yyyy");

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("العملة :");

        cbCutomerNameOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("اسم العميل :");

        cbCurrenyNameOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("المبلغ :");

        txtValueOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtValueOutgoingDocument.setForeground(new java.awt.Color(255, 0, 0));
        txtValueOutgoingDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValueOutgoingDocument.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValueOutgoingDocumentKeyReleased(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("البيان :");

        txtCommentOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtCommentOutgoingDocument.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        btnSaveOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnSaveOutgoingDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/save.png"))); // NOI18N
        btnSaveOutgoingDocument.setText("حفظ");
        btnSaveOutgoingDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveOutgoingDocumentActionPerformed(evt);
            }
        });

        btnEditOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnEditOutgoingDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/edit.png"))); // NOI18N
        btnEditOutgoingDocument.setText("تعديل");
        btnEditOutgoingDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOutgoingDocumentActionPerformed(evt);
            }
        });

        btnDeleteOutgoingDocument.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnDeleteOutgoingDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/delete.png"))); // NOI18N
        btnDeleteOutgoingDocument.setText("حذف");
        btnDeleteOutgoingDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOutgoingDocumentActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("التاريخ :");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCommentOutgoingDocument)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(cbCurrenyNameOutgoingDocument, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(txtValueOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel11)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDateOutgoingDocument, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbCutomerNameOutgoingDocument, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSaveOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDateOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCutomerNameOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCurrenyNameOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValueOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCommentOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteOutgoingDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel27.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("شريط الحالة :");

        lbOutgoingDocumentStatus.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbOutgoingDocumentStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbOutgoingDocumentStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(lbOutgoingDocumentStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbOutgoingDocuments.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tbOutgoingDocuments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم", "التاريخ", "اسم العميل", "العملة", "القيمة", "البيان"
            }
        ));
        tbOutgoingDocuments.setRowHeight(30);
        jScrollPane4.setViewportView(tbOutgoingDocuments);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("سند صرف", jPanel3);

        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel28.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel28.setText("العملات : ");

        jLabel29.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel29.setText("اسم العميل :");
        jLabel29.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btnPrintCustomerBalanceReport.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnPrintCustomerBalanceReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/print.png"))); // NOI18N
        btnPrintCustomerBalanceReport.setText("طباعة");
        btnPrintCustomerBalanceReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintCustomerBalanceReportActionPerformed(evt);
            }
        });

        btnPrintTotalCustomerBalanceReport.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnPrintTotalCustomerBalanceReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/total-report.png"))); // NOI18N
        btnPrintTotalCustomerBalanceReport.setText("طباعة تقرير اجمالي");
        btnPrintTotalCustomerBalanceReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintTotalCustomerBalanceReportActionPerformed(evt);
            }
        });

        cbCustomerNameReportsUI.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel30.setText("العملة :");
        jLabel30.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cbCurrencyNameReportsUI.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnPrintTotalCustomerBalanceReport, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                            .addComponent(btnPrintCustomerBalanceReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbCustomerNameReportsUI, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addComponent(cbCurrencyNameReportsUI, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cbCustomerNameReportsUI, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(cbCurrencyNameReportsUI, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrintCustomerBalanceReport, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrintTotalCustomerBalanceReport, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(331, Short.MAX_VALUE))
        );

        panelReport.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelReportLayout = new javax.swing.GroupLayout(panelReport);
        panelReport.setLayout(panelReportLayout);
        panelReportLayout.setHorizontalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
        );
        panelReportLayout.setVerticalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("كشف حساب العميل", jPanel21);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtCustomerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerNameActionPerformed

    private void txtCustomerPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerPhoneActionPerformed

    private void btnCustomerSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerSaveActionPerformed
        try {
            String name = txtCustomerName.getText();
            String phone = txtCustomerPhone.getText();
            
            if(name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل اسم العميل", "خطأ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل رقم هاتف العميل", "خطأ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Customer customer = new CustomerBuilder()
                                        .name(name)
                                        .phone(phone)
                                        .build();
            
            if(customerRepo.save(customer)) {
                lbCustomersStatus.setText("تم إضافة العميل : " + customer.getName());
                setCustomerTableData();
                clearCustomerTextFields();
                setIncomingDocumentCustomerNameCbItems();
                setOutgoingDocumentCustomerNameCbItems();
                setCbCustomerNameReportsUIData();
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "خطأ", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCustomerSaveActionPerformed

    private void btnCustomerEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerEditActionPerformed
        try {
            int id = Integer.valueOf(txtCustomerId.getText());
            String name = txtCustomerName.getText();
            String phone = txtCustomerPhone.getText();
            
            if(name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل جميع بيانات العميل");
                return;
            }
            
//            System.out.println("id: "+ id);
            Customer newCustomer = new CustomerBuilder()
                    .id(id)
                    .name(name)
                    .phone(phone)
                    .build();
            
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد تعديل العميل رقم " + newCustomer.getId()+ " ؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION){
                if(customerRepo.update(newCustomer)){
                lbCustomersStatus.setText("تم تعديل العميل رقم " + newCustomer.getId() + " بنجاح");
                setCustomerTableData();
                setIncomingDocumentCustomerNameCbItems();
                setOutgoingDocumentCustomerNameCbItems();
                setCbCustomerNameReportsUIData();
            }
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "خطأ", 0);
        }
    }//GEN-LAST:event_btnCustomerEditActionPerformed

    private void btnCustomerDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerDeleteActionPerformed
        try {
            
            if(txtCustomerId.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "قم بإختيار العميل اولا");
                return;
            }
            
            int id = Integer.valueOf(txtCustomerId.getText());

            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حذف العميل رقم " + id + " ؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            ArrayList<AccountMovement> accountMovementList = accountMovementRepo.findAll();
            
            // check if customer have an account movement
            
            for (AccountMovement accountMovement : accountMovementList) {
                if(id == accountMovement.getCustomerId()) {
                    JOptionPane.showMessageDialog(null, "لا يمكن حذف عميل لديه حركة سندات", "خطأ", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if(result == JOptionPane.YES_OPTION){
                if(customerRepo.delete(id)){
                    lbCustomersStatus.setText("تم حذف العميل رقم " + id);
                    clearCustomerTextFields();
                    setCustomerTableData();
                    setIncomingDocumentCustomerNameCbItems();
                    setOutgoingDocumentCustomerNameCbItems();
                    setCbCustomerNameReportsUIData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnCustomerDeleteActionPerformed

    private void txtCustomerIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerIdActionPerformed

    private void txtCustomerNameSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustomerNameSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustomerNameSearchActionPerformed

    private void btnClearCutomerSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearCutomerSelectionActionPerformed
        clearCustomerTextFields();
    }//GEN-LAST:event_btnClearCutomerSelectionActionPerformed

    private void txtCustomerNameSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCustomerNameSearchKeyReleased
        try {
            String searchWords = txtCustomerNameSearch.getText();
            

            
            ArrayList<Customer> list = customerRepo.findBySearchWords(searchWords);
            

            
            DefaultTableModel model = (DefaultTableModel) tbCustomers.getModel();
            model.setRowCount(0);
            
            for(Customer customer : list) {
                Vector vector = new Vector();
                vector.add(customer.getId());
                vector.add(customer.getName());
                vector.add(customer.getPhone());
                
                model.addRow(vector);
            }
            
            tbCustomers.setModel(model);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_txtCustomerNameSearchKeyReleased

    private void txtCurrencyIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurrencyIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurrencyIdActionPerformed

    private void btnClearCurrencySelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearCurrencySelectionActionPerformed
        clearCurrencyTextFields();
    }//GEN-LAST:event_btnClearCurrencySelectionActionPerformed

    private void btnSaveCurrencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveCurrencyActionPerformed
        try {
            String name = txtCurrencyName.getText();
            
            if(name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "الرجاء ادخال اسم العملة اولا");
                return;
            }
            
            Currency currency = Currency.builder()
                    .name(name)
                    .build();
            
            if(currencyRepo.save(currency)) {
                lbCurrencyStatus.setText("تم إضافة العملة " + currency.getName());
                setCurrencyTableData();
                setIncomingDocumentCurrencyNameCbItems();
                setOutgoingDocumentCurrencyNameCbItems();
                clearCurrencyTextFields();
                setCbCurrencyNameReportsUIData();
            }
        } catch (Exception e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnSaveCurrencyActionPerformed

    private void btnEditCurrencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditCurrencyActionPerformed
        try {
            if(txtCurrencyId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "الرجاء اختيار العملة المراد تعديلها اولا");
                return;
            }
            
            if(txtCurrencyName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل اسم العملة الجديد");
                return;
            }
            
            
            int id = Integer.valueOf(txtCurrencyId.getText());
            String name = txtCurrencyName.getText();
            
            Currency newCurrency = Currency.builder()
                    .id(id)
                    .name(name)
                    .build();
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد تعديل العملة رقم " + newCurrency.getId(), "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION) {
                if(currencyRepo.update(newCurrency)) {
                    lbCurrencyStatus.setText("تم تعديل العملة رقم " + newCurrency.getId());
                    setCurrencyTableData();
                    clearCurrencyTextFields();
                    setIncomingDocumentCurrencyNameCbItems();
                    setOutgoingDocumentCurrencyNameCbItems();
                    setCbCurrencyNameReportsUIData();
                }   
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnEditCurrencyActionPerformed

    private void btnDeleteCurrencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCurrencyActionPerformed
        try {
            if(txtCurrencyId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "اختر العملة اولا");
                return;
            }
            
            int id = Integer.valueOf(txtCurrencyId.getText());
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حذف العملة رقم " + id, "تأكيد", JOptionPane.YES_NO_OPTION);
            
            // check if the currency have an account movement
            
            ArrayList<AccountMovement> accountMovementList = accountMovementRepo.findAll();
            
            for (AccountMovement accountMovement : accountMovementList) {
                if(id == accountMovement.getCurrencyId()) {
                    JOptionPane.showMessageDialog(null, "لا يمكن حذف عملة لديها حركة سندات", "خطأ", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if(result == JOptionPane.YES_OPTION) {
                if(currencyRepo.delete(id)){
                    lbCurrencyStatus.setText("تم حذف العملة رقم " + id);
                    setCurrencyTableData();
                    clearCurrencyTextFields();
                    setIncomingDocumentCurrencyNameCbItems();
                    setOutgoingDocumentCurrencyNameCbItems();
                    setCbCurrencyNameReportsUIData();
                }
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnDeleteCurrencyActionPerformed

    private void btnEditIncomingDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditIncomingDocumentActionPerformed
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            
            String date = df.format(txtDateIncomingDocument.getDate());
            double value = Double.valueOf(txtValueIncomingDocument.getText());
            String comment = txtCommentIncomingDocument.getText();
            int incomingDocumentId = Integer.valueOf(txtIdIncomingDocument.getText());
            
            
            HashMap<String, Integer> cutomersMap = getCustomersMap();
            HashMap<String, Integer> currencyMap = getCurrencyMap();
            
            int currencyId = currencyMap.get(cbCurrenyNameIncomingDocument.getSelectedItem().toString());
            int customerId = cutomersMap.get(cbCutomerNameIncomingDocument.getSelectedItem().toString());
            
//            System.out.println(currencyId);
//            System.out.println(customerId);
            
            IncomingDocument incomingDocument = IncomingDocument.builder()
                    .id(incomingDocumentId)
                    .date(date)
                    .currencyId(currencyId)
                    .customerId(customerId)
                    .value(value)
                    .comment(comment)
                    .build();
            
            // Data validation
            
            if(txtIdIncomingDocument.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "اختر سند للتعديل");
                return;
            }
            
            if(date.isEmpty()){
                JOptionPane.showMessageDialog(null, "اختر التاريخ اولا");
                return;
            }
            
            if(value <= 0) {
                JOptionPane.showMessageDialog(null, "ادخل قيمة عددية صحيحة");
                return;
            }
            
            if(comment.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل البيان");
                return;
            }
            
            if(currencyId <= 0) {
                JOptionPane.showMessageDialog(null, "اختر العملة");
                return;
            }
            
            if(customerId <= 0) {
                JOptionPane.showMessageDialog(null, "اختر العميل");
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد تعديل هذا السند ؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION) {
                if(incomingDocumentRepo.update(incomingDocument)) {
                    lbIncomingDocumentStatus.setText("تم تعديل سند قبض رقم :" + " " + incomingDocumentId);

                    // add account movement
                    AccountMovement accountMovement = AccountMovement.builder()
                            .date(date)
                            .customerId(customerId)
                            .currencyId(currencyId)
                            .incomingDocumentId(incomingDocumentId)
                            .outgoingDocumentId(0)
                            .incomingValue(value)
                            .outgoingValue(0)
                            .comment(comment)
                            .build();

                    accountMovementRepo.update(accountMovement);
                    setIncomingDocumentTableData();
                    clearIncomingDocumentTextFields();
                }
            }                      
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnEditIncomingDocumentActionPerformed

    private void btnClearIncomingDocumentTextFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearIncomingDocumentTextFieldsActionPerformed
        clearIncomingDocumentTextFields();
    }//GEN-LAST:event_btnClearIncomingDocumentTextFieldsActionPerformed

    private void btnSaveIncomingDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveIncomingDocumentActionPerformed
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            
            String date = df.format(txtDateIncomingDocument.getDate());
            double value = Double.valueOf(txtValueIncomingDocument.getText());
            String comment = txtCommentIncomingDocument.getText();
            
            
            HashMap<String, Integer> cutomersMap = getCustomersMap();
            HashMap<String, Integer> currencyMap = getCurrencyMap();
            
            int currencyId = currencyMap.get(cbCurrenyNameIncomingDocument.getSelectedItem().toString());
            int customerId = cutomersMap.get(cbCutomerNameIncomingDocument.getSelectedItem().toString());
            
//            System.out.println(currencyId);
//            System.out.println(customerId);
            
            IncomingDocument incomingDocument = IncomingDocument.builder()
                    .date(date)
                    .currencyId(currencyId)
                    .customerId(customerId)
                    .value(value)
                    .comment(comment)
                    .build();
            
            // Data validation
            
            if(date.isEmpty()){
                JOptionPane.showMessageDialog(null, "اختر التاريخ اولا");
                return;
            }
            
            if(value <= 0) {
                JOptionPane.showMessageDialog(null, "ادخل قيمة عددية صحيحة");
                return;
            }
            
            if(comment.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل البيان");
                return;
            }
            
            if(currencyId <= 0) {
                JOptionPane.showMessageDialog(null, "اختر العملة");
                return;
            }
            
            if(customerId <= 0) {
                JOptionPane.showMessageDialog(null, "اختر العميل");
                return;
            }
            
            int generatedKey = incomingDocumentRepo.save(incomingDocument);
            
            System.out.println(generatedKey);
            if(generatedKey > 0 ) {
                lbIncomingDocumentStatus.setText("تم اضافة سند قبض رقم :" + " " + generatedKey);
                
                // add account movement
                AccountMovement accountMovement = AccountMovement.builder()
                        .date(date)
                        .customerId(customerId)
                        .currencyId(currencyId)
                        .incomingDocumentId(generatedKey)
                        .outgoingDocumentId(0)
                        .incomingValue(value)
                        .outgoingValue(0)
                        .comment(comment)
                        .build();
                
                accountMovementRepo.save(accountMovement);
                setIncomingDocumentTableData();
                clearIncomingDocumentTextFields();
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnSaveIncomingDocumentActionPerformed

    private void btnDeleteIncomingDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteIncomingDocumentActionPerformed
        try {
            if(txtIdIncomingDocument.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "اختر السند الذي تريد حذفه");
            }
            
            long incomingDocumentId = Integer.valueOf(txtIdIncomingDocument.getText());
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حذف السند رقم : " + incomingDocumentId , "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION) {
                if(incomingDocumentRepo.delete(incomingDocumentId)) {
                    lbIncomingDocumentStatus.setText("تم حذف السند رقم : " + incomingDocumentId);
                    accountMovementRepo.delete(incomingDocumentId, 0);
                    clearIncomingDocumentTextFields();
                    setIncomingDocumentTableData();
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnDeleteIncomingDocumentActionPerformed

    private void txtSearchIdIncomingDocumentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchIdIncomingDocumentKeyReleased
        filterTbIncomingDocumentById();
    }//GEN-LAST:event_txtSearchIdIncomingDocumentKeyReleased

    private void txtValueIncomingDocumentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValueIncomingDocumentKeyReleased
        
    }//GEN-LAST:event_txtValueIncomingDocumentKeyReleased

    private void btnClearOutgoingDocumentTextFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearOutgoingDocumentTextFieldsActionPerformed
        clearOutgoingDocumentTextFields();
    }//GEN-LAST:event_btnClearOutgoingDocumentTextFieldsActionPerformed

    private void txtSearchIdOutgoingDocumentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchIdOutgoingDocumentKeyReleased
        filterTbOutgoingDocumentById();
    }//GEN-LAST:event_txtSearchIdOutgoingDocumentKeyReleased

    private void txtValueOutgoingDocumentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValueOutgoingDocumentKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValueOutgoingDocumentKeyReleased

    private void btnSaveOutgoingDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveOutgoingDocumentActionPerformed
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            
            String date = df.format(txtDateOutgoingDocument.getDate());
            double value = Double.valueOf(txtValueOutgoingDocument.getText());
            String comment = txtCommentOutgoingDocument.getText();
            
            
            HashMap<String, Integer> cutomersMap = getCustomersMap();
            HashMap<String, Integer> currencyMap = getCurrencyMap();
            
            int currencyId = currencyMap.get(cbCurrenyNameOutgoingDocument.getSelectedItem().toString());
            int customerId = cutomersMap.get(cbCutomerNameOutgoingDocument.getSelectedItem().toString());
            
//            System.out.println(currencyId);
//            System.out.println(customerId);
            
            OutgoingDocument outgoingDocument = OutgoingDocument.builder()
                    .date(date)
                    .currencyId(currencyId)
                    .customerId(customerId)
                    .value(value)
                    .comment(comment)
                    .build();
            
            // Data validation
            
            if(date.isEmpty()){
                JOptionPane.showMessageDialog(null, "اختر التاريخ اولا");
                return;
            }
            
            if(value <= 0) {
                JOptionPane.showMessageDialog(null, "ادخل قيمة عددية صحيحة");
                return;
            }
            
            if(comment.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل البيان");
                return;
            }
            
            if(currencyId <= 0) {
                JOptionPane.showMessageDialog(null, "اختر العملة");
                return;
            }
            
            if(customerId <= 0) {
                JOptionPane.showMessageDialog(null, "اختر العميل");
                return;
            }
            
            long generatedKey = outgoingDocumentRepo.save(outgoingDocument);
            
            System.out.println(generatedKey);
            if(generatedKey > 0 ) {
                lbOutgoingDocumentStatus.setText("تم اضافة سند صرف رقم :" + " " + generatedKey);
                
                // add account movement
                AccountMovement accountMovement = AccountMovement.builder()
                        .date(date)
                        .customerId(customerId)
                        .currencyId(currencyId)
                        .incomingDocumentId(0)
                        .outgoingDocumentId(generatedKey)
                        .incomingValue(0)
                        .outgoingValue(value)
                        .comment(comment)
                        .build();
                
                accountMovementRepo.save(accountMovement);
                setOutgoingDocumentTableData();
                clearOutgoingDocumentTextFields();
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnSaveOutgoingDocumentActionPerformed

    private void btnEditOutgoingDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOutgoingDocumentActionPerformed
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            
            String date = df.format(txtDateOutgoingDocument.getDate());
            double value = Double.valueOf(txtValueOutgoingDocument.getText());
            String comment = txtCommentOutgoingDocument.getText();
            int outgoingDocumentId = Integer.valueOf(txtIdOutgoingDocument.getText());
            
            
            HashMap<String, Integer> cutomersMap = getCustomersMap();
            HashMap<String, Integer> currencyMap = getCurrencyMap();
            
            int currencyId = currencyMap.get(cbCurrenyNameOutgoingDocument.getSelectedItem().toString());
            int customerId = cutomersMap.get(cbCutomerNameOutgoingDocument.getSelectedItem().toString());
            
//            System.out.println(currencyId);
//            System.out.println(customerId);
            
            OutgoingDocument outgoingDocument = OutgoingDocument.builder()
                    .id(outgoingDocumentId)
                    .date(date)
                    .currencyId(currencyId)
                    .customerId(customerId)
                    .value(value)
                    .comment(comment)
                    .build();
            
            // Data validation
            
            if(txtIdOutgoingDocument.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "اختر سند للتعديل");
                return;
            }
            
            if(date.isEmpty()){
                JOptionPane.showMessageDialog(null, "اختر التاريخ اولا");
                return;
            }
            
            if(value <= 0) {
                JOptionPane.showMessageDialog(null, "ادخل قيمة عددية صحيحة");
                return;
            }
            
            if(comment.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل البيان");
                return;
            }
            
            if(currencyId <= 0) {
                JOptionPane.showMessageDialog(null, "اختر العملة");
                return;
            }
            
            if(customerId <= 0) {
                JOptionPane.showMessageDialog(null, "اختر العميل");
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد تعديل هذا السند ؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION) {
                if(outgoingDocumentRepo.update(outgoingDocument)) {
                    lbOutgoingDocumentStatus.setText("تم تعديل سند صرف رقم :" + " " + outgoingDocumentId);

                    // add account movement
                    AccountMovement accountMovement = AccountMovement.builder()
                            .date(date)
                            .customerId(customerId)
                            .currencyId(currencyId)
                            .incomingDocumentId(0)
                            .outgoingDocumentId(outgoingDocumentId)
                            .incomingValue(0)
                            .outgoingValue(value)
                            .comment(comment)
                            .build();

                    accountMovementRepo.update(accountMovement);
                    setOutgoingDocumentTableData();
                    clearOutgoingDocumentTextFields();
                }
            }                      
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnEditOutgoingDocumentActionPerformed

    private void btnDeleteOutgoingDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOutgoingDocumentActionPerformed
        try {
            if(txtIdOutgoingDocument.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "اختر السند الذي تريد حذفه");
            }
            
            long outgoingDocumentId = Integer.valueOf(txtIdOutgoingDocument.getText());
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حذف السند رقم : " + outgoingDocumentId , "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION) {
                if(outgoingDocumentRepo.delete(outgoingDocumentId)) {
                    lbOutgoingDocumentStatus.setText("تم حذف السند رقم : " + outgoingDocumentId);
                    accountMovementRepo.delete(0, outgoingDocumentId);
                    clearOutgoingDocumentTextFields();
                    setOutgoingDocumentTableData();
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnDeleteOutgoingDocumentActionPerformed

    private void btnPrintCustomerBalanceReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintCustomerBalanceReportActionPerformed
        try {
            panelReport.removeAll();
            panelReport.updateUI();
            
            HashMap customersMap = getCustomersMap();
            HashMap currencyMap = getCurrencyMap();
            
            String customerName = cbCustomerNameReportsUI.getSelectedItem().toString();
            String currencyName = cbCurrencyNameReportsUI.getSelectedItem().toString();
            
            int customerId = (int) customersMap.get(customerName);
            int currencyId = (int) currencyMap.get(currencyName);
            
            
            
            String reportName = reportsPath +"customerAccountByCurrencyName.jasper";
            
//            JOptionPane.showMessageDialog(null, System.getProperty("user.dir") + "\n" + reportName);
//            JOptionPane.showMessageDialog(null, reportName);

            HashMap map = new HashMap();
//            map.put("customerName", "customer");
//            map.put("currencyName", "");
//            map.put("total", 100);
            map.put("customerName", customerName);
            map.put("currencyName", currencyName);
            
            ArrayList<AccountMovement> list = accountMovementRepo.findByCustomerIdAndCurrencyId(customerId, currencyId);
//            JasperReport jsR = JasperCompileManager.compileReport(new FileInputStream(new File(this.getClass().getResource(reportName).getPath().g)));
            
            double balance = 0.00;
            
            for (AccountMovement accountMovement : list) {
                double b = accountMovement.getOutgoingValue() - accountMovement.getIncomingValue();
                balance = balance + b;
//                accountMovement.builder().balance(balance).build();
                accountMovement.setBalance(balance);
                System.out.println(balance);
            }
            
            String sBalance = numberFormater.format(balance);
            map.put("balance", sBalance);
            
            InputStream report;
            report = getClass().getResourceAsStream(reportName);
            
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
            
//            JasperPrint jPrint = JasperFillManager.fillReport(report, map);
            JasperPrint jPrint = JasperFillManager.fillReport(report, map, ds);
            
            JRViewer viewer = new JRViewer(jPrint);
            viewer.setZoomRatio(CENTER_ALIGNMENT);
            viewer.setVisible(true);
            
            panelReport.setLayout(new BorderLayout());
            panelReport.repaint();
            
            panelReport.add(viewer);
            panelReport.revalidate();
            
//            JasperViewer.viewReport(jPrint, false);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnPrintCustomerBalanceReportActionPerformed

    private void btnPrintTotalCustomerBalanceReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintTotalCustomerBalanceReportActionPerformed
        try {
            panelReport.removeAll();
            panelReport.updateUI();
            
            HashMap customersMap = getCustomersMap();
            HashMap currencyMap = getCurrencyMap();
            
            String customerName = cbCustomerNameReportsUI.getSelectedItem().toString();
//            String currencyName = cbCurrencyNameReportsUI.getSelectedItem().toString();
            
            int customerId = (int) customersMap.get(customerName);
//            int currencyId = (int) currencyMap.get(currencyName);
            
            
            
            String reportName = reportsPath +"customerTotalBalance.jasper";
            

            HashMap map = new HashMap();

            map.put("customerName", customerName);
            
            ArrayList<Currency> currencyList = currencyRepo.findAll();
            
            ArrayList<CustomerBalance> customerBalanceList = new ArrayList<>();
            
            double balance = 0.00;
            
            for (Currency currency : currencyList) {
                balance = accountMovementRepo.getCustomerBalance(customerId, currency.getId());
                
                CustomerBalance customerBalance = new CustomerBalance(currency.getName(), numberFormater.format(balance));
                
                customerBalanceList.add(customerBalance);
                
            }
                                            
            
            InputStream report;
            report = getClass().getResourceAsStream(reportName);
            
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(customerBalanceList);
            
//            JasperPrint jPrint = JasperFillManager.fillReport(report, map);
            JasperPrint jPrint = JasperFillManager.fillReport(report, map, ds);
            
            JRViewer viewer = new JRViewer(jPrint);
            viewer.setZoomRatio(CENTER_ALIGNMENT);
            viewer.setVisible(true);
            
            panelReport.setLayout(new BorderLayout());
            panelReport.repaint();
            
            panelReport.add(viewer);
            panelReport.revalidate();
            
//            JasperViewer.viewReport(jPrint, false);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnPrintTotalCustomerBalanceReportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearCurrencySelection;
    private javax.swing.JButton btnClearCutomerSelection;
    private javax.swing.JButton btnClearIncomingDocumentTextFields;
    private javax.swing.JButton btnClearOutgoingDocumentTextFields;
    private javax.swing.JButton btnCustomerDelete;
    private javax.swing.JButton btnCustomerEdit;
    private javax.swing.JButton btnCustomerSave;
    private javax.swing.JButton btnDeleteCurrency;
    private javax.swing.JButton btnDeleteIncomingDocument;
    private javax.swing.JButton btnDeleteOutgoingDocument;
    private javax.swing.JButton btnEditCurrency;
    private javax.swing.JButton btnEditIncomingDocument;
    private javax.swing.JButton btnEditOutgoingDocument;
    private javax.swing.JButton btnPrintCustomerBalanceReport;
    private javax.swing.JButton btnPrintTotalCustomerBalanceReport;
    private javax.swing.JButton btnSaveCurrency;
    private javax.swing.JButton btnSaveIncomingDocument;
    private javax.swing.JButton btnSaveOutgoingDocument;
    private javax.swing.JComboBox<String> cbCurrencyNameReportsUI;
    private javax.swing.JComboBox<String> cbCurrenyNameIncomingDocument;
    private javax.swing.JComboBox<String> cbCurrenyNameOutgoingDocument;
    private javax.swing.JComboBox<String> cbCustomerNameReportsUI;
    private javax.swing.JComboBox<String> cbCutomerNameIncomingDocument;
    private javax.swing.JComboBox<String> cbCutomerNameOutgoingDocument;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbCurrencyStatus;
    private javax.swing.JLabel lbCustomersStatus;
    private javax.swing.JLabel lbIncomingDocumentStatus;
    private javax.swing.JLabel lbOutgoingDocumentStatus;
    private javax.swing.JPanel panelReport;
    private javax.swing.JTable tbCurrency;
    private javax.swing.JTable tbCustomers;
    private javax.swing.JTable tbIncomingDocuments;
    private javax.swing.JTable tbOutgoingDocuments;
    private javax.swing.JTextField txtCommentIncomingDocument;
    private javax.swing.JTextField txtCommentOutgoingDocument;
    private javax.swing.JTextField txtCurrencyId;
    private javax.swing.JTextField txtCurrencyName;
    private javax.swing.JTextField txtCustomerId;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerNameSearch;
    private javax.swing.JTextField txtCustomerPhone;
    private com.toedter.calendar.JDateChooser txtDateIncomingDocument;
    private com.toedter.calendar.JDateChooser txtDateOutgoingDocument;
    private javax.swing.JTextField txtIdIncomingDocument;
    private javax.swing.JTextField txtIdOutgoingDocument;
    private javax.swing.JTextField txtSearchIdIncomingDocument;
    private javax.swing.JTextField txtSearchIdOutgoingDocument;
    private javax.swing.JTextField txtValueIncomingDocument;
    private javax.swing.JTextField txtValueOutgoingDocument;
    // End of variables declaration//GEN-END:variables

    private void setCurrencyTableData() {
        try {
            ArrayList<Currency> list = currencyRepo.findAll();
            DefaultTableModel model = (DefaultTableModel) tbCurrency.getModel();
            model.setRowCount(0);
            
            for(Currency currency : list) {
                Vector vector = new Vector();
                vector.add(currency.getId());
                vector.add(currency.getName());
                model.addRow(vector);
                
            }
            
            tbCurrency.setModel(model);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }

    private void clearCurrencyTextFields() {
        txtCurrencyId.setText("");
        txtCurrencyName.setText("");
    }

    private void setIncomingDocumentCustomerNameCbItems() {
        try {
            cbCutomerNameIncomingDocument.removeAllItems();
            ArrayList<Customer> list = customerRepo.findAll();
            for (Customer customer : list) {
                cbCutomerNameIncomingDocument.addItem(customer.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }

    private void setIncomingDocumentCurrencyNameCbItems() {
        try {
            cbCurrenyNameIncomingDocument.removeAllItems();
            ArrayList<Currency> list = currencyRepo.findAll();
            for (Currency currency : list) {
                cbCurrenyNameIncomingDocument.addItem(currency.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }
    
    private void clearIncomingDocumentTextFields() {
        txtCommentIncomingDocument.setText("");
        txtDateIncomingDocument.setDate(new Date());
        txtIdIncomingDocument.setText("");
//        txtSearchCurrencyIncomingDocument.setText("");
//        txtSearchCustomerNameIncomingDocument.setText("");
        txtValueIncomingDocument.setText("0.00");
//        cbCurrenyNameIncomingDocument.setSelectedIndex(0);
//        cbCutomerNameIncomingDocument.setSelectedIndex(0);
    }
    
    private HashMap<String, Integer> getCustomersMap() {
        HashMap<String, Integer> customersMap = new HashMap();
        ArrayList<Customer> customersList = customerRepo.findAll();

        for (Customer customer : customersList) {
            customersMap.put(customer.getName(), customer.getId());
        }
        
        return customersMap;
    }
    
    private HashMap<String, Integer> getCurrencyMap() {
        HashMap<String, Integer> currencyMap = new HashMap();
        ArrayList<Currency> currencyList = currencyRepo.findAll();

        for (Currency currency : currencyList) {
            currencyMap.put(currency.getName(), currency.getId());
        }
        
        return currencyMap;
    }
    
    void filterTbIncomingDocumentById() {
        try {
            
            if(txtSearchIdIncomingDocument.getText().isEmpty()){
                setIncomingDocumentTableData();
                return;
            }
            
            DefaultTableModel model = (DefaultTableModel) tbIncomingDocuments.getModel();
            model.setRowCount(0);
            
            HashMap<String, Integer> customersMap = getCustomersMap();
            HashMap<String, Integer> currencyMap = getCurrencyMap();
            
//            int customerId = customersMap.get(txtSearchCustomerNameIncomingDocument.getText());
//            int currencyId = currencyMap.get(txtSearchCurrencyIncomingDocument.getText());
            
            ArrayList<IncomingDocument> list = incomingDocumentRepo.filterById(Long.valueOf(txtSearchIdIncomingDocument.getText()));
            
            for (IncomingDocument incomingDocument : list) {
                Vector vector = new Vector();
                vector.add(incomingDocument.getId());
                vector.add(incomingDocument.getDate());
                Currency currency = currencyRepo.findById(incomingDocument.getCurrencyId());
                Customer customer = customerRepo.findById(incomingDocument.getCustomerId());
                vector.add(customer.getName());
                vector.add(currency.getName());
                vector.add(incomingDocument.getValue());
                vector.add(incomingDocument.getComment());
                
                model.addRow(vector);
            }
            tbIncomingDocuments.setModel(model);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }

    private void clearOutgoingDocumentTextFields() {
        try {
            txtCommentOutgoingDocument.setText("");
            txtDateOutgoingDocument.setDate(new Date());
            txtIdOutgoingDocument.setText("");
    //        txtSearchCurrencyIncomingDocument.setText("");
    //        txtSearchCustomerNameIncomingDocument.setText("");
            txtValueOutgoingDocument.setText("0.00");
//            cbCurrenyNameOutgoingDocument.setSelectedIndex(0);
//            cbCutomerNameOutgoingDocument.setSelectedIndex(0);
        } catch (Exception e) {
        }
    }

    private void setOutgoingDocumentCustomerNameCbItems() {
        try {
            cbCutomerNameOutgoingDocument.removeAllItems();
            ArrayList<Customer> list = customerRepo.findAll();
            for (Customer customer : list) {
                cbCutomerNameOutgoingDocument.addItem(customer.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }

    private void setOutgoingDocumentCurrencyNameCbItems() {
        try {
            cbCurrenyNameOutgoingDocument.removeAllItems();
            ArrayList<Currency> list = currencyRepo.findAll();
            for (Currency currency : list) {
                cbCurrenyNameOutgoingDocument.addItem(currency.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }

    private void setOutgoingDocumentTableData() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbOutgoingDocuments.getModel();
            model.setRowCount(0);
            
            ArrayList <OutgoingDocument> list = outgoingDocumentRepo.findAllDesc();
            
            for (OutgoingDocument outgoingDocument : list) {
                Vector vector = new Vector();
                vector.add(outgoingDocument.getId());
                vector.add(outgoingDocument.getDate());
                Customer customer = customerRepo.findById(outgoingDocument.getCustomerId());
                Currency currency = currencyRepo.findById(outgoingDocument.getCurrencyId());
                vector.add(customer.getName());
                vector.add(currency.getName());
                vector.add(numberFormater.format(outgoingDocument.getValue()));
                vector.add(outgoingDocument.getComment());
                
                model.addRow(vector);
            }
            
            tbOutgoingDocuments.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }
    
    private void filterTbOutgoingDocumentById() {
        try {
            
            if(txtSearchIdOutgoingDocument.getText().isEmpty()){
                setOutgoingDocumentTableData();
                return;
            }
            
            DefaultTableModel model = (DefaultTableModel) tbOutgoingDocuments.getModel();
            model.setRowCount(0);
            
            HashMap<String, Integer> customersMap = getCustomersMap();
            HashMap<String, Integer> currencyMap = getCurrencyMap();
            
//            int customerId = customersMap.get(txtSearchCustomerNameIncomingDocument.getText());
//            int currencyId = currencyMap.get(txtSearchCurrencyIncomingDocument.getText());
            
            ArrayList<OutgoingDocument> list = outgoingDocumentRepo.filterById(Long.valueOf(txtSearchIdOutgoingDocument.getText()));
            
            for (OutgoingDocument outgoingDocument : list) {
                Vector vector = new Vector();
                vector.add(outgoingDocument.getId());
                vector.add(outgoingDocument.getDate());
                Currency currency = currencyRepo.findById(outgoingDocument.getCurrencyId());
                Customer customer = customerRepo.findById(outgoingDocument.getCustomerId());
                vector.add(customer.getName());
                vector.add(currency.getName());
                vector.add(outgoingDocument.getValue());
                vector.add(outgoingDocument.getComment());
                
                model.addRow(vector);
            }
            tbOutgoingDocuments.setModel(model);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }
}
