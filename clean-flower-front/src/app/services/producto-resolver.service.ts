import { Injectable } from '@angular/core';
import { ProductoService } from './producto.service';
import { ActivatedRouteSnapshot } from '@angular/router';
import { Observable, of } from 'rxjs';
import { ProductoDTO } from '../dtos/ProductoDTO';

@Injectable({
  providedIn: 'root'
})
export class ProductoResolverService {

  constructor(private productoService: ProductoService) { }

  resolve(route: ActivatedRouteSnapshot):Observable<ProductoDTO>{
    const id = route.paramMap.get('idProducto');
    return this.productoService.buscarProducto(id);
  }
}
