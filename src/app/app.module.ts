import { SetupPageComponent } from './pages/setup/setup.component';
import { HomePageComponent } from './pages/home/home.component';
import { LoginPageComponent } from './pages/login/login.component';
import { SudokuRequestService } from './core/services/sudoku.request.service';
import { BoardComponent } from './core/components/board/board.component';
import { SidebarComponent } from './core/components/sidebar/sidebar.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PlayComponent } from './pages/play/play.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    BoardComponent,
    LoginPageComponent,
    HomePageComponent,
    PlayComponent,
    SetupPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    SudokuRequestService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
