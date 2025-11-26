package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.model.ChamadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface ChamadoRepository extends JpaRepository<ChamadoModel, Long> {

    List<ChamadoModel> findAllByStatus(Status status);

    @Query("SELECT c FROM ChamadoModel c " +
            "LEFT JOIN FETCH c.solicitante " +
            "LEFT JOIN FETCH c.responsavel " +
            "LEFT JOIN FETCH c.anexo " +
            "LEFT JOIN FETCH c.categoria " +
            "LEFT JOIN FETCH c.sla " +
            "WHERE c.cdChamado = :cdChamado")

    Optional<ChamadoModel> findByIdWithRelations(@Param("cdChamado") Long cdChamado);

    List<ChamadoModel> findAllBySolicitante_CdUsuario(Long cdUsuario);
    List<ChamadoModel> findAllByResponsavel_CdUsuario(Long cdUsuario);
    List<ChamadoModel> findAllByResponsavelIsNull();

}
