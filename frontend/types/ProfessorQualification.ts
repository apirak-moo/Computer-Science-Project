import type { Degree } from "./Degree";

export interface ProfessorQualification {
    id?: number;
    university: string;
    major: string;
    yearGraduation: string;
    degree?: Degree | undefined
}