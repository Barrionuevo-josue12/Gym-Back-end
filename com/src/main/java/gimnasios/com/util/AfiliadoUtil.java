package gimnasios.com.util;

import gimnasios.com.dto.*;
import gimnasios.com.exception.AfiliadoException;
import gimnasios.com.exception.ReglaDeNegocioException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AfiliadoUtil {

    public static void validarAfiliado(AfiliadoDto dto){
        if(dto.getNombreCompleto() == null || dto.getNombreCompleto().isBlank()){
            log.error("El afiliado{} tiene un nombre incorrecto",dto);
            throw new AfiliadoException("El afiliado tiene un nombre incorrecto: "+dto);
        }
        if(dto.getEmail() == null || dto.getEmail().isBlank()){
            log.error("El afiliado{} tiene un email incorrecto",dto);
            throw new AfiliadoException("El afiliado tiene un email incorrecto: "+dto);
        }
        if(dto.getDni() == null || dto.getDni().isBlank()){
            log.error("El afiliado{} tiene un dni incorrecto",dto);
            throw new AfiliadoException("El afiliado tiene un dni incorrecto: "+dto);
        }
        if(dto.getFechaNacimiento() == null || dto.getFechaNacimiento().isBlank()){
            log.error("El afiliado{} tiene una fecha de nacimiento incorrecta",dto);
            throw new AfiliadoException("El afiliado tiene una fecha de nacimiento incorrecta: "+dto);
        }
        if(dto.getSucursalId() == null){
            log.error("El afiliado no posee una sucursal {}",dto);
            throw  new AfiliadoException("El afiliado no posee una sucursal "+dto);
        }
    }
    public static void validarAfiliadoCorporativo (AfiliadoCorporativoDto dto){
        validarAfiliado(dto);
        if(dto.getNombreEmpresa() == null || dto.getNombreEmpresa().isBlank()){
            log.error("El afiliado corporativo tiene un nombre de empresa incorrecto{}",dto);
            throw new AfiliadoException("El afiliado corporativo tiene un nombre de empresa incorrecto "+dto);
        }
        if(dto.getCuit() == null || dto.getCuit().isBlank()){
            log.error("El afiliado corporativo tiene un cuit incorrecto{}",dto);
            throw new AfiliadoException("El afiliado corporativo tiene un cuit incorrecto"+dto);
        }
    }
    public static void validarAfiliadoIndependiente(AfiliadoIndependienteDto dto){
        validarAfiliado(dto);
        if(dto.getAptoFisico() == null){
            log.error("afiliado independiente con apto fisico incorrecto{}",dto);
            throw new AfiliadoException("afiliado independiente con apto fisico incorrecto: "+dto);
        }
        if(dto.getTelefono() == null || dto.getTelefono().isBlank()){
            log.error("afiliado independiente con telefono incorrecto{}",dto);
            throw new AfiliadoException("afiliado independiente con telefono incorrecto "+dto);
        }
    }
    public static void validarActualizacionAfiliadoIndependiente(AfiliadoIndependienteRequestDto dto){
        boolean allNull = dto.getAptoFisico() == null && dto.getTelefono() == null;
        if(allNull && allAfiliatesAtributtesAreNull(dto)){
            log.error("Se ha intentado actualizar un afiliado independiente, donde todos sus valores son nulls/vacios {}",dto);
            throw new ReglaDeNegocioException("Se ha  Se ha intentado actualizar un afiliado independiente, donde todos sus valores son nulls");
        }
    }

    public static boolean allAfiliatesAtributtesAreNull(AfiliadoRequestDto dto){
        return (dto.getEmail() == null && dto.getSucursalId() == null && dto.getNombreCompleto() == null && dto.getFechaNacimiento() == null);
    }

    public static void validarActualizacionAfiliadoCorporativo(AfiliadoCorporativoRequestDto dto){
        boolean allNull = dto.getNombreEmpresa() == null && dto.getCuit() == null;
        if(allAfiliatesAtributtesAreNull(dto) && allNull){
            log.error("Se ha intentado actualizar un afiliado corporativo, cuyos valores son vacios {} ",dto);
            throw new ReglaDeNegocioException("Se ha intentado actualizar un afiliado corporativo, cuyos valores son vacions "+dto);
        }
    }
}
