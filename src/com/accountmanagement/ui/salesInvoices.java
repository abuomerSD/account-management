
package com.accountmanagement.ui;

import com.accountmanagement.models.Customer;
import com.accountmanagement.models.CustomerBuilder;
import com.accountmanagement.models.SalesInvoiceDetails;
import com.accountmanagement.models.SalesInvoiceHeader;
import com.accountmanagement.repositories.sCustomer.ScustomerSqliteRepository;
import com.accountmanagement.repositories.salesinvoicedetails.SalesInvoiceDetailsSqliteRepository;
import com.accountmanagement.repositories.salesinvoiceheader.SalesInvoiceHeaderSqliteRepository;
import com.accountmanagement.utils.Constants;
import com.accountmanagement.utils.OSDetector;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class salesInvoices extends javax.swing.JPanel {
    
    SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
    DecimalFormat numberFormater =  new DecimalFormat("#,###,###.##");
    double invoiceTotal = 0.00;
    
    boolean isUpdateInvoice = false;
    
    String reportsPath = Constants.REPORTS_PATH;

    ScustomerSqliteRepository customerRepo = new ScustomerSqliteRepository();
    SalesInvoiceHeaderSqliteRepository headerRepo = new SalesInvoiceHeaderSqliteRepository();
    SalesInvoiceDetailsSqliteRepository detailsRepo = new SalesInvoiceDetailsSqliteRepository();
    
    public salesInvoices() {
        initComponents();
        
        // RTL
        jTabbedPane1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tbCustomers.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tbProducts.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tbInvoicesList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        // set tables data
        
        setCustomerTableData();
        setInvoicesListTableDate();
        
        // sort table
        
        tbInvoicesList.setAutoCreateRowSorter(true);
        
        // table renderer
        
        DefaultTableCellRenderer tbCustomersRenderer = (DefaultTableCellRenderer) tbCustomers.getDefaultRenderer(String.class);
        tbCustomersRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer tbProductsRenderer = (DefaultTableCellRenderer) tbProducts.getDefaultRenderer(String.class);
        tbProductsRenderer.setHorizontalAlignment(0);
        
        DefaultTableCellRenderer tbInvoiceListRenderer = (DefaultTableCellRenderer) tbInvoicesList.getDefaultRenderer(String.class);
        tbInvoiceListRenderer.setHorizontalAlignment(0);
        
        JTableHeader tbProductsHeader = tbProducts.getTableHeader();
        tbProductsHeader.setDefaultRenderer(tbProductsRenderer);
        tbProductsHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
       
        
        JTableHeader tbCustomersHeader = tbCustomers.getTableHeader();
        tbCustomersHeader.setDefaultRenderer(tbCustomersRenderer);
        tbCustomersHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        
        JTableHeader tbInvoicesHeader = tbInvoicesList.getTableHeader();
        tbInvoicesHeader.setDefaultRenderer(tbInvoiceListRenderer);
        tbInvoicesHeader.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        
//        tbProducts.setDefaultRenderer(String.class, renderer);
        
        lbInvoiceId.setVisible(false);
        
        
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
        
        
        // set customer combo box items
        setCbCustomerInvoiceUiData();
        
        
        // add numbers only for textfeilds
        
//        txtTax.addKeyListener(new KeyAdapter() {
//         public void keyPressed(KeyEvent ke) {
//            String value = txtTax.getText();
//            int l = value.length();
//            
//            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
//               txtTax.setEditable(true);
//               lbInvoiceStatus.setText("");
//            } else {
//               txtTax.setEditable(false);
//               lbInvoiceStatus.setText("ادخل ارقام فقط");
//            }
//         }
//      });
//        
//        txtDiscount.addKeyListener(new KeyAdapter() {
//         @Override
//         public void keyPressed(KeyEvent ke) {
//            String value = txtDiscount.getText();
//            int l = value.length();
//            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
//               txtDiscount.setEditable(true);
//               lbInvoiceStatus.setText("");
//            } else {
//               txtDiscount.setEditable(false);
//               lbInvoiceStatus.setText("ادخل ارقام فقط");
//            }
//         }
//      });
//        
//        txtProductQty.addKeyListener(new KeyAdapter() {
//         @Override
//         public void keyPressed(KeyEvent ke) {
//            String value = txtProductQty.getText();
//            int l = value.length();
//            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
//               txtProductQty.setEditable(true);
//               lbInvoiceStatus.setText("");
//            } else {
//               txtProductQty.setEditable(false);
//               lbInvoiceStatus.setText("ادخل ارقام فقط");
//            }
//         }
//      });
//        
//        txtProductPrice.addKeyListener(new KeyAdapter() {
//         @Override
//         public void keyPressed(KeyEvent ke) {
//            String value = txtProductPrice.getText();
//            int l = value.length();
//            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
//               txtProductPrice.setEditable(true);
//               lbInvoiceStatus.setText("");
//            } else {
//               txtProductPrice.setEditable(false);
//               lbInvoiceStatus.setText("ادخل ارقام فقط");
//            }
//         }
//      });
        
//        txtSearchId.addKeyListener(new KeyAdapter() {
//         @Override
//         public void keyPressed(KeyEvent ke) {
//            String value = txtSearchId.getText();
//            int l = value.length();
//            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
//               txtSearchId.setEditable(true);
////               txtSearchId.setText("");
//            } else {
//               txtSearchId.setEditable(false);
////               txtSearchId.setText("ادخل ارقام فقط");
//            }
//         }
//      });
        
        
        
        
        // set print button disabled
        btnPrintInvoice.setEnabled(false);
        
        // set cb search customers names list
        setCbSearchCustomerName();
        
        // cbs listener
        cbSearchInvoiceType.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(cbSearchInvoiceType.getSelectedIndex() > -1) {
                    filterInvoicesListTable();
                } 
            }
        });
        
        cbSearchCustomerName.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(cbSearchCustomerName.getSelectedIndex() > -1) {
                    filterInvoicesListTable();
                } 
            }
        });
        
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        customersPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCustomerPhone = new javax.swing.JTextField();
        btnCustomerSave = new javax.swing.JButton();
        btnCustomerEdit = new javax.swing.JButton();
        btnCustomerDelete = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCustomerId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCustomerNameSearch = new javax.swing.JTextField();
        btnClearCutomerSelection = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCustomers = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbCustomersStatus = new javax.swing.JLabel();
        invoicePanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbInvoiceId = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtInvoicedate = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        cbInvoicetype = new javax.swing.JComboBox<>();
        txtFilePath = new javax.swing.JTextField();
        btnChooseFile = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cbCustomerName = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtProductQty = new javax.swing.JTextField();
        txtProductPrice = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btnAddProduct = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btnDeleteProduct = new javax.swing.JButton();
        btnDeleteAllProducts = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbProducts = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lbTax = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lbProductsTotal = new javax.swing.JLabel();
        lbDiscount = new javax.swing.JLabel();
        lbInvoiceTotal = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        txtTax = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        txtComment = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        lbInvoiceStatus = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        btnSaveInvoice = new javax.swing.JButton();
        btnNewInvoice = new javax.swing.JButton();
        btnPrintInvoice = new javax.swing.JButton();
        invoiceListPanel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        cbSearchInvoiceType = new javax.swing.JComboBox<>();
        cbSearchCustomerName = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        txtSearchId = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbInvoicesList = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        btn_il_edit = new javax.swing.JButton();
        btn_il_delete = new javax.swing.JButton();
        btn_il_print = new javax.swing.JButton();

        setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jTabbedPane1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1200, 1200));

        customersPanel.setPreferredSize(new java.awt.Dimension(1200, 1200));

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
                .addContainerGap(619, Short.MAX_VALUE))
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
                .addContainerGap(652, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1277, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
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

        javax.swing.GroupLayout customersPanelLayout = new javax.swing.GroupLayout(customersPanel);
        customersPanel.setLayout(customersPanelLayout);
        customersPanelLayout.setHorizontalGroup(
            customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(customersPanelLayout.createSequentialGroup()
                        .addGroup(customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        customersPanelLayout.setVerticalGroup(
            customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customersPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(customersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(customersPanelLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 157, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("العملاء", customersPanel);

        invoicePanel.setPreferredSize(new java.awt.Dimension(1200, 1200));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbInvoiceId.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbInvoiceId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel7.setFont(new java.awt.Font("Lucida Grande", 1, 16)); // NOI18N
        jLabel7.setText("فاتورة مبيعات :");

        txtInvoicedate.setDate(new Date());
        txtInvoicedate.setDateFormatString("dd-MMMM-yyyy");

        jLabel8.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("التاريخ :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtInvoicedate, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtInvoicedate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(0, 0, 0))))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("نوع الفاتورة :");

        cbInvoicetype.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        cbInvoicetype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "عادية", "من ملف" }));

        txtFilePath.setEditable(false);
        txtFilePath.setPreferredSize(new java.awt.Dimension(80, 30));
        txtFilePath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFilePathActionPerformed(evt);
            }
        });

        btnChooseFile.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnChooseFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/open-file.png"))); // NOI18N
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("اسم العميل :");

        cbCustomerName.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(779, 779, 779)
                .addComponent(txtFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChooseFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbInvoicetype, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnChooseFile, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbInvoicetype))
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbCustomerName, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel11.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("اسم الصنف :");

        txtProductName.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtProductName.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtProductName.setFocusCycleRoot(true);
        txtProductName.setNextFocusableComponent(txtProductQty);
        txtProductName.setPreferredSize(new java.awt.Dimension(80, 40));

        jLabel12.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("العدد :");

        txtProductQty.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtProductQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtProductQty.setName(""); // NOI18N
        txtProductQty.setNextFocusableComponent(txtProductPrice);
        txtProductQty.setPreferredSize(new java.awt.Dimension(80, 40));

        txtProductPrice.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtProductPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtProductPrice.setNextFocusableComponent(txtProductName);
        txtProductPrice.setPreferredSize(new java.awt.Dimension(80, 40));
        txtProductPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductPriceActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("السعر :");

        btnAddProduct.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/add-to-cart.png"))); // NOI18N
        btnAddProduct.setText("إضافة");
        btnAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtProductQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtProductPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAddProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnDeleteProduct.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnDeleteProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/delete.png"))); // NOI18N
        btnDeleteProduct.setText("حذف الصنف");
        btnDeleteProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteProductActionPerformed(evt);
            }
        });

        btnDeleteAllProducts.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnDeleteAllProducts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/delete-all.png"))); // NOI18N
        btnDeleteAllProducts.setText("حذف الكل");
        btnDeleteAllProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllProductsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteAllProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDeleteProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(btnDeleteAllProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbProducts.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tbProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "اسم الصنف", "العدد", "السعر", "الاجمالي"
            }
        ));
        tbProducts.setRowHeight(30);
        jScrollPane2.setViewportView(tbProducts);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("اجمالي المنتجات :");

        lbTax.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbTax.setForeground(new java.awt.Color(0, 51, 255));
        lbTax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTax.setText("0.00");

        jLabel16.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("الخصم :");

        jLabel17.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("الضريبة :");

        jLabel18.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("الاجمالي :");

        lbProductsTotal.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbProductsTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbProductsTotal.setText("0.00");

        lbDiscount.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbDiscount.setForeground(new java.awt.Color(255, 0, 0));
        lbDiscount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDiscount.setText("0.00");

        lbInvoiceTotal.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbInvoiceTotal.setForeground(new java.awt.Color(0, 153, 0));
        lbInvoiceTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbInvoiceTotal.setText("0.00");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTax, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(lbProductsTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbInvoiceTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbProductsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTax, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbInvoiceTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtTax.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtTax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTax.setText("0.00");
        txtTax.setFocusCycleRoot(true);
        txtTax.setNextFocusableComponent(txtDiscount);
        txtTax.setPreferredSize(new java.awt.Dimension(80, 40));
        txtTax.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTaxKeyReleased(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("الضريبة :");

        jLabel23.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("الخصم :");

        txtDiscount.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtDiscount.setText("0.00");
        txtDiscount.setNextFocusableComponent(txtComment);
        txtDiscount.setPreferredSize(new java.awt.Dimension(80, 40));
        txtDiscount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDiscountKeyReleased(evt);
            }
        });

        txtComment.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtComment.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtComment.setNextFocusableComponent(btnSaveInvoice);
        txtComment.setPreferredSize(new java.awt.Dimension(80, 40));

        jLabel24.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("البيان :");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(txtComment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel25.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("شريط الحالة :");

        lbInvoiceStatus.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        lbInvoiceStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbInvoiceStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbInvoiceStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSaveInvoice.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnSaveInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/save.png"))); // NOI18N
        btnSaveInvoice.setText("حفظ الفاتورة");
        btnSaveInvoice.setPreferredSize(new java.awt.Dimension(100, 30));
        btnSaveInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveInvoiceActionPerformed(evt);
            }
        });

        btnNewInvoice.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnNewInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/new.png"))); // NOI18N
        btnNewInvoice.setText("فاتورة جديدة");
        btnNewInvoice.setPreferredSize(new java.awt.Dimension(100, 30));
        btnNewInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewInvoiceActionPerformed(evt);
            }
        });

        btnPrintInvoice.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btnPrintInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/print.png"))); // NOI18N
        btnPrintInvoice.setText("طباعة الفاتورة");
        btnPrintInvoice.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPrintInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintInvoiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSaveInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addComponent(btnNewInvoice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPrintInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSaveInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrintInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNewInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout invoicePanelLayout = new javax.swing.GroupLayout(invoicePanel);
        invoicePanel.setLayout(invoicePanelLayout);
        invoicePanelLayout.setHorizontalGroup(
            invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invoicePanelLayout.createSequentialGroup()
                .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(invoicePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(invoicePanelLayout.createSequentialGroup()
                        .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        invoicePanelLayout.setVerticalGroup(
            invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invoicePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(invoicePanelLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(invoicePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, invoicePanelLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(112, 112, 112))
        );

        jTabbedPane1.addTab("فاتورة جديدة", invoicePanel);

        invoiceListPanel.setPreferredSize(new java.awt.Dimension(1200, 1200));

        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel19.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("رقم الفاتورة :");

        jLabel20.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("نوع الفاتورة :");

        cbSearchInvoiceType.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        cbSearchInvoiceType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "عادية", "من ملف" }));

        cbSearchCustomerName.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/reload.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("اسم العميل :");

        txtSearchId.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        txtSearchId.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSearchId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchIdKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 553, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSearchInvoiceType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(cbSearchCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchId, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbSearchInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbSearchCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearchId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tbInvoicesList.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        tbInvoicesList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الرقم", "التاريخ", "اسم العميل", "البيان", "الضريبة", "الخصم", "الاجمالي", "نوع الفاتورة", "مسار الملف"
            }
        ));
        tbInvoicesList.setGridColor(new java.awt.Color(102, 102, 102));
        tbInvoicesList.setRowHeight(30);
        tbInvoicesList.setShowGrid(false);
        jScrollPane3.setViewportView(tbInvoicesList);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_il_edit.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btn_il_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/edit.png"))); // NOI18N
        btn_il_edit.setText("تعديل");
        btn_il_edit.setPreferredSize(new java.awt.Dimension(100, 30));
        btn_il_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_il_editActionPerformed(evt);
            }
        });

        btn_il_delete.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btn_il_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/delete.png"))); // NOI18N
        btn_il_delete.setText("حذف");
        btn_il_delete.setPreferredSize(new java.awt.Dimension(100, 30));
        btn_il_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_il_deleteActionPerformed(evt);
            }
        });

        btn_il_print.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        btn_il_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/accountmanagement/ui/images/print.png"))); // NOI18N
        btn_il_print.setText("عرض");
        btn_il_print.setPreferredSize(new java.awt.Dimension(100, 30));
        btn_il_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_il_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(284, 284, 284)
                .addComponent(btn_il_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_il_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_il_print, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_il_print, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addComponent(btn_il_edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_il_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout invoiceListPanelLayout = new javax.swing.GroupLayout(invoiceListPanel);
        invoiceListPanel.setLayout(invoiceListPanelLayout);
        invoiceListPanelLayout.setHorizontalGroup(
            invoiceListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invoiceListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(invoiceListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        invoiceListPanelLayout.setVerticalGroup(
            invoiceListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(invoiceListPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(372, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("قائمة الفواتير", invoiceListPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
//                setCbCustomerInvoiceUiData();
                setCbSearchCustomerName();

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
                    setCbSearchCustomerName();
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
                    setCbSearchCustomerName();
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

    private void btnAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddProductActionPerformed
        addProductToTable();
    }//GEN-LAST:event_btnAddProductActionPerformed

    private void btnDeleteProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteProductActionPerformed
        try {
            DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
            
            if(model.getRowCount() < 1) {
                JOptionPane.showMessageDialog(null, "اختر منتج اولا");
                return;
            }
            
            int row = tbProducts.getSelectedRow();          
          
            lbInvoiceStatus.setText("تم حذف المنتج " + tbProducts.getValueAt(row, 0));
            
            model.removeRow(row);
            
            setInvoiceTotals();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnDeleteProductActionPerformed

    private void btnDeleteAllProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllProductsActionPerformed
        try {
            DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
            
            if(model.getRowCount() == 0) {
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حذف جميع المنتجات ؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result == JOptionPane.YES_OPTION) {
                model.setRowCount(0);
                setInvoiceTotals();
                lbInvoiceStatus.setText("تم حذف جميع المنتجات" );
            }
                      
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnDeleteAllProductsActionPerformed

    private void txtFilePathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFilePathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFilePathActionPerformed

    private void txtProductPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductPriceActionPerformed
        addProductToTable();
    }//GEN-LAST:event_txtProductPriceActionPerformed

    private void txtTaxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTaxKeyReleased
        setInvoiceTotals();
    }//GEN-LAST:event_txtTaxKeyReleased

    private void txtDiscountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDiscountKeyReleased
        setInvoiceTotals();
    }//GEN-LAST:event_txtDiscountKeyReleased

    private void btnSaveInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveInvoiceActionPerformed
        if(!isUpdateInvoice) {
            saveInvoice();
        } else {
            updateInvoice();
        }
    }//GEN-LAST:event_btnSaveInvoiceActionPerformed

    private void btnPrintInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintInvoiceActionPerformed
        long headerId = 0;
        try {
            headerId = Long.valueOf(lbInvoiceId.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        printInvoice(headerId);
    }//GEN-LAST:event_btnPrintInvoiceActionPerformed

    private void btnNewInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewInvoiceActionPerformed
        int result = JOptionPane.showConfirmDialog(null, "هل تريد فتح فاتورة جديدة ؟", "تأكيد", JOptionPane.YES_NO_OPTION);
        if(result == JOptionPane.YES_OPTION){
            resetInvoice();
            btnSaveInvoice.setEnabled(true);
            btnPrintInvoice.setEnabled(false);
            lbInvoiceStatus.setText("تم فتح فاتورة جديدة");
            isUpdateInvoice = false;
            enableInvoiceControls();
        }
    }//GEN-LAST:event_btnNewInvoiceActionPerformed

    private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileActionPerformed
        openFileChooser();
    }//GEN-LAST:event_btnChooseFileActionPerformed

    private void btn_il_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_il_printActionPerformed
        try {
            int row = tbInvoicesList.getSelectedRow();
            
            if(row == -1) {
                JOptionPane.showMessageDialog(null, "اختر الفاتورة اولا");
                return;
            }
            
            String type = tbInvoicesList.getValueAt(row, 7).toString();
            String filePath = tbInvoicesList.getValueAt(row, 8).toString();
            long id = (long) tbInvoicesList.getValueAt(row, 0);
            
            File file = new File(filePath);
            
            if(type.equals("من ملف")){
                showInvoiceFile(file);
            } else {
                printInvoice(id);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_il_printActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setInvoicesListTableDate();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSearchIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchIdKeyReleased
        try{
            String id = txtSearchId.getText();
            filterInvoicesListById(id);
        } catch(Exception e) {
            
        }
        
    }//GEN-LAST:event_txtSearchIdKeyReleased

    private void btn_il_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_il_deleteActionPerformed
        try{
            int row = tbInvoicesList.getSelectedRow();
            String type = tbInvoicesList.getValueAt(row, 7).toString();
            
            if(row == -1) {
                JOptionPane.showMessageDialog(null, "اختر الفاتورة اولا");
                return;
            }
            
            long id = (long) tbInvoicesList.getValueAt(row, 0);
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حذف الفاتورة رقم " + id + "؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result != JOptionPane.YES_OPTION) {
                return;
            }
            
            if(type.equals("من ملف"))   {
                if(headerRepo.delete(id)) {
                    JOptionPane.showMessageDialog(null, "تم حذف الفاتورة رقم " + id);
                    setInvoicesListTableDate();
                }
            } else {
                if(headerRepo.delete(id) && detailsRepo.delete(id)) {
                JOptionPane.showMessageDialog(null, "تم حذف الفاتورة رقم " + id);
                setInvoicesListTableDate();
            }
            }
            
            
            
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_il_deleteActionPerformed

    private void btn_il_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_il_editActionPerformed
        try {
            
            int row = tbInvoicesList.getSelectedRow();
            
            if(row == -1) {
                JOptionPane.showMessageDialog(null, "اختر فاتورة اولا");
                return;
            }
            
            jTabbedPane1.setSelectedIndex(1);
            
            resetInvoice();
            enableInvoiceControls();
            
            long id = (long) tbInvoicesList.getValueAt(row, 0);
            
            SalesInvoiceHeader header = headerRepo.findById(id);
            ArrayList<SalesInvoiceDetails> details = detailsRepo.findByHeaderID(id);
            
            txtInvoicedate.setDate(new Date(header.getDate()));
            Customer customer = customerRepo.findById(header.getCustomerId());
            cbCustomerName.setSelectedItem(customer.getName());
            txtFilePath.setText(header.getFilePath());
            lbInvoiceId.setText(String.valueOf(id));
            
            String type = "";
            
            if(header.isIsFileType()) {
                type = "من ملف";
            } else {
                type = "عادية";
            }
            cbInvoicetype.setSelectedItem(type);
            
            DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
            model.setRowCount(0);
            
            for (SalesInvoiceDetails detail : details) {
                Vector vector = new Vector();
                vector.add(detail.getProductName());
                vector.add(detail.getProductQty());
                vector.add(detail.getProductPrice());
                vector.add(detail.getProductTotal());
                
                model.addRow(vector);
            }
            tbProducts.setModel(model);
            
            txtTax.setText(String.valueOf(header.getTax()));
            txtDiscount.setText(String.valueOf(header.getDiscount()));
            txtComment.setText(header.getComment());
            
            setInvoiceTotals();
            isUpdateInvoice = true;
            btnSaveInvoice.setEnabled(true);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_il_editActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnClearCutomerSelection;
    private javax.swing.JButton btnCustomerDelete;
    private javax.swing.JButton btnCustomerEdit;
    private javax.swing.JButton btnCustomerSave;
    private javax.swing.JButton btnDeleteAllProducts;
    private javax.swing.JButton btnDeleteProduct;
    private javax.swing.JButton btnNewInvoice;
    private javax.swing.JButton btnPrintInvoice;
    private javax.swing.JButton btnSaveInvoice;
    private javax.swing.JButton btn_il_delete;
    private javax.swing.JButton btn_il_edit;
    private javax.swing.JButton btn_il_print;
    private javax.swing.JComboBox<String> cbCustomerName;
    private javax.swing.JComboBox<String> cbInvoicetype;
    private javax.swing.JComboBox<String> cbSearchCustomerName;
    private javax.swing.JComboBox<String> cbSearchInvoiceType;
    private javax.swing.JPanel customersPanel;
    private javax.swing.JPanel invoiceListPanel;
    private javax.swing.JPanel invoicePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbCustomersStatus;
    private javax.swing.JLabel lbDiscount;
    private javax.swing.JLabel lbInvoiceId;
    private javax.swing.JLabel lbInvoiceStatus;
    private javax.swing.JLabel lbInvoiceTotal;
    private javax.swing.JLabel lbProductsTotal;
    private javax.swing.JLabel lbTax;
    private javax.swing.JTable tbCustomers;
    private javax.swing.JTable tbInvoicesList;
    private javax.swing.JTable tbProducts;
    private javax.swing.JTextField txtComment;
    private javax.swing.JTextField txtCustomerId;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtCustomerNameSearch;
    private javax.swing.JTextField txtCustomerPhone;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtFilePath;
    private com.toedter.calendar.JDateChooser txtInvoicedate;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtProductPrice;
    private javax.swing.JTextField txtProductQty;
    private javax.swing.JTextField txtSearchId;
    private javax.swing.JTextField txtTax;
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
        try {
            cbCustomerName.removeAllItems();
            ArrayList<Customer> list = customerRepo.findAll();
            for (Customer customer : list) {
                cbCustomerName.addItem(customer.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }

    private void addProductToTable() {
        try {
            
            // validation
            
            if(txtProductName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل اسم الصنف");
                return;
            }
            
            if(txtProductQty.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل الكمية");
                return;
            }
            
            if(txtProductPrice.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ادخل سعر الصنف");
                return;
            }
            
            String productName = txtProductName.getText();
            double productQty = Double.valueOf(txtProductQty.getText());
            double productPrice = Double.valueOf(txtProductPrice.getText());
            double total = productQty * productPrice;
            
            
            DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
            Vector vector = new Vector();
            vector.add(productName);
            vector.add(productQty);
            vector.add(productPrice);
            vector.add(total);
            
            model.addRow(vector);
            
            tbProducts.setModel(model);
            
            // clear text feilds 
            txtProductQty.setText("");
            txtProductPrice.setText("");
            txtProductName.setText("");
            
            // set focus on txtproductname
            txtProductName.requestFocus();
            
            setInvoiceTotals();
            
            lbInvoiceStatus.setText("تم اضافة المنتج " + productName);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setInvoiceTotals() {
        try {
            
            double tax = Double.valueOf(txtTax.getText());
            double discount = Double.valueOf(txtDiscount.getText());
            
            DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
            int rows = model.getRowCount();
            
            double productsTotal = 0.00;
            if(rows > 0) {
                
                for (int i = 0; i < rows; i++) {
                    productsTotal = productsTotal + (double) tbProducts.getValueAt(i, 3);
                }
            }
            
            double total = productsTotal + tax - discount;
            invoiceTotal = total;
            
            lbInvoiceTotal.setText(numberFormater.format(total));
            lbDiscount.setText(numberFormater.format(discount));
            lbTax.setText(numberFormater.format(tax));
            lbProductsTotal.setText(numberFormater.format(productsTotal));
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void saveInvoice() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
            
            if(cbInvoicetype.getSelectedItem().toString().equals("من ملف")) {
                saveInvoiceAsFile();
                return;
            }
            
            if(model.getRowCount() < 1) {
                JOptionPane.showMessageDialog(null, "ادخل بعض الاصناف اولا");
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حفظ الفاتورة؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result != JOptionPane.YES_OPTION) {
                return;
            }
            
            // invoice header
            
            Date dateBeforeFormat = txtInvoicedate.getDate();
            String date = df.format(dateBeforeFormat);
            
            String customerName = cbCustomerName.getSelectedItem().toString();
            
            HashMap customersMap = getCustomerHashMap();
            int customerId = (int) customersMap.get(customerName);
            
            boolean isFileType;
            String filePath = null;
            
            if(cbInvoicetype.getSelectedItem().toString().equals("من ملف")){
                isFileType = true;
                filePath = txtFilePath.getText();
            } else {
                isFileType = false;
            }
            
            double tax = Double.valueOf(txtTax.getText());
            double discount = Double.valueOf(txtDiscount.getText());
            String comment = txtComment.getText();
            
            
            SalesInvoiceHeader header = SalesInvoiceHeader.builder()
                    .date(date)
                    .customerId(customerId)
                    .total(invoiceTotal)
                    .isFileType(isFileType)
                    .filePath(filePath)
                    .tax(tax)
                    .discount(discount)
                    .comment(comment)
                    .build();
            
            long headerId = headerRepo.save(header);
            lbInvoiceStatus.setText("جاري حفظ الفاتورة ...");
            
            // invoice details
            
            
            
            int rowCount = model.getRowCount();
            
            for (int i = 0; i < rowCount; i++) {
                SalesInvoiceDetails details = SalesInvoiceDetails.builder()
                        .headerId(headerId)
                        .productName((String) tbProducts.getValueAt(i, 0))
                        .productQty((double) tbProducts.getValueAt(i, 1))
                        .productPrice((double) tbProducts.getValueAt(i, 2))
                        .productTotal((double) tbProducts.getValueAt(i, 3))
                        .build();
                
                detailsRepo.save(details);
            }
            
            resetInvoice();
            btnPrintInvoice.setEnabled(true);
            btnSaveInvoice.setEnabled(false);
            lbInvoiceStatus.setText("تم حفظ الفاتورة بالرقم :" + headerId);
            lbInvoiceId.setText(String.valueOf(headerId));
            setInvoicesListTableDate();
            disableInvoiceControls();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private HashMap<String, Integer> getCustomerHashMap() {
        ArrayList<Customer> list = customerRepo.findAll();
        HashMap<String, Integer> map = new HashMap();
        
        for (Customer customer : list) {
            map.put(customer.getName(), customer.getId());
        }
        
        return map;
    }

    private void printInvoice(long headerId) {
        try {

            
            HashMap customersMap = getCustomerHashMap();
            
            SalesInvoiceHeader header = headerRepo.findById(headerId);
            
            
            int customerId = header.getCustomerId();
            Customer customer = customerRepo.findById(customerId);
            String date = header.getDate();
            String comment = header.getComment();
            double total = header.getTotal();
            double tax = header.getTax();
            double discount = header.getDiscount();
            
            String reportName = reportsPath +"salesInvoice.jasper";
            

            HashMap map = new HashMap();

            map.put("id", headerId);
            map.put("date", date);
            map.put("customerName", customer.getName());
            map.put("total", numberFormater.format(total));
            map.put("tax", numberFormater.format(tax));
            map.put("comment", comment);
            map.put("discount", numberFormater.format(discount));
            
            ArrayList<SalesInvoiceDetails> list = detailsRepo.findByHeaderID(headerId);            
            
            InputStream report;
            report = getClass().getResourceAsStream(reportName);
            
            JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
            

            JasperPrint jPrint = JasperFillManager.fillReport(report, map, ds);
            
            JasperViewer.viewReport(jPrint, false);
            
            lbInvoiceId.setText("تم طباعة فاتورة رقم :" + headerId);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
    }

    private void resetInvoice() {
        try {
            
            txtInvoicedate.setDate(new Date());
            cbCustomerName.setSelectedIndex(0);
            cbInvoicetype.setSelectedIndex(0);
            txtFilePath.setText("");
            txtProductName.setText("");
            txtProductPrice.setText("");
            txtProductQty.setText("");
            DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
            model.setRowCount(0);
            lbDiscount.setText("0.00");
            lbTax.setText("0.00");
            lbInvoiceTotal.setText("0.00");
            lbProductsTotal.setText("0.00");
            txtTax.setText("0.00");
            txtDiscount.setText("0.00");
            txtComment.setText("");
            lbInvoiceId.setText("");
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void saveInvoiceAsFile() {
        try {
            
            

            if(txtFilePath.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "اختر ملف الفاتورة ثم اضفط حفظ");
                return;
            }
            
            File src = new File(txtFilePath.getText());
            
            String extension = "";

            int i = src.getName().lastIndexOf('.');
            if (i > 0) {
                extension = src.getName().substring(i+1);
            }
            
            String newFileName = UUID.randomUUID().toString() + "." + extension;
            
            String distPath = System.getProperty("user.dir") + "/invoices/" + newFileName ;
            File dist = new File(distPath);
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حفظ الفاتورة؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result != JOptionPane.YES_OPTION) {
                return;
            }
            

            copyFile(src, dist);

            // invoice header
            
            Date dateBeforeFormat = txtInvoicedate.getDate();
            String date = df.format(dateBeforeFormat);
            
            String customerName = cbCustomerName.getSelectedItem().toString();
            
            HashMap customersMap = getCustomerHashMap();
            int customerId = (int) customersMap.get(customerName);
            
            boolean isFileType;
            String filePath = null;
            
            if(cbInvoicetype.getSelectedItem().toString().equals("من ملف")){
                isFileType = true;
                filePath = distPath;
            } else {
                isFileType = false;
            }
            
            double tax = 0;
            double discount = 0;
            String comment = txtComment.getText();
            
            
            SalesInvoiceHeader header = SalesInvoiceHeader.builder()
                    .date(date)
                    .customerId(customerId)
                    .total(0)
                    .isFileType(isFileType)
                    .filePath(filePath)
                    .tax(tax)
                    .discount(discount)
                    .comment(comment)
                    .build();
            
            lbInvoiceStatus.setText("جاري حفظ الفاتورة ...");
            
            long headerId = headerRepo.save(header);
            
            if(headerId > 0) {
                lbInvoiceStatus.setText("تم حفظ الفاتورة بالرقم :" + headerId);
                resetInvoice();
                setInvoicesListTableDate();
                disableInvoiceControls();
                btnSaveInvoice.setEnabled(false);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void openFileChooser() {
        try {
//            JFrame frame = new JFrame("اختر ملف");           
//            frame.setSize(520, 400);                                  
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            JTextArea textArea = new JTextArea();
//            JScrollPane textAreaScroller = new JScrollPane(textArea);
//
//
//            frame.add(textAreaScroller);
            
            JFileChooser chooser = new JFileChooser();
            
            int returnedValue = chooser.showOpenDialog(this);
            
            if(returnedValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                String filepath = selectedFile.getPath();
                
//                System.out.println(selectedFile.getName());
//                System.out.println(filepath);
                txtFilePath.setText(filepath);
            }
           
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void copyFile(File src, File dist) throws FileNotFoundException, IOException {
        
        FileInputStream in = null;
        FileOutputStream out = null;
        
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dist);
            
            
            
            int c;
            
            while ((c = in.read()) != -1) {
                
                out.write(c);
                System.out.println(c);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
        finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        
    }



    private void setInvoicesListTableDate() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbInvoicesList.getModel();
            model.setRowCount(0);
            
            
            
            ArrayList<SalesInvoiceHeader> list = headerRepo.findAllDesc();
            
            for (SalesInvoiceHeader header : list) {
                Vector vector = new Vector();
                vector.add(header.getId());
                vector.add(header.getDate());
                Customer customer = customerRepo.findById(header.getCustomerId());
                vector.add(customer.getName());
                vector.add(header.getComment());
                vector.add(numberFormater.format(header.getTax()));
                vector.add(numberFormater.format(header.getDiscount()));
                vector.add(numberFormater.format(header.getTotal()));
                String invoiceType = "";
                String filePath = "";
                if(header.isIsFileType()){
                    invoiceType = "من ملف";
                    filePath = header.getFilePath();
                } else {
                    invoiceType = "عادية";
                    filePath = "لا يوجد";
                }
                vector.add(invoiceType);
                
                vector.add(filePath);
                
                model.addRow(vector);
            }
            
            tbInvoicesList.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void filterInvoicesListTable() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbInvoicesList.getModel();
            model.setRowCount(0);
            
            HashMap customersMap = getCustomerHashMap();
//            String id = txtSearchId.getText();
            String customerName = cbSearchCustomerName.getSelectedItem().toString();
            int customerId = (int) customersMap.get(customerName);
            String cusId = String.valueOf(customerId);
//            String date = txtSearchDate.getText();
            String isFileType = "0" ;
            
            if(cbSearchInvoiceType.getSelectedItem().toString().equals("من ملف")) {
                isFileType = "1";
            } else {
                isFileType = "0";
            }
                    
            
            
            ArrayList<SalesInvoiceHeader> list = headerRepo.findAllBySearchWords(cusId, isFileType);
            
            for (SalesInvoiceHeader header : list) {
                Vector vector = new Vector();
                vector.add(header.getId());
                vector.add(header.getDate());
                Customer customer = customerRepo.findById(header.getCustomerId());
                vector.add(customer.getName());
                vector.add(header.getComment());
                vector.add(numberFormater.format(header.getTax()));
                vector.add(numberFormater.format(header.getDiscount()));
                vector.add(numberFormater.format(header.getTotal()));
                String invoiceType = "";
                String filePath = "";
                if(header.isIsFileType()){
                    invoiceType = "من ملف";
                    filePath = header.getFilePath();
                } else {
                    invoiceType = "عادية";
                    filePath = "لا يوجد";
                }
                vector.add(invoiceType);
                
                vector.add(filePath);
                
                model.addRow(vector);
            }
            
            tbInvoicesList.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void setCbSearchCustomerName() {
        try {
            cbSearchCustomerName.removeAllItems();
            ArrayList<Customer> list = customerRepo.findAll();
            
            for (Customer customer : list) {
                cbSearchCustomerName.addItem(customer.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void showInvoiceFile(File file) {
        try {
            if (OSDetector.isWindows())
        {
            Runtime.getRuntime().exec(new String[]
            {"rundll32", "url.dll,FileProtocolHandler",
             file.getAbsolutePath()});
//            return true;
        } else if (OSDetector.isLinux() || OSDetector.isMac())
        {
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open",
                                                   file.getAbsolutePath()});
//            return true;
        } else
        {
            // Unknown OS, try with desktop
            if (Desktop.isDesktopSupported())
            {
                Desktop.getDesktop().open(file);
//                return true;
            }
            else
            {
//                return false;
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }


    private void filterInvoicesListById(String id) {
        try{
            DefaultTableModel model = (DefaultTableModel) tbInvoicesList.getModel();
            model.setRowCount(0);                                            
            
            ArrayList<SalesInvoiceHeader> list = headerRepo.findAllById(id);
            
            for (SalesInvoiceHeader header : list) {
                Vector vector = new Vector();
                vector.add(header.getId());
                vector.add(header.getDate());
                Customer customer = customerRepo.findById(header.getCustomerId());
                vector.add(customer.getName());
                vector.add(header.getComment());
                vector.add(numberFormater.format(header.getTax()));
                vector.add(numberFormater.format(header.getDiscount()));
                vector.add(numberFormater.format(header.getTotal()));
                String invoiceType = "";
                String filePath = "";
                if(header.isIsFileType()){
                    invoiceType = "من ملف";
                    filePath = header.getFilePath();
                } else {
                    invoiceType = "عادية";
                    filePath = "لا يوجد";
                }
                vector.add(invoiceType);
                
                vector.add(filePath);
                
                model.addRow(vector);
            }
            
            tbInvoicesList.setModel(model);
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void updateInvoice() {
        try {
            DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
            
            if(cbInvoicetype.getSelectedItem().toString().equals("من ملف")) {
//                saveInvoiceAsFile();
                updateInvoiceAsFile();
                return;
            }
            
            if(model.getRowCount() < 1) {
                JOptionPane.showMessageDialog(null, "ادخل بعض الاصناف اولا");
                return;
            }
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حفظ الفاتورة؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result != JOptionPane.YES_OPTION) {
                return;
            }
            
            // invoice header
            
            Date dateBeforeFormat = txtInvoicedate.getDate();
            String date = df.format(dateBeforeFormat);
            
            String customerName = cbCustomerName.getSelectedItem().toString();
            
            HashMap customersMap = getCustomerHashMap();
            int customerId = (int) customersMap.get(customerName);
            
            boolean isFileType;
            String filePath = null;
            
            if(cbInvoicetype.getSelectedItem().toString().equals("من ملف")){
                isFileType = true;
                filePath = txtFilePath.getText();
            } else {
                isFileType = false;
            }
            
            double tax = Double.valueOf(txtTax.getText());
            double discount = Double.valueOf(txtDiscount.getText());
            String comment = txtComment.getText();
            long headerId = Long.valueOf(lbInvoiceId.getText());
            
            SalesInvoiceHeader header = SalesInvoiceHeader.builder()
                    .id(headerId)
                    .date(date)
                    .customerId(customerId)
                    .total(invoiceTotal)
                    .isFileType(isFileType)
                    .filePath(filePath)
                    .tax(tax)
                    .discount(discount)
                    .comment(comment)
                    .build();
            
            lbInvoiceStatus.setText("جاري حفظ الفاتورة ...");
            
            headerRepo.update(header);
            
            
            // invoice details
            
            // delete all previous details
            
            detailsRepo.delete(headerId);
            
            int rowCount = model.getRowCount();
            
            for (int i = 0; i < rowCount; i++) {
                SalesInvoiceDetails details = SalesInvoiceDetails.builder()
                        .headerId(headerId)
                        .productName((String) tbProducts.getValueAt(i, 0))
                        .productQty((double) tbProducts.getValueAt(i, 1))
                        .productPrice((double) tbProducts.getValueAt(i, 2))
                        .productTotal((double) tbProducts.getValueAt(i, 3))
                        .build();
                
                detailsRepo.save(details);
            }
            
            resetInvoice();
            btnPrintInvoice.setEnabled(true);
            btnSaveInvoice.setEnabled(false);
            lbInvoiceStatus.setText("تم حفظ الفاتورة بالرقم :" + headerId);
            lbInvoiceId.setText(String.valueOf(headerId));
            setInvoicesListTableDate();
            disableInvoiceControls();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void updateInvoiceAsFile() {
        try {
            
            long headerId = Long.valueOf(lbInvoiceId.getText());

            if(txtFilePath.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "اختر ملف الفاتورة ثم اضفط حفظ");
                return;
            }
            
            File src = new File(txtFilePath.getText());
            
            String extension = "";

            int i = src.getName().lastIndexOf('.');
            if (i > 0) {
                extension = src.getName().substring(i+1);
            }
            
            String newFileName = UUID.randomUUID().toString() + "." + extension;
            
            String distPath = System.getProperty("user.dir") + "/invoices/" + newFileName ;
            File dist = new File(distPath);
            
            int result = JOptionPane.showConfirmDialog(null, "هل تريد حفظ الفاتورة؟", "تأكيد", JOptionPane.YES_NO_OPTION);
            
            if(result != JOptionPane.YES_OPTION) {
                return;
            }
            

            copyFile(src, dist);

            // invoice header
            
            Date dateBeforeFormat = txtInvoicedate.getDate();
            String date = df.format(dateBeforeFormat);
            
            String customerName = cbCustomerName.getSelectedItem().toString();
            
            HashMap customersMap = getCustomerHashMap();
            int customerId = (int) customersMap.get(customerName);
            
            boolean isFileType;
            String filePath = null;
            
            if(cbInvoicetype.getSelectedItem().toString().equals("من ملف")){
                isFileType = true;
                filePath = distPath;
            } else {
                isFileType = false;
            }
            
            double tax = 0;
            double discount = 0;
            String comment = txtComment.getText();
            
            
            SalesInvoiceHeader header = SalesInvoiceHeader.builder()
                    .id(headerId)
                    .date(date)
                    .customerId(customerId)
                    .total(0)
                    .isFileType(isFileType)
                    .filePath(filePath)
                    .tax(tax)
                    .discount(discount)
                    .comment(comment)
                    .build();
            
            lbInvoiceStatus.setText("جاري حفظ الفاتورة ...");
            
            boolean result1 = headerRepo.update(header);
            
            if(result1) {
                lbInvoiceStatus.setText("تم تعديل الفاتورة  :" + headerId);
                resetInvoice();
                setInvoicesListTableDate();
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void enableInvoiceControls() {
        txtInvoicedate.setEnabled(true);
        cbCustomerName.setEnabled(true);
        btnChooseFile.setEnabled(true);
        cbInvoicetype.setEnabled(true);
        txtProductName.setEnabled(true);
        txtProductPrice.setEnabled(true);
        txtProductQty.setEnabled(true);
        btnAddProduct.setEnabled(true);
        btnDeleteProduct.setEnabled(true);
        btnDeleteAllProducts.setEnabled(true);
        txtTax.setEnabled(true);
        txtDiscount.setEnabled(true);
        txtComment.setEnabled(true);
    }
    private void disableInvoiceControls() {
        txtInvoicedate.setEnabled(false);
        cbCustomerName.setEnabled(false);
        btnChooseFile.setEnabled(false);
        cbInvoicetype.setEnabled(false);
        txtProductName.setEnabled(false);
        txtProductPrice.setEnabled(false);
        txtProductQty.setEnabled(false);
        btnAddProduct.setEnabled(false);
        btnDeleteProduct.setEnabled(false);
        btnDeleteAllProducts.setEnabled(false);
        txtTax.setEnabled(false);
        txtDiscount.setEnabled(false);
        txtComment.setEnabled(false);
    }
}
