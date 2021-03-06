package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import DTO.ProductDTO;
import BUS.Product_BUS;
import BUS.DetailProduct_BUS;
import DTO.CategoryDTO;
import DTO.chitietsanphamDTO;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JComboBox;
import stolenBtn.ThemButton;

public class ProductInsert_Frame extends JFrame {

	private static Pattern dateRegexPattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
	private JPanel contentPane;
	private JTextField masp;
        private JTextField mact;
        private JTextField madm;
	private JTextField tensp;
        JComboBox<CategoryDTO> danhmuc;
        private JTextField mota;
        private JTextField giaca;        
	private JTextField soluong;
        private JTextField kichthuoc;
	private JTextField trongluong;
        private JTextField mausac;
	private JTextField bonhotrong;
        private JTextField ram;
	private JTextField hedieuhanh;
        private JTextField camtrc;
	private JTextField camsau;
        private JTextField pin;
	private JTextField baohanh;
        private JTextField tinhtrang;
	private Calendar cal = Calendar.getInstance();
	Font text = new Font("Tahoma", Font.PLAIN, 25);
	private Product_BUS pd_bus = new Product_BUS();
	private DetailProduct_BUS dd_bus = new DetailProduct_BUS();
	private ArrayList <ProductDTO> product;
        private JComboBox<CategoryDTO> getTL(JComboBox<CategoryDTO> tl) {
		ArrayList<CategoryDTO> th = pd_bus.getTL();
		for(int i=0;i<th.size();i++){
                    tl.addItem(th.get(i));
                }
		return tl;
	}
	public ProductInsert_Frame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		int j = 0;
		product = pd_bus.getArrayList();
		
		setSize(900, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//top
		JLabel lblNewLabel = new JLabel("M?? s???n ph???m:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 25));
		lblNewLabel.setBounds(100, 20, 150, 30);
		contentPane.add(lblNewLabel);
		
		masp = new JTextField();
		masp.setBounds(265, 20, 120, 35);
                masp.setFont(text);
		masp.setEditable(false);
		contentPane.add(masp);
		masp.setText(Integer.toString(pd_bus.getMaxId()+1));
		masp.setColumns(10);
                
                JLabel lbltheloai = new JLabel("M?? chi ti???t:");
		lbltheloai.setHorizontalAlignment(SwingConstants.RIGHT);
		lbltheloai.setFont(new Font("Calibri", Font.BOLD, 25));
		lbltheloai.setBounds(450, 20, 150, 30);
		contentPane.add(lbltheloai);
		
		mact= new JTextField();
		mact.setBounds(615, 20, 120, 35);
                mact.setFont(text);
		mact.setEditable(false);
		contentPane.add(mact);
		mact.setText(Integer.toString(pd_bus.getMaxId()+1));
		mact.setColumns(10);
		//
		JLabel lblTnCb = new JLabel("T??n s???n ph???m:");
		lblTnCb.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTnCb.setFont(new Font("Calibri", Font.BOLD, 25));
		lblTnCb.setBounds(120, 65, 150, 30);
		contentPane.add(lblTnCb);
		
		tensp = new JTextField();
		tensp.setColumns(10);
                tensp.setFont(text);
		tensp.setBounds(300, 65, 400, 35);
                tensp.setFont(text);
		contentPane.add(tensp);
		//1
		JLabel lblMMb = new JLabel("M?? t???:");
		lblMMb.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMMb.setFont(new Font("Calibri", Font.BOLD, 25));
		lblMMb.setBounds(100, 110, 150, 30);//
		contentPane.add(lblMMb);
		
		mota = new JTextField();
		mota.setColumns(10);
                mota.setFont(text);
		mota.setBounds(265, 110, 120, 35);
		contentPane.add(mota);
		
                //comboboxdanhmuc
                JLabel lbldanhmuc = new JLabel("Danh m???c:");
		lbldanhmuc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbldanhmuc.setFont(new Font("Calibri", Font.BOLD,25));
		lbldanhmuc.setBounds(450, 110, 150, 30);
		contentPane.add(lbldanhmuc);
                		
		danhmuc = getTL(new JComboBox());
		danhmuc.setBounds(615, 110, 120, 35);
                danhmuc.setFont(text);
                danhmuc.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent ie) {
                        if(ie.getStateChange()==ItemEvent.SELECTED){
                            CategoryDTO x = (CategoryDTO) danhmuc.getSelectedItem();
                            madm.setText(x.getType());
                        }
                    }
                });
		contentPane.add(danhmuc);
                //
                //2
		JLabel lblimi = new JLabel("Gi??:");
		lblimi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblimi.setFont(new Font("Calibri", Font.BOLD, 25));
		lblimi.setBounds(100, 155, 150, 30);
		contentPane.add(lblimi);
		
		giaca = new JTextField();
		giaca.setColumns(10);
                giaca.setFont(text);
		giaca.setBounds(265, 155, 120, 35);
		contentPane.add(giaca);
                
                JLabel lblmadm = new JLabel("M?? danh m???c:");
		lblmadm.setHorizontalAlignment(SwingConstants.RIGHT);
		lblmadm.setFont(new Font("Calibri", Font.BOLD, 25));
		lblmadm.setBounds(450, 155, 150, 30);
		contentPane.add(lblmadm);
		
		madm = new JTextField();
		madm.setColumns(10);
                madm.setFont(text);
                CategoryDTO y = (CategoryDTO) danhmuc.getSelectedItem();
                madm.setText(y.getType());
                madm.setEditable(false);
		madm.setBounds(615, 155, 120, 35);
		contentPane.add(madm);                
		//
                
                //3
		JLabel lblimn = new JLabel("S??? l?????ng:");
		lblimn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblimn.setFont(new Font("Calibri", Font.BOLD, 25));
		lblimn.setBounds(100, 200, 150, 30);
		contentPane.add(lblimn);
		
                soluong = new JTextField();
		soluong.setColumns(10);
                soluong.setFont(text);
		soluong.setBounds(265, 200, 120, 35);
		contentPane.add(soluong);
                
                JLabel lblhdh = new JLabel("H??? ??i???u h??nh:");
		lblhdh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblhdh.setFont(new Font("Calibri", Font.BOLD, 25));
		lblhdh.setBounds(450, 200, 150, 30);
		contentPane.add(lblhdh);
		
		hedieuhanh = new JTextField();
		hedieuhanh.setColumns(10);
                hedieuhanh.setFont(text);
		hedieuhanh.setBounds(615, 200, 120, 35);
		contentPane.add(hedieuhanh);               
		//
                //4
		JLabel lblNgyi = new JLabel("K??ch th?????c:");
		lblNgyi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgyi.setFont(new Font("Calibri", Font.BOLD, 25));
		lblNgyi.setBounds(100, 245, 150, 30);
		contentPane.add(lblNgyi);
		
		kichthuoc = new JTextField();
		kichthuoc.setColumns(10);
                kichthuoc.setFont(text);
		kichthuoc.setBounds(265, 248, 120, 35);
		contentPane.add(kichthuoc);
                
                JLabel lblcamtrc = new JLabel("Camera tr?????c:");
		lblcamtrc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblcamtrc.setFont(new Font("Calibri", Font.BOLD, 25));
		lblcamtrc.setBounds(450, 245, 150, 30);
		contentPane.add(lblcamtrc);
		
                camtrc = new JTextField();
		camtrc.setColumns(10);
                camtrc.setFont(text);
		camtrc.setBounds(615, 245, 120, 35);
		contentPane.add(camtrc);                
		//
                //5
		JLabel lblNgyn = new JLabel("Tr???ng l?????ng:");
		lblNgyn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNgyn.setFont(new Font("Calibri", Font.BOLD, 25));
		lblNgyn.setBounds(100, 290, 150, 30);
		contentPane.add(lblNgyn);
		
                trongluong = new JTextField();
		trongluong.setColumns(10);
		trongluong.setBounds(265, 293, 120, 35);
                trongluong.setFont(text);
		contentPane.add(trongluong);
                
                JLabel lblcamsau = new JLabel("Camera sau:");
		lblcamsau.setHorizontalAlignment(SwingConstants.RIGHT);
		lblcamsau.setFont(new Font("Calibri", Font.BOLD, 25));
		lblcamsau.setBounds(450, 290, 150, 30);
		contentPane.add(lblcamsau);
		
		camsau = new JTextField();
		camsau.setColumns(10);
                camsau.setFont(text);
		camsau.setBounds(615, 290, 120, 35);
		contentPane.add(camsau);                
                //
                //6
		JLabel lblSnBayi = new JLabel("M??u s???c:");
		lblSnBayi.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSnBayi.setFont(new Font("Calibri", Font.BOLD, 25));
		lblSnBayi.setBounds(100, 335, 150, 30);
		contentPane.add(lblSnBayi);
		
		mausac = new JTextField();
		mausac.setColumns(10);
                mausac.setFont(text);
		mausac.setBounds(265, 335, 120, 35);
		contentPane.add(mausac);
                
                JLabel lblpin = new JLabel("Pin:");
		lblpin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblpin.setFont(new Font("Calibri", Font.BOLD, 25));
		lblpin.setBounds(450, 335, 150, 30);
		contentPane.add(lblpin);
		
                pin = new JTextField();
		pin.setColumns(10);
                pin.setFont(text);
		pin.setBounds(615, 335, 120, 35);
		contentPane.add(pin);                
                //
		//7				
		JLabel lblSnBayn = new JLabel("B??? nh??? trong:");
		lblSnBayn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSnBayn.setFont(new Font("Calibri", Font.BOLD, 25));
		lblSnBayn.setBounds(100, 380, 150, 30);
		contentPane.add(lblSnBayn);
		
                bonhotrong = new JTextField();
		bonhotrong.setColumns(10);
                bonhotrong.setFont(text);
		bonhotrong.setBounds(265, 380, 120, 35);
		contentPane.add(bonhotrong);
                
                JLabel lblbaohanh = new JLabel("B???o h??nh:");
		lblbaohanh.setHorizontalAlignment(SwingConstants.RIGHT);
		lblbaohanh.setFont(new Font("Calibri", Font.BOLD, 25));
		lblbaohanh.setBounds(450, 380, 150, 30);
		contentPane.add(lblbaohanh);
		
		baohanh = new JTextField();
		baohanh.setColumns(10);
                baohanh.setFont(text);
		baohanh.setBounds(615, 380, 120, 35);
		contentPane.add(baohanh);                
                //              
                //8
		JLabel lblram = new JLabel("B??? nh??? ?????m:");
		lblram.setHorizontalAlignment(SwingConstants.RIGHT);
		lblram.setFont(new Font("Calibri", Font.BOLD, 25));
		lblram.setBounds(100, 425, 150, 30);
		contentPane.add(lblram);
		
                ram = new JTextField();
		ram.setColumns(10);
                ram.setFont(text);
		ram.setBounds(265, 425, 120, 35);
		contentPane.add(ram);
                
                JLabel lbltinhtrang = new JLabel("T??nh tr???ng:");
		lbltinhtrang.setHorizontalAlignment(SwingConstants.RIGHT);
		lbltinhtrang.setFont(new Font("Calibri", Font.BOLD, 25));
		lbltinhtrang.setBounds(450, 425, 150, 30);
		contentPane.add(lbltinhtrang);
		
                tinhtrang = new JTextField();
		tinhtrang.setColumns(10);
                tinhtrang.setFont(text);
		tinhtrang.setBounds(615, 425, 120, 35);
		contentPane.add(tinhtrang);
		//
		JButton btn_cancle = new JButton("H???y B???");
		btn_cancle.setBounds(650, 520, 150, 40);
                btn_cancle.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(btn_cancle);
		
		JButton btn_reset = new JButton("Nh???p L???i");
		btn_reset.setBounds(350, 520, 150, 40);
                btn_reset.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(btn_reset);
		
		ThemButton btn_save = new ThemButton();
		btn_save.setBounds(100, 520, 150, 40);
                btn_save.setFont(new Font("Arial", Font.BOLD, 18));
		contentPane.add(btn_save);
		
		//EVENTS
			btn_cancle.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
			btn_save.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(tensp.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "T??n s???n ph???m kh??ng ???????c b??? tr???ng","T??n s???n ph???m tr???ng!!",JOptionPane.ERROR_MESSAGE);
						tensp.requestFocus();
						return;
					} else
					if(mota.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "M?? t??? c???a s???n ph???m kh??ng ???????c b??? tr???ng","M?? t??? tr???ng!!",JOptionPane.ERROR_MESSAGE);
						mota.requestFocus();
						return;
					} else
                                        if(giaca.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Gi?? c???a s???n ph???m kh??ng ???????c b??? tr???ng","Gi?? tr???ng!!",JOptionPane.ERROR_MESSAGE);
						giaca.requestFocus();
						return;
					}else
                                        if(soluong.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "S??? l?????ng c???a s???n ph???m kh??ng ???????c b??? tr???ng","S??? l?????ng  tr???ng!!",JOptionPane.ERROR_MESSAGE);
						soluong.requestFocus();
						return;
					}else
                                        if(kichthuoc.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "K??ch th?????c c???a s???n ph???m kh??ng ???????c b??? tr???ng","K??ch th?????c  tr???ng!!",JOptionPane.ERROR_MESSAGE);
						kichthuoc.requestFocus();
						return;
					}else
                                        if(trongluong.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Tr???ng l?????ng c???a s???n ph???m kh??ng ???????c b??? tr???ng","Tr???ng l?????ng tr???ng!!",JOptionPane.ERROR_MESSAGE);
						trongluong.requestFocus();
						return;
					}
                                        if(mausac.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "M??u s???c c???a s???n ph???m kh??ng ???????c b??? tr???ng","M??u tr???ng!!",JOptionPane.ERROR_MESSAGE);
						mausac.requestFocus();
						return;
					}else
                                        if(bonhotrong.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "B??? nh??? trong c???a s???n ph???m kh??ng ???????c b??? tr???ng","B??? nh??? trong tr???ng!!",JOptionPane.ERROR_MESSAGE);
						bonhotrong.requestFocus();
						return;
					}else
                                        if(ram.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "B??? nh??? ?????m c???a s???n ph???m kh??ng ???????c b??? tr???ng","B??? nh??? ?????m tr???ng!!",JOptionPane.ERROR_MESSAGE);
						ram.requestFocus();
						return;
					}else
                                        if(hedieuhanh.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "H??? ??i???u h??nh c???a s???n ph???m kh??ng ???????c b??? tr???ng","H??? ??i???u h??nh tr???ng!!",JOptionPane.ERROR_MESSAGE);
						hedieuhanh.requestFocus();
						return;
					}else
                                        if(camtrc.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Camera tr?????c c???a s???n ph???m kh??ng ???????c b??? tr???ng","Camera tr?????c tr???ng!!",JOptionPane.ERROR_MESSAGE);
						camtrc.requestFocus();
						return;
					}
                                        if(camsau.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Camera sau c???a s???n ph???m kh??ng ???????c b??? tr???ng","Camera sau tr???ng!!",JOptionPane.ERROR_MESSAGE);
						camsau.requestFocus();
						return;
					}else
                                        if(pin.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Pin c???a s???n ph???m kh??ng ???????c b??? tr???ng","Pin tr???ng!!",JOptionPane.ERROR_MESSAGE);
						pin.requestFocus();
						return;
					}else
                                        if(baohanh.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "B???o h??nh c???a s???n ph???m kh??ng ???????c b??? tr???ng","B???o h??nh tr???ng!!",JOptionPane.ERROR_MESSAGE);
						baohanh.requestFocus();
						return;
					}else
                                        if(tinhtrang.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "T??nh tr???ng c???a s???n ph???m kh??ng ???????c b??? tr???ng","T??nh tr???ng tr???ng!!",JOptionPane.ERROR_MESSAGE);
						tinhtrang.requestFocus();
						return;
					}
                                        else {
                                            try{
                                            ProductDTO sp = new ProductDTO(Integer.parseInt(masp.getText()),tensp.getText(),mota.getText(),Integer.parseInt(giaca.getText()),Integer.parseInt(soluong.getText()));
                                            chitietsanphamDTO ctsp = new  chitietsanphamDTO(Integer.parseInt(mact.getText()),Integer.parseInt(masp.getText()),madm.getText(),kichthuoc.getText(),trongluong.getText(),mausac.getText(),bonhotrong.getText(),ram.getText(),hedieuhanh.getText(),camtrc.getText(),camsau.getText(),pin.getText(),baohanh.getText(),tinhtrang.getText());
                                            if(pd_bus.Insert(sp)&&dd_bus.insert(ctsp)) {
                                                NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
                                                Product_Frame.getTModel().addRow(new Object[]{
                                                    Integer.parseInt(masp.getText()),tensp.getText(),mota.getText(),giaca.getText(),Integer.parseInt(soluong.getText())
                                                });
                                                JOptionPane.showMessageDialog(null, "Th??m s???n ph???m th??nh c??ng","SUCCESS",JOptionPane.INFORMATION_MESSAGE);
                                            }
                                            else JOptionPane.showMessageDialog(null, "S???n ph???m ???? t???n t???i","Th??m Th???t B???i",JOptionPane.ERROR_MESSAGE);
                                            } catch(NumberFormatException ne){
                                                JOptionPane.showMessageDialog(null, "Gi??, S??? l?????ng ph???i l?? S??? Nguy??n","FAIL",JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
				}
			});
			
			btn_reset.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					tensp.setText("");
					mota.setText("");
                                        giaca.setText("");
                                        soluong.setText("");
                                        kichthuoc.setText("");
                                        trongluong.setText("");
                                        mausac.setText("");
                                        bonhotrong.setText("");
                                        ram.setText("");
                                        hedieuhanh.setText("");
                                        camtrc.setText("");
                                        camsau.setText("");
                                        pin.setText("");
                                        baohanh.setText("");
                                        tinhtrang.setText("");
				}
			});
		//END OF EVENTS
		
		setVisible(true);
	
	    }
}
