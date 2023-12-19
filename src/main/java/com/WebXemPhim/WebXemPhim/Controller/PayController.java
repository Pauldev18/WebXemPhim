package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.Config.VNPayConfig;
import com.WebXemPhim.WebXemPhim.DTO.ThongTinVe;
import com.WebXemPhim.WebXemPhim.Entity.*;
import com.WebXemPhim.WebXemPhim.Repository.*;
import com.WebXemPhim.WebXemPhim.Service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PayController {

    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private DatVeRepository datVeRepository;
    @Autowired
    private MaVeRepo maVeRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LichSuDatVerepo lichSuDatVerepo;
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Autowired
    private ChoNgoiRepo choNgoiRepo;
    @PostMapping("/pay")
    public String getPay(@RequestParam("price") long price, @RequestParam("idDatCho") int IDDatCho,
                         @RequestParam("IDUser") int idUser) throws UnsupportedEncodingException{

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = price*100;
        String bankCode = "NCB";

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl + "?IDDatCho=" + IDDatCho + "&idUser=" + idUser);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }
    @GetMapping("/checkPay")
    public RedirectView  checkPaymentStatus(@RequestParam("vnp_ResponseCode") String responseCode,
                                     @RequestParam("vnp_TxnRef") String transactionRef,
                                     @RequestParam("IDDatCho") int IDDatCho,
                                     @RequestParam("vnp_Amount") Long amount, @RequestParam("idUser") int idUser,
                                            RedirectAttributes redirectAttributes) {
        if ("00".equals(responseCode)) {
            String secretKey = "MaVe" + UUID.randomUUID();
            byte[] qrCode = qrCodeService.generateQrCode(secretKey, 500, 500);
            // Tạo ra mã vé mới để gửi cho User
            MaVe newMaVe = new MaVe();
            newMaVe.setMaSoVe(qrCode);
            newMaVe.setTrangThai(1);
            Date currentDate = Calendar.getInstance().getTime();
            newMaVe.setCreatedAt(currentDate);
            //
            DatVe datVe = datVeRepository.getTTSuatChieu(IDDatCho);
            newMaVe.setDatVe(datVe);
            maVeRepo.save(newMaVe);
           //
            LichSuDatVe addVeChoUser = new LichSuDatVe();
            Users select = userRepo.findById(idUser);
            addVeChoUser.setUser(select);
            addVeChoUser.setMaVe(newMaVe);
            addVeChoUser.setNgayMua(currentDate);
            lichSuDatVerepo.save(addVeChoUser);
            //
            HoaDon hoaDon = new HoaDon();
            hoaDon.setTotalPrice((double) amount);
            hoaDon.setUsers(select);
            hoaDon.setCreatedAt(currentDate);
            hoaDon.setTrangThai("Đã thanh toán");
            hoaDon.setDatVe(datVe);
            hoaDonRepo.save(hoaDon);
            //
            //
            ChoNgoi setTrangThai = choNgoiRepo.findById(datVe.getChoNgoi().getId_cho_ngoi());
            setTrangThai.setTrangThai(1);
            choNgoiRepo.save(setTrangThai);
            //
            ThongTinVe thongTinVe = new ThongTinVe();
            thongTinVe.setGioChieu(datVe.getGioChieu().getGio_chieu());
            thongTinVe.setTenGhe(datVe.getChoNgoi().getCho_ngoi());
            thongTinVe.setDiaDiem(datVe.getDiaDiem().getDia_chi());
            thongTinVe.setTenPhim(datVe.getPhim().getTenPhim());
            thongTinVe.setTenRap(datVe.getLoaiRap().getLoai_rap());
            thongTinVe.setAnhPhim(datVe.getPhim().getAnhPhim());
            thongTinVe.setNgayChieu(datVe.getNgayChieu().getNgayChieu());
            thongTinVe.setMaVe(qrCode);
            thongTinVe.setNgayMua(currentDate);
            redirectAttributes.addFlashAttribute("thongTinVe", thongTinVe);

            // Redirect to the success page
            return new RedirectView("http://127.0.0.1:5500/index.html");
        } else {
            return new RedirectView(VNPayConfig.urlFail);
        }
    }

}
