package gimnasios.com.mapper;

import gimnasios.com.domain.Sucursal;
import gimnasios.com.dto.SucursalDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class SucursalMapper {
    public  Sucursal toSucursalEntity(SucursalDto dto){
        LocalDateTime fecha = LocalDateTime.parse(dto.getHorarioApertura());
        return Sucursal.builder().nombreSede(dto.getNombreSede())
                .horarioApertura(fecha)
                .build();
    }
    public SucursalDto toSucursalDto (Sucursal entidad){
        return SucursalDto.builder().idSucursal(entidad.getIdSucursal())
                .horarioApertura(entidad.getHorarioApertura().toString())
                .nombreSede(entidad.getNombreSede())
                .build();
    }
}
