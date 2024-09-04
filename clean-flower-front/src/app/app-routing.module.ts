import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home.component';
import { ProductoResolverService } from './services/producto-resolver.service';
import { AltaEdicionComponent } from './component/alta-edicion/alta-edicion.component';

const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch:'full' },
  { path: 'inicio', component: HomeComponent },
  { path: 'editar/:idProducto', component: AltaEdicionComponent, resolve: { producto: ProductoResolverService}},
  { path: 'crear', component: AltaEdicionComponent},
  { path: '',
    component:HomeComponent, 
    children: [ 
    {path: '', redirectTo: '/producto' , pathMatch: 'full'}
  ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
