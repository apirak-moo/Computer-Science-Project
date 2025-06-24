import type { Branch } from "./Branch";

export interface BranchCourse {
    id?: number;
    branch?: Branch;
    code: string;
    year: number;
    sector: number;
    nameTh: string;
    nameEn: string;
    credit: number;
}