package GUI_Bandt;

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

import DTO.DetailOrDTO;
import DTO.OrderDTO;
import BUS.HoaDon_BUS;
import DTO.ProductDTO;
import BUS.Product_BUS;
import BUS.DetailProduct_BUS;
import DTO.CategoryDTO;
import DTO.chitietsanphamDTO;
import Excel.ExportExcel;
import Excel.ImportExcel;
import PDF.ExportPDF;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javafx.scene.control.TableColumn;

public class Purchase_Frame extends JPanel {
        private String makh;
	private int WIDTH;
	private JPanel header, data, bottom;
	private DefaultTableModel model, model_result;
	private Calendar cal = Calendar.getInstance();
	private ArrayList <ProductDTO> listSP, sr;
	private ExportExcel wfe = new ExportExcel();
        private HoaDon_BUS hd_bus = new HoaDon_BUS();
	private Product_BUS pd_bus = new Product_BUS();
	private Choice gia;
	private JComboBox<CategoryDTO> thuonghieu;
	private DetailProduct_BUS dtpd_bus = new DetailProduct_BUS();
        private Date date = new Date();
        private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
	public Purchase_Frame() {
		
	}
	
	public Purchase_Frame(int WIDTH, String makh) {
		this.WIDTH = WIDTH;
                this.makh = makh;
		init();
	}
	
	public void init() {
		JTable product_data, product_result;
		Font title = new Font("Calibri", Font.PLAIN, 28);
		Font lb = new Font("Tahoma", Font.PLAIN, 16);
		JButton btn_them, btn_xoa, btn_sua;
		setLayout(null);
		setBackground(null);
		setBounds(new Rectangle(0, 0, WIDTH + 23, 800));
		
		//HEADER
			header = new JPanel();
			header.setBounds(0, 0, WIDTH +23, 100);
			header.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
			JLabel title_header = new JLabel("QU???N L?? S???N PH???M");
			title_header.setFont(title);
			header.add(title_header);
		//END OF HEADER
		
		//DATA
			data = new JPanel();
			data.setBounds(0, 101, WIDTH +23, 250);
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
			product_data.setDefaultEditor(Object.class, null);
			product_data.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane scroll_data = new JScrollPane(product_data);
			data.add(scroll_data);
		//END OF DATA
		
		//BOTTOM
			bottom = new JPanel();
			bottom.setBounds(0, 350, WIDTH +23, 450);
			bottom.setLayout(null);
			//FUNCTION
                        /*JPanel function = new JPanel();
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
                        btn_them = new JButton("Th??m");
                        btn_them.setFont(lb);
                        btn_them.setBounds(3, 25, 195, 45);
                        btn_xoa = new JButton("X??a");
                        btn_xoa.setFont(lb);
                        btn_xoa.setBounds(3, 75, 195, 45);
                        btn_sua = new JButton("S???a");
                        btn_sua.setFont(lb);
                        btn_sua.setBounds(3, 125, 195, 45);
                        JTextField search_box = new JTextField();
                        search_box.setBounds(10, 25, 180, 35);
                        search_box.setHorizontalAlignment(JTextField.CENTER);
                        JButton search_maPD = new JButton("TK Theo M?? S???n ph???m");
                        search_maPD.setBounds(5, 75, 190, 40);
                        JButton search_tenPD = new JButton("TK Theo T??n S???n ph???m");
                        search_tenPD.setBounds(5, 125, 190, 40);
                        top_function.add(btn_them);
                        top_function.add(btn_xoa);
                        top_function.add(btn_sua);
                        bottom_function.add(search_box);
                        bottom_function.add(search_maPD);
                        bottom_function.add(search_tenPD);
                        function.add(top_function);
                        function.add(bottom_function);*/
			//END OF FUNCTION
				
			//FUNCTION AND RESULT
				
				JButton find,mua;
				JPanel result = new JPanel();                                
				result.setBounds(5, 0, WIDTH + 187, 420);
				result.setLayout(null);
				JPanel search_advance, result_data,search,purchare;
                                JLabel image_product;
                                //MUA H??NG
                                        purchare = new JPanel();
                                        purchare.setLayout(null);
					purchare.setBounds(5, 5, WIDTH-620, 180);
					purchare.setBorder(new TitledBorder(null, "Mua H??ng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
                                        mua = new JButton("Mua");
					mua.setBounds(10, 70, 120, 50);
                                        purchare.add(mua);
                                //                               
                                //H??NH ???NH S???N PH???M
                                        image_product = new JLabel(new ImageIcon(new ImageIcon(Purchase_Frame.class.getResource("/icons/arrow.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
					image_product.setLayout(null);
					image_product.setBounds(150, 5, WIDTH-600, 180);
					image_product.setBorder(new TitledBorder(null, "H??nh ???nh", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				//
                                //T??M KI???M
                                        search = new JPanel();
					search.setLayout(null);
					search.setBounds(320, 5, WIDTH -550, 180);
					search.setBorder(new TitledBorder(null, "T??m Ki???m ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
                                        JTextField search_box = new JTextField();
                                        search_box.setBounds(15, 25, 180, 35);
                                        search_box.setHorizontalAlignment(JTextField.CENTER);
                                        JButton search_maPD = new JButton("TK Theo M?? S???n ph???m");
                                        search_maPD.setBounds(10, 75, 190, 40);
                                        JButton search_tenPD = new JButton("TK Theo T??n S???n ph???m");
                                        search_tenPD.setBounds(10, 125, 190, 40);
                                        search.add(search_box);
                                        search.add(search_maPD);
                                        search.add(search_tenPD);
                                //  K???T TH??C T??M KI???M
                                //T??M KI???M N??NG CAO
					search_advance = new JPanel();
					search_advance.setLayout(null);
					search_advance.setBounds(540, 5, WIDTH - 530, 180);
					search_advance.setBorder(new TitledBorder(null, "T??m Ki???m N??ng Cao", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					//Gi?? c???
						JLabel gia_lb = new JLabel("Gi?? c???:");
						gia_lb.setBounds(40, 25, 75, 20);
						gia_lb.setFont(lb);
						gia_lb.setHorizontalAlignment(JLabel.RIGHT);
					//K???T TH??C KHAI B??O
					
					//Th????ng hi???u
						JLabel thuonghieu_lb = new JLabel("Th????ng hi???u:");
						thuonghieu_lb.setBounds(15, 55, 100, 20);
						thuonghieu_lb.setFont(lb);
						thuonghieu_lb.setHorizontalAlignment(JLabel.RIGHT);
					//K???T TH??C KHAI B??O
					
					
					//K???T TH??C KHAI B??O
						
					//N??T T??M KI???M N??NG CAO
						find = new JButton("T??m");
						find.setBounds(120, 145, 60, 25);
					//K???T TH??C KHAI B??O
					
					//T???O GI?? V?? TH????NG HI???U
						gia = getPrice(new Choice());						
						gia.setBounds(120, 25, 100, 20);
                                                
                                                thuonghieu = getTL(new JComboBox());
                                                thuonghieu.setBounds(120, 55, 100, 20);
					//K???T TH??C T???O GI?? V?? TH????NG HI???U
					
					//ADDING COMPONENTS                                        
						search_advance.add(gia_lb);
						search_advance.add(thuonghieu_lb);
						search_advance.add(find);
					//END ADDING COMPONENTS
					
					search_advance.add(gia);
                                        search_advance.add(thuonghieu);
				//K???T TH??C T??M KI???M N??NG CAO
				
		
				
				//D??? LI???U T??M KI???M
					result_data = new JPanel();
					result_data.setLayout(new CardLayout());
					result_data.setBounds(5, 185, WIDTH +10, 220);
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
                                        product_result.getColumnModel().getColumn(0).setPreferredWidth(100);
                                        product_result.getColumnModel().getColumn(1).setPreferredWidth(120);
                                        product_result.getColumnModel().getColumn(2).setPreferredWidth(169);
                                        product_result.getColumnModel().getColumn(3).setPreferredWidth(50);
                                        product_result.getColumnModel().getColumn(4).setPreferredWidth(100);
					product_result.setDefaultEditor(Object.class, null);
					product_result.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					JScrollPane scroll_result = new JScrollPane(product_result);
					result_data.add(scroll_result);
				//K???T TH??C D??? LI???U T??M KI???M
                                result.add(purchare);
                                result.add(search);
				result.add(search_advance);
				result.add(result_data);
                                result.add(image_product);
				
			//END OF FUNCTION AND RESULT
		
			//bottom.add(function);
			bottom.add(result);
		//END OF BOTTOM
		
		//EVENTS
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
        if(ctsp.get(0).getFilenamehinhanh().contains(":/")) image_product.setIcon(new ImageIcon(new ImageIcon(ctsp.get(0).getFilenamehinhanh()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        else image_product.setIcon(new ImageIcon(new ImageIcon(Purchase_Frame.class.getResource("/"+ctsp.get(0).getFilenamehinhanh())).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            } catch(NullPointerException  ne){
                
            } catch(IndexOutOfBoundsException ie){
                
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
         if(ctsp.get(0).getFilenamehinhanh().contains(":/")) image_product.setIcon(new ImageIcon(new ImageIcon(ctsp.get(0).getFilenamehinhanh()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        else image_product.setIcon(new ImageIcon(new ImageIcon(Purchase_Frame.class.getResource("/"+ctsp.get(0).getFilenamehinhanh())).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            } catch(NullPointerException ne){
                
            }
                    }
});
			
			
			//S??? KI???N SEARCH
				//SEARCH C?? B???N
					search_maPD.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
							model_result.setRowCount(0);
							sr = pd_bus.search_coban("M?? s???n ph???m", search_box.getText());
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
							sr = pd_bus.search_coban("T??n ??i???n tho???i", search_box.getText());
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
							String price = gia.getSelectedItem();
							CategoryDTO brand = (CategoryDTO) thuonghieu.getSelectedItem();
                                                        int startPrice = 0;
                                                        int endPrice = 999999999;
							switch(price){
                                                            case "0 - 10 tri???u":
                                                                startPrice = 0;
                                                                endPrice = 10000000;
                                                                break;
                                                            case "10 - 20 tri???u":
                                                                startPrice = 10000000;
                                                                endPrice = 20000000;
                                                                break;
                                                            case "20 - 30 tri???u":
                                                                startPrice = 20000000;
                                                                endPrice = 30000000;
                                                                break;
                                                            case "tr??n 30 tri???u":
                                                                startPrice = 30000000;
                                                                endPrice = 999999999;
                                                                break;
                                                            default:
                                                                break;
                                                        }
							sr = pd_bus.search_nangcao(null,search_box.getText(), brand.getType(), startPrice, endPrice);
							for(int i = 0; i < sr.size(); i++) {
								model_result.addRow(new Object[]{
                                                                    sr.get(i).getIdProd(),sr.get(i).getNameProd(),sr.get(i).getDescription(),nf.format(sr.get(i).getPrice()),sr.get(i).getSL()
                                                                });
							}
						}
					});
				//K???T TH??C S??? KI???N
			//K???T TH??C S??? KI???N SEARCH
			mua.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int totalmoney = 0;
                        ArrayList<DetailOrDTO> dts = new ArrayList();
                        int[] dataRows = product_data.getSelectedRows();
                        int[] resultRows = product_result.getSelectedRows();
                        for(int i=0 ; i<product_data.getSelectedRowCount();i++){                       
                        DetailOrDTO dt = new DetailOrDTO(hd_bus.getMaxId(),Integer.parseInt(product_data.getValueAt(dataRows[i], 0).toString()),1,Integer.parseInt(product_data.getValueAt(dataRows[i], 3).toString().replaceAll("(\\.)|??| ", "")),"-1");
                        dts.add(dt);
                        totalmoney += dt.getMoney();
                        }
                        
                        for(int i=0 ; i<product_result.getSelectedRowCount();i++){
                        DetailOrDTO dt = new DetailOrDTO(hd_bus.getMaxId(),Integer.parseInt(product_result.getValueAt(resultRows[i], 0).toString()),1,Integer.parseInt(product_result.getValueAt(resultRows[i], 3).toString().replaceAll("(\\.)|??| ", "")),"-1");
                        dts.add(dt);
                        totalmoney += dt.getMoney();
                        }
                        OrderDTO or = new OrderDTO(hd_bus.getMaxId(),Integer.parseInt(makh), totalmoney, df.format(date),LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                        hd_bus.addToCart(or, dts);
                        JOptionPane.showMessageDialog(null, "Mua Th??nh C??ng");
                    }
                });
		//END OF EVENTS

		add(header);
		add(data);
		add(bottom);
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
        private Choice getPrice(Choice price) {
                price.add("");
                price.add("0 - 10 tri???u");
                price.add("10 - 20 tri???u");
                price.add("20 - 30 tri???u");
                price.add("tr??n 30 tri???u");
                return price;
        }
	
}
