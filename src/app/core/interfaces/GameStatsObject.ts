import { Duration } from './Durration';
import { User } from './User';
import { Sudoku } from './Sudoku';
export interface GameStatsObject{
    sudoku: Sudoku
    user: User
    duration : Duration
    currentResult : string
}