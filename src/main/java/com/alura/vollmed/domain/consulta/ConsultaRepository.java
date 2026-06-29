package com.alura.vollmed.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("""
    SELECT COUNT(c) > 0
    FROM Consulta c
    WHERE c.idPaciente = :idPaciente
      AND c.data BETWEEN :inicioDoDia AND :fimDoDia
    """)
    Boolean existsByPacienteNoIntervalo(
            Long idPaciente,
            LocalDateTime inicioDoDia,
            LocalDateTime fimDoDia
    );

    @Query("""
            SELECT c
            FROM Consulta c
            WHERE c.idMedico = :idMedico
            AND c.data = :data
            """)
    List<Consulta> findAllByData(Long idMedico,LocalDateTime data);
}
