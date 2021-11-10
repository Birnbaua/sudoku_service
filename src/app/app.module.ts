import { SudokuService } from './core/services/sudoku.service';
import { BoardComponent } from './core/components/board/board.component';
import { SidebarComponent } from './core/components/sidebar/sidebar.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    BoardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    SudokuService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
