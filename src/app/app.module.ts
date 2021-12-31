import { GameStatsRequestService } from './core/services/gamestats.request.service';
import { ProfileComponent } from './core/components/profile/profile.component';
import { UserRequestService } from './core/services/user.request.service';
import { RegisterPageComponent } from './pages/register/register.component';
import { AuthGuard } from './core/guards/auth.guard';
import { SudokuDataService } from './core/services/sudoku.data.service';
import { SetupPageComponent } from './pages/setup/setup.component';
import { HomePageComponent } from './pages/home/home.component';
import { LoginPageComponent } from './pages/login/login.component';
import { SudokuRequestService } from './core/services/sudoku.request.service';
import { BoardComponent } from './core/components/board/board.component';
import { SidebarComponent } from './core/components/sidebar/sidebar.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PlayComponent } from './pages/play/play.component';
import { AuthService } from './core/services/auth.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    BoardComponent,
    LoginPageComponent,
    HomePageComponent,
    PlayComponent,
    SetupPageComponent,
    RegisterPageComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    SudokuRequestService,
    SudokuDataService,
    AuthService,
    AuthGuard,
    UserRequestService,
    GameStatsRequestService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
