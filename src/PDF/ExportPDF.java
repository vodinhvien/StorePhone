package PDF;

import BUS.QuanLyChiTietPhieuNhapBUS;
import BUS.QuanLyNhaCungCapBUS;
import BUS.QuanLyPhieuNhapBUS;
import java.awt.Font;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ByteBuffer;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import DTO.OrderDTO;
import DTO.KhachHang;
import DTO.ProductDTO;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.awt.FileDialog;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class ExportPDF {
	public ExportPDF() {
		try {
            fontData = new com.itextpdf.text.Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, com.itextpdf.text.Font.NORMAL);
            fontTitle = new com.itextpdf.text.Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, com.itextpdf.text.Font.NORMAL);
            fontHeader = new com.itextpdf.text.Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 13, com.itextpdf.text.Font.NORMAL);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ExportPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
        com.itextpdf.text.Font fontData;
        com.itextpdf.text.Font fontTitle;
        com.itextpdf.text.Font fontHeader;
        Document document;
        FileOutputStream file;
	FileDialog fd = new FileDialog(new JFrame(), "Xu???t file", FileDialog.SAVE);
        NumberFormat PriceFormatter = NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
        
        public void setTitle(String title) {
        try {
            Paragraph pdfTitle = new Paragraph(new Phrase(title, fontTitle));
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfTitle);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
//            JOptionPane.showMessageDialog(null, "Khong goi duoc document !");
            ex.printStackTrace();
        }
    }
        
        private String getFile() {
        fd.setFile("untitled.pdf");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("nullnull")) {
            return null;
        }
        return url;
        }
        
        public void writePhieuNhap(String mapn) {
        String url = "";
        try {
            fd.setTitle("In phi???u nh???p");
            url = getFile();
            if (url == null) {
                return;
            }
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            
            setTitle("Th??ng tin phi???u nh???p");

            QuanLyPhieuNhapBUS qlpnBUS = new QuanLyPhieuNhapBUS();
            QuanLyChiTietPhieuNhapBUS qlctpnBUS = new QuanLyChiTietPhieuNhapBUS();
            BUS.Product_BUS qlspBUS = new BUS.Product_BUS();
            QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();
            BUS.NhanVien_BUS qlnvBUS = new BUS.NhanVien_BUS();

            DTO.PhieuNhap pn = qlpnBUS.getPhieuNhap(mapn);

            Chunk glue = new Chunk(new VerticalPositionMark());// Khoang trong giua hang
            Paragraph paraMaHoaDon = new Paragraph(new Phrase("Phi???u nh???p: " + String.valueOf(pn.getMaPN()), fontData));
            Paragraph para1 = new Paragraph();
            para1.setFont(fontData);
            para1.add(String.valueOf("Nh?? cung c???p: " + qlnccBUS.getNhaCungCap(pn.getMaNCC()).getTenNCC() + "  -  " + pn.getMaNCC()));
            para1.add(glue);
            para1.add("Ng??y l???p: " + String.valueOf(pn.getNgayNhap()));

            Paragraph para2 = new Paragraph();
            para2.setPaddingTop(30);
            para2.setFont(fontData);
            para2.add(String.valueOf("Nh??n vi??n: " + qlnvBUS.getNhanVien(pn.getMaNV()).getTenNV() + "  -  " + pn.getMaNV()));
            para2.add(glue);
            para2.add("Gi??? l???p: " + String.valueOf(pn.getGioNhap()));

            document.add(paraMaHoaDon);
            document.add(para1);
            document.add(para2);
//            document.add(paraKhuyenMai);
            document.add(Chunk.NEWLINE);//add hang trong de tao khoang cach

            //Tao table cho cac chi tiet cua hoa don
            PdfPTable pdfTable = new PdfPTable(5);
            PdfPCell cell;

            //Set headers cho table chi tiet
            pdfTable.addCell(new PdfPCell(new Phrase("M?? s???n ph???m", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("T??n s???n ph???m", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("????n gi??", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("S??? l?????ng", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("T???ng ti???n", fontHeader)));

            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                pdfTable.addCell(cell);
            }

            //Truyen thong tin tung chi tiet vao table
            for (DTO.ChiTietPhieuNhap ctpn : qlctpnBUS.getAllChiTiet(mapn)) {
                pdfTable.addCell(new PdfPCell(new Phrase(ctpn.getMaSP(), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(qlspBUS.getSanPham(ctpn.getMaSP()).getNameProd(), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(PriceFormatter.format(ctpn.getDonGia()), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(String.valueOf(ctpn.getSoLuong()), fontData)));
                pdfTable.addCell(new PdfPCell(new Phrase(PriceFormatter.format(ctpn.getDonGia() * ctpn.getSoLuong()), fontData)));
            }

            document.add(pdfTable);
            document.add(Chunk.NEWLINE);

            Paragraph paraTongThanhToan = new Paragraph(new Phrase("T???ng thanh to??n: " + PriceFormatter.format(pn.getTongTien()), fontData));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
            document.close();
            
            JOptionPane.showMessageDialog(null, "Ghi file th??nh c??ng: " + url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "L???i khi ghi file " + url);
        }

    }
        
	public void writePDFProduct(ArrayList <ProductDTO> sp, String filePath) throws DocumentException, IOException{

		Document document = new Document(PageSize.A3);
		Paragraph title = new Paragraph("Danh s??ch s???n ph???m c??n l???i trong kho", fontTitle);
		title.add(new Paragraph("\n"));
		title.setAlignment(Paragraph.ALIGN_CENTER);
		PdfPTable table = new PdfPTable(new float[] {
			4, 10, 4, 4, 4
		});
		
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new PdfPCell(new Phrase("M?? s???n ph???m",fontHeader)));
		table.addCell(new PdfPCell(new Phrase("T??n s???n ph???m",fontHeader)));
		table.addCell(new PdfPCell(new Phrase("Chi ti???t s???n ph???m",fontHeader)));
		table.addCell(new PdfPCell(new Phrase("Gi?? ",fontHeader)));
		table.addCell(new PdfPCell(new Phrase("S??? l?????ng",fontHeader)));
		table.setHeaderRows(1);
		PdfPCell [] cells = table.getRow(0).getCells();
		
		for(int i = 0; i < cells.length; i++) {
			cells[i].setBackgroundColor(BaseColor.GRAY);
		}
		for(int i = 0; i < sp.size(); i++) {
			table.addCell(new PdfPCell(new Phrase(Integer.toString(sp.get(i).getIdProd()),fontData)));
                        table.addCell(new PdfPCell(new Phrase(sp.get(i).getNameProd(),fontData)));
                        table.addCell(new PdfPCell(new Phrase(sp.get(i).getDescription(),fontData)));
                        table.addCell(new PdfPCell(new Phrase(Integer.toString(sp.get(i).getPrice()),fontData)));
                        table.addCell(new PdfPCell(new Phrase(Integer.toString(sp.get(i).getSL()),fontData)));
		}
		
		PdfWriter.getInstance(document, new FileOutputStream("PDF/danhsachsanphamtrongkho.pdf"));
		document.open();
		document.add(title);
		document.add(table);
		document.close();
		System.out.println("Xu???t PDF th??nh c??ng");
		
	}
        
        public void writePDFStatics(ArrayList <OrderDTO> orders, ArrayList <KhachHang> cus, String filePath) throws DocumentException, IOException{

		Document document = new Document(PageSize.A3);
		Paragraph title = new Paragraph("Danh s??ch th???ng k?? ????n h??ng", fontTitle);
		title.add(new Paragraph("\n"));
		title.setAlignment(Paragraph.ALIGN_CENTER);
		PdfPTable table = new PdfPTable(new float[] {
			4, 10, 6, 4, 10, 4, 10, 10, 6
		});
		
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(new PdfPCell(new Phrase("M?? ????n h??ng",fontHeader)));
		table.addCell(new PdfPCell(new Phrase("T???ng ti???n",fontHeader)));
		table.addCell(new PdfPCell(new Phrase("Ng??y l???p",fontHeader)));
		table.addCell(new PdfPCell(new Phrase("M?? kh??ch h??ng ",fontHeader)));
		table.addCell(new PdfPCell(new Phrase("T??n kh??ch h??ng",fontHeader)));
                table.addCell(new PdfPCell(new Phrase("Gi???i t??nh",fontHeader)));
                table.addCell(new PdfPCell(new Phrase("CMND",fontHeader)));
                table.addCell(new PdfPCell(new Phrase("S??? ??i???n tho???i",fontHeader)));
                table.addCell(new PdfPCell(new Phrase("Ng??y sinh",fontHeader)));
		table.setHeaderRows(1);
		PdfPCell [] cells = table.getRow(0).getCells();
		
		for(int i = 0; i < cells.length; i++) {
			cells[i].setBackgroundColor(BaseColor.GRAY);
		}
		for(int i = 0; i < orders.size(); i++) {
                    for(int j = 0 ; j < cus.size(); j++){
                        if(cus.get(j).getMaKh().equals(Integer.toString(orders.get(i).getId()))){
			table.addCell(new PdfPCell(new Phrase(Integer.toString(orders.get(i).getIdOrder()),fontData)));
                        table.addCell(new PdfPCell(new Phrase(Integer.toString(orders.get(i).getTotalmoney()),fontData)));
                        table.addCell(new PdfPCell(new Phrase(orders.get(i).getOrderdate(),fontData)));
                        table.addCell(new PdfPCell(new Phrase(Integer.toString(orders.get(i).getId()),fontData)));
                        table.addCell(new PdfPCell(new Phrase(cus.get(j).getTenKH(),fontData)));
                        table.addCell(new PdfPCell(new Phrase(cus.get(j).getGender(),fontData)));
                        table.addCell(new PdfPCell(new Phrase(cus.get(j).getCMND(),fontData)));
                        table.addCell(new PdfPCell(new Phrase(cus.get(j).getSdt(),fontData)));
                        table.addCell(new PdfPCell(new Phrase(cus.get(j).getBirthDate(),fontData)));
                        }
                    }
		}
		
		PdfWriter.getInstance(document, new FileOutputStream("PDF/danhsachthongke.pdf"));
		document.open();
		document.add(title);
		document.add(table);
		document.close();
		System.out.println("Xu???t PDF th??nh c??ng");
		
	}
	
	private String Encoding(String a) {
		byte[] utf8;
		String value = null;
		try {
			utf8 = a.getBytes("UTF-8");
			value = new String(utf8, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	private Paragraph addEmptyLine(Paragraph p , int number) {
		for(int i = 0; i < number;i ++) {
			p.add(new Paragraph(""));
		}
		return p;
	}
}
