import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProductoDTO } from 'src/app/dtos/ProductoDTO';
import { ProductoService } from 'src/app/services/producto.service';
import { Utils } from 'src/app/services/utils';

@Component({
  selector: 'app-alta-edicion',
  templateUrl: './alta-edicion.component.html',
  styleUrls: ['./alta-edicion.component.css']
})
export class AltaEdicionComponent implements OnInit{
  formProducto!: FormGroup;
  producto: ProductoDTO = new ProductoDTO;
  todosDocumentos: string = '';

  constructor(private formBuilder: FormBuilder,
              private productoService: ProductoService,
              private toastr: ToastrService,
              private router: Router,
              private route: ActivatedRoute){}

  ngOnInit(): void {
    let productoEdicion = this.route.snapshot.data['producto'];
    if(productoEdicion)
      this.producto = productoEdicion as ProductoDTO;
    this.cargarForm();
  }

  cargarForm(){
    this.formProducto = this.formBuilder.group(
      {
        id: new FormControl({value: this.producto?.id, disabled: false}),
        descripcion: new FormControl({value: this.producto?.descripcion, disabled: false}, Validators.required),
        codigo: new FormControl({value: this.producto?.codigo, disabled: false}, Validators.required),
        cantidad: new FormControl({value: this.producto?.cantidad, disabled: false}, Validators.required),
        precioCosto: new FormControl({value: this.producto?.precioCosto, disabled: false}, Validators.required),
        precioVenta: new FormControl({value: this.producto?.precioVenta, disabled: false}, Validators.required),
      }
    )
  }

  guardarProducto(){
    let body = this.formProducto.value as ProductoDTO;
    let peticion = body.id ? this.productoService.editarProducto(body) : this.productoService.guardarProducto(body);
    peticion.subscribe({
      next: (data:ProductoDTO) => {
        this.producto = data;
        this.success();
      },
      error: (err) => {
        console.error('Error al crear el producto: ', err);
        this.toastr.error("Error al intentar guardar el producto.");
      }
    })
  }

  cargarDatosProducto(idProducto: number){
    this.productoService.buscarProducto(idProducto).subscribe({
      next: (data:any) => {
        this.cargarForm();
      },
      error: (err) => {
        console.error('Error al cargar datos del producto: ', err);
        this.toastr.error("Error al cargar datos del producto.");
      }
    })
  }

  validation( campo:string ){
    return Utils.validationForm( campo, this.formProducto);
  }

  success(){
    this.toastr.success('Producto creado correctamente.');
    this.router.navigate(['/inicio']);
  }

  volver(){
    this.router.navigate(['/inicio']);
  }

  formatearFecha(cadenaFecha: string): string {
    // Convertir la cadena de fecha en un objeto Date
    const fecha = new Date(cadenaFecha);

    // Extraer el año, mes y día
    const anio = fecha.getFullYear();
    const mes = (fecha.getMonth() + 1).toString().padStart(2, '0'); // Sumamos 1 porque los meses van de 0 a 11
    const dia = fecha.getDate().toString().padStart(2, '0');

    // Formar la nueva cadena en el formato "yyyy-mm-dd"
    return `${anio}-${mes}-${dia}`;
}


}

class Accordion {
  private accordionItems: HTMLElement[];

  constructor() {
    this.accordionItems = Array.from(document.querySelectorAll('.accordion-item'));
    this.init();
  }

  private init() {
    this.accordionItems.forEach(item => {
      const button = item.querySelector('.accordion-button');
      const collapse = item.querySelector('.accordion-collapse');

      if (button && collapse) {
        button.addEventListener('click', () => {
          const isCollapsed = collapse.classList.contains('show');
          this.closeAll();

          if (!isCollapsed) {
            collapse.classList.add('show');
          }
        });
      }
    });
  }

  private closeAll() {
    this.accordionItems.forEach(item => {
      const collapse = item.querySelector('.accordion-collapse');
      if (collapse) {
        collapse.classList.remove('show');
      }
    });
  }
}

document.addEventListener('DOMContentLoaded', () => {
  new Accordion();
});
