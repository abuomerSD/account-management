
package com.accountmanagement.ui;

import com.accountmanagement.models.Customer;
import com.accountmanagement.models.CustomerBuilder;
import com.accountmanagement.repositories.sCustomer.ScustomerSqliteRepository;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class salesInvoices extends javax.swing.JPanel {

    ScustomerSqliteRepository customerRepo = new ScustomerSqliteRepository();
    
    public salesInvoices() {
        initComponents();
        
        // RTL
        jTabbedPane1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tbCustomers.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // set tables data
        
        setCustomerTableData();
        
        // table renderer
        DefaultTableCellRenderer tbCustomersRenderer = (DefaultTableCellRenderer) tbCustomers.getDefaultRenderer(String.class);
        tbCustomersRenderer.setHorizontalAlignment(0);
        
        JTableHeader tbCustomersHeader = tbCustomers.getTableHeader();
        tbCustomersHeader.setDefaultRenderer(tbCustomersRenderer);
        tbCustomersHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        
        // table listener
        tbCustomers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(tbCustomers.getSelectedRow() > -1) {
                    int row = tbCustomers.getSelectedRow();
                    txtCustomerId.setText(String.valueOf(tbCustomers.getValueAt(row, 0)));
                    txtCustomerName.setText((String) tbCustomers.getValueAt(row, 1));
                    txtCustomerPhone.setText((String) tbCustomers.getValueAt(row, 2));
                }
            }
            
        });
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        customersPanel = new javax.swing.JPanel();
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
        invoicePanel = new javax.swing.JPanel();
        invoiceListPanel = new javax.swing.JPanel();

        setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
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

        javax.swing.GroupLayout customersPanelLayout = new javax.swing.GroupLayout(customersPanel);
        customersPanel.setLayout(customersPanelLayout);
        customersPanelLayout.setHorizontalGroup(
            customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 991, Short.MAX_VALUE)
            .addGroup(customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(customersPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        customersPanelLayout.setVerticalGroup(
            customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
            .addGroup(customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(customersPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("العملاء", customersPanel);

        javax.swing.GroupLayout invoicePanelLayout = new javax.swing.GroupLayout(invoicePanel);
        invoicePanel.setLayout(invoicePanelLayout);
        invoicePanelLayout.setHorizontalGroup(
            invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 991, Short.MAX_VALUE)
        );
        invoicePanelLayout.setVerticalGroup(
            invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("فاتورة جديدة", invoicePanel);

        javax.swing.GroupLayout invoiceListPanelLayout = new javax.swing.GroupLayout(invoiceListPanel);
        invoiceListPanel.setLayout(invoiceListPanelLayout);
        invoiceListPanelLayout.setHorizontalGroup(
            invoiceListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 991, Short.MAX_VALUE)
        );
        invoiceListPanelLayout.setVerticalGroup(
            invoiceListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("قائمة الفواتير", invoiceListPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
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
                setCbCustomerInvoiceUiData();

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
                    setCbCustomerInvoiceUiData();
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


            if(result == JOptionPane.YES_OPTION){
                if(customerRepo.delete(id)){
                    lbCustomersStatus.setText("تم حذف العميل رقم " + id);
                    clearCustomerTextFields();
                    setCustomerTableData();
                    setCbCustomerInvoiceUiData();
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

    private void btnClearCutomerSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearCutomerSelectionActionPerformed
        clearCustomerTextFields();
    }//GEN-LAST:event_btnClearCutomerSelectionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearCutomerSelection;
    private javax.swing.JButton btnCustomerDelete;
    private javax.swing.JButton btnCustomerEdit;
    private javax.swing.JButton btnCustomerSave;
    private javax.swing.JPanel customersPanel;
    private javax.swing.JPanel invoiceListPanel;
    private javax.swing.JPanel invoicePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbCustomersStatus;
    private javax.swing.JTable tbCustomers;
    private javax.swing.JTextField txtCustomerId;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerNameSearch;
    private javax.swing.JTextField txtCustomerPhone;
    // End of variables declaration//GEN-END:variables

    private void setCustomerTableData() {
        try {
            

            ArrayList<Customer> list = customerRepo.findAll();

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
    }

    private void clearCustomerTextFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerPhone.setText("");
    }



    private void setCbCustomerInvoiceUiData() {
        
    }
}
