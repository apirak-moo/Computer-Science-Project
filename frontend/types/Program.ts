import type { Degree } from "./Degree";

export interface Program {
    id?: number;
    nameTh: string;
    nameEn: string;
    degree: Degree;
}