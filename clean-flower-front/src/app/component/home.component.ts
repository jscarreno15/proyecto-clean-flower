import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { ProductoDTO } from '../dtos/ProductoDTO';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { MatPaginatorIntl, PageEvent } from '@angular/material/paginator';
import { ProductoService } from '../services/producto.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  displayedColumns: string[] = ['id', 'descripcion', 'codigo', 'stock', 'precioCosto', 'precioVenta', 'editar', 'borrar', 'sumarCarrito'];
  productos : ProductoDTO[] = [];
  clickedRows = new Set<ProductoDTO>();
  paginaActual: number = 0;
  totalRegistros: number = 10;
  totalPaginas: number = 0;
  registrosPorPagina: number = 10;
  todosDocumentos: string = '';
  codigoFiltro: string= '';
  nombreFiltro: string = '';
  modalAbierto: boolean = false;
  productosCarrito: ProductoDTO[] = [];

  constructor(private productoService: ProductoService,
              private router: Router,
              public dialog: MatDialog,
              private toastr: ToastrService,
              private paginatorIntl: MatPaginatorIntl){}

  ngOnInit(): void {
    this.cargarDatosTabla();
    this.paginatorIntl.itemsPerPageLabel = 'Productos por página';
  }

  cargarDatosTabla(){
    this.productoService.listarProducto(
      this.paginaActual,
      this.registrosPorPagina,
      this.nombreFiltro,
      this.codigoFiltro
    ).subscribe({
      next: (data: any) => {
        this.productos = data?.listaProductos;
        this.totalRegistros = data?.totalRegistros;
        this.totalPaginas = data?.totalPaginas;
        this.paginaActual = data?.paginaActual+1;
      },
      error: (err) => {
        console.error('Error al obtener los datos de tabla ', err);
        this.toastr.error('Error al obtener los datos de tabla.');
      }
    })
  }

  editarProducto(idProducto: ProductoDTO) {
    this.router.navigate(['/editar', idProducto]);
  }

  borrar(idProducto: number) {
    this.productoService.borrarProducto(idProducto).subscribe({
      next: (data: boolean) => {
        this.toastr.success("El producto fue eliminado correctamente.");
        this.paginaActual = 0;
        this.registrosPorPagina = 5;
        this.cargarDatosTabla();
      },
      error: (err) => {
        console.error('Error al borrar registro ', err);
        this.toastr.error('Error al borrar registro.');
      }
    })
  }


  aplicarFiltro(){
    this.paginaActual = 0;
    this.registrosPorPagina = 5;
    this.cargarDatosTabla();
  }

  redirectToCrear() {
    this.router.navigate(['/crear']);
  }

  onPageChange(event: PageEvent) {
    this.paginaActual = event.pageIndex;
    this.registrosPorPagina = event.pageSize;
    this.cargarDatosTabla();
  }

  openDialog(idProducto: number, enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogRef = this.dialog.open(DialogAnimations, {
      width: '500px',
      height: '250px',
      enterAnimationDuration,
      exitAnimationDuration,
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      if (result === true)
        this.borrar(idProducto);
    });
  }

  openDialogVenta(enterAnimationDuration: string, exitAnimationDuration: string): void {
    const dialogRef = this.dialog.open(DialogVenta, {
      width: '750px',
      height: '700px',
      enterAnimationDuration,
      exitAnimationDuration,
      data: {
        productos: this.productosCarrito
      }
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      if(result){
        this.toastr.success("Venta concretada exitosamente");
        this.productosCarrito = [];
        this.paginaActual = 0;
        this.totalRegistros = 10;
        this.totalPaginas = 0;
        this.registrosPorPagina = 10;
        this.cargarDatosTabla();
      }
    });
  }

  sumarCarrito(element: ProductoDTO, cantidad: number){
    if(element && element?.cantidad > 0 ){
      this.productosCarrito.push(element);
      this.toastr.success("El producto fue agregado al carrito de venta.");
    }else{
      this.toastr.error("No existe stock para el producto que desea agregar. ");
    }

  }

}



@Component({
  selector: 'dialog-animations-example-dialog',
  templateUrl: './dialogs/home.dialog.html',
  styleUrls: ['./home.component.css']
})
export class  DialogAnimations {
  constructor(public dialogRef: MatDialogRef<DialogAnimations>) {}

  onAccept(): void {
    this.dialogRef.close(true);
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}

@Component({
  selector: 'dialog-animations-example-dialog',
  templateUrl: './dialogs/dialog-venta.html',
  styleUrls: ['./home.component.css']
})
export class  DialogVenta implements OnInit, OnDestroy{
  constructor(public dialogRef: MatDialogRef<DialogAnimations>,
              @Inject(MAT_DIALOG_DATA) public data: any,
              private productoService: ProductoService,
              private toastr: ToastrService) {
  this.productosCarrito = data?.productos;
}

  cantidadVendida: number = 0;
  productosCarrito: ProductoDTO[] = [];
  displayedColumns: string[] = ['descripcion', 'codigo', 'precio', 'borrar', 'agregar'];
  clickedRows = new Set<ProductoDTO>();
  totalVenta: number = 0;


  ngOnInit(): void {
    this.productosCarrito.forEach(producto => this.totalVenta = this.totalVenta + producto.precioVenta)
  }

  ngOnDestroy(): void {
    this.totalVenta = 0;
    this.productosCarrito = [];
  }

  onAccept(): void {
      this.productoService.venderProducto(this.productosCarrito).subscribe({
        next: () => {
          this.dialogRef.close(true);
          this.totalVenta = 0;
          this.productosCarrito = [];
        },
        error: (err) => {
          console.error('Error al generar la venta ', err);
          this.toastr.error('Error al generar la venta: '+ err?.error.mensaje);
        }
      })
  }
  

  onCancel(): void {
    this.dialogRef.close();
  }

  quitarElemento(elementId: number){
    const producto = this.productosCarrito.find(item => item.id === elementId);
    if (producto) {
      this.totalVenta -= producto.precioVenta;
      this.productosCarrito = this.productosCarrito.filter(item => item.id !== elementId);
    }
  }
}