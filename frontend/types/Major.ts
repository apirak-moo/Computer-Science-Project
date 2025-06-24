import type { MajorCourse } from "./MajorCourse";
import type { Program } from "./Program";

export interface Major {
    id?: number;
    program: Program;
    nameTh: string;
    nameEn: string;
    tuitionFees: number;
    language: string;
    courses: MajorCourse[];
    image: File | undefined;
    description: string;
    careers: string;
    status: boolean;
}