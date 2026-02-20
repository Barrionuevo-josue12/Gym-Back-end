package gimnasios.com.mapper;

import gimnasios.com.domain.AfiliadoCorporativo;
import gimnasios.com.domain.AfiliadoIndependiente;
import gimnasios.com.dto.AfiliadoCorporativoDto;
import gimnasios.com.dto.AfiliadoIndependienteDto;

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
}
