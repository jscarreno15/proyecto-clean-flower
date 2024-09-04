import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { ProductoDTO } from '../dtos/ProductoDTO';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  constructor(private httpClient: HttpClient) { }

  private baseUrl: string = environment.baseUrl;

  guardarProducto(data: ProductoDTO): Observable<ProductoDTO>{
    return this.httpClient.post<ProductoDTO>(this.baseUrl + 'productos', data);
  }

  editarProducto(data: ProductoDTO): Observable<ProductoDTO>{
    return this.httpClient.put<ProductoDTO>(this.baseUrl + 'productos', data);
  }

  listarProducto(paginaActual:number, totalRegistros:number, descripcion?:string, codigo?:string):Observable<any>{
    return this.httpClient.get<any>(this.baseUrl+'productos/listar?paginaActual='+ paginaActual + '&totalRegistros=' + totalRegistros + '&descripcion=' + descripcion + '&codigo=' +codigo);
  }

  buscarProducto(idProducto:any):Observable<ProductoDTO>{
    return this.httpClient.get<ProductoDTO>(this.baseUrl+'productos/'+idProducto);
  }

  borrarProducto(idProducto:number):Observable<boolean>{
    return this.httpClient.delete<boolean>(this.baseUrl+'productos/'+idProducto);
  }

  venderProducto(ventaProducto : ProductoDTO[]):Observable<boolean>{
    return this.httpClient.put<boolean>(this.baseUrl+'productos/venta/', ventaProducto);
  }
}
