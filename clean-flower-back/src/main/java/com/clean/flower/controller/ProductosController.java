package com.clean.flower.controller;

import com.clean.flower.service.ProductoService;
import com.clean.flower.dtos.ProductoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@Slf4j
@CrossOrigin(origins = "*")
public class ProductosController {

    @Autowired
    private ProductoService productoService;


    /**
     * Método para crear producto.
     * @param producto del objeto producto.
     * @return el producto creada con su respectivo ID.
     */
    @PostMapping("")
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO producto){
        log.info(":::::::: ENDPOINT CREAR PRODUCTO ::::::::");
        ProductoDTO productoDTO = productoService.guardarProducto(producto);
        return ResponseEntity.status(HttpStatus.OK).body(productoDTO);
    }

    /**
     * Método para editar producto.
     * @param producto DTO del objeto producto.
     * @return el producto editada con su respectivo ID.
     */
    @PutMapping("")
    public ResponseEntity<ProductoDTO> editarProducto(@RequestBody ProductoDTO producto){
        log.info(":::::::: ENDPOINT EDITAR PRODUCTO ::::::::");
        ProductoDTO productoDTO = productoService.editarProducto(producto);
        return ResponseEntity.status(HttpStatus.OK).body(productoDTO);
    }

    /**
     * Método para listar productos paginadas y filtros.
     * @param paginaActual pagina actual del paginado.
     * @param totalRegistros cantidad de resultados a mostrar por pagina.
     * @param descripcion nombre de producto para aplicar filtro.
     * @return listado de productos.
     */
    @GetMapping("listar")
    public ResponseEntity<Map<String, Object>> listarItems(@RequestParam(required = false, defaultValue = "0")  Integer paginaActual,
                                                           @RequestParam(required = false, defaultValue = "5") Integer totalRegistros,
                                                           @RequestParam(required = false, defaultValue = "")   String descripcion,
                                                           @RequestParam(required = false, defaultValue = "")   String codigo){
        log.info(":::::::: ENDPOINT LISTAR PRODUCTOS ::::::::");
        Map<String,Object> respuesta = productoService.listarProductos(paginaActual,totalRegistros,descripcion,codigo);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);

    }

    /**
     * Método para buscar producto por su id.
     * @param id del producto a buscar.
     * @return producto con el id ingresado.
     */
    @GetMapping("{id}")
    public ResponseEntity<ProductoDTO> buscarProductoPorId(@PathVariable  Long id){
        log.info(":::::::: ENDPOINT BUSCAR PRODUCTO POR ID ::::::::");
        ProductoDTO producto = productoService.buscarProductoPorID(id);
        return ResponseEntity.status(HttpStatus.OK).body(producto);
    }

    /**
     * Método para eliminar producto.
     * @param idProducto para eliminar registro.
     * @return true o false segun corresponda.
     */
    @DeleteMapping("{idProducto}")
    public ResponseEntity<Boolean> eliminarProducto (@PathVariable  Long idProducto){
        log.info(":::::::: ENDPOINT BORRAR PRODUCTO ::::::::");
        Boolean respuesta = productoService.borrarProducto(idProducto);
        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    /**
     * Metodo para venta de productos
     * @return boolean si se realizo transaccion
     */
    @PutMapping("venta/")
    public ResponseEntity<Boolean> venderProducto(@RequestBody List<ProductoDTO> listaProductos){
        log.info(":::::::: ENDPOINT VENTA PRODUCTO ::::::::");
        return ResponseEntity.status(HttpStatus.OK).body(productoService.ventaProducto(listaProductos));
    }

}
