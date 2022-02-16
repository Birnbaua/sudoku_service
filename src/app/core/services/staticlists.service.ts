import { Injectable } from "@angular/core"

@Injectable({
    providedIn: 'root'
})
export class StaticListsService{
    sudokuTypes: Array<String> = ["Normal", "Diagonal", "Samurai"]

    getSudokuTypes(){
        return this.sudokuTypes
    }
}