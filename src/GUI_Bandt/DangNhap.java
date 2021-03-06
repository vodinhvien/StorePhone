package GUI_Bandt;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DTO.User;
import DTO.KhachHang;
import BUS.User_BUS;
import BUS.KhachHang_BUS;
import java.util.TimerTask;

public class DangNhap extends JFrame {

	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private Date date = new Date();
	
	private JPanel contentPane;
	private JTextField userName;
	private JTextField dk_user;
	private JTextField dk_email;
	private JTextField dk_sdt;
	private JTextField dk_userName;
	private String maKH = "";
	
	private User_BUS u_bus = new User_BUS();
	private KhachHang_BUS kh_bus = new KhachHang_BUS();
	private ArrayList <User> user;
	private ArrayList <KhachHang> customer;
	private JPasswordField dk_repass;
	private JPasswordField dk_pass;
	private JPasswordField password;
        String regex = "^(09|03|07|08|05)([0-9]{8})$";
        String regexEmail = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DangNhap frame = new DangNhap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DangNhap() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel left = new JPanel();
		//left.setBorder(new MatteBorder(0, 0, 0, 4, (Color) new Color(255, 128, 128)));
                left.setBackground(new Color(0, 0, 0, 64));
		left.setBounds(0, 0, 600, 660);
		contentPane.add(left);
		left.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("????ng K?? Ng?????i D??ng");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD, 25));
                lblNewLabel_1.setForeground(Color.black);
		lblNewLabel_1.setBounds(150, 23, 300, 45);
		left.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("T??n ????ng Nh???p:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
                lblNewLabel_2.setForeground(Color.black);
		lblNewLabel_2.setBounds(25, 80, 200, 24);
		left.add(lblNewLabel_2);
		
		JLabel lblMtKhu_1 = new JLabel("M???t Kh???u:");
		lblMtKhu_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMtKhu_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
                lblMtKhu_1.setForeground(Color.black);
		lblMtKhu_1.setBounds(25, 140, 200, 24);
		left.add(lblMtKhu_1);
		
		JLabel lblNhpLiMk = new JLabel("Nh???p L???i MK:");
		lblNhpLiMk.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNhpLiMk.setFont(new Font("Tahoma", Font.PLAIN, 18));
                lblNhpLiMk.setForeground(Color.black);
		lblNhpLiMk.setBounds(25, 200, 200, 24);
		left.add(lblNhpLiMk);
		
                JLabel lblTnNgiDng = new JLabel("T??n Ng?????i D??ng:");
		lblTnNgiDng.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTnNgiDng.setFont(new Font("Tahoma", Font.PLAIN, 18));
                lblTnNgiDng.setForeground(Color.black);
		lblTnNgiDng.setBounds(25, 260, 200, 24);
		left.add(lblTnNgiDng);
                
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
                lblEmail.setForeground(Color.black);
		lblEmail.setBounds(25, 320, 200, 24);
		left.add(lblEmail);
		
		JLabel lblSt = new JLabel("S??T:");
		lblSt.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSt.setFont(new Font("Tahoma", Font.PLAIN, 18));
                lblSt.setForeground(Color.black);
		lblSt.setBounds(25, 380, 200, 24);
		left.add(lblSt);
		
		dk_user = new JTextField();
		dk_user.setBounds(240, 80, 200, 30);
                dk_user.setFont(new Font("Arial", Font.PLAIN, 18));
		left.add(dk_user);
		dk_user.setColumns(10);
                
                dk_pass = new JPasswordField();
		dk_pass.setBounds(240, 140, 200, 30);
                dk_pass.setFont(new Font("Arial", Font.PLAIN, 18));
		left.add(dk_pass);
                
                dk_repass = new JPasswordField();
		dk_repass.setBounds(240, 200, 200, 30);
                dk_repass.setFont(new Font("Arial", Font.PLAIN, 18));
		left.add(dk_repass);
                
                dk_userName = new JTextField();
		dk_userName.setColumns(10);
		dk_userName.setBounds(240, 260, 200, 30);
                dk_userName.setFont(new Font("Arial", Font.PLAIN, 18));
		left.add(dk_userName);
                
		dk_email = new JTextField();
		dk_email.setColumns(10);
		dk_email.setBounds(240, 320, 200, 30);
                dk_email.setFont(new Font("Arial", Font.PLAIN, 18));
		left.add(dk_email);
		
		dk_sdt = new JTextField();
		dk_sdt.setColumns(10);
		dk_sdt.setBounds(240, 380, 200, 30);
                dk_sdt.setFont(new Font("Arial", Font.PLAIN, 18));
		left.add(dk_sdt);
		
		JButton btn_register = new JButton("????ng K??");
		btn_register.setFont(new Font("Arial", Font.BOLD, 30));
                btn_register.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0,0,0,10)));
                btn_register.setBackground(null);
                btn_register.setOpaque(false);
		btn_register.setBounds(15, 450, 560, 65);
		left.add(btn_register);
		
                
		JPanel right = new JPanel();
		right.setBorder(new MatteBorder(0, 4, 0, 0, (Color) new Color(255, 128, 128)));
		right.setBounds(600, 0, 600, 660);
                right.setBackground(new Color(0, 0, 0, 64));
		contentPane.add(right);
		right.setLayout(null);
		
                                
		JLabel lblNewLabel = new JLabel(new ImageIcon(DangNhap.class.getResource("/icons/user.png")));
		lblNewLabel.setBounds(70, 140, 80, 80);
                lblNewLabel.setName("");
		right.add(lblNewLabel);
		
		JLabel lblMtKhu = new JLabel(new ImageIcon(DangNhap.class.getResource("/icons/lock.png")));		                
                lblMtKhu.setBounds(70, 300, 80, 80);
                lblMtKhu.setName("");
		right.add(lblMtKhu);
		
		userName = new JTextField();
                userName.setName("txUser");
                userName.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
                userName.setFont(new Font("Arial", Font.PLAIN, 20));
                userName.setBackground(new Color(0,0,0));
                userName.setOpaque(false);
		userName.setBounds(160, 160, 350, 50);
		right.add(userName);
		userName.setColumns(10);
		
                password = new JPasswordField();
                password.setName("txpass");
                password.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0,0,0)));                
                password.setFont(new Font("Arial", Font.PLAIN, 20));                
                password.setOpaque(false);
		password.setBounds(160, 320, 350, 50);
		right.add(password);
                
		JButton btn_login = new JButton("????ng Nh???p");
		btn_login.setFont(new Font("Arial", Font.BOLD, 30));                
                btn_login.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0,0,0,10)));
                btn_login.setBackground(null);
                btn_login.setOpaque(false);
		btn_login.setBounds(20, 400, 560, 65);
                btn_login.setName("");
		right.add(btn_login);
                
                
		
                
                //???NH N???N
                
                JLabel lblNewbg = new JLabel(new ImageIcon(DangNhap.class.getResource("/img_BG/3.png")));
                lblNewbg.setBounds(0, 0, 600, 660);
                for(int i=0;i<left.getComponentCount();i++) if(!left.getComponent(i).equals(lblNewbg)) left.getComponent(i).setVisible(true);            
                left.add(lblNewbg);
                JLabel lblNewbgr = new JLabel(new ImageIcon(DangNhap.class.getResource("/img_BG/1.png")));
                lblNewbgr.setBounds(0, 0, 600, 660);               
                for(int i=0;i<right.getComponentCount();i++) if(!right.getComponent(i).equals(lblNewbgr)) right.getComponent(i).setVisible(true);
                right.add(lblNewbgr);
                //
                //H??NH N???N
                //JPanel bg = new JPanel();
                //bg.setBounds(0, 0, 590, 660);
                //contentPane.add(bg);
                //
				
		
		
		//EVENTS
			btn_register.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					registerAction();
				}
			});
                        for(int i=0;i<left.getComponentCount();i++){
                    if(left.getComponent(i) instanceof JTextField) left.getComponent(i).addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e){
                            if(e.getKeyCode()==KeyEvent.VK_ENTER) registerAction();
                        }
                    });
                }
			btn_login.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					loginAction();
				}
			});
			 for(int i=0;i<right.getComponentCount();i++){
                    if(right.getComponent(i) instanceof JTextField) right.getComponent(i).addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e){
                            if(e.getKeyCode()==KeyEvent.VK_ENTER) loginAction();
                        }
                    });
                         }
		//END OF EVENTS
		setVisible(true);
	}
        
        public void registerAction(){
            user = u_bus.getArrayList();
					
					
					String repass = dk_repass.getText();
					customer = kh_bus.getArrayList_normal();
					
					if(dk_user.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "T??n ????ng nh???p kh??ng ???????c ????? tr???ng", "T??n ????ng nh???p b??? tr???ng", JOptionPane.ERROR_MESSAGE);
						dk_user.requestFocus();
						return;
					}
					if(dk_pass.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "M???t kh???u b??? b??? tr???ng", "M???t Kh???u b??? tr???ng", JOptionPane.ERROR_MESSAGE);
						dk_repass.requestFocus();
						return;
					}
					if(repass.equals("")) {
						JOptionPane.showMessageDialog(null, "B???n ph???i nh???p l???i m???t kh???u", "Nh???p l???i m???t kh???u b??? tr???ng", JOptionPane.ERROR_MESSAGE);
						dk_repass.requestFocus();
						return;
					}
					if(!dk_pass.getText().equals(repass)) {
						JOptionPane.showMessageDialog(null, "Vi???c nh???p l???i m???t kh???u kh??ng tr??ng kh???p v???i m???t kh???u tr??n", "M???t Kh???u kh??ng tr??ng kh???p", JOptionPane.ERROR_MESSAGE);
						dk_repass.requestFocus();
						return;
					}
					
					if(dk_userName.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "T??n ng?????i d??ng kh??ng ???????c b??? tr???ng", "T??n ng?????i d??ng b??? tr???ng", JOptionPane.ERROR_MESSAGE);
						dk_userName.requestFocus();
						return;
					}
					if(dk_email.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Email kh??ng ???????c b??? tr???ng", "Email d??ng b??? tr???ng", JOptionPane.ERROR_MESSAGE);
						dk_email.requestFocus();
						return;
					}
					if(dk_email.getText().length() < 10) {
						JOptionPane.showMessageDialog(null, "Email ph???i ??t nh???t 10 k?? t???", "Email thi???u d??? li???u", JOptionPane.ERROR_MESSAGE);
						dk_email.requestFocus();
						return;
					}
					if(!dk_email.getText().matches(regexEmail)) {
						JOptionPane.showMessageDialog(null, "Email kh??ng h???p l??? kh??ng ???????c ch???a k?? t??? ?????c bi???t ho???c d???u VD: abcxyz@gmail.com", "Email kh??ng h???p l???", JOptionPane.ERROR_MESSAGE);
						dk_email.requestFocus();
						return;
					}
					if(dk_sdt.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "S??? ??i???n tho???i kh??ng ???????c b??? tr???ng", "S??? ??i???n tho???i b??? tr???ng", JOptionPane.ERROR_MESSAGE);
						dk_sdt.requestFocus();
						return;
					}
					if(!(dk_sdt.getText().length() == 10)) {
						JOptionPane.showMessageDialog(null, "S??? ??i???n tho???i ph???i ????ng 10 s???", "S??? ??i???n tho???i thi???u d??? li???u", JOptionPane.ERROR_MESSAGE);
						dk_sdt.requestFocus();
						return;
					}
					if(!dk_sdt.getText().matches(regex)) {
						JOptionPane.showMessageDialog(null, "S??? ??i???n tho???i kh??ng h???p l??? nh???p ????ng ?????u s??? VN VD:0931443823", "S??? ??i???n tho???i kh??ng h???p l???", JOptionPane.ERROR_MESSAGE);
						dk_sdt.requestFocus();
						return;
					}
					
					for(int i = 0; i < user.size(); i++) {
						if(dk_user.getText().equals(user.get(i).getUserName())) {
							JOptionPane.showMessageDialog(null, "???? t???n t???i t??i kho???n n??y", "Tr??ng kh???p d??? li???u", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					
					maKH = Integer.toString(customer.size() + 1);
					String password = dk_pass.getText();
					String idUser = dk_user.getText().trim();
					KhachHang kh = new KhachHang(maKH, dk_userName.getText().trim(), "", "", dk_sdt.getText(), "", "");
					kh_bus.Insert(kh);
					User u = new User(idUser, password, maKH, dk_email.getText().trim(), dk_userName.getText().trim(), dk_sdt.getText().trim(), "" + df.format(date),null);
					u_bus.Insert(u);
					JOptionPane.showMessageDialog(null, "???? ????ng k?? th??nh c??ng", "????ng K??", JOptionPane.INFORMATION_MESSAGE);
					userName.setText(dk_user.getText());
					dk_user.setText("");
					dk_pass.setText("");
					dk_repass.setText("");
					dk_email.setText("");
					dk_userName.setText("");
					dk_sdt.setText("");
					return;
        }
        
        public void loginAction(){
            userName.getText();
					password.getText();
					user = u_bus.getArrayList();
					
					if(userName.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "T??n ????ng nh???p kh??ng ???????c ????? tr???ng", "T??n ????ng nh???p b??? tr???ng", JOptionPane.ERROR_MESSAGE);
						userName.requestFocus();
						return;
					}
					if(password.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "M???t kh???u kh??ng ???????c ????? tr???ng", "T??n ????ng nh???p b??? tr???ng", JOptionPane.ERROR_MESSAGE);
						password.requestFocus();
						return;
					}
					
					int flag = 0;
					for(int i = 0; i < user.size(); i++) {
						if(userName.getText().equals(user.get(i).getUserName())) {
							if(!password.getText().equals(user.get(i).getPassword())) {
								JOptionPane.showMessageDialog(null, "Sai m???t kh???u", "Sai m???t kh???u", JOptionPane.ERROR_MESSAGE);
								password.requestFocus();
								return;
							}
							flag = 1;
							u_bus.Update_LoginDate("" + df.format(date), userName.getText());
                                                        if(user.get(i).getMaKH()!=null) new GiaoDienMuaHang(user.get(i).getTenUser(), user.get(i).getMaKH());
                                                        else new GUI.mainFrame(user.get(i).getMaNV()).setVisible(true);
							dispose();
							return;
						}
							
					}
					if(flag == 0) {
						JOptionPane.showMessageDialog(null, "Kh??ng t???n t???i t??i kho???n n??y", "Kh??ng t???n t???i d??? li???u", JOptionPane.ERROR_MESSAGE);
						userName.requestFocus();
						return;
					}
        }
}
