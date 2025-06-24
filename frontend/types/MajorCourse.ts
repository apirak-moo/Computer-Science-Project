import type { Major } from "./Major";

export interface MajorCourse {
    id?: number;
    major?: Major;
    code: string;
    year: number;
    sector: number;
    nameTh: string;
    nameEn: string;
    credit: number;
}