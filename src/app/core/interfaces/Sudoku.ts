import { Difficulty } from "./Difficulty";

export interface Sudoku{
    id?: number;
    desc?: string;
    difficulty?: Difficulty;
    unsolved?: string;
    grouping?: string;
    type? : string;
}