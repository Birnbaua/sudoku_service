import { Time } from "@angular/common";
import { Sudoku } from "./Sudoku";
import { User } from "./User";

export interface GameStat{
    currentResult : string
    preview : string
    sudoku : Sudoku
    user : User
    createdAt? : Time
    updatedAt? : Time
    duration : string
    finished : boolean
}