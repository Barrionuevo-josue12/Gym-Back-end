package gimnasios.com.mapper;

import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.AfiliadoIndependiente;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.dto.AfiliadoCorporativoRequestDto;
import gimnasios.com.dto.AfiliadoIndependienteDto;
import gimnasios.com.util.GimnasioUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AfiliadoMapper {

    public AfiliadoCorporativo toAfiliadoCorporativoEntity(AfiliadoCorporativoDto dto) {
        return AfiliadoCorporativo.builder().dni(dto.getDni())
                .email(dto.getEmail())
                .nombreCompleto(dto.getNombreCompleto())
                .cuit(dto.getCuit())
                .nombreEmpresa(dto.getNombreEmpresa())
                .build();
    }

    public AfiliadoIndependiente toAfiliadoIndependienteEntity(AfiliadoIndependienteDto dto){
        return AfiliadoIndependiente.builder().dni(dto.getDni())
                .aptoFisico(dto.getAptoFisico())
                .email(dto.getEmail())
                .nombreCompleto(dto.getNombreCompleto())
                .telefono(dto.getTelefono())
                .build();
    }
    public AfiliadoIndependienteDto toAfiliadoIndependienteDto (AfiliadoIndependiente entidad){
        return AfiliadoIndependienteDto.builder().dni(entidad.getDni())
                .afiliadoId(entidad.getAfiliadoId())
                .aptoFisico(entidad.getAptoFisico())
                .email(entidad.getEmail())
                .fechaNacimiento(entidad.getFechaNacimiento().toString())
                .telefono(entidad.getTelefono())
                .nombreCompleto(entidad.getNombreCompleto())
                .build();
    }
    public AfiliadoCorporativoDto toAfiliadoCorporativoDto (AfiliadoCorporativo entidad){
        return AfiliadoCorporativoDto.builder().dni(entidad.getDni())
                .afiliadoId(entidad.getAfiliadoId())
                .email(entidad.getEmail())
                .fechaNacimiento(entidad.getFechaNacimiento().toString())
                .nombreCompleto(entidad.getNombreCompleto())
                .cuit(entidad.getCuit())
                .nombreEmpresa(entidad.getNombreEmpresa())
                .sucursalId(entidad.getSucursal().getIdSucursal()) //podria haber error de lazy initialization
                .build();
    }
    public AfiliadoCorporativo toAfiliadoCorporativoSinceRequestDto(AfiliadoCorporativo entity, AfiliadoCorporativoRequestDto dto) {
        if (GimnasioUtil.stringOk(dto.getNombreCompleto())) {
            entity.setNombreCompleto(dto.getNombreCompleto());
        }
        if (GimnasioUtil.stringOk(dto.getEmail())) {
            entity.setEmail(dto.getEmail());
        }
        if (GimnasioUtil.stringOk(dto.getFechaNacimiento())) {
            LocalDate fecha = LocalDate.parse(dto.getFechaNacimiento());
            entity.setFechaNacimiento(fecha);
        }
        if (GimnasioUtil.stringOk(dto.getNombreEmpresa())) {
            entity.setNombreEmpresa(dto.getNombreEmpresa());
        }
        if (GimnasioUtil.stringOk(dto.getCuit())) {
            entity.setCuit(dto.getCuit());
        }
        return entity;
    }
}
