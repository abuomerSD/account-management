/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accountmanagement.ui;

import com.accountmanagement.models.Product;
import com.accountmanagement.repositories.product.ProductSqliteRepository;
import java.awt.ComponentOrientation;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author asdf
 */
public class products extends javax.swing.JPanel {

    
    private ProductSqliteRepository repo = new ProductSqliteRepository();
    
    
    public products() {
          
        initComponents();
//        jPanel1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // set RTL to tables and tabbedPane
        
        jTabbedPane1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        addProuctTable.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tbSearchProduct.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        // set Products Search Table columns Width
        tbSearchProduct.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbSearchProduct.getColumnModel().getColumn(1).setMinWidth(150);
        tbSearchProduct.getColumnModel().getColumn(2).setMinWidth(100);
        tbSearchProduct.getColumnModel().getColumn(3).setMinWidth(100);
        tbSearchProduct.getColumnModel().getColumn(4).setMinWidth(150);
        tbSearchProduct.getColumnModel().getColumn(5).setMinWidth(100);
        tbSearchProduct.getColumnModel().getColumn(6).setMinWidth(100);
        tbSearchProduct.getColumnModel().getColumn(7).setMinWidth(100);

        
        // set products table header RTL and center
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) addProuctTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer headerRenderer1 = (DefaultTableCellRenderer) tbSearchProduct.getTableHeader().getDefaultRenderer();
        headerRenderer1.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer centerStringRenderer = (DefaultTableCellRenderer) tbSearchProduct.getDefaultRenderer(String.class);
        centerStringRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbSearchProduct.setDefaultRenderer(String.class, centerStringRenderer);
        
        DefaultTableCellRenderer centerIntRenderer = (DefaultTableCellRenderer) tbSearchProduct.getDefaultRenderer(Integer.class);
        centerIntRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbSearchProduct.setDefaultRenderer(Integer.class, centerIntRenderer);
        
        DefaultTableCellRenderer centerDoubleRenderer = (DefaultTableCellRenderer) tbSearchProduct.getDefaultRenderer(Double.class);
        centerDoubleRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbSearchProduct.setDefaultRenderer(Double.class, centerDoubleRenderer);
        
        
        // set tables data
        setAddProductsTableData();
        setSearchProductTableData();
        
        // add table changed selection listener
        
        addProuctTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (addProuctTable.getSelectedRow() > -1) {
                    setSelectedItemDataFromAddProductsTable();
                }
            }
        });
    }
    
    
    private void filterAddProductTableBySearchWords() {
        try {
            String serial = txtAddProductSearchSerial.getText();
            String buyerName = txtAddProductSearchBuyerName.getText();
            
//            ProductSqliteRepository repo = new ProductSqliteRepository();
            
            ArrayList<Product> list = repo.findBySerialOrBuyerName(serial, buyerName);
            
            DefaultTableModel model = (DefaultTableModel) addProuctTable.getModel();
            model.setRowCount(0);
            
            for(Product product : list) {
                Vector vector = new Vector();
                vector.add(product.getId());
                vector.add(product.getSerial());
                vector.add(product.getBuyerName());
                vector.add(product.getBuyerPhone());
                vector.add(product.getBuyerEmail());
                vector.add(product.getPassword());
                vector.add(product.getSubscribtionDate());
                vector.add(product.getSubscribtionValue());
                
                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    private void filterTbSearchProductBySearchWords() {
        try {
            String serial = txtSearchProduct.getText();
            String buyerName = txtSearchProductBuyerName.getText();
            ArrayList<Product> list = repo.findBySerialOrBuyerName(serial, buyerName);
            
            DefaultTableModel model = (DefaultTableModel) tbSearchProduct.getModel();
            model.setRowCount(0);
            
            for(Product product : list) {
                Vector vector = new Vector();
                vector.add(product.getId());
                vector.add(product.getSerial());
                vector.add(product.getBuyerName());
                vector.add(product.getBuyerPhone());
                vector.add(product.getBuyerEmail());
                vector.add(product.getPassword());
                vector.add(product.getSubscribtionDate());
                vector.add(product.getSubscribtionValue());
                model.addRow(vector);
            }
            
            tbSearchProduct.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void setAddProductsTableData() {
        
        DefaultTableModel model = (DefaultTableModel) addProuctTable.getModel();
        model.setRowCount(0);
        
        try {
//            ProductSqliteRepository repo = new ProductSqliteRepository();
            ArrayList<Product> list = repo.findAll();
            
            for(Product product : list) {
                Vector vector = new Vector();
                vector.add(product.getId());
                vector.add(product.getSerial());
                vector.add(product.getBuyerName());
                vector.add(product.getBuyerPhone());
                vector.add(product.getBuyerEmail());
                vector.add(product.getPassword());
                vector.add(product.getSubscribtionDate());
                vector.add(product.getSubscribtionValue());
                
                model.addRow(vector);
            }
            
            addProuctTable.setModel(model);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "خطأ", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void setSearchProductTableData() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbSearchProduct.getModel();
            model.setRowCount(0);
            
            ArrayList<Product> list = repo.findAll();
            
            for(Product product : list) {
                Vector vector = new Vector();
                vector.add(product.getId());
                vector.add(product.getSerial());
                vector.add(product.getBuyerName());
                vector.add(product.getBuyerPhone());
                vector.add(product.getBuyerEmail());
                vector.add(product.getPassword());
                vector.add(product.getSubscribtionDate());
                vector.add(product.getSubscribtionValue());
                
                model.addRow(vector);
                
            }
            
            tbSearchProduct.setModel(model);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    private void setSelectedItemDataFromAddProductsTable() {
        try {
            int row = addProuctTable.getSelectedRow();
            txtProductId.setText(String.valueOf(addProuctTable.getValueAt(row, 0)));
            txtAddProductSerial.setText((String) addProuctTable.getValueAt(row, 1));
            txtAddPoductBuyer.setText((String) addProuctTable.getValueAt(row, 2));
            txtAddProductBuyerPhone.setText((String) addProuctTable.getValueAt(row, 3));
            txtAddProductBuyerEmail.setText((String) addProuctTable.getValueAt(row, 4));
            txtAddProductPassword.setText((String) addProuctTable.getValueAt(row, 5));

            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            txtAddProductSubscribtionDate.setDate( df.parse((String) addProuctTable.getValueAt(row, 6)));
            txtAddProductSuscribtionValue.setText(String.valueOf(addProuctTable.getValueAt(row, 7))); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
    }
    
    
    private void clearAddProductTextFields() {
        txtProductId.setText("");
        txtAddPoductBuyer.setText("");
        txtAddProductBuyerEmail.setText("");
        txtAddProductBuyerPhone.setText("");
        txtAddProductPassword.setText("");
        txtAddProductSerial.setText("");
        txtAddProductSubscribtionDate.cleanup();
        txtAddProductSuscribtionValue.setText("");
                
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
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtAddProductSerial = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAddPoductBuyer = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAddProductBuyerPhone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtAddProductBuyerEmail = new javax.swing.JTextField();
        txtAddProductPassword = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtAddProductSuscribtionValue = new javax.swing.JTextField();
        txtAddProductSubscribtionDate = new com.toedter.calendar.JDateChooser();
        btnAddProductSave = new javax.swing.JButton();
        btnAddProductEdit = new javax.swing.JButton();
        btnAddProductDelete = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addProuctTable = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtAddProductSearchSerial = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtAddProductSearchBuyerName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtProductId = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnClear = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbSearchProduct = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        txtSearchProduct = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSearchProductBuyerName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();

        jTabbedPane1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("السيريال :");

        txtAddProductSerial.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtAddProductSerial.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAddProductSerial.setToolTipText("");
        txtAddProductSerial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddProductSerialActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel2.setText("المنتجات : ");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel3.setText("اسم المشتري :");

        txtAddPoductBuyer.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtAddPoductBuyer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAddPoductBuyer.setToolTipText("");
        txtAddPoductBuyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddPoductBuyerActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel4.setText("رقم التلفون :");

        txtAddProductBuyerPhone.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtAddProductBuyerPhone.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAddProductBuyerPhone.setToolTipText("");
        txtAddProductBuyerPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddProductBuyerPhoneActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel5.setText("الايميل :");

        txtAddProductBuyerEmail.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtAddProductBuyerEmail.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAddProductBuyerEmail.setToolTipText("");
        txtAddProductBuyerEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddProductBuyerEmailActionPerformed(evt);
            }
        });

        txtAddProductPassword.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtAddProductPassword.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAddProductPassword.setToolTipText("");
        txtAddProductPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddProductPasswordActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel6.setText("كلمة السر :");

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel7.setText("تاريخ الاشتراك :");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel8.setText("قيمة الاشتراك :");

        txtAddProductSuscribtionValue.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtAddProductSuscribtionValue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAddProductSuscribtionValue.setToolTipText("");
        txtAddProductSuscribtionValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddProductSuscribtionValueActionPerformed(evt);
            }
        });

        btnAddProductSave.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnAddProductSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/save.png"))); // NOI18N
        btnAddProductSave.setText("حفظ");
        btnAddProductSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductSaveActionPerformed(evt);
            }
        });

        btnAddProductEdit.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnAddProductEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/edit.png"))); // NOI18N
        btnAddProductEdit.setText("تعديل");
        btnAddProductEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductEditActionPerformed(evt);
            }
        });

        btnAddProductDelete.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnAddProductDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/delete.png"))); // NOI18N
        btnAddProductDelete.setText("حذف");
        btnAddProductDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(191, 191, 191)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAddProductSerial, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAddProductBuyerEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAddProductBuyerPhone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAddPoductBuyer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAddProductPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtAddProductSuscribtionValue)
                            .addComponent(txtAddProductSubscribtionDate, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(btnAddProductSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddProductEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddProductDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtAddProductSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtAddPoductBuyer, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAddProductBuyerPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAddProductBuyerEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddProductPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtAddProductSubscribtionDate, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAddProductSuscribtionValue, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddProductSave, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddProductEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddProductDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addProuctTable.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        addProuctTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم", "السيريال", "اسم المشتري", "رقم التلفون", "الايميل", "كلمة السر", "تاريخ الاشتراك", "قيمة الاشتراك"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        addProuctTable.setRowHeight(30);
        addProuctTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addProuctTableMouseClicked(evt);
            }
        });
        addProuctTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                addProuctTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(addProuctTable);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtAddProductSearchSerial.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtAddProductSearchSerial.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAddProductSearchSerial.setToolTipText("");
        txtAddProductSearchSerial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddProductSearchSerialActionPerformed(evt);
            }
        });
        txtAddProductSearchSerial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAddProductSearchSerialKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel9.setText("السيريال :");

        txtAddProductSearchBuyerName.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtAddProductSearchBuyerName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtAddProductSearchBuyerName.setToolTipText("");
        txtAddProductSearchBuyerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddProductSearchBuyerNameActionPerformed(evt);
            }
        });
        txtAddProductSearchBuyerName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAddProductSearchBuyerNameKeyReleased(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel10.setText("اسم المشتري :");

        txtProductId.setEditable(false);
        txtProductId.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtProductId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtProductId.setToolTipText("");
        txtProductId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductIdActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel12.setText("رقم المنتج :");

        btnClear.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/clear.png"))); // NOI18N
        btnClear.setText("إزالة تحديد");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(txtAddProductSearchBuyerName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddProductSearchSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtAddProductSearchSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtAddProductSearchBuyerName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel11.setText("شريط الحالة :");

        lbStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbStatus))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69))
        );

        jTabbedPane1.addTab("إضافة منتجات", jPanel1);

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbSearchProduct.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tbSearchProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم", "السيريال", "اسم المشتري", "رقم التلفون", "الايميل", "كلمة السر", "تاريخ الاشتراك", "قيمة الاشتراك"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbSearchProduct.setRowHeight(30);
        tbSearchProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSearchProductMouseClicked(evt);
            }
        });
        tbSearchProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbSearchProductKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbSearchProduct);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtSearchProduct.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtSearchProduct.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSearchProduct.setToolTipText("");
        txtSearchProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchProductActionPerformed(evt);
            }
        });
        txtSearchProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchProductKeyReleased(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel13.setText("السيريال :");

        txtSearchProductBuyerName.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtSearchProductBuyerName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSearchProductBuyerName.setToolTipText("");
        txtSearchProductBuyerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchProductBuyerNameActionPerformed(evt);
            }
        });
        txtSearchProductBuyerName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchProductBuyerNameKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel14.setText("اسم المشتري :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(359, Short.MAX_VALUE)
                .addComponent(txtSearchProductBuyerName, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtSearchProductBuyerName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("قائمة المنتجات", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 65, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtAddProductSerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddProductSerialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddProductSerialActionPerformed

    private void txtAddPoductBuyerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddPoductBuyerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddPoductBuyerActionPerformed

    private void txtAddProductBuyerPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddProductBuyerPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddProductBuyerPhoneActionPerformed

    private void txtAddProductBuyerEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddProductBuyerEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddProductBuyerEmailActionPerformed

    private void txtAddProductPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddProductPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddProductPasswordActionPerformed

    private void txtAddProductSuscribtionValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddProductSuscribtionValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddProductSuscribtionValueActionPerformed

    private void btnAddProductSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductSaveActionPerformed
       
        try {
            String serial = txtAddProductSerial.getText();
            String buyerName = txtAddPoductBuyer.getText();
            String buyerPhone = txtAddProductBuyerPhone.getText();
            String buyerEmail = txtAddProductBuyerEmail.getText();
            String password = txtAddProductPassword.getText();
            Date subscribtionDate = txtAddProductSubscribtionDate.getDate();
            double subscribtionValue = Double.valueOf(txtAddProductSuscribtionValue.getText());
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        
            if(serial.isEmpty() || buyerName.isEmpty() || buyerPhone.isEmpty() ||
                buyerEmail.isEmpty() || password.isEmpty() || subscribtionDate.toString().isEmpty() ||
                txtAddProductSuscribtionValue.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "الرجاء ادخال جميع البيانات المطلوبة", "خطأ", JOptionPane.ERROR_MESSAGE);
                    return;
            }
        
        
            
            String date = df.format(subscribtionDate);
            
            Product product = new Product(serial, buyerName, buyerPhone, buyerEmail, password, date, subscribtionValue);
            
            
//            ProductSqliteRepository repo = new ProductSqliteRepository();
            
            
            
            if(repo.save(product)) {
                setAddProductsTableData();
                setSearchProductTableData();
                lbStatus.setText("تم إضافة " + product.getSerial() + " بنجاح");
                clearAddProductTextFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "خطأ", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnAddProductSaveActionPerformed

    private void btnAddProductEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductEditActionPerformed
        try {
            String serial = txtAddProductSerial.getText();
            String buyerName = txtAddPoductBuyer.getText();
            String buyerPhone = txtAddProductBuyerPhone.getText();
            String buyerEmail = txtAddProductBuyerEmail.getText();
            String password =txtAddProductPassword.getText();
            
            SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
            
            Date subscribtionDate = txtAddProductSubscribtionDate.getDate();
            double subscribtionValue = Double.valueOf(txtAddProductSuscribtionValue.getText());
            
            if(serial.isEmpty() || buyerName.isEmpty() || buyerPhone.isEmpty() ||
            buyerEmail.isEmpty() || password.isEmpty() || subscribtionDate.toString().isEmpty() ||
            txtAddProductSuscribtionValue.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "الرجاء ادخال جميع البيانات المطلوبة", "خطأ", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Product newProduct = new Product(serial, buyerName, buyerPhone, buyerEmail, password, df.format(subscribtionDate), subscribtionValue);
            
            newProduct.setId(Integer.valueOf(txtProductId.getText()));
            
//            ProductSqliteRepository repo = new ProductSqliteRepository();
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد تعديل المنتج رقم " + newProduct.getId() + "؟", "رسالة تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION){
                repo.update(newProduct);
                setAddProductsTableData();
                setSearchProductTableData();
                lbStatus.setText("تم تحديث بيانات الصنف " + serial);
            }

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }//GEN-LAST:event_btnAddProductEditActionPerformed

    private void btnAddProductDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductDeleteActionPerformed
        
        try {
            int id = Integer.valueOf(txtProductId.getText());
//            ProductSqliteRepository repo = new ProductSqliteRepository();
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حذف المنتج رقم " + id + "؟", "رسالة تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION){
                if (repo.delete(id)) {
                lbStatus.setText("تم حذف المنتج رقم " + id);
                clearAddProductTextFields();
                setAddProductsTableData();
                setSearchProductTableData();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnAddProductDeleteActionPerformed

    private void txtAddProductSearchSerialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddProductSearchSerialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddProductSearchSerialActionPerformed

    private void txtAddProductSearchBuyerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddProductSearchBuyerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAddProductSearchBuyerNameActionPerformed

    private void addProuctTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addProuctTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addProuctTableMouseClicked

    private void txtProductIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductIdActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearAddProductTextFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void addProuctTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addProuctTableKeyReleased
        
    }//GEN-LAST:event_addProuctTableKeyReleased

    private void txtAddProductSearchSerialKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddProductSearchSerialKeyReleased
        filterAddProductTableBySearchWords();
    }//GEN-LAST:event_txtAddProductSearchSerialKeyReleased

    private void txtAddProductSearchBuyerNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAddProductSearchBuyerNameKeyReleased
        filterAddProductTableBySearchWords();
    }//GEN-LAST:event_txtAddProductSearchBuyerNameKeyReleased

    private void tbSearchProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSearchProductMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbSearchProductMouseClicked

    private void tbSearchProductKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbSearchProductKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbSearchProductKeyReleased

    private void txtSearchProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchProductActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchProductActionPerformed

    private void txtSearchProductKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProductKeyReleased
        filterTbSearchProductBySearchWords();
    }//GEN-LAST:event_txtSearchProductKeyReleased

    private void txtSearchProductBuyerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchProductBuyerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchProductBuyerNameActionPerformed

    private void txtSearchProductBuyerNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchProductBuyerNameKeyReleased
        filterTbSearchProductBySearchWords();
    }//GEN-LAST:event_txtSearchProductBuyerNameKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable addProuctTable;
    private javax.swing.JButton btnAddProductDelete;
    private javax.swing.JButton btnAddProductEdit;
    private javax.swing.JButton btnAddProductSave;
    private javax.swing.JButton btnClear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JTable tbSearchProduct;
    private javax.swing.JTextField txtAddPoductBuyer;
    private javax.swing.JTextField txtAddProductBuyerEmail;
    private javax.swing.JTextField txtAddProductBuyerPhone;
    private javax.swing.JTextField txtAddProductPassword;
    private javax.swing.JTextField txtAddProductSearchBuyerName;
    private javax.swing.JTextField txtAddProductSearchSerial;
    private javax.swing.JTextField txtAddProductSerial;
    private com.toedter.calendar.JDateChooser txtAddProductSubscribtionDate;
    private javax.swing.JTextField txtAddProductSuscribtionValue;
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtSearchProduct;
    private javax.swing.JTextField txtSearchProductBuyerName;
    // End of variables declaration//GEN-END:variables
}
