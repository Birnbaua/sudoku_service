import { GameStatDataService } from './core/services/gamestat.data.service';
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
import {DigitOnlyDirective} from './core/components/board/digit-only.directive';
import { PicUploaderComponent } from './core/components/profile/pic-uploader/pic-uploader.component';
import { NgxDropzoneModule } from 'ngx-dropzone';
import { Gamestat } from './core/components/gamestat/gamestat.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    Gamestat,
    BoardComponent,
    LoginPageComponent,
    HomePageComponent,
    PlayComponent,
    SetupPageComponent,
    RegisterPageComponent,
    ProfileComponent,
    DigitOnlyDirective,
    PicUploaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDropzoneModule
  ],
  providers: [
    SudokuRequestService,
    SudokuDataService,
    GameStatDataService,
    AuthService,
    AuthGuard,
    UserRequestService,
    GameStatsRequestService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
