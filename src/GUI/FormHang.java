/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BUS.DetailProduct_BUS;
import BUS.KhachHang_BUS;
import BUS.NhanVien_BUS;
import BUS.Product_BUS;
import DTO.ChiTietPhieuNhap;
import BUS.QuanLyChiTietPhieuNhapBUS;
import DTO.NhaCungCap;
import BUS.QuanLyNhaCungCapBUS;
import DTO.PhieuNhap;
import BUS.QuanLyPhieuNhapBUS;
import BUS.loaiSP_BUS;
import DTO.KhachHang;
import DTO.NhanVien;
import DTO.DetailOrDTO;
import DTO.OrderDTO;
import Excel.ExportExcel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author DELL
 */
public class FormHang extends JPanel {
    
    ChonSanPhamPanel target;

    public FormHang() {

    }

    public void addChiTiet(String masp, int soluong) {

    }
    
    public void setTarget(ChonSanPhamPanel target) {
        this.target = target;
    }
}

class PhieuNhapHang extends FormHang {
    NumberFormat PriceFormatter = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
    QuanLyChiTietPhieuNhapBUS qlctpn = new QuanLyChiTietPhieuNhapBUS();
    QuanLyPhieuNhapBUS qlpnBUS = new QuanLyPhieuNhapBUS();
    Product_BUS qlspBUS = new Product_BUS();
    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();
    BUS.NhanVien_BUS qlnvBUS = new BUS.NhanVien_BUS();

    NhanVien nhanVien;
    NhaCungCap nhacungcap;

    JTextField txMaPhieuNhap = new JTextField(20);
    JTextField txNhanVien = new JTextField(17);
    JTextField txNhaCC = new JTextField(17);
    JTextField txNgayLap = new JTextField(20);
    JTextField txGioLap = new JTextField(20);
    JTextField txTongTien = new JTextField(20);

    MoreButton btnChonNhanVien = new MoreButton();
    MoreButton btnChonNhaCC = new MoreButton();

    MyTable tbChiTietPhieuNhap = new MyTable();
    XoaButton btnXoa = new XoaButton();
    SuaButton btnSua = new SuaButton();
    RefreshButton btnRefresh = new RefreshButton();

    JButton btnNhapHang = new JButton("Nh???p h??ng");
    JButton btnHuy = new JButton("H???y");

    ArrayList<ChiTietPhieuNhap> dsctpn = new ArrayList<>();

    public PhieuNhapHang(int _x, int _y, int _width, int _height) {
        this.setBounds(_x, _y, _width, _height);
        this.setBackground(new Color(0, 0, 0));
        this.setLayout(new FlowLayout());

        // =============== panel input =================
        int plIP_height = 180;
        JPanel plInput = new JPanel();
        plInput.setPreferredSize(new Dimension(_width - 10, plIP_height));
        plInput.setBackground(new Color(240, 240, 240));
        plInput.setLayout(new FlowLayout());

        // btn
        btnChonNhaCC.setPreferredSize(new Dimension(30, 30));
        btnChonNhaCC.addActionListener((ae) -> {
            ChonNhaCungCapForm cncc = new ChonNhaCungCapForm(txNhaCC);
            cncc.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    String mancc = txNhaCC.getText();
                    nhacungcap = qlnccBUS.getNhaCungCap(mancc);
                    if (nhacungcap != null) {
                        txNhaCC.setText(nhacungcap.getTenNCC() + " (" + nhacungcap.getMaNCC() + ")");
                    }
                }
            });
        });

        btnChonNhanVien.setPreferredSize(new Dimension(30, 30));
        btnChonNhanVien.addActionListener((ae) -> {
            ChonNhanVienForm cnv = new ChonNhanVienForm(txNhanVien);
            cnv.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    String mavn = txNhanVien.getText();
                    nhanVien = qlnvBUS.getNhanVien(mavn);
                    if (nhanVien != null) {
                        txNhanVien.setText(nhanVien.getTenNV() + " (" + nhanVien.getMaNV() + ")");
                    }
                }
            });
        });
        btnChonNhanVien.setEnabled(false);

        // set border
        txMaPhieuNhap.setBorder(BorderFactory.createTitledBorder("M?? phi???u nh???p:"));
        txNhanVien.setBorder(BorderFactory.createTitledBorder("Nh??n vi??n:"));
        txNgayLap.setBorder(BorderFactory.createTitledBorder("Ng??y l???p:"));
        txGioLap.setBorder(BorderFactory.createTitledBorder("Gi??? l???p:"));
        txNhaCC.setBorder(BorderFactory.createTitledBorder("Nh?? cung c???p:"));
        txTongTien.setBorder(BorderFactory.createTitledBorder("T???ng ti???n:"));

        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        txMaPhieuNhap.setFont(f);
        txNhanVien.setFont(f);
        txNgayLap.setFont(f);
        txGioLap.setFont(f);
        txNhaCC.setFont(f);
        txMaPhieuNhap.setFont(f);
        txTongTien.setFont(f);

        // set Text
        if (mainFrame.getManv() != null) {
            nhanVien = qlnvBUS.getNhanVien(mainFrame.getManv());
            txNhanVien.setText(nhanVien.getTenNV() + " (" + nhanVien.getMaNV() + ")");
        }

        txMaPhieuNhap.setText(qlpnBUS.getNextID());
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                txNgayLap.setText(LocalDate.now().toString());
                txGioLap.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                if (txNhanVien.getText().equals("")
                        || txNhaCC.getText().equals("")
                        || txTongTien.getText().equals("")
                        || txTongTien.getText().equals("0")) {
                    btnNhapHang.setEnabled(false);
                } else {
                    btnNhapHang.setEnabled(true);
                }
            }
        }, 0, 1000);

        // set editable
        txMaPhieuNhap.setEditable(false);
        txNhanVien.setEditable(false);
        txNhaCC.setEditable(false);
        txNgayLap.setEditable(false);
        txGioLap.setEditable(false);
        txTongTien.setEditable(false);

        // add to panel
        plInput.add(txMaPhieuNhap);
        plInput.add(txTongTien);
        plInput.add(txNhaCC);
        plInput.add(btnChonNhaCC);
        plInput.add(txNhanVien);
        plInput.add(btnChonNhanVien);
        plInput.add(txNgayLap);
        plInput.add(txGioLap);

        this.add(plInput);

        // =============== panel c??c s???n ph???m ???? ch???n ==================
        int plSP_height = 495;
        JPanel plSanPham = new JPanel();
        plSanPham.setPreferredSize(new Dimension(_width - 10, plSP_height));
        plSanPham.setBackground(new Color(250, 250, 29));
        plSanPham.setLayout(new BorderLayout());

        int plBtn_height = 50;
        JPanel plButtonChiTiet = new JPanel();
        plButtonChiTiet.setLayout(new FlowLayout(FlowLayout.CENTER));
        plButtonChiTiet.setBackground(new Color(220, 220, 220));
        plButtonChiTiet.setPreferredSize(new Dimension(_width - 10, plBtn_height));

        btnXoa.addActionListener((ae) -> {
            btnXoaOnClick();
        });
        btnSua.addActionListener((ae) -> {
            btnSuaOnClick();
        });
        btnRefresh.addActionListener((ae) -> {
            setDataToTable(dsctpn, tbChiTietPhieuNhap);
        });

        plButtonChiTiet.add(btnXoa);
        plButtonChiTiet.add(btnSua);
        plButtonChiTiet.add(btnRefresh);

        tbChiTietPhieuNhap.setPreferredSize(new Dimension(_width - 10, plSP_height - plBtn_height));
        tbChiTietPhieuNhap.setHeaders(new String[]{"STT", "M??", "T??n", "S??? l?????ng", "????n gi??", "Th??nh ti???n"});
        tbChiTietPhieuNhap.setColumnsWidth(new double[]{1, 2, 3, 2.2, 2.5, 3});
        tbChiTietPhieuNhap.setAlignment(0, JLabel.CENTER);
        tbChiTietPhieuNhap.setAlignment(1, JLabel.CENTER);
        tbChiTietPhieuNhap.setAlignment(3, JLabel.CENTER);
        tbChiTietPhieuNhap.setAlignment(4, JLabel.RIGHT);
        tbChiTietPhieuNhap.setAlignment(5, JLabel.RIGHT);

        plSanPham.add(tbChiTietPhieuNhap, BorderLayout.CENTER);
        plSanPham.add(plButtonChiTiet, BorderLayout.SOUTH);

        this.add(plSanPham);

        // ============= panel Thanh to??n ==============
        int plTT_height = _height - plIP_height - plSP_height - 20;
        JPanel plThanhToan = new JPanel();
        plThanhToan.setLayout(new FlowLayout(FlowLayout.RIGHT));
        plThanhToan.setPreferredSize(new Dimension(_width - 10, plTT_height));
        plThanhToan.setBackground(new Color(0, 0, 0));

        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/icons/icons8_cancel_30px_1.png")));
        btnNhapHang.setIcon(new ImageIcon(this.getClass().getResource("/icons/icons8_us_dollar_30px.png")));

        btnHuy.addActionListener((ae) -> {
            btnHuyOnClick();
        });
        btnNhapHang.addActionListener((ae) -> {
            btnNhapHangOnClick();
        });

        plThanhToan.add(btnHuy);
        plThanhToan.add(btnNhapHang);

        this.add(plThanhToan);
    }

    private void btnHuyOnClick() {
        clear();
    }

    private void btnNhapHangOnClick() {
        PhieuNhap pn = new PhieuNhap(
                txMaPhieuNhap.getText(),
                nhacungcap.getMaNCC(),
                nhanVien.getMaNV(),
                LocalDate.parse(txNgayLap.getText()),
                LocalTime.parse(txGioLap.getText()),
                Float.parseFloat(txTongTien.getText().replaceAll("(\\.)+|??| ", "")));
        qlpnBUS.add(pn);

        for (ChiTietPhieuNhap ct : dsctpn) {
            qlctpn.add(ct);
        }
        
        int reply = JOptionPane.showConfirmDialog(getRootPane(),
                        "Nh???p h??ng th??nh c??ng, b???n c?? mu???n IN PHI???U NH???P?", "Th??nh c??ng",
                        JOptionPane.YES_NO_OPTION);
        if(reply == JOptionPane.OK_OPTION) {
            new PDF.ExportPDF().writePhieuNhap(txMaPhieuNhap.getText());
        }
        txMaPhieuNhap.setText(qlpnBUS.getNextID()); // l???y m?? cho phi???u nh???p m???i
        clear();
        this.target.refreshAll();
    }

    private void btnXoaOnClick() {
        int i = tbChiTietPhieuNhap.getTable().getSelectedRow();
        if (i >= 0 && i < dsctpn.size()) {
            dsctpn.remove(i);
            setDataToTable(dsctpn, tbChiTietPhieuNhap);
        }
    }

    private void btnSuaOnClick() {
        int i = tbChiTietPhieuNhap.getTable().getSelectedRow();
        if (i >= 0 && i < dsctpn.size()) {
            ChiTietPhieuNhap ct = dsctpn.get(i);
            this.target.showInfo(ct.getMaSP(), ct.getSoLuong());

            dsctpn.remove(i);
            setDataToTable(dsctpn, tbChiTietPhieuNhap);
        }
    }

    public void clear() {
        txNhaCC.setText("");
        txTongTien.setText("");
        dsctpn.clear();
        setDataToTable(dsctpn, tbChiTietPhieuNhap);
    }

    @Override
    public void addChiTiet(String masp, int soluong) {
        DTO.ProductDTO sp = qlspBUS.getSanPham(masp);

        Boolean daCo = false; // check xem trong danh s??ch chi ti???t h??a ????n ???? c?? s???n ph???m n??y ch??a
        for (ChiTietPhieuNhap ctpn : dsctpn) {
            if (ctpn.getMaSP().equals(sp.getIdProd())) {
                int tongSoLuong = soluong + ctpn.getSoLuong();
                ctpn.setSoLuong(tongSoLuong); // c?? r???i th?? thay ?????i s??? l?????ng
                daCo = true;
            }
        }

        if (!daCo) { // n???u ch??a c?? th?? th??m m???i
            ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap(qlpnBUS.getNextID(), masp, soluong, (float) sp.getPrice());
            dsctpn.add(ctpn);
        }

        // c???p nh???t l???i table
        setDataToTable(dsctpn, tbChiTietPhieuNhap);
    }

    public void setDataToTable(ArrayList<ChiTietPhieuNhap> arr, MyTable t) {
        t.clear();
        float tongtien = 0;
        int stt = 1;
        for (ChiTietPhieuNhap ctpn : arr) {
            String masp = ctpn.getMaSP();
            DTO.ProductDTO sp = qlspBUS.getSanPham(masp);
            String tensp = sp.getNameProd();
            int soluong = ctpn.getSoLuong();
            float dongia = ctpn.getDonGia();
            float thanhtien = soluong * dongia;

            t.addRow(new String[]{
                String.valueOf(stt),
                masp,
                tensp,
                String.valueOf(soluong),
                PriceFormatter.format(dongia),
                PriceFormatter.format(thanhtien)
            });
            stt++;
            tongtien += thanhtien;
        }

        // check khuy???n m??i
        t.addRow(new String[]{"", "", "", "", "", ""});
        t.addRow(new String[]{"", "", "", "", "T???ng ti???n", PriceFormatter.format(tongtien)});
        txTongTien.setText(PriceFormatter.format(tongtien));
    }
}

class HoaDonBanHang extends FormHang {
NumberFormat PriceFormatter = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
    Product_BUS qlspBUS = new BUS.Product_BUS();
    KhachHang_BUS qlkhBUS = new KhachHang_BUS();
    NhanVien_BUS qlnvBUS = new NhanVien_BUS();
    BUS.HoaDon_BUS qlhdBUS = new BUS.HoaDon_BUS();

    NhanVien nhanVien;
    KhachHang khachHang;

    JTextField txMaHoaDon = new JTextField(20);
    JTextField txNhanVien = new JTextField(17);
    JTextField txNgayLap = new JTextField(9);
    JTextField txGioLap = new JTextField(9);
    JTextField txKhachHang = new JTextField(17);
    JTextField txTongTien = new JTextField(20);

    MoreButton btnChonNhanVien = new MoreButton();
    MoreButton btnChonKhachHang = new MoreButton();

    MyTable tbChiTietHoaDon = new MyTable();
    XoaButton btnXoa = new XoaButton();
    SuaButton btnSua = new SuaButton();
    RefreshButton btnRefresh = new RefreshButton();

    JButton btnThanhToan = new JButton("Thanh to??n");
    JButton btnHuy = new JButton("H???y");

    ArrayList<DetailOrDTO> dscthd = new ArrayList<>();

    public HoaDonBanHang(int _x, int _y, int _width, int _height) {
        NumberFormat PriceFormatter = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
        this.setBounds(_x, _y, _width, _height);
        this.setBackground(new Color(0, 0, 0));
        this.setLayout(new FlowLayout());

        // =============== panel input =================
        int plIP_height = 180;
        JPanel plInput = new JPanel();
        plInput.setPreferredSize(new Dimension(_width - 10, plIP_height));
        plInput.setBackground(new Color(240, 240, 240));
        plInput.setLayout(new FlowLayout());

        // btn
        btnChonKhachHang.setPreferredSize(new Dimension(30, 30));
        btnChonKhachHang.addActionListener((ae) -> {
            ChonKhachHangForm ckh = new ChonKhachHangForm(txKhachHang);
            ckh.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    String makh = txKhachHang.getText();
                    khachHang = qlkhBUS.getKhachHang(makh);
                    if (khachHang != null) {
                        txKhachHang.setText(khachHang.getTenKH() + " (" + khachHang.getMaKh()+ ")");
                    }
                }
            });
        });

        btnChonNhanVien.setPreferredSize(new Dimension(30, 30));
        btnChonNhanVien.addActionListener((ae) -> {
            ChonNhanVienForm cnv = new ChonNhanVienForm(txNhanVien);
            cnv.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    String mavn = txNhanVien.getText();
                    nhanVien = qlnvBUS.getNhanVien(mavn);
                    if (nhanVien != null) {
                        txNhanVien.setText(nhanVien.getTenNV() + " (" + nhanVien.getMaNV() + ")");
                    }
                }
            });
        });
        btnChonNhanVien.setEnabled(false);


        // set border
        txMaHoaDon.setBorder(BorderFactory.createTitledBorder("M?? h??a ????n:"));
        txNhanVien.setBorder(BorderFactory.createTitledBorder("Nh??n vi??n:"));
        txNgayLap.setBorder(BorderFactory.createTitledBorder("Ng??y l???p:"));
        txGioLap.setBorder(BorderFactory.createTitledBorder("Gi??? l???p:"));
        txKhachHang.setBorder(BorderFactory.createTitledBorder("Kh??ch h??ng:"));
        txTongTien.setBorder(BorderFactory.createTitledBorder("T???ng ti???n:"));

        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        txMaHoaDon.setFont(f);
        txNhanVien.setFont(f);
        txNgayLap.setFont(f);
        txGioLap.setFont(f);
        txKhachHang.setFont(f);
        txMaHoaDon.setFont(f);
        txTongTien.setFont(f);

        // set Text
        if (mainFrame.getManv() != null) {
            nhanVien = qlnvBUS.getNhanVien(mainFrame.getManv());
            txNhanVien.setText(nhanVien.getTenNV() + " (" + nhanVien.getMaNV() + ")");
        }

        txMaHoaDon.setText(Integer.toString(qlhdBUS.getMaxId()));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                txNgayLap.setText(LocalDate.now().toString());
                txGioLap.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                if (txNhanVien.getText().equals("")
                        || txKhachHang.getText().equals("")
                        || dscthd.isEmpty()) {
                    btnThanhToan.setEnabled(false);
                } else {
                    btnThanhToan.setEnabled(true);
                }
            }
        }, 0, 1000);

        // set editable
        txMaHoaDon.setEditable(false);
        txNhanVien.setEditable(false);
        txKhachHang.setEditable(false);
        txNgayLap.setEditable(false);
        txGioLap.setEditable(false);
        txTongTien.setEditable(false);

        // add to panel
        plInput.add(txMaHoaDon);
        plInput.add(txTongTien);
        plInput.add(txKhachHang);
        plInput.add(btnChonKhachHang);
        plInput.add(txNhanVien);
        plInput.add(btnChonNhanVien);
        plInput.add(txNgayLap);
        plInput.add(txGioLap);

        this.add(plInput);

        // =============== panel c??c s???n ph???m ???? ch???n ==================
        int plSP_height = 495;
        JPanel plSanPham = new JPanel();
        plSanPham.setPreferredSize(new Dimension(_width - 10, plSP_height));
        plSanPham.setBackground(new Color(250, 250, 29));
        plSanPham.setLayout(new BorderLayout());

        int plBtn_height = 50;
        JPanel plButtonChiTiet = new JPanel();
        plButtonChiTiet.setLayout(new FlowLayout(FlowLayout.CENTER));
        plButtonChiTiet.setBackground(new Color(220, 220, 220));
        plButtonChiTiet.setPreferredSize(new Dimension(_width - 10, plBtn_height));

        btnXoa.addActionListener((ae) -> {
            btnXoaOnClick();
        });
        btnSua.addActionListener((ae) -> {
            btnSuaOnClick();
        });
        btnRefresh.addActionListener((ae) -> {
            setDataToTable(dscthd, tbChiTietHoaDon);
        });

        plButtonChiTiet.add(btnXoa);
        plButtonChiTiet.add(btnSua);
        plButtonChiTiet.add(btnRefresh);

        tbChiTietHoaDon.setPreferredSize(new Dimension(_width - 10, plSP_height - plBtn_height));
        tbChiTietHoaDon.setHeaders(new String[]{"STT", "M??", "T??n", "S??? l?????ng", "????n gi??", "Th??nh ti???n"});
        tbChiTietHoaDon.setColumnsWidth(new double[]{1, 2, 3, 2.2, 2.5, 3});
        tbChiTietHoaDon.setAlignment(0, JLabel.CENTER);
        tbChiTietHoaDon.setAlignment(1, JLabel.CENTER);
        tbChiTietHoaDon.setAlignment(3, JLabel.CENTER);
        tbChiTietHoaDon.setAlignment(4, JLabel.RIGHT);
        tbChiTietHoaDon.setAlignment(5, JLabel.RIGHT);

        plSanPham.add(tbChiTietHoaDon, BorderLayout.CENTER);
        plSanPham.add(plButtonChiTiet, BorderLayout.SOUTH);

        this.add(plSanPham);

        // ============= panel Thanh to??n ==============
        int plTT_height = _height - plIP_height - plSP_height - 20;
        JPanel plThanhToan = new JPanel();
        plThanhToan.setLayout(new FlowLayout(FlowLayout.RIGHT));
        plThanhToan.setPreferredSize(new Dimension(_width - 10, plTT_height));
        plThanhToan.setBackground(new Color(0, 0, 0));

        btnHuy.setIcon(new ImageIcon(this.getClass().getResource("/icons/icons8_cancel_30px_1.png")));
        btnThanhToan.setIcon(new ImageIcon(this.getClass().getResource("/icons/icons8_us_dollar_30px.png")));

        btnHuy.addActionListener((ae) -> {
            btnHuyOnClick();
        });
        btnThanhToan.addActionListener((ae) -> {
            btnThanhToanOnClick();
        });

        plThanhToan.add(btnHuy);
        plThanhToan.add(btnThanhToan);

        this.add(plThanhToan);
    }

    private void btnHuyOnClick() {
        clear();
    }

    private void btnThanhToanOnClick() {
    try {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        OrderDTO hd = new OrderDTO(
                Integer.parseInt(txMaHoaDon.getText()),                
                Integer.parseInt(khachHang.getMaKh()),
                nhanVien.getMaNV(),
                Integer.parseInt(txTongTien.getText().replaceAll("(\\.)|??| ", "")),
                df.format(new SimpleDateFormat("yyyy-MM-dd").parse(txNgayLap.getText())),
                        LocalTime.parse(txGioLap.getText()).toString()
                );
        qlhdBUS.addToCart(hd, dscthd);
        
        int reply = JOptionPane.showConfirmDialog(getRootPane(),
                "Thanh to??n th??nh c??ng, b???n c?? mu???n IN H??A ????N?", "Th??nh c??ng",
                JOptionPane.YES_NO_OPTION);
        if(reply == JOptionPane.OK_OPTION) {
            ExportExcel wfe = new ExportExcel();
            ArrayList<OrderDTO> bill = qlhdBUS.getArrayList_condition("`M?? ng?????i d??ng`", khachHang.getMaKh());
            ArrayList<KhachHang> customer = qlkhBUS.getArrayList_normal();
            try {
                wfe.writeStatistic(customer,bill, "Excel/hoadon.xlsx");
                JOptionPane.showMessageDialog(null, "???? xu???t file Excel th??nh c??ng", "Th??ng B??o", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                JOptionPane.showMessageDialog(null, "Xu???t file Excel th???t b???i", "Th??ng B??o", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        txMaHoaDon.setText(Integer.toString(qlhdBUS.getMaxId()));
        clear();
        this.target.refreshAll();
    } catch (ParseException ex) {
        Logger.getLogger(HoaDonBanHang.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    private void btnXoaOnClick() {
        int i = tbChiTietHoaDon.getTable().getSelectedRow();
        if (i >= 0 && i < dscthd.size()) {
            dscthd.remove(i);
            setDataToTable(dscthd, tbChiTietHoaDon);
        }
    }

    private void btnSuaOnClick() {
        int i = tbChiTietHoaDon.getTable().getSelectedRow();
        if (i >= 0 && i < dscthd.size()) {
            DetailOrDTO ct = dscthd.get(i);
            this.target.showInfo(Integer.toString(ct.getIdProd()), ct.getSl());

            dscthd.remove(i);
            setDataToTable(dscthd, tbChiTietHoaDon);
        }
    }

    public void clear() {
        txKhachHang.setText("");
        txTongTien.setText("");
        dscthd.clear();
        setDataToTable(dscthd, tbChiTietHoaDon);
    }

    @Override
    public void addChiTiet(String masp, int soluong) {
        DTO.ProductDTO sp = qlspBUS.getSanPham(masp);

        Boolean daCo = false; // check xem trong danh s??ch chi ti???t h??a ????n ???? c?? s???n ph???m n??y ch??a
        for (DetailOrDTO cthd : dscthd) {
            if (cthd.getIdProd()==sp.getIdProd()) {
                int tongSoLuong = soluong + cthd.getSl();
                if (tongSoLuong > sp.getSL()) {
                    JOptionPane.showMessageDialog(this, "S??? l?????ng s???n ph???m trong kho kh??ng ????? (" + sp.getSL()+ ")");
                    return;
                }
                cthd.setSl(tongSoLuong); // c?? r???i th?? thay ?????i s??? l?????ng
                daCo = true;
            }
        }

        if (!daCo) { // n???u ch??a c?? th?? th??m m???i
            if (soluong > sp.getSL()) {
                JOptionPane.showMessageDialog(this, "S??? l?????ng s???n ph???m trong kho kh??ng ????? (" + sp.getSL() + ")");
                return;
            }
            DetailOrDTO cthd = new DetailOrDTO(qlhdBUS.getMaxId(), Integer.parseInt(masp), soluong, sp.getPrice(), "0");
            dscthd.add(cthd);
        }

        // c???p nh???t l???i table
        setDataToTable(dscthd, tbChiTietHoaDon);
    }

    public void setDataToTable(ArrayList<DetailOrDTO> arr, MyTable t) {
        t.clear();
        float tongtien = 0;
        int stt = 1;
        for (DetailOrDTO cthd : arr) {
            String masp = Integer.toString(cthd.getIdProd());
            DTO.ProductDTO sp = qlspBUS.getSanPham(masp);
            String tensp = sp.getNameProd();
            int soluong = cthd.getSl();
            float dongia = cthd.getMoney();
            float thanhtien = soluong * dongia;

            t.addRow(new String[]{
                String.valueOf(stt),
                masp,
                tensp,
                String.valueOf(soluong),
                PriceFormatter.format(dongia),
                PriceFormatter.format(thanhtien)
            });
            stt++;
            tongtien += thanhtien;           
        }
        txTongTien.setText(PriceFormatter.format(tongtien));
 
    }
}

class ChonSanPhamPanel extends JPanel {
NumberFormat PriceFormatter = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
    BUS.loaiSP_BUS qllspBUS = new loaiSP_BUS();
    Product_BUS qlspBUS = new Product_BUS();
    BUS.DetailProduct_BUS qlctspBUS = new DetailProduct_BUS();
    MyTable tbSanPham = new MyTable();
    JTextField txTimKiem = new JTextField(30);

    JLabel lbImage = new JLabel();
    JTextField txMaSP = new JTextField(12);
    JTextField txLoaiSP = new JTextField(12);
    JTextField txTenSP = new JTextField(12);
    JTextField txDonGia = new JTextField(12);
    JTextField txSoLuong = new JTextField(7);

    RefreshButton btnRefresh = new RefreshButton();
    ThemButton btnThem = new ThemButton();

    FormHang target = new FormHang();

    public ChonSanPhamPanel(int _x, int _y, int _width, int _height) {
        this.setBounds(_x, _y, _width, _height);
        this.setBackground(new Color(59, 68, 75));
        this.setLayout(new BorderLayout());

        // panel hi???n th??? s???n ph???m
        int plSP_height = _height - 300;
        JPanel plSanPham = new JPanel();
        plSanPham.setPreferredSize(new Dimension(_width - 10, plSP_height));
        plSanPham.setBackground(new Color(49, 49, 49));
        plSanPham.setLayout(new BorderLayout());

        JPanel plTimKiem = new JPanel();
        txTimKiem.setBorder(BorderFactory.createTitledBorder("T??m ki???m"));
        txTimKiem.setHorizontalAlignment(JLabel.CENTER);
        addDocumentListener(txTimKiem);
        plTimKiem.add(txTimKiem);
        btnRefresh.addActionListener((ae) -> {
            refreshTable();
        });
        plTimKiem.add(btnRefresh);
        plSanPham.add(plTimKiem, BorderLayout.NORTH);

        tbSanPham.setHeaders(new String[]{"M??", "Lo???i", "T??n", "????n gi??", "S??? l?????ng"});
        tbSanPham.setColumnsWidth(new double[]{.5, .5, 3, 1, .5});
        tbSanPham.setAlignment(3, JLabel.RIGHT);
        tbSanPham.setAlignment(4, JLabel.RIGHT);
        tbSanPham.setupSort();
        plSanPham.add(tbSanPham, BorderLayout.CENTER);

        this.add(plSanPham, BorderLayout.CENTER);

        // =========== panel chi ti???t s???n ph???m ch???n ================
        JPanel plChiTiet = new JPanel();
        plChiTiet.setPreferredSize(new Dimension(_width - 10, 258));
        plChiTiet.setBackground(new Color(240, 240, 240));
        plChiTiet.setLayout(new BorderLayout());

        lbImage.setBackground(Color.yellow);
        lbImage.setPreferredSize(new Dimension(200, 200));
        plChiTiet.add(lbImage, BorderLayout.WEST);

        JPanel plTextField = new JPanel();
        plTextField.setLayout(new FlowLayout());
        plTextField.setBackground(new Color(240, 240, 240));
        // border
        txMaSP.setBorder(BorderFactory.createTitledBorder("M?? s???n ph???m"));
        txLoaiSP.setBorder(BorderFactory.createTitledBorder("Lo???i s???n ph???m"));
        txTenSP.setBorder(BorderFactory.createTitledBorder("T??n s???n ph???m"));
        txDonGia.setBorder(BorderFactory.createTitledBorder("????n gi??"));
        txSoLuong.setBorder(BorderFactory.createTitledBorder("S??? l?????ng"));
        // disable
        txMaSP.setEditable(false);
        txLoaiSP.setEditable(false);
        txTenSP.setEditable(false);
        txDonGia.setEditable(false);
        // font
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        txMaSP.setFont(f);
        txLoaiSP.setFont(f);
        txTenSP.setFont(f);
        txDonGia.setFont(f);
        txSoLuong.setFont(f);
        // add to panel
        plTextField.add(txMaSP);
        plTextField.add(txLoaiSP);
        plTextField.add(txTenSP);
        plTextField.add(txDonGia);
        plTextField.add(txSoLuong);

        btnThem.addActionListener((ae) -> {
            try {
                String masp = txMaSP.getText();
                int soluong = Integer.parseInt(txSoLuong.getText());
                if (soluong > 0) {
                    this.target.addChiTiet(masp, soluong);
                } else {
                    JOptionPane.showMessageDialog(txSoLuong, "S??? l?????ng ph???i l?? s??? d????ng!");
                    txSoLuong.requestFocus();
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(txSoLuong, "S??? l?????ng ph???i l?? s??? nguy??n!");
                txSoLuong.requestFocus();
            }
        });

        plChiTiet.add(plTextField, BorderLayout.CENTER);
        plChiTiet.add(btnThem, BorderLayout.SOUTH);

        this.add(plChiTiet, BorderLayout.SOUTH);

        // event
        tbSanPham.getTable().addMouseListener(new MouseAdapter() { // copy t??? HienThiSanPham
            @Override
            public void mouseReleased(MouseEvent me) {
                String masp = getSelectedSanPham(0);
                if (masp != null) {
                    showInfo(masp, 1);
                }
            }
        });

        refreshAll();
    }

    public void setTarget(FormHang target) {
        this.target = target;
    }

    public void refreshTable() {
        setDataToTable(qlspBUS.getArrayList(), tbSanPham);
    }

    public void refreshAll() {
        refreshTable();
        txMaSP.setText("");
        txLoaiSP.setText("");
        txTenSP.setText("");
        txDonGia.setText("");
        txSoLuong.setText("");
        lbImage.setIcon(null);
    }

    public void showInfo(String masp, int soluong) {
        // https://stackoverflow.com/questions/16343098/resize-a-picture-to-fit-a-jlabel
        if (masp != null) {
            // show h??nh
            for (DTO.ProductDTO sp : qlspBUS.getArrayList()) {
                if (Integer.toString(sp.getIdProd()).equals(masp)) {
                    for(DTO.chitietsanphamDTO ctsp : qlctspBUS.getList()){
                        if(ctsp.getmasanpham()==sp.getIdProd()){
                    int w = lbImage.getWidth();
                    int h = lbImage.getHeight();
                     try{
         if(ctsp.getFilenamehinhanh().contains(":/")) lbImage.setIcon(new ImageIcon(new ImageIcon(ctsp.getFilenamehinhanh()).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)));
        else lbImage.setIcon(new ImageIcon(new ImageIcon(Product_Frame.class.getResource("/"+ctsp.getFilenamehinhanh())).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH)));
            } catch(NullPointerException ne){
                
            }

                    // show info
                    String loai = qllspBUS.getLoaiSanPham(ctsp.getmadanhmuc()).getName();
                    txMaSP.setText(Integer.toString(sp.getIdProd()));
                    txTenSP.setText(sp.getNameProd());
                    txLoaiSP.setText(loai + " - " + ctsp.getmadanhmuc());
                    txDonGia.setText(PriceFormatter.format(sp.getPrice()));
                    txSoLuong.setText(String.valueOf(soluong));
                    return;
                        }
                    }
                }
            }
        }
    }

    private void addDocumentListener(JTextField tx) {
        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        tx.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                txSearchOnChange();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                txSearchOnChange();
            }
        });
    }

    public void txSearchOnChange() {
        setDataToTable(qlspBUS.search(txTimKiem.getText()), tbSanPham);
    }

    public String getSelectedSanPham(int col) {
        int i = tbSanPham.getTable().getSelectedRow();
        if (i >= 0) {
            int realI = tbSanPham.getTable().convertRowIndexToModel(i);
            return tbSanPham.getModel().getValueAt(realI, col).toString();
        }
        return null;
    }

    private void setDataToTable(ArrayList<DTO.ProductDTO> data, MyTable table) {
        table.clear();
        for (DTO.ProductDTO sp : data) {
            for(DTO.chitietsanphamDTO ctsp : qlctspBUS.getList()){
                if(ctsp.getmasanpham()==sp.getIdProd()){
                table.addRow(new String[]{
                    Integer.toString(sp.getIdProd()),
                    ctsp.getmadanhmuc(),
                    sp.getNameProd(),
                    PriceFormatter.format(sp.getPrice()),
                    String.valueOf(sp.getSL()),});
                }
            }
        }
    }
}
