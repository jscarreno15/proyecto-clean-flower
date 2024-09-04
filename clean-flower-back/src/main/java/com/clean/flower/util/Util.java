package com.clean.flower.util;

import org.apache.logging.log4j.util.Strings;

import java.text.SimpleDateFormat;
import java.util.*;

public class Util {

    public static <T> Map<String, Object> itemMapeoPaginado(List<T> listaItems, Integer paginaActual, Long totalRegistros, Integer totalPaginas) {
        Map<String, Object> response = new HashMap<>();
        response.put("listaProductos", listaItems);
        response.put("paginaActual", paginaActual);
        response.put("totalRegistros", totalRegistros);
        response.put("totalPaginas", totalPaginas);
        return response;
    }

    public static String formatoFecha(Date fecha){
        if (Objects.nonNull(fecha)){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(fecha);
        }
        return Strings.EMPTY;
    }
}
