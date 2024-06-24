/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountmanagement.ui;

import com.accountmanagement.models.Currency;
import com.accountmanagement.models.Customer;
import com.accountmanagement.models.CustomerBuilder;
import com.accountmanagement.repositories.currency.CurrencySqliteRepository;
import com.accountmanagement.repositories.customer.CustomerSqliteRepository;
import java.awt.ComponentOrientation;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author asdf
 */
public class customers extends javax.swing.JPanel {
    
    
    CustomerSqliteRepository customerRepo = new CustomerSqliteRepository();
    CurrencySqliteRepository currencyRepo = new CurrencySqliteRepository();
    
    
    /**
     * Creates new form customers
     */
    public customers() {
        initComponents();
       // Set Tabed pane RTL 
       jTabbedPane1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       tbCustomers.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       tbCurrency.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       
       
        // set customer table width
        tbCustomers.getColumnModel().getColumn(0).setMaxWidth(150);
        
        // set Currency table col width
        tbCurrency.getColumnModel().getColumn(0).setMaxWidth(150);
        
       // set tables headers center
        DefaultTableCellRenderer customerTableCellRenderer = (DefaultTableCellRenderer) tbCustomers.getTableHeader().getDefaultRenderer();
        customerTableCellRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer centerStringRenderer = (DefaultTableCellRenderer) tbCustomers.getDefaultRenderer(String.class);
        centerStringRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer currencyTableHeaderRenderer = (DefaultTableCellRenderer) tbCurrency.getTableHeader().getDefaultRenderer();
        currencyTableHeaderRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer currencyTableRenderer = (DefaultTableCellRenderer) tbCurrency.getDefaultRenderer(String.class);
        currencyTableRenderer.setHorizontalAlignment(0);

        
       // set tables data 
       setCustomerTableData();
       setCurrencyTableData();
       
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
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
                .addContainerGap(215, Short.MAX_VALUE))
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
        btnClearCutomerSelection.setText("إزالة تحديد");
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCustomers.setRowHeight(30);
        jScrollPane1.setViewportView(tbCustomers);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 981, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("سند قبض", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 981, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("سند صرف", jPanel3);

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 611, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
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
        btnClearCurrencySelection.setText("إزالة تحديد");
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
            }
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "خطأ", 0);
        }
    }//GEN-LAST:event_btnCustomerEditActionPerformed

    private void btnCustomerDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomerDeleteActionPerformed
        try {
            int id = Integer.valueOf(txtCustomerId.getText());
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حذف العميل رقم " + id + " ؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION){
                if(customerRepo.delete(id)){
                    lbCustomersStatus.setText("تم حذف العميل رقم " + id);
                    clearCustomerTextFields();
                    setCustomerTableData();
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
            
            if(result == JOptionPane.YES_OPTION) {
                if(currencyRepo.delete(id)){
                    lbCurrencyStatus.setText("تم حذف العملة رقم " + id);
                    setCurrencyTableData();
                    clearCurrencyTextFields();
                }
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }//GEN-LAST:event_btnDeleteCurrencyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearCurrencySelection;
    private javax.swing.JButton btnClearCutomerSelection;
    private javax.swing.JButton btnCustomerDelete;
    private javax.swing.JButton btnCustomerEdit;
    private javax.swing.JButton btnCustomerSave;
    private javax.swing.JButton btnDeleteCurrency;
    private javax.swing.JButton btnEditCurrency;
    private javax.swing.JButton btnSaveCurrency;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbCurrencyStatus;
    private javax.swing.JLabel lbCustomersStatus;
    private javax.swing.JTable tbCurrency;
    private javax.swing.JTable tbCustomers;
    private javax.swing.JTextField txtCurrencyId;
    private javax.swing.JTextField txtCurrencyName;
    private javax.swing.JTextField txtCustomerId;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerNameSearch;
    private javax.swing.JTextField txtCustomerPhone;
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
}
