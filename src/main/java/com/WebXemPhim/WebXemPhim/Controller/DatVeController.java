package com.WebXemPhim.WebXemPhim.Controller;

import com.WebXemPhim.WebXemPhim.DTO.*;
import com.WebXemPhim.WebXemPhim.Entity.DatVe;
import com.WebXemPhim.WebXemPhim.Entity.DiaDiem;
import com.WebXemPhim.WebXemPhim.Entity.GioChieu;
import com.WebXemPhim.WebXemPhim.Entity.Tinh;
import com.WebXemPhim.WebXemPhim.Repository.DatVeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.event.ListDataListener;
import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin
@RestController
public class DatVeController {

    @Autowired
    private DatVeRepository datVeRepository;
    @GetMapping("/datve")
    public List<DatVe> getById(@RequestParam("id") int id)
    {
        return datVeRepository.findByIdTinh(id);
    }

    @GetMapping("/getTinhPhim")
    public ResponseEntity<List<DiaDiemByTinhAndPhim>> getDiaDiem(@RequestParam("idNgayChieu") int idNgayChieu, @RequestParam("idPhim") int idPhim){

        List<DatVe> allVe = datVeRepository.getDiaDiemByTinhAndPhim(idNgayChieu, idPhim);

        Set<String> uniqueDiaDiems = new HashSet<>();

        List<DiaDiemByTinhAndPhim> allDiaDiem = allVe.stream().filter(datVe -> uniqueDiaDiems.add(datVe.getDiaDiem().getDia_chi()))
                .map(diaDiem -> {
                    DiaDiemByTinhAndPhim diaDiemDTO = new DiaDiemByTinhAndPhim();
                    diaDiemDTO.setDiaDiem(diaDiem.getDiaDiem().getDia_chi());
                    diaDiemDTO.setId(diaDiem.getDiaDiem().getId_dia_diem());
                    return diaDiemDTO;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(allDiaDiem, HttpStatus.OK);
    }
    @GetMapping("/getNgayPhim")
    public ResponseEntity<List<NgayByIdPhim>> getNgay(@RequestParam("IDPhim") int idPhim){
        List<DatVe> allNgay = datVeRepository.findNgayByIdPhim(idPhim);
        Set<Date> filterNgayTrung = new HashSet<>();
        List<NgayByIdPhim> ngays = allNgay.stream()
                .filter(ngay -> filterNgayTrung.add(ngay.getNgayChieu().getNgayChieu()))
                .map(ngay ->{
            NgayByIdPhim ngayByIdPhim = new NgayByIdPhim();
            ngayByIdPhim.setThoiGian(ngay.getNgayChieu().getNgayChieu());
            ngayByIdPhim.setId(ngay.getNgayChieu().getId());
            return ngayByIdPhim;
        })
         .distinct()
         .collect(Collectors.toList());
        return new ResponseEntity<>(ngays, HttpStatus.OK);
    }


    @GetMapping("/getRapPhim")
    public ResponseEntity<List<RapDTO>> getRap(@RequestParam("idPhim") int idPhim, @RequestParam("idNgayChieu") int idNgayChieu,
                                               @RequestParam("idTinh") int idTinh)
    {
        List<DatVe> listIdRap = datVeRepository.getLoaiRap(idPhim, idNgayChieu, idTinh);
        Set<String> filterRapTrung = new HashSet<>();
        List<RapDTO> listTenRap = listIdRap.stream()
                .filter(rap -> filterRapTrung.add(rap.getLoaiRap().getLoai_rap()))
                .map(
                IDrap ->{
                    RapDTO rapDTO = new RapDTO();
                    rapDTO.setTenRap(IDrap.getLoaiRap().getLoai_rap());
                    rapDTO.setId(IDrap.getLoaiRap().getId_loai_rap());
                    return rapDTO;
                }
        )
         .distinct()
         .collect(Collectors.toList());
        return new ResponseEntity<>(listTenRap, HttpStatus.OK);
    }

    @GetMapping("/getDiaDiaAndGioChieu")
    public ResponseEntity<List<DiaDiemAndGioChieu>> getAll(
            @RequestParam("idPhim") int idPhim,
            @RequestParam("idNgayChieu") int idNgayChieu,
            @RequestParam("idTinh") int idTinh,
            @RequestParam("idRap") int idRap) {

        List<DatVe> result = datVeRepository.getDiaDiemAndGioChieu(idPhim, idNgayChieu, idTinh, idRap);

        // Sử dụng LinkedHashMap để duy trì thứ tự của các cặp key-value theo thứ tự đầu tiên xuất hiện
        Map<DiaDiem, List<GioChieuDTO>> groupedByDiaDiem = new LinkedHashMap<>();

        for (DatVe datVe : result) {
            DiaDiem diaDiem = datVe.getDiaDiem();
            GioChieuDTO gioChieuDTO = new GioChieuDTO(datVe.getGioChieu().getId_gio_chieu(), datVe.getGioChieu().getGio_chieu());


            // Nếu diaDiem đã có trong Map, thêm gioChieuDTO vào danh sách tương ứng
            // Nếu không, tạo mới một danh sách và thêm vào Map
            groupedByDiaDiem.computeIfAbsent(diaDiem, k -> new ArrayList<>()).add(gioChieuDTO);
        }

        // Tạo danh sách kết quả từ Map
        List<DiaDiemAndGioChieu> list = groupedByDiaDiem.entrySet().stream().map(entry -> {
                    DiaDiem diaDiem = entry.getKey();
                    List<GioChieuDTO> gioChieuList = entry.getValue()
                            .stream()
                            .distinct() // Sử dụng distinct với equals và hashCode đã được override trong GioChieuDTO
                            .collect(Collectors.toList());

                    DiaDiemAndGioChieu diaDiemAndGioChieu = new DiaDiemAndGioChieu();
                    diaDiemAndGioChieu.setIdDiaDiem(diaDiem.getId_dia_diem());
                    diaDiemAndGioChieu.setDiaDiem(diaDiem.getDia_chi());
                    diaDiemAndGioChieu.setListGioChieu(gioChieuList);

                    return diaDiemAndGioChieu;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/getChoNgoi")
    public ResponseEntity<List<ChoNgoiDTO>> getChoNgoi(@RequestParam("idPhim") int idPhim,
                                                       @RequestParam("idNgayChieu") int idNgayChieu,
                                                       @RequestParam("idTinh") int idTinh,
                                                       @RequestParam("idRap") int idRap,
                                                       @RequestParam("idDiaDiem") int idDiaDiem,
                                                       @RequestParam("idGioChieu") int idGioChieu){
        List<DatVe> IDChoNgoi = datVeRepository.getChoNgoi(idPhim, idNgayChieu, idTinh, idRap, idDiaDiem, idGioChieu);
        Set<String> filterRapTrung = new HashSet<>();
        List<ChoNgoiDTO> choNgoiDTOS = IDChoNgoi.stream().filter(cn -> filterRapTrung.add(cn.getChoNgoi().getCho_ngoi())).map(
                choNgoi ->{
                    ChoNgoiDTO choNgoiDTO = new ChoNgoiDTO();
                    choNgoiDTO.setId(choNgoi.getChoNgoi().getId_cho_ngoi());
                    choNgoiDTO.setChoNgoi(choNgoi.getChoNgoi().getCho_ngoi());
                    choNgoiDTO.setTrangThai(choNgoi.getChoNgoi().getTrangThai());
                    return choNgoiDTO;
                }
        ).collect(Collectors.toList());
        return new ResponseEntity<>(choNgoiDTOS, HttpStatus.OK);

    }



}
