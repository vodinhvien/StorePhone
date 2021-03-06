package GUI;

import java.util.ArrayList;
import java.util.Vector;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.DocumentException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import DTO.ProductDTO;
import BUS.Product_BUS;
import BUS.DetailProduct_BUS;
import DTO.CategoryDTO;
import DTO.chitietsanphamDTO;
import Excel.ExportExcel;
import Excel.ImportExcel;
import PDF.ExportPDF;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import stolenBtn.ExportExcelButton;
import stolenBtn.ExportPdfButton;
import stolenBtn.ImportExcelButton;
import stolenBtn.SuaButton;
import stolenBtn.ThemButton;
import stolenBtn.XoaButton;

public class Product_Frame extends JPanel {
	private int WIDTH;
	private JPanel header, data;
	private static DefaultTableModel model, model_result;
	private Calendar cal = Calendar.getInstance();
	private ArrayList <ProductDTO> listSP, sr;
	private ExportExcel wfe = new ExportExcel();
	private Product_BUS pd_bus = new Product_BUS();
	private JComboBox<String> gia;
	private JComboBox<CategoryDTO> thuonghieu;
	private DetailProduct_BUS dtpd_bus = new DetailProduct_BUS();
        
	public Product_Frame() {
		
	}
	
	public Product_Frame(int WIDTH) {
		this.WIDTH = WIDTH;
		init();
	}
	
        public static void setTModel(DefaultTableModel models){
                model = models;
        }
        
        public static DefaultTableModel getTModel(){
            return model;
        }
        
	public void init() {
		JTable product_data, product_result;		
		Font lb = new Font("Tahoma", Font.PLAIN, 16);
                Font cn = new Font("Arial", Font.BOLD, 18);
                Font tk = new Font("Tahoma", Font.PLAIN, 18);
		ThemButton btn_them;
                XoaButton btn_xoa;
                SuaButton btn_sua;
		setLayout(null);
		setBackground(null);
		setBounds(new Rectangle(0, 0, WIDTH, 960));		
		
		
		//DATA
			data = new JPanel();
			data.setBounds(0, 350, WIDTH, 612);
			data.setLayout(new CardLayout());
			model = getModel(model);
			model = getDataRow(model);
			product_data = new JTable(model){
                            @Override
                    public boolean isCellEditable(int row, int column){
                        return false;
                    }
                    @Override
                    public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                    java.awt.Component component = super.prepareRenderer(renderer, row, column);
                    int rendererWidth = component.getPreferredSize().width;
                    javax.swing.table.TableColumn tableColumn = getColumnModel().getColumn(column);
                    tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                    return component;
                    }
                        };
                        product_data.setAutoCreateRowSorter(true);
                                product_data.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				product_data.setDefaultEditor(Object.class, null);
                                product_data.setFillsViewportHeight(true);
        product_data.setFont(new Font("Segoe UI", 0, 16));
        product_data.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        product_data.setRowHeight(40);

        // color
        int bgColor = 235;
        int color = 0;
        product_data.getTableHeader().setBackground(new Color(bgColor, bgColor, bgColor));
       product_data.getTableHeader().setForeground(new Color(color, color, color));
        product_data.setBackground(new Color(bgColor, bgColor, bgColor));
        product_data.setForeground(new Color(color, color, color));
                                double total = 0;
                               DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        product_data.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
                                double[] percentages = new double[]{1,1.5,5,1,1};
        for (int i = 0; i < product_data.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < product_data.getColumnModel().getColumnCount(); i++) {
            TableColumn column = product_data.getColumnModel().getColumn(i);
            column.setPreferredWidth((int) (getPreferredSize().width * (percentages[i] / total)));
        }
			product_data.setDefaultEditor(Object.class, null);
			product_data.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane scroll_data = new JScrollPane(product_data);
			data.add(scroll_data);
		//END OF DATA
		
		//HEADER
			header = new JPanel();
			header.setBounds(0, 0, WIDTH, 380);
			header.setLayout(null);
			/*//FUNCTION
				JPanel function = new JPanel();
				JPanel top_function = new JPanel();
				top_function.setLayout(null);
				top_function.setBounds(5, 5, 200, 190);
				JPanel bottom_function = new JPanel();
				bottom_function.setLayout(null);
				bottom_function.setBounds(5, 195, 200, 255);
				function.setLayout(null);
				function.setBounds(0, 0, 210, 450);
				top_function.setBorder(new TitledBorder(null, "Ch???c N??ng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				bottom_function.setBorder(new TitledBorder(null, "T??m Ki???m C?? B???n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				btn_them = new ThemButton();
				btn_them.setFont(lb);
				btn_them.setBounds(3, 25, 195, 45);
				btn_xoa = new XoaButton();
				btn_xoa.setFont(lb);
				btn_xoa.setBounds(3, 75, 195, 45);
				btn_sua = new SuaButton();
				btn_sua.setFont(lb);
				btn_sua.setBounds(3, 125, 195, 45);
				JTextField search_box = new JTextField();
				search_box.setBounds(10, 25, 180, 35);
				search_box.setHorizontalAlignment(JTextField.CENTER);
				JButton search_maPD = new JButton("T??m Ki???m Theo M?? S???n ph???m");
				search_maPD.setBounds(5, 75, 190, 40);
				JButton search_tenPD = new JButton("T??m Ki???m Theo T??n S???n ph???m");
				search_tenPD.setBounds(5, 125, 190, 40);
				top_function.add(btn_them);
				top_function.add(btn_xoa);
				top_function.add(btn_sua);
				bottom_function.add(search_box);
				bottom_function.add(search_maPD);
				bottom_function.add(search_tenPD);				
				function.add(top_function);
				function.add(bottom_function);
			//END OF FUNCTION*/
				
			//RESULT
				JButton find,upload;
				JPanel result = new JPanel();
				result.setBounds(0, 0, WIDTH, 450);
				result.setLayout(null);
				JPanel search_advance, result_data,search;
                                JLabel image_product;
                                //H??NH ???NH S???N PH???M
                                        image_product = new JLabel(new ImageIcon(new ImageIcon(Product_Frame.class.getResource("/icons/arrow.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
					image_product.setLayout(null);
					image_product.setBounds(5, 80, WIDTH-1300, 220);
					image_product.setBorder(new TitledBorder(null, "H??nh ???nh S???n Ph???m", TitledBorder.LEADING, TitledBorder.TOP, null, null));
                                        upload = new JButton("Thay ?????i H??nh ???nh");
                                        upload.setBounds(5, 305, WIDTH -1300, 35);
                                        upload.setFont(lb);
                                //
                                //CH???C N??NG                                        
                                        JPanel function = new JPanel();
                                        function.setLayout(null);
                                        function.setBounds(220, 265, WIDTH -260, 80);                                      
                                        function.setBorder(new TitledBorder(null, "Ch???c N??ng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				
                                        btn_them = new ThemButton();
                                        btn_them.setFont(cn);
                                        btn_them.setBounds(20, 25, 195, 45);
                                        btn_xoa = new XoaButton();
                                        btn_xoa.setFont(cn);
                                        btn_xoa.setBounds(220, 25, 195, 45);
                                        btn_sua = new SuaButton();
                                        btn_sua.setFont(cn);
                                        btn_sua.setBounds(420, 25, 195, 45);
                                        function.add(btn_them);
                                        function.add(btn_xoa);
                                        function.add(btn_sua);
                                        //?????C XU???T FILE
                                        ImportExcelButton docexcel; 
                                        ExportExcelButton ghiexcel;
                                        ExportPdfButton ghipdf;
					docexcel = new ImportExcelButton();
					ghiexcel = new ExportExcelButton();
					ghipdf = new ExportPdfButton();                                        
					docexcel.setBounds(620, 25, 195, 45);
                                        docexcel.setFont(cn);
					ghiexcel.setBounds(820, 25, 195, 45);
                                        ghiexcel.setFont(cn);
					ghipdf.setBounds(1020, 25, 195, 45);
                                        ghipdf.setFont(cn);
                                        function.add(docexcel);
                                        function.add(ghiexcel);
                                        function.add(ghipdf);
                                        //K???T TH??C ?????C XU???T FILE
                                //K???T TH??C CH???C N??NG
                                //T??M KI???M
                                        search = new JPanel();
					search.setLayout(null);
					search.setBounds(10, 5, WIDTH -570, 60);					
                                        JTextField search_box = new JTextField();
                                        search_box.setBounds(0, 0, WIDTH -580, 50);
                                        search_box.setHorizontalAlignment(JTextField.CENTER);
                                        search_box.setFont(lb);
                                        search.add(search_box);                                        
                                        //  K???T TH??C T??M KI???M
                                //T??M KI???M N??NG CAO                                        
					search_advance = new JPanel();
					search_advance.setLayout(null);
					search_advance.setBounds(950, 5, WIDTH -500, 90);
					search_advance.setBorder(new TitledBorder(null, "T??m Ki???m N??ng Cao", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					JButton search_maPD = new JButton("TK Theo M?? S???n Ph???m");
                                        search_maPD.setBounds(90, 50, 220, 30);
                                        search_maPD.setFont(lb);
                                        JButton search_tenPD = new JButton("TK Theo T??n S???n Ph???m");
                                        search_tenPD.setBounds(320, 50, 220, 30);
                                        search_tenPD.setFont(lb);
                                        //Gi?? c???
						JLabel gia_lb = new JLabel("Gi?? c???:");
						gia_lb.setBounds(20, 7, 100, 50);
						gia_lb.setFont(tk);
						gia_lb.setHorizontalAlignment(JLabel.RIGHT);
					//K???T TH??C KHAI B??O
					
					//Th????ng hi???u
						JLabel thuonghieu_lb = new JLabel("Th????ng hi???u:");
						thuonghieu_lb.setBounds(220, 7, 150, 50);
						thuonghieu_lb.setFont(tk);
						thuonghieu_lb.setHorizontalAlignment(JLabel.RIGHT);
					//K???T TH??C KHAI B??O
					
					
					//K???T TH??C KHAI B??O
						
					//N??T T??M KI???M N??NG CAO
						find = new JButton("T??m");
						find.setBounds(10, 50, 70, 30);
                                                find.setFont(lb);
					//K???T TH??C KHAI B??O
					
					//CREATE CHOICE
						gia = getPrice(new JComboBox());						
						gia.setBounds(130, 15, 120, 30);
                                                gia.setFont(tk);
                                                
                                                thuonghieu = getTL(new JComboBox());
                                                thuonghieu.setBounds(370, 15, 120, 30);
                                                thuonghieu.setFont(tk);
					//END CREATING CHOICE
					
					//ADDING COMPONENTS
						search_advance.add(gia_lb);
						search_advance.add(thuonghieu_lb);
						search_advance.add(find);
					//END ADDING COMPONENTS
					search_advance.add(search_maPD);
                                        search_advance.add(search_tenPD);
					search_advance.add(gia);
                                        search_advance.add(thuonghieu);
				//K???T TH??C T??M KI???M N??NG CAO
				
				//D??? LI???U T??M KI???M
					result_data = new JPanel();
					result_data.setLayout(new CardLayout());
					result_data.setBounds(220, 100, WIDTH - 215, 170);
					result_data.setBorder(new TitledBorder(null, "K???t Qu??? T??m Ki???m", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					model_result = getModel(model_result);
					product_result = new JTable(model_result){
                    @Override
                    public boolean isCellEditable(int row, int column){
                        return false;
                    }
                    
                    @Override
                    public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                    java.awt.Component component = super.prepareRenderer(renderer, row, column);
                    int rendererWidth = component.getPreferredSize().width;
                    javax.swing.table.TableColumn tableColumn = getColumnModel().getColumn(column);
                    tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                    return component;
                    }
                                        };
                                        product_result.setAutoCreateRowSorter(true);
                                product_result.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				product_result.setDefaultEditor(Object.class, null);
                                product_result.setFillsViewportHeight(true);
        product_result.setFont(new Font("Segoe UI", 0, 16));
        product_result.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        product_result.setRowHeight(40);

        // color
        int bgColor2 = 235;
        int color2 = 0;
        product_result.getTableHeader().setBackground(new Color(bgColor2, bgColor2, bgColor2));
       product_result.getTableHeader().setForeground(new Color(color2, color2, color2));
        product_result.setBackground(new Color(bgColor2, bgColor2, bgColor2));
        product_result.setForeground(new Color(color2, color2, color2));
                                double total2 = 0;
                               DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
        centerRenderer2.setHorizontalAlignment(JLabel.CENTER);
        product_result.getColumnModel().getColumn(0).setCellRenderer(centerRenderer2);
                                double[] percentages2 = new double[]{1,1.5,5,1,1};
        for (int i = 0; i < product_result.getColumnModel().getColumnCount(); i++) {
            total2 += percentages2[i];
        }

        for (int i = 0; i < product_result.getColumnModel().getColumnCount(); i++) {
            TableColumn column = product_result.getColumnModel().getColumn(i);
            column.setPreferredWidth((int) (getPreferredSize().width * (percentages2[i] / total2)));
        }
                                        
					product_result.setDefaultEditor(Object.class, null);
					product_result.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					JScrollPane scroll_result = new JScrollPane(product_result);
					result_data.add(scroll_result);
				//K???T TH??C D??? LI???U T??M KI???M*/
                                result.add(function);
                                result.add(search);
				result.add(search_advance);
				result.add(result_data);
                                result.add(image_product);
                                result.add(upload);
				
			//END OF RESULT
		
			//bottom.add(function);
			header.add(result);
		//END OF HEADER
		
		//EVENTS
                upload.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if(product_data.getSelectedRow()!=-1){
                            if(JOptionPane.showConfirmDialog(null, "B???n c?? mu???n thay d???i h??nh ???nh cho s???n ph???m c?? m?? "+product_data.getValueAt(product_data.getSelectedRow(),0)+" kh??ng?", "", JOptionPane.YES_NO_CANCEL_OPTION)==JOptionPane.YES_OPTION){
                                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
							int returnValue = jfc.showOpenDialog(null);
							if(returnValue == JFileChooser.APPROVE_OPTION) {
								File selectedFile = jfc.getSelectedFile();
                                        File saveFile = new File(System.getProperty("user.home")+"\\"+selectedFile.getName());
                                        selectedFile.renameTo(saveFile);
                                        System.out.println(saveFile.toString());
                                        String filepath = saveFile.toString().replaceAll("\\\\", "/");
                                        dtpd_bus.upload(filepath,(int) product_data.getValueAt(product_data.getSelectedRow(), 0));
                                        JOptionPane.showMessageDialog(null, "Upload th??nh c??ng", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                                                                                                     
                                                        }
                            }
                        }
                        if(product_result.getSelectedRow()!=-1){
                            if(JOptionPane.showConfirmDialog(null, "B???n c?? mu???n thay d???i h??nh ???nh cho s???n ph???m c?? m?? "+product_result.getValueAt(product_result.getSelectedRow(),0)+" kh??ng?", "", JOptionPane.YES_NO_CANCEL_OPTION)==JOptionPane.YES_OPTION){
                                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
							int returnValue = jfc.showOpenDialog(null);
							if(returnValue == JFileChooser.APPROVE_OPTION) {
								File selectedFile = jfc.getSelectedFile();
                                        File saveFile = new File(System.getProperty("user.home")+"\\"+selectedFile.getName());
                                        selectedFile.renameTo(saveFile);
                                        System.out.println(saveFile.toString());
                                        String filepath = saveFile.toString().replaceAll("\\\\", "/");
                                        dtpd_bus.upload(filepath,(int) product_result.getValueAt(product_result.getSelectedRow(), 0));
                                        JOptionPane.showMessageDialog(null, "Upload th??nh c??ng", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);                                                          
                                                        }
                            }
                        }
                    }
                });
                product_data.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e){
                        if(e.getClickCount()==2){
                            ArrayList<chitietsanphamDTO> ctsp = dtpd_bus.getList();
        javax.swing.JPanel ctspPane = new javax.swing.JPanel();
        DefaultTableModel model = new DefaultTableModel();
        javax.swing.JTable ctspTable = new javax.swing.JTable(model){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
             @Override
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
            java.awt.Component component = super.prepareRenderer(renderer, row, column);
            int rendererWidth = component.getPreferredSize().width;
            javax.swing.table.TableColumn tableColumn = getColumnModel().getColumn(column);
            tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
            return component;
            }
        };
        ctspTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        String[] columns = {"M?? chi ti???t","M?? s???n ph???m","M?? danh m???c","K??ch th?????c","Tr???ng l?????ng","M??u s???c","B??? nh??? trong","B??? nh??? ?????m/Ram","H??? ??i???u h??nh","Camera tr?????c","Camera sau","Pin","B???o h??nh","T??nh tr???ng"};
        model.setColumnIdentifiers(columns);
        for(int i =0;i<ctsp.size();i++){
            if(ctsp.get(i).getmasanpham()==(int)product_data.getValueAt(product_data.getSelectedRow(), 0)){
            model.addRow(new Object[]{
                ctsp.get(i).getmachitiet(),ctsp.get(i).getmasanpham(),ctsp.get(i).getmadanhmuc(),ctsp.get(i).getkichthuoc(),ctsp.get(i).gettrongluong(),ctsp.get(i).getmausac(),ctsp.get(i).getbonhotrong(),ctsp.get(i).getbonhodem(),ctsp.get(i).gethedieuhanh(),ctsp.get(i).getcameratruoc(),ctsp.get(i).getcamerasau(),ctsp.get(i).getpin(),ctsp.get(i).getbaohanh(),ctsp.get(i).gettinhtrang()
            });
            break;
            }
        }
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(ctspTable,javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);       
        scrollPane.setPreferredSize(new java.awt.Dimension(ctspTable.getPreferredSize().width,100));        
        ctspPane.setLayout(new java.awt.GridBagLayout());       
        ctspPane.add(scrollPane,new java.awt.GridBagConstraints(0, 0, 1, 1, 1, 1, java.awt.GridBagConstraints.NORTHWEST, java.awt.GridBagConstraints.BOTH, new java.awt.Insets(5, 5, 5, 0), 0, 0));
        ctspPane.addHierarchyListener(new java.awt.event.HierarchyListener() {
            @Override
		public void hierarchyChanged(HierarchyEvent e) {
			java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(ctspPane);
			if (window instanceof java.awt.Dialog) {
				java.awt.Dialog dialog = (java.awt.Dialog) window;
				if (!dialog.isResizable()) {
					dialog.setResizable(true);
				}
			}
		}
	});
        JOptionPane.showMessageDialog(null, ctspPane, "CHI TI???T S???N PH???M", JOptionPane.INFORMATION_MESSAGE);
                        }
        ArrayList<chitietsanphamDTO> ctsp = dtpd_bus.getArrayList("M?? s???n ph???m", product_data.getValueAt(product_data.getSelectedRow(), 0).toString());
        try{
        if(ctsp.get(0).getFilenamehinhanh().contains(":/")) image_product.setIcon(new ImageIcon(new ImageIcon(ctsp.get(0).getFilenamehinhanh()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        else image_product.setIcon(new ImageIcon(new ImageIcon(Product_Frame.class.getResource("/"+ctsp.get(0).getFilenamehinhanh())).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            } catch(NullPointerException ne){
                
            }        
                    }
});
                product_result.addMouseListener(new MouseAdapter() {
                     @Override
                    public void mouseClicked(MouseEvent e){
                        if(e.getClickCount()==2){
                            ArrayList<chitietsanphamDTO> ctsp = dtpd_bus.getArrayList("M?? s???n ph???m", product_result.getValueAt(product_result.getSelectedRow(), 0).toString());
        javax.swing.JPanel ctspPane = new javax.swing.JPanel();
        DefaultTableModel model = new DefaultTableModel();
        javax.swing.JTable ctspTable = new javax.swing.JTable(model){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
             @Override
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
            java.awt.Component component = super.prepareRenderer(renderer, row, column);
            int rendererWidth = component.getPreferredSize().width;
            javax.swing.table.TableColumn tableColumn = getColumnModel().getColumn(column);
            tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
            return component;
            }
        };
        ctspTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        String[] columns = {"M?? chi ti???t","M?? s???n ph???m","M?? danh m???c","K??ch th?????c","Tr???ng l?????ng","M??u s???c","B??? nh??? trong","B??? nh??? ?????m/Ram","H??? ??i???u h??nh","Camera tr?????c","Camera sau","Pin","B???o h??nh","T??nh tr???ng"};
        model.setColumnIdentifiers(columns);
        for(int i =0;i<ctsp.size();i++){
            if(ctsp.get(i).getmasanpham()==(int)product_result.getValueAt(product_result.getSelectedRow(), 0)){
            model.addRow(new Object[]{
                ctsp.get(i).getmachitiet(),ctsp.get(i).getmasanpham(),ctsp.get(i).getmadanhmuc(),ctsp.get(i).getkichthuoc(),ctsp.get(i).gettrongluong(),ctsp.get(i).getmausac(),ctsp.get(i).getbonhotrong(),ctsp.get(i).getbonhodem(),ctsp.get(i).gethedieuhanh(),ctsp.get(i).getcameratruoc(),ctsp.get(i).getcamerasau(),ctsp.get(i).getpin(),ctsp.get(i).getbaohanh(),ctsp.get(i).gettinhtrang()
            });
            break;
            }
        }
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(ctspTable,javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);       
        scrollPane.setPreferredSize(new java.awt.Dimension(ctspTable.getPreferredSize().width,100));        
        ctspPane.setLayout(new java.awt.GridBagLayout());       
        ctspPane.add(scrollPane,new java.awt.GridBagConstraints(0, 0, 1, 1, 1, 1, java.awt.GridBagConstraints.NORTHWEST, java.awt.GridBagConstraints.BOTH, new java.awt.Insets(5, 5, 5, 0), 0, 0));
        ctspPane.addHierarchyListener(new java.awt.event.HierarchyListener() {
            @Override
		public void hierarchyChanged(HierarchyEvent e) {
			java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(ctspPane);
			if (window instanceof java.awt.Dialog) {
				java.awt.Dialog dialog = (java.awt.Dialog) window;
				if (!dialog.isResizable()) {
					dialog.setResizable(true);
				}
			}
		}
	});
        JOptionPane.showMessageDialog(null, ctspPane, "CHI TI???T S???N PH???M", JOptionPane.INFORMATION_MESSAGE);
                        }
            ArrayList<chitietsanphamDTO> ctsp = dtpd_bus.getArrayList("M?? s???n ph???m", product_result.getValueAt(product_result.getSelectedRow(), 0).toString());
            try{
         if(ctsp.get(0).getFilenamehinhanh().contains(":/")) image_product.setIcon(new ImageIcon(new ImageIcon(ctsp.get(0).getFilenamehinhanh()).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        else image_product.setIcon(new ImageIcon(new ImageIcon(Product_Frame.class.getResource("/"+ctsp.get(0).getFilenamehinhanh())).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            } catch(NullPointerException ne){
                
            }
                    }
});
			//N??T TH??M
				btn_them.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new ProductInsert_Frame();
					}
				});
			//K???T TH??C S??? KI???N N??T TH??M
	
			//N??T X??A
				btn_xoa.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int []row;
							row = product_data.getSelectedRows();
						
							int confirm = JOptionPane.showConfirmDialog(null, "B???n c?? mu???n x??a " + row.length + " m???t h??ng kh??ng", "C???nh B??o",JOptionPane.YES_NO_OPTION);
							if(confirm == JOptionPane.YES_OPTION) {
								for(int i = 0; i < row.length; i++) {
									pd_bus.Delete(product_data.getValueAt(row[i]-i, 0).toString());
									model.removeRow(row[i]-i);
								}
								JOptionPane.showMessageDialog(null, "???? x??a th??nh c??ng", "Th??ng B??o", JOptionPane.INFORMATION_MESSAGE);
							}
							
						}
				});
			//K???T TH??C S??? KI???N N??T X??A
			
			//N??T S???A
				btn_sua.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int row = -1;
							row = product_data.getSelectedRow();
						if(row != -1) {
							int confirm = JOptionPane.showConfirmDialog(null, "B???n c?? mu???n c???p nh???t th??ng tin c???a m?? chuy???n bay: " + product_data.getValueAt(row, 0), "Th??ng B??o", JOptionPane.YES_NO_OPTION);
							if(confirm == JOptionPane.YES_OPTION) {
								new ProductUpdate_Frame(product_data.getValueAt(row, 0).toString());
							}
						}
					}
				});
			//K???T TH??C S??? KI???N N??T S???A
			
			//S??? KI???N SEARCH
				//SEARCH C?? B???N
					search_maPD.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
							model_result.setRowCount(0);
                                                        String price = (String) gia.getSelectedItem();
							CategoryDTO brand = (CategoryDTO) thuonghieu.getSelectedItem();
                                                        int startPrice = 0;
                                                        int endPrice = 999999999;
							switch(price){
                                                            case "0-10tr":
                                                                startPrice = 0;
                                                                endPrice = 10000000;
                                                                break;
                                                            case "10-20tr":
                                                                startPrice = 10000000;
                                                                endPrice = 20000000;
                                                                break;
                                                            case "20-30tr":
                                                                startPrice = 20000000;
                                                                endPrice = 30000000;
                                                                break;
                                                            case "Tr??n 30tr":
                                                                startPrice = 30000000;
                                                                endPrice = 999999999;
                                                                break;
                                                            default:
                                                                break;
                                                        }
							sr = pd_bus.search_nangcao("sp.`M?? s???n ph???m`",search_box.getText(), brand.getType(), startPrice, endPrice);
							if(sr.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Kh??ng t??m th???y k???t qu??? n??o m?? b???n c???n t??m", "Th??ng B??o", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							for(int i = 0; i < sr.size(); i++) {
								model_result.addRow(new Object[]{
                                                                    sr.get(i).getIdProd(),sr.get(i).getNameProd(),sr.get(i).getDescription(),nf.format(sr.get(i).getPrice()),sr.get(i).getSL()
                                                                });
							}
						}
					});
					search_tenPD.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
                                                    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
							model_result.setRowCount(0);
							String price = (String) gia.getSelectedItem();
							CategoryDTO brand = (CategoryDTO) thuonghieu.getSelectedItem();
                                                        int startPrice = 0;
                                                        int endPrice = 999999999;
							switch(price){
                                                            case "0-10tr":
                                                                startPrice = 0;
                                                                endPrice = 10000000;
                                                                break;
                                                            case "10-20tr":
                                                                startPrice = 10000000;
                                                                endPrice = 20000000;
                                                                break;
                                                            case "20-30tr":
                                                                startPrice = 20000000;
                                                                endPrice = 30000000;
                                                                break;
                                                            case "Tr??n 30tr":
                                                                startPrice = 30000000;
                                                                endPrice = 999999999;
                                                                break;
                                                            default:
                                                                break;
                                                        }
							sr = pd_bus.search_nangcao("sp.`T??n ??i???n tho???i`",search_box.getText(), brand.getType(), startPrice, endPrice);
							if(sr.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Kh??ng t??m th???y k???t qu??? n??o m?? b???n c???n t??m", "Th??ng B??o", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							for(int i = 0; i < sr.size(); i++) {
								model_result.addRow(new Object[]{
                                                                    sr.get(i).getIdProd(),sr.get(i).getNameProd(),sr.get(i).getDescription(),nf.format(sr.get(i).getPrice()),sr.get(i).getSL()
                                                                });
							}
						}
					});
					find.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							model_result.setRowCount(0);
							NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
							String price = (String) gia.getSelectedItem();
							CategoryDTO brand = (CategoryDTO) thuonghieu.getSelectedItem();
                                                        int startPrice = 0;
                                                        int endPrice = 999999999;
							switch(price){
                                                            case "0-10tr":
                                                                startPrice = 0;
                                                                endPrice = 10000000;
                                                                break;
                                                            case "10-20tr":
                                                                startPrice = 10000000;
                                                                endPrice = 20000000;
                                                                break;
                                                            case "20-30tr":
                                                                startPrice = 20000000;
                                                                endPrice = 30000000;
                                                                break;
                                                            case "Tr??n 30tr":
                                                                startPrice = 30000000;
                                                                endPrice = 999999999;
                                                                break;
                                                            default:
                                                                break;
                                                        }
							sr = pd_bus.search_nangcao(null,search_box.getText(), brand.getType(), startPrice, endPrice);
                                                        if(sr.isEmpty()) {
								JOptionPane.showMessageDialog(null, "Kh??ng t??m th???y k???t qu??? n??o m?? b???n c???n t??m", "Th??ng B??o", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							for(int i = 0; i < sr.size(); i++) {
								model_result.addRow(new Object[]{
                                                                    sr.get(i).getIdProd(),sr.get(i).getNameProd(),sr.get(i).getDescription(),nf.format(sr.get(i).getPrice()),sr.get(i).getSL()
                                                                });
							}
						}
					});
				//K???T TH??C S??? KI???N
			//K???T TH??C S??? KI???N SEARCH
			
			//S??? KI???N ?????C V?? GHI FILE
				//EXCEL
                                
					ghiexcel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							wfe = new ExportExcel("Excel/Danhsachsanpham.xlsx");
							JOptionPane.showMessageDialog(null, "???? in ra file Excel th??nh c??ng", "Th??ng B??o", JOptionPane.INFORMATION_MESSAGE);
						}
					});
					docexcel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
							int returnValue = jfc.showOpenDialog(null);
							if(returnValue == JFileChooser.APPROVE_OPTION) {
								File selectedFile = jfc.getSelectedFile();
								model.setRowCount(0);
								try {
									ImportExcel ref = new ImportExcel();
									listSP = ref.readSanpham(selectedFile.getAbsolutePath());
									for(int i = 0; i < listSP.size(); i++) {
										Integer maSP	= listSP.get(i).getIdProd();
										String tenSP	= listSP.get(i).getNameProd();
										String mota	= listSP.get(i).getDescription();
										Integer gia	= listSP.get(i).getPrice();
										Integer soluong  = listSP.get(i).getSL();
										Object []data = {
												maSP,tenSP,mota,gia,soluong
										};
                                                                                pd_bus.Insert(listSP.get(i));
										model.addRow(data);
									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
						}
					});
				//XONG ?????C FILE, GHI FILE EXCEL
				
				//PDF
					ghipdf.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ExportPDF wpdf = new ExportPDF();
							try {
								wpdf.writePDFProduct(listSP, "PDF/danhsachsanphamtrongkho.pdf");
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (DocumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "???? in ra file PDF th??nh c??ng ??? PDF/danhsachsanphamtrongkho.pdf" , "Th??ng B??o", JOptionPane.INFORMATION_MESSAGE);
						}
					});
				//XONG ?????C FILE, GHI FILE PDF
			//K???T TH??C S??? KI???N ?????C, GHI FILE
		//END OF EVENTS

		add(header);
		add(data);		
	}
                                        
	private DefaultTableModel getModel(DefaultTableModel model) {
		Vector columnName = new Vector();
		columnName.add("M?? s???n ph???m");
		columnName.add("T??n ??i???n tho???i");
		columnName.add("M?? t???");
		columnName.add("Gi?? c???");
		columnName.add("S??? l?????ng");
		model = new DefaultTableModel(columnName, 0);
		return model;
	}
	private DefaultTableModel getDataRow(DefaultTableModel model) {
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
		listSP = pd_bus.getArrayList();
		for(int i = 0; i < listSP.size(); i++) {
			model.addRow(new Object[]{
                            listSP.get(i).getIdProd(),listSP.get(i).getNameProd(),listSP.get(i).getDescription(),nf.format(listSP.get(i).getPrice()),listSP.get(i).getSL()
                        });
		}
		return model;
	}
        
	private JComboBox<CategoryDTO> getTL(JComboBox<CategoryDTO> tl) {
		tl.addItem(new CategoryDTO("", ""));
		ArrayList<CategoryDTO> th = pd_bus.getTL();
		for(int i=0;i<th.size();i++){
                    tl.addItem(th.get(i));
                }
		return tl;
	}
        
        private JComboBox<String> getPrice(JComboBox<String> price) {
                price.addItem("Ch???n Gi??");
                price.addItem("0-10tr ");
                price.addItem("10-20tr");
                price.addItem("20-30tr");
                price.addItem("Tr??n 30tr");
                return price;
        }
	
}
