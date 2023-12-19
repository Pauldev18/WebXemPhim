package com.WebXemPhim.WebXemPhim.Repository;

import com.WebXemPhim.WebXemPhim.Entity.ChoNgoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChoNgoiRepo extends JpaRepository<ChoNgoi, Integer> {

   ChoNgoi findById(int idChoNgoi);
   @Query(value = "SELECT * FROM cho_ngoi WHERE id_cho_ngoi IN :ids", nativeQuery = true)
   List<ChoNgoi> findAllByIds(List<Integer> ids);
}
