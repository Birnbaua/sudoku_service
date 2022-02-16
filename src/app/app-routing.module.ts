import { CreatePageComponent } from './pages/create/create.component';
import { RegisterPageComponent } from './pages/register/register.component';
import { AuthGuard } from './core/guards/auth.guard';
import { SetupPageComponent } from './pages/setup/setup.component';
import { HomePageComponent } from './pages/home/home.component';
import { LoginPageComponent } from './pages/login/login.component';
import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PlayComponent} from './pages/play/play.component';

const routes: Routes = [
  {path: '', component: HomePageComponent, canActivate:[AuthGuard]},
  {path: 'home', component: HomePageComponent, canActivate:[AuthGuard]},
  {path: 'login', component: LoginPageComponent},
  {path: 'register', component: RegisterPageComponent},
  {path: 'play', component: PlayComponent, canActivate:[AuthGuard]},
  {path: 'setup', component: SetupPageComponent, canActivate:[AuthGuard]},
  {path: 'create', component: CreatePageComponent, canActivate:[AuthGuard]},
  {path: '**', component: HomePageComponent, canActivate:[AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
