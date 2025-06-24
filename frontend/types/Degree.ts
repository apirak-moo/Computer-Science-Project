import type { Program } from "./Program";

export interface Degree {
    programs?: Program[];
    id?: number;
    nameTh: string;
    nameEn: string;
}