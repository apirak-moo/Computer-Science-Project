import type { Program } from "./Program";

export interface BranchMajor {
    id?: number;
    program: Program;
    nameTh: string;
    nameEn: string;
    tuitionFees: number;
    language: string;
    image: File | undefined;
    description: string;
    careers: string;
    status: boolean;
}